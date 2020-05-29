package gameLogic;


import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import builders.AbilityBuilder;
import builders.BattleUnitBuilder;
import builders.ItemBuilder;
import builders.NameGenerator;
import game.Leaderboard.LeaderBoardEntry;
import game.Leaderboard.Leaderboard;

public class Game implements Serializable {
	public boolean isGame_over() {
		return game_over;
	}
	private Player player; // change this for multiplayer
	private Player opponent;
	private BattleTicked battle;
	private WarriorsReadyForBattleTable prepareTable;
	public double image_scale;
	protected int maximumGroupSize=5;
	private Shop shop;
	public ItemBuilder itemBuilder;
	public AbilityBuilder abilityBuilder;
	public BattleUnitBuilder unitBuilder;
	public NameGenerator name_generator;
	//public ItemSpecialBuilder itemSpecialBuilder;
	//public ItemSuffixBuilder itemSuffixBuilder;
	public MyLog log;
	//private LinkedList<Quest> availableQuests;
	private int idleStressRelief = 10;
	private BattleUnit lastCaster;
	private boolean game_over = false; //TODO
	public Game(double image_scale) {
		super();
		log = new MyLog();	
		this.image_scale=image_scale;
		prepareTable= new WarriorsReadyForBattleTable(7, 2, 2, this);
		player = new Player(this,false);
		lastCaster= null;
		try {
			abilityBuilder=new AbilityBuilder();
			itemBuilder= new ItemBuilder(this);
			unitBuilder= new BattleUnitBuilder(this);
			name_generator = new NameGenerator(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shop= new Shop(this);
//		cardBuilder = new CardBuilder();
//		itemBuilder = new ItemBuilder(this,"resources/items.properties");
//		itemSpecialBuilder = new ItemSpecialBuilder(this,"resources/itemsSpecial.properties");
//		itemSuffixBuilder = new ItemSuffixBuilder();
		//generator = new GeneratorRandom(this);
			
		// cardBuilder.printMap();
	}
	public void startExampleBattle() {
		
		Player defender = new Player(this,true);
		for (int i = 0; i < 15; i++) {
			defender.addHero(unitBuilder.buildUnitbyName("giant_rat", defender));	
		}			
		
		defender.getHeroes().get(1).equip(itemBuilder.buildItembyName("slingshot"));
		defender.getHeroes().get(0).equip(itemBuilder.buildItembyName("leatherarmor"));
		defender.getHeroes().get(0).equip(itemBuilder.buildItembyName("shortsword"));
		for (int i = 0; i < defender.getHeroes().size(); i++) {
			defender.getHeroes().get(i).setBattle_participant(true);
		}
		//battle= new Battle(this, new Battlefield(20, 8, 2, this), player, defender);
		battle= new BattleTicked(this, new Battlefield(38, 19, image_scale, this), player, defender);		
		battle.start();
	}
	
	public void game_over() {
		// TODO Game ends here
		Leaderboard leaderboard = Leaderboard.loadLeaderboard();
		leaderboard.addLeaderboardEntryInRightOrder(new LeaderBoardEntry(this));
		leaderboard.writeToFile();
		game_over=true;
	}
	
	
	// getters and setters

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getMaximumGroupSize() {
		return maximumGroupSize;
	}
	public BattleUnit getLastCaster() {
		return lastCaster;
	}
	public void setLastCaster(BattleUnit lastCaster) {
		this.lastCaster = lastCaster;
	}
	public BattleTicked getBattle() {
		return battle;
	}
	public void setBattle(BattleTicked battle) {
		this.battle = battle;
	}
	public Player getOpponent() {
		return opponent;
	}
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	public double getImage_scale() {
		return image_scale;
	}
	public void setImage_scale(double image_scale) {
		this.image_scale = image_scale;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public WarriorsReadyForBattleTable getPrepareTable() {
		return prepareTable;
	}
	public void setPrepareTable(WarriorsReadyForBattleTable prepareTable) {
		this.prepareTable = prepareTable;
	}
	
	
	
}
