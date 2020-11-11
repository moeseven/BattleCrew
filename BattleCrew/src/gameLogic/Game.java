package gameLogic;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import builders.GameObjectBuilder;

import game.Leaderboard.LeaderBoardEntry;
import game.Leaderboard.Leaderboard;
import gameLogic.campaignTiles.CampaignTile;

public class Game implements Serializable {
	public Player getKing_of_the_hill() {
		return king_of_the_hill;
	}
	public void setKing_of_the_hill(Player king_of_the_hill) {
		this.king_of_the_hill = king_of_the_hill;
	}
	public WarriorsReadyForBattleTableEnemy getEnemyTable() {
		return enemyTable;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	private Player player; // change this for multiplayer
	private Player opponent;
	private Player king_of_the_hill;
	private BattleTicked battle;
	private WarriorsReadyForBattleTable prepareTable;
	private WarriorsReadyForBattleTableEnemy enemyTable;
	protected int maximumGroupSize=5;
	private Shop shop;
	public GameObjectBuilder builder;
	private Campaign campaign;
	//public ItemSpecialBuilder itemSpecialBuilder;
	//public ItemSuffixBuilder itemSuffixBuilder;
	public MyLog log;
	//private LinkedList<Quest> availableQuests;
	private int idleStressRelief = 10;
	private BattleUnit lastCaster;
	public enum GameState{
		Menu,
		Leaderboard,
		BattleSummary,
		BattlePrepare,
		Battle,
		City,
		CharacterCreation,
		GameOver
	};
	private GameState state = GameState.City;
	private GameState previous_in_game_state = state;
	public GameState previous_game_state() {
		return previous_in_game_state;
	}
	public GameState get_state() {
		return state;
	}
	
	public boolean set_state(GameState new_state) {
		if (new_state == GameState.GameOver) {
			previous_in_game_state = GameState.GameOver;
		}
		if (state != GameState.Leaderboard && state != GameState.Menu) {
			previous_in_game_state = state;
		}
		
		if (state == GameState.GameOver && new_state != GameState.Menu) {
			return false;
		}else {
			state = new_state;
			return true;
		}
	}
	public Game() throws Exception {
		super();
		log = new MyLog();	
		prepareTable= new WarriorsReadyForBattleTable(9, 3, 1, this);
		enemyTable = new WarriorsReadyForBattleTableEnemy(9, 3, 1, this);
		player = new Player(this,false);	
		//load from file;
		ObjectInputStream ois=null;
		try {
			ois=new ObjectInputStream(new FileInputStream("./saves/king_of_the_hill.dat"));
			king_of_the_hill = (Player) ois.readObject();
			king_of_the_hill.setGame(this);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			try {
				ois.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		lastCaster= null;
		try {
			builder = new GameObjectBuilder(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		campaign = new Campaign(this);
		shop= new Shop(this);
//		cardBuilder = new CardBuilder();
//		itemBuilder = new ItemBuilder(this,"resources/items.properties");
//		itemSpecialBuilder = new ItemSpecialBuilder(this,"resources/itemsSpecial.properties");
//		itemSuffixBuilder = new ItemSuffixBuilder();
		//generator = new GeneratorRandom(this);
			
		// cardBuilder.printMap();
	}
	public void startExampleBattle() throws Exception {
		
		Player defender = new Player(this,true);
		for (int i = 0; i < 10; i++) {
			defender.addHero(builder.buildUnitbyName("giant_rat", defender));	
		}			
		
//		defender.getHeroes().get(1).equip(itemBuilder.buildItembyName("slingshot"));
//		defender.getHeroes().get(0).equip(itemBuilder.buildItembyName("leatherarmor"));
//		defender.getHeroes().get(0).equip(itemBuilder.buildItembyName("shortsword"));		
		enter_battle(defender);
	}
	
	public void game_over() {
		if (state != GameState.GameOver) {
			Leaderboard leaderboard = Leaderboard.loadLeaderboard();
			leaderboard.addLeaderboardEntryInRightOrder(new LeaderBoardEntry(this));
			leaderboard.writeToFile();
			state = GameState.GameOver;
		}
		
	}
	
//	public void enter_city() {
//		player.setAction_points(3);
//		set_state(GameState.City);
//	}
	
	public void enter_battle(Player opponent) {
		set_state(GameState.Battle);
		for (int i = 0; i < opponent.getHeroes().size(); i++) {
			opponent.getHeroes().get(i).setBattle_participant(true);
			//salary
			
		}
		battle= new BattleTicked(this, new Battlefield(38, 19, 1, this), player, opponent);		
		battle.start();
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
