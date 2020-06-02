package gameLogic;


import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileUnit;


public class Player implements HexTilePlayer,Serializable{

	public int getExperience_reward() {
		return experience_reward;
	}
	public void setExperience_reward(int experience_reward) {
		this.experience_reward = experience_reward;
	}
	public int getGold_reward() {
		return gold_reward;
	}
	public void setGold_reward(int gold_reward) {
		this.gold_reward = gold_reward;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getAction_points() {
		return action_points;
	}
	public void setAction_points(int action_points) {
		this.action_points = action_points;
	}
	public boolean pay_action_points(int p) {
		if (action_points >= p) {
			action_points--;
			return true;
		}else {
			return false;
		}
	}
	public int getScore() {
		return score;
	}
	public void earn_score(int score) {
		this.score += score;
	}
	private BattleUnit selectedWarrior;
	private HexTile selectedTile;
	private Item selectedItem;
	private boolean AI;
	//private LinkedList<Warrior> availableWarriors;
	protected LinkedList<BattleUnit> warriors;
	private Inventory inventory;
	private Commander commander;
	private int action_points=0;
	private Game game;
	private boolean cheat=false;
	private int score = 0;
	private int experience_reward;
	private int gold_reward;
	public Player(Game game, Boolean AI) {
		this.game=game;
		this.AI=AI;
		warriors=new LinkedList<BattleUnit>();
		inventory=new Inventory();
		
	}
	public void gainGold(int gold) {
		commander.gain_gold(gold);		
	}
	public boolean addSummon(BattleUnit hero) {
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
	public boolean addHero(BattleUnit hero) {// do not exeed maximum size
		if(warriors.size()<getGroupSize()) {
			for(int a=0; a<warriors.size();a++) {// prevent equal names
				for(int b=0; b<warriors.size();b++) {
					if(warriors.get(b).getName().equals(hero.getName())) {
						hero.setName(hero.getName()+"I");
					}
				}
			}
			warriors.addFirst(hero);
			hero.setPlayer(this);
			selectedWarrior=hero;
			return true;
		}else {
			return false;
		}
	}
	
	public void recover_warriors() {
		for (Iterator iterator = warriors.iterator(); iterator.hasNext();) {
			BattleUnit battleUnit = (BattleUnit) iterator.next();
			battleUnit.recover();
		}
	}
//	public void removeHeroFromTavern(Warrior hero) {
//		if(availableWarriors.size()>=1&&availableWarriors.contains(hero)) {
//			availableWarriors.remove(hero);
//			hero.setPlayer(null);
//		}
//	}
//	public void removeDeadHeroesFromRoster() {
//		LinkedList<BattleUnit> deadHeroes=new LinkedList<BattleUnit>();
//		for(int i=0; i<warriors.size();i++) {
//			if(warriors.get(i).is_unable_to_fight()) {
//				deadHeroes.add(warriors.get(i));
//			}
//		}
//		for(int i=0; i<deadHeroes.size();i++) {
//			warriors.remove(deadHeroes.get(i));
//		}
//	}
	public void removeHero(BattleUnit hero) {
		if(warriors.size()>=1&&warriors.contains(hero)) {
			warriors.remove(hero);
			//hero.setPlayer(null);
//			hero.setInventory(new LinkedList<Item>());
		}
	}
//	public void addMultipleItemsToInventory(LinkedList<Item> items) {
//		//remove items form source and add to player inventory if there is space
//		LinkedList<Item> itemsTaken=new LinkedList<Item>();
//		for(int a=0;a<items.size();a++) {
//			addItemtoInventory(items.get(a));	
//			itemsTaken.add(items.get(a));
//		}
//		for(int a=0;a<itemsTaken.size();a++) {
//			items.remove(itemsTaken.get(a));
//		}
//	}
	//////////////////HEXTILEPLAYER
	@Override
	public HexTile getSelectedTile() {
		// TODO Auto-generated method stub
		return selectedTile;
	}
	@Override
	public BattleUnit getSelectedUnit() {
		// TODO Auto-generated method stub
		return selectedWarrior;
	}
	@Override
	public void setSelectedUnit(HexTileUnit unit) {
		// TODO Auto-generated method stub
		selectedWarrior=(BattleUnit) unit;
	}	
	@Override
	public void setSelectedTile(HexTile tile) {
		// TODO Auto-generated method stub
		this.selectedTile=tile;
	}
	///////////////////////////////	

	public void setSelectedHero(BattleUnit selectedHero) {
		this.selectedWarrior = selectedHero;
	}
	public LinkedList<BattleUnit> getHeroes() {
		return warriors;
	}
	public void setHeroes(LinkedList<BattleUnit> heroes) {
		this.warriors = heroes;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public int getGold() {
		return commander.getWealth();
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
		if (commander == null) {
			return 20;
		}else {
			return commander.getGroup_size();
		}
		
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
	
	public Commander getCommander() {
		return commander;
	}
	public void setCommander(Commander commander) {
		this.commander = commander;
	}
	
	
}
