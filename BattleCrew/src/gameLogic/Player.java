package gameLogic;


import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileUnit;


public class Player implements HexTilePlayer,Serializable{


	public void setGold(int gold) {
		this.gold = gold;
	}

	public void prevent_double_appoint(BattleUnit warrior) {
		if(healer == warrior) {
			healer = null;
		}
		if(smith == warrior) {
			smith = null;
		}
		if(treasurer == warrior) {
			treasurer = null;
		}
		if(leader == warrior) {
			leader = null;
		}
		if(champion == warrior) {
			champion = null;
		}
		if(recruiter == warrior) {
			recruiter = null;
		}
	}
	
	public BattleUnit getHealer() {
		if(getHeroes().contains(healer)) {
			return healer;
		}
		return null;
	}
	public void appointHealer(BattleUnit healer) {
		prevent_double_appoint(healer);
		this.healer = healer;
	}
	public BattleUnit getSmith() {
		if(getHeroes().contains(smith)) {
			return smith;
		}
		return null;
	}
	public void appointSmith(BattleUnit smith) {
		prevent_double_appoint(smith);
		this.smith = smith;
	}
	public BattleUnit getTreasurer() {
		if(getHeroes().contains(treasurer)) {
			return treasurer;
		}
		return null;
	}
	public void appointTreasurer(BattleUnit treasurer) {
		prevent_double_appoint(treasurer);
		this.treasurer = treasurer;
	}
	public BattleUnit getChampion() {
		if(getHeroes().contains(champion)) {
			return champion;
		}
		return null;
	}
	public void appointChampion(BattleUnit champion) {
		prevent_double_appoint(champion);
		this.champion = champion;
	}
	public BattleUnit getLeader() {
		if(getHeroes().contains(leader)) {
			return leader;
		}
		return null;
	}
	public void appointLeader(BattleUnit leader) {
		prevent_double_appoint(leader);
		this.leader = leader;
	}
	public BattleUnit getRecruiter() {
		if(getHeroes().contains(recruiter)) {
			return recruiter;
		}
		return null;
	}	
	public void appointRecruiter(BattleUnit recruiter) {
		prevent_double_appoint(recruiter);
		this.recruiter = recruiter;
	}
	//
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
//	public int getAction_points() {
//		return action_points;
//	}
//	public void setAction_points(int action_points) {
//		this.action_points = action_points;
//	}
	public int getGold() {
		return gold;
	}
	public boolean pay_gold(int p) {
		if (gold >= p) {
			gainGold(-p);
			return true;
		}else {
			return false;
		}
	}
	public int getScore() {
		return score;
	}
	public void earn_score(int score) {
		if (commander != null) {
			score =  score * commander.getScore_gain()/100;
		}
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
	// special professions
	private BattleUnit healer;
	private BattleUnit smith;
	private BattleUnit treasurer;
	private BattleUnit champion;
	private BattleUnit leader;
	private BattleUnit recruiter;
	//
	private Game game;
	private boolean cheat=false;
	private int score = 0;
	private int gold = 0;
	private int experience_reward;
	private int gold_reward;
	public Player(Game game, Boolean AI) {
		this.game=game;
		this.AI=AI;
		warriors=new LinkedList<BattleUnit>();
		inventory=new Inventory();
		
	}
	public void gainGold(int g) {	
		if(g>0) {
			if (treasurer != null) {
				g= (int) (g*(1+treasurer.gold_bonus/100.0));
			}	
			getGame().log.addLine("gained "+g+" gold.");			
		}else {
			getGame().log.addLine("lost "+(-g)+" gold.");
		}
		gold += g;
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
