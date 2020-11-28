package gameLogic;

import java.io.Serializable;
import java.util.LinkedList;

import gameLogic.spellLibrary.BloodlustSpell;
import gameLogic.spellLibrary.StoneskinSpell;

public class Shop implements Serializable{
	public LinkedList<Spell> getSpells() {
		return spells;
	}
	public void setSpells(LinkedList<Spell> spells) {
		this.spells = spells;
	}
	private Game game;
	private int imageNumber=382;
	private Inventory inventory;
	private LinkedList<Spell> spells;
	public Shop(Game game) {
		this.game=game;
		spells = new LinkedList<Spell>();
		spells.add(new BloodlustSpell());
		spells.add(new StoneskinSpell());
		inventory = new Inventory();
		
		//one hand meele weapon
		inventory.add(game.builder.buildItembyName("dagger"));
		inventory.add(game.builder.buildItembyName("skimtar"));
		inventory.add(game.builder.buildItembyName("shortsword"));
		inventory.add(game.builder.buildItembyName("club"));
		inventory.add(game.builder.buildItembyName("mace"));
		inventory.add(game.builder.buildItembyName("longsword"));
		//two handed meele weapon
		
		inventory.add(game.builder.buildItembyName("staff"));
		inventory.add(game.builder.buildItembyName("pike"));
		inventory.add(game.builder.buildItembyName("broadsword"));
		inventory.add(game.builder.buildItembyName("helebard"));
		
		//ranged weapon
		
		inventory.add(game.builder.buildItembyName("slingshot"));
		inventory.add(game.builder.buildItembyName("shortbow"));
		inventory.add(game.builder.buildItembyName("longbow"));
		
		
		//shield
		inventory.add(game.builder.buildItembyName("buckler"));
		inventory.add(game.builder.buildItembyName("shield"));
		//armor
		inventory.add(game.builder.buildItembyName("leatherarmor"));
		inventory.add(game.builder.buildItembyName("armor"));
		
		inventory.add(game.builder.buildItembyName("leathercap"));
		inventory.add(game.builder.buildItembyName("helmet"));
		
		//rings
		inventory.add(game.builder.buildItembyName("ring"));
		
		//ammunition
		for (int i = 0; i < 11; i++) {
			inventory.add(game.builder.buildItembyName("arrow"));
		}
		for (int i = 0; i < 11; i++) {
			inventory.add(game.builder.buildItembyName("stone"));
		}
		
		//potions
		inventory.add(game.builder.buildItembyName("healing potion"));
		
		
		
	}
	public boolean buy_item(Player player, Item item) {
		if(player.getGold() >= item.getGold_value()) {
			Item item_built = game.builder.buildItembyName(item.getName());
			if (item.getCategory() == 6) {
				item_built.setImage((int) (item_built.getImage()+Math.random()*6));
				 item_built.add_affix(player.getGame().builder.random_affix());
			}
			if(player.getSmith() != null) {
				if (Math.random() < (player.getSmith().getEnchant_chance()/100.0) && item.getCategory() != 7) {
					item_built.add_affix(player.getGame().builder.random_affix());
				}
			}			
			if (Math.random() < (5/100.0) && item.getCategory() != 7) {
				item_built.add_affix(player.getGame().builder.random_affix());
			}
			if(player.getInventory().add(item_built)) {
				player.gainGold(-item.getGold_value());
				
				return true;
			}								
		}
		return false;
	}
	public void sell_item(Player player, Item item) {
		if(item!=null) {
			if(player.getInventory().contains(item)) {
				//sell TODO(store last sold item for rebuy)
				player.getInventory().remove(item);
				player.gainGold((int) (item.getGold_value()/5.0));//sell for one fifth of value
				if(player.getInventory().getInventory_list().size()>0) {
					player.setSelectedItem(player.getInventory().getInventory_list().getFirst().get(0));
				}
			}	
		}
	}
	public int getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
}
