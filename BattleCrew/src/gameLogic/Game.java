package gameLogic;


import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import builders.AbilityBuilder;
import builders.ItemBuilder;

public class Game implements Serializable {
	private Player player; // change this for multiplayer
	private Player opponent;
	private Battle battle;
	public double image_scale;
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
	public Game(double image_scale) {
		super();
		this.image_scale=image_scale;
		player = new Player(this,false);
		lastCaster= null;
		try {
			abilityBuilder=new AbilityBuilder();
			itemBuilder= new ItemBuilder(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		cardBuilder = new CardBuilder();
//		itemBuilder = new ItemBuilder(this,"resources/items.properties");
//		itemSpecialBuilder = new ItemSpecialBuilder(this,"resources/itemsSpecial.properties");
//		itemSuffixBuilder = new ItemSuffixBuilder();
		//generator = new GeneratorRandom(this);
		log = new MyLog();		
		// cardBuilder.printMap();
	}
	public void startExampleBattle(Player attacker, Player defender) {
		battle= new Battle(this, new Battlefield(20, 8, image_scale, this), attacker, defender);		
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
	public void setBattle(Battle battle) {
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
	
	
}
