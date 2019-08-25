package gameLogic;


import java.io.Serializable;
import java.util.LinkedList;

import builders.AbilityBuilder;
import builders.ItemBuilder;

public class Game implements Serializable {
	private Player player; // change this for multiplayer
	private Player opponent;
	private Battle battle;
	protected int maximumGroupSize=5;
	//public GeneratorRandom generator;
	// public MyImageLoader imageLoader;
	//public CardBuilder cardBuilder;
	public ItemBuilder itemBuilder;
	public AbilityBuilder abilityBuilder;
	//public ItemSpecialBuilder itemSpecialBuilder;
	//public ItemSuffixBuilder itemSuffixBuilder;
	public MyLog log;
	//private LinkedList<Quest> availableQuests;
	private int idleStressRelief = 10;
	private Warrior lastCaster;
	public Game() {
		super();
		player = new Player(this);
		lastCaster= null;
//		cardBuilder = new CardBuilder();
//		itemBuilder = new ItemBuilder(this,"resources/items.properties");
//		itemSpecialBuilder = new ItemSpecialBuilder(this,"resources/itemsSpecial.properties");
//		itemSuffixBuilder = new ItemSuffixBuilder();
		//generator = new GeneratorRandom(this);
		log = new MyLog();		
		// cardBuilder.printMap();
	}
	public void startBattle(Battlefield battlefield, Player attacker, Player defender) {
		battlefield= new Battlefield(10, 10, 15, this);
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
	public Warrior getLastCaster() {
		return lastCaster;
	}
	public void setLastCaster(Warrior lastCaster) {
		this.lastCaster = lastCaster;
	}
	public Battle getBattle() {
		return battle;
	}
	public void setBattlefield(Battle battle) {
		this.battle = battle;
	}
	public Player getOpponent() {
		return opponent;
	}
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	
}
