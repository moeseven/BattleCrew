

import java.io.Serializable;
import java.util.LinkedList;


public class Player implements Serializable{
	private Warrior selectedHero;
	//private Quest activeQuest;
	protected LinkedList<Warrior> heroes;
	private LinkedList<Item> inventory;
	protected int inventoryCapacity;
	private LinkedList<Warrior> availableHeroes;
	protected int groupSize;
	private Game game;
	private int gold;
	private boolean cheat=false;
	public Player(Game game) {
		this.game=game;
		heroes=new LinkedList<Warrior>();
		availableHeroes=new LinkedList<Warrior>();
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
		for(int a=0; a<heroes.size();a++) {// prevent equal names
			for(int b=0; b<heroes.size();b++) {
				if(heroes.get(b).getName().equals(hero.getName())) {
					hero.setName(hero.getName()+" I");
				}
			}
		}
		heroes.addFirst(hero);
		hero.setPlayer(this);
		return true;
	}
	public boolean addHero(Warrior hero) {// do not exeed maximum size
		if(heroes.size()<groupSize) {
			for(int a=0; a<heroes.size();a++) {// prevent equal names
				for(int b=0; b<heroes.size();b++) {
					if(heroes.get(b).getName().equals(hero.getName())) {
						hero.setName(hero.getName()+" I");
					}
				}
			}
			heroes.addFirst(hero);
//			hero.setInventory(inventory);
			hero.setPlayer(this);
			selectedHero=hero;
			return true;
		}else {
			return false;
		}
	}
	public void removeHeroFromTavern(Warrior hero) {
		if(availableHeroes.size()>=1&&availableHeroes.contains(hero)) {
			availableHeroes.remove(hero);
			hero.setPlayer(null);
		}
	}
	public void removeDeadHeroesFromRoster() {
		LinkedList<Warrior> deadHeroes=new LinkedList<Warrior>();
		for(int i=0; i<heroes.size();i++) {
			if(heroes.get(i).isDead()) {
				deadHeroes.add(heroes.get(i));
			}
		}
		for(int i=0; i<deadHeroes.size();i++) {
			heroes.remove(deadHeroes.get(i));
		}
	}
	public void removeHero(Warrior hero) {
		if(heroes.size()>=1&&heroes.contains(hero)) {
			heroes.remove(hero);
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
			game.log.addLine(item.getName()+" added to inventory.");
			inventory.add(item);
			success=true;
		}
		return success;
	}
	public Warrior getSelectedHero() {
		return selectedHero;
	}
	public void setSelectedHero(Warrior selectedHero) {
		this.selectedHero = selectedHero;
	}
	public LinkedList<Warrior> getHeroes() {
		return heroes;
	}
	public void setHeroes(LinkedList<Warrior> heroes) {
		this.heroes = heroes;
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
	public LinkedList<Warrior> getAvailableHeroes() {
		return availableHeroes;
	}
	public void setAvailableHeroes(LinkedList<Warrior> availableHeroes) {
		this.availableHeroes = availableHeroes;
	}
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
}
