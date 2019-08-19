package gameLogic;


import java.io.Serializable;
import java.util.LinkedList;

public class Game implements Serializable {
	private Player player; // change this for multiplayer
	protected int maximumGroupSize=5;
	//public GeneratorRandom generator;
	// public MyImageLoader imageLoader;
	//public CardBuilder cardBuilder;
	//public ItemBuilder itemBuilder;
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

}
