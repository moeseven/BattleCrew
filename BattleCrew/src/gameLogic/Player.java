package gameLogic;


import java.io.Serializable;
import java.util.LinkedList;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileUnit;


public class Player implements HexTilePlayer,Serializable{
	private Warrior selectedWarrior;
	private HexTile selectedTile;
	private Item selectedItem;
	private boolean AI;
	//private LinkedList<Warrior> availableWarriors;
	protected LinkedList<Warrior> warriors;
	private LinkedList<Item> inventory;
	protected int inventoryCapacity;
	
	protected int groupSize;
	private Game game;
	private int gold;
	private boolean cheat=false;
	public Player(Game game, Boolean AI) {
		this.game=game;
		this.AI=AI;
		warriors=new LinkedList<Warrior>();
//		availableWarriors=new LinkedList<Warrior>();
		inventoryCapacity=100;
		inventory=new LinkedList<Item>();
		gold=100;
		groupSize=3;
	}
	public void gainGold(int g) {
		gold+=g;
		if(g>=0) {
			if(g!=0) {
				game.log.addLine("gained "+g+" gold.");
			}			
		}else {
			game.log.addLine("lost "+(-g)+" gold.");
		}
		
	}
	public boolean addSummon(Warrior hero) {
		for(int a=0; a<warriors.size();a++) {// prevent equal names
			for(int b=0; b<warriors.size();b++) {
				if(warriors.get(b).getName().equals(hero.getName())) {
					hero.setName(hero.getName()+" I");
				}
			}
		}
		warriors.addFirst(hero);
		hero.setPlayer(this);
		return true;
	}
	public boolean addHero(Warrior hero) {// do not exeed maximum size
		if(warriors.size()<groupSize) {
			for(int a=0; a<warriors.size();a++) {// prevent equal names
				for(int b=0; b<warriors.size();b++) {
					if(warriors.get(b).getName().equals(hero.getName())) {
						hero.setName(hero.getName()+" I");
					}
				}
			}
			warriors.addFirst(hero);
//			hero.setInventory(inventory);
			hero.setPlayer(this);
			selectedWarrior=hero;
			return true;
		}else {
			return false;
		}
	}
//	public void removeHeroFromTavern(Warrior hero) {
//		if(availableWarriors.size()>=1&&availableWarriors.contains(hero)) {
//			availableWarriors.remove(hero);
//			hero.setPlayer(null);
//		}
//	}
	public void removeDeadHeroesFromRoster() {
		LinkedList<Warrior> deadHeroes=new LinkedList<Warrior>();
		for(int i=0; i<warriors.size();i++) {
			if(warriors.get(i).isDead()) {
				deadHeroes.add(warriors.get(i));
			}
		}
		for(int i=0; i<deadHeroes.size();i++) {
			warriors.remove(deadHeroes.get(i));
		}
	}
	public void removeHero(Warrior hero) {
		if(warriors.size()>=1&&warriors.contains(hero)) {
			warriors.remove(hero);
			//hero.setPlayer(null);
//			hero.setInventory(new LinkedList<Item>());
		}
	}
	public void addMultipleItemsToInventory(LinkedList<Item> items) {
		//remove items form source and add to player inventory if there is space
		LinkedList<Item> itemsTaken=new LinkedList<Item>();
		for(int a=0;a<items.size();a++) {
			addItemtoInventory(items.get(a));	
			itemsTaken.add(items.get(a));
		}
		for(int a=0;a<itemsTaken.size();a++) {
			items.remove(itemsTaken.get(a));
		}
	}
	public boolean addItemtoInventory(Item item) {
		boolean success;
		int totalWeight=item.getWeight();
		for(int i=0; i<inventory.size();i++) {
			totalWeight+=inventory.get(i).getWeight();
		}
		if(totalWeight>inventoryCapacity) {
			//TODO
			success=false;
		}else {
			game.log.addLine(item.getName()+" added to the arsenal.");
			inventory.add(item);
			success=true;
		}
		return success;
	}
	//////////////////HEXTILEPLAYER
	@Override
	public HexTile getSelectedTile() {
		// TODO Auto-generated method stub
		return selectedTile;
	}
	@Override
	public Warrior getSelectedUnit() {
		// TODO Auto-generated method stub
		return selectedWarrior;
	}
	@Override
	public void setSelectedUnit(HexTileUnit unit) {
		// TODO Auto-generated method stub
		selectedWarrior=(Warrior) unit;
	}	
	@Override
	public void setSelectedTile(HexTile tile) {
		// TODO Auto-generated method stub
		this.selectedTile=tile;
	}
	///////////////////////////////	

	public void setSelectedHero(Warrior selectedHero) {
		this.selectedWarrior = selectedHero;
	}
	public LinkedList<Warrior> getHeroes() {
		return warriors;
	}
	public void setHeroes(LinkedList<Warrior> heroes) {
		this.warriors = heroes;
	}
	public LinkedList<Item> getInventory() {
		return inventory;
	}
	public void setInventory(LinkedList<Item> inventory) {
		this.inventory = inventory;
	}
	public int getGold() {
		return gold;
	}

	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
//	public LinkedList<Warrior> getAvailableHeroes() {
//		return availableWarriors;
//	}
//	public void setAvailableHeroes(LinkedList<Warrior> availableHeroes) {
//		this.availableWarriors = availableHeroes;
//	}
	public int getGroupSize() {
		return groupSize;
	}
	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}
	public boolean hasCheat() {
		// TODO Auto-generated method stub
		return cheat;
	}
	public void setCheat(boolean c) {
		cheat=c;
	}
	public boolean isAI() {
		// TODO Auto-generated method stub
		return AI;
	}
	public Item getSelectedItem() {
		return selectedItem;
	}
	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}



	
}
