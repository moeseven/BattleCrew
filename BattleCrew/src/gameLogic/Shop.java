package gameLogic;

import java.io.Serializable;
import java.util.LinkedList;

public class Shop implements Serializable{
	private Game game;
	private int imageNumber=382;
	private Inventory inventory;
	public Shop(Game game) {
		this.game=game;
		inventory = new Inventory();
		
		//one hand meele weapon
		inventory.add(game.itemBuilder.buildItembyName("dagger"));
		inventory.add(game.itemBuilder.buildItembyName("skimtar"));
		inventory.add(game.itemBuilder.buildItembyName("shortsword"));
		inventory.add(game.itemBuilder.buildItembyName("club"));
		inventory.add(game.itemBuilder.buildItembyName("mace"));
		inventory.add(game.itemBuilder.buildItembyName("longsword"));
		//two handed meele weapon
		
		inventory.add(game.itemBuilder.buildItembyName("staff"));
		inventory.add(game.itemBuilder.buildItembyName("pike"));
		inventory.add(game.itemBuilder.buildItembyName("broadsword"));
		inventory.add(game.itemBuilder.buildItembyName("helebard"));
		
		//ranged weapon
		
		inventory.add(game.itemBuilder.buildItembyName("slingshot"));
		inventory.add(game.itemBuilder.buildItembyName("shortbow"));
		inventory.add(game.itemBuilder.buildItembyName("longbow"));
		
		
		//shield
		inventory.add(game.itemBuilder.buildItembyName("buckler"));
		inventory.add(game.itemBuilder.buildItembyName("shield"));
		//armor
		inventory.add(game.itemBuilder.buildItembyName("leatherarmor"));
		inventory.add(game.itemBuilder.buildItembyName("armor"));
		
		inventory.add(game.itemBuilder.buildItembyName("leathercap"));
		inventory.add(game.itemBuilder.buildItembyName("helmet"));
		
		//rings
		inventory.add(game.itemBuilder.buildItembyName("ring"));
		
		//ammunition
		for (int i = 0; i < 11; i++) {
			inventory.add(game.itemBuilder.buildItembyName("arrow"));
		}
		for (int i = 0; i < 11; i++) {
			inventory.add(game.itemBuilder.buildItembyName("stone"));
		}
		
		//potions
		inventory.add(game.itemBuilder.buildItembyName("healing potion"));
		
		
		
	}
	public boolean buy_item(Player player, Item item) {
		if(player.getGold() >= item.getGold_value()) {
			Item item_built = game.itemBuilder.buildItembyName(item.getName());
			if (item.getCategory() == 6) {
				item_built.setImage((int) (item_built.getImage()+Math.random()*6));
				 item_built.add_affix(player.getGame().affix_builder.random_affix());
			}
			if(player.getSmith() != null) {
				if (Math.random() < (player.getSmith().getEnchant_chance()/100.0) && item.getCategory() != 7) {
					item_built.add_affix(player.getGame().affix_builder.random_affix());
				}
			}			
			if (Math.random() < (5/100.0) && item.getCategory() != 7) {
				item_built.add_affix(player.getGame().affix_builder.random_affix());
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
