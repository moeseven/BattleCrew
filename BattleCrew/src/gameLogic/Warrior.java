package gameLogic;
import java.util.Collections;
import java.util.LinkedList;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileUnit;
import builders.AbilityBuilder;
import javafx.scene.control.skin.TitledPaneSkin;

public class Warrior implements HexTileUnit{
	private static int BASE_HP=10;
	private static int BASE_STAMINA=20;
	private Player player;
	private String name;
	private Deck offensive_deck,defensive_deck;
	private LinkedList<Card> offensives,defensives;
	private LinkedList<Ability> abilities;
	private Ability selected_ability;
	private Ability fist_punch;
	private Equipment equipment;
	private int image_number;
	private int walked_tiles_this_round;	
	private int round_actions;
	private HexTile tile;

	private int level;
	private int skillpoints;
	//attributes
	private int offense; //~deck size offensive (avg card quality increases with size)
	private int defense; //~deck size defensive (avg card quality increases with size)
	private int strength; //  stamina cost penalty vs weight of equipment satminacost_mult= max(1,1+(strength-total_weight)*0.1)
	private int dexterity; //miss chance mitigation vs dexterity demand of ability + //TODO reroll defensive/offensive card vs lower dexterity opponent
	private int endurance; //~stamina_max=3*endurance
	private int vitality; //~health_max=3*vitality
	private int speed; //penalty mitigation for moving more than one tile
	//stats
	
	private int health;
	private double stamina;
	private int armor;
	// offensive deck: 0(shuffle),-3,-2,-2,-1,-1,-1,0,0,0,0,1,1,1,1,1 ...
	// defensive deck: 0(shuffle),1,1,1,2,2,2,2,3,3,3,3,3 ...
	public Warrior(String name,Player player,int level) {
		this.player=player;
		this.level=level;
		this.name=name;
		armor=0;
		abilities= new LinkedList<Ability>();
		equipment=new Equipment(this);
		offensive_deck= new OffensiveDeck();
		defensive_deck= new DefensiveDeck();
		offensives=new LinkedList<Card>();
		defensives=new LinkedList<Card>();
		give_random_starting_stats();
		initialize();
		//weapon use ability
		fist_punch=player.getGame().abilityBuilder.buildAbilitybyName("fistpunch");
	}
	private void give_random_starting_stats() {
		image_number=33;
		//lvl 1 stats:
		offense=defense=strength=dexterity=endurance=vitality=speed=1;
		addRandomStat(3);
		addRandomStat(2);
		addRandomStat(1);
		for (int levels_to_go = level; levels_to_go > 0; levels_to_go--) {
			lvlUp(false,false);
		}
	}
	public void initialize() {
		health=calcMaxHp();
		stamina=calcMaxStamina();
		setUpDecks();
	}
	public void setUpDecks() {
		offensive_deck.setUp();
		defensive_deck.setUp();
	}
	public void battleBegin() {
		setUpDecks();
		initialize();
	}
	public void roundBegin() {
		round_actions=3;
		//reset abilities
		fist_punch.setUsed(false);
		for (int i = 0; i < equipment.getAllEquippedItems().size(); i++) {
			equipment.getAllEquippedItems().get(i).resetAbilityCooldowns();
		}
		for (int i = 0; i < abilities.size(); i++) {
			abilities.get(i).setUsed(false);
		}
		///
		walked_tiles_this_round=0;
		stamina+=3;
		if(stamina>calcMaxStamina()) {
			stamina=calcMaxStamina();
		}
		if (stamina<0) {
			stamina = 0;
		}		
	}
	public void lvlUp(boolean display,boolean manualSkilling) { 		
		if(display) {
			//TODO 
		}
		if (manualSkilling) {
			skillpoints+=1;
			addRandomStat(1);
		}else {
			addRandomStat(2);
		}
		level++;
	}
	public void addRandomStat(int burst) {
			int random=(int) (Math.random()*7);
			switch (random) {
			case 0:
				offense+=burst;
				break;
			case 1:
				defense+=burst;
				break;	
			case 2:
				strength+=burst;
				break;
			case 3:
				dexterity+=burst;
				break;
			case 4:
				endurance+=burst;
				break;	
			case 5:
				vitality+=burst;
				break;
			case 6:
				speed+=burst;
			default:
				vitality+=burst;
				break;
			}
	}
	//battle
	public boolean useMainHand(Battle battle,Tile tile) {
		if (battle.getActiveWarrior()==this) {
			if (equipment.getHand1()!=null) {
				if (equipment.getHand1().getAbilities().get(0)!=null) {
					equipment.getHand1().getAbilities().get(0).attempt(this, tile.getWarrior());
					return true;
				}				
			}else {
				fist_punch.attempt(this,tile.getWarrior());
				return true;
			}
		}
		return false;
	}
	public boolean useAbility(Battle battle, Tile tile) {
		if (battle.getActiveWarrior()==this) { //TODO
			if (selected_ability==null) {
				moveOneTile(tile);
				return true;
			}else {
				if (tile.getWarrior()!=null) {
					selected_ability.attempt(this, tile.getWarrior());
				}
				
			}
		}
		
		return false;
	}
	public boolean isAHit(Ability ability, Warrior target_warrior) {//miss_chance horrible performance
		int deficit=dexterity-ability.getDexterity_demand()-player.getGame().getBattle().getBattleField().getDistance(getHexTile(), getHexTile());		
		LinkedList<Boolean> dice= new LinkedList<Boolean>();
		for (int i = 0; i < 9; i++) {
			dice.add(true);
		}
		dice.add(false);
		if (deficit>0) {
			for (int i = 0; i < deficit; i++) {
				dice.add(true);
			}
		}else {
			for (int i = 0; i < -deficit; i++) {
				dice.add(false);
			}
		}
		Collections.shuffle(dice);
		Boolean hit= dice.getFirst();
		if (!hit) {
			player.getGame().log.addLine("miss!");
		}		
		return hit;
	}
	public void takeDamage(int damage) {
		int after_armor_damage=Math.max(damage-armor,0);		
		this.setHealth(health-(after_armor_damage));
		if (health<=0) {
			health=0;
		}
	}
	//getters calc
	public double getStaminaCostMultiplier() {
		return  Math.max(1,1+(equipment.getTotalWeight()-strength)*0.15)*(1+0.1*walked_tiles_this_round);
	}
	public int calcMaxHp() {
		return vitality+BASE_HP+level;
	}
	public int calcMaxStamina() {
		return endurance+BASE_STAMINA;
	}
	public int offensiveRoll() {
		return offensive_deck.pullCard().getModifier();
	}
	public int defensiveRoll() {
		return defensive_deck.pullCard().getModifier();
	}
	
	////////////////////////////////////////////////
	//Deck
	private class Deck {
		protected LinkedList<Card> cards;
		public Deck() {
			super();
			cards = new LinkedList<Card>();
			
		}
		public Card pullCard() {//puts top card back to bottom and returns it
			Card pulledCard= cards.removeFirst();
			cards.addLast(pulledCard);
			if(pulledCard.isShuffler()) {//shuffle
				shuffle();
			}
			return pulledCard;
		}
		public void shuffle() {
			Collections.shuffle(cards);
		}
		public void setUp() {
			//deck: 0(shuffle),1,1,1,2,2,2,2,3,3,3,3,3 ...
			cards= new LinkedList<Card>();
			cards.add(new Card(0,true));//shuffle card
			int modifier=1;
			int modifierCount=3;
			int modifierCounter=0;
			for (int i = 0; i < defense; i++) {
				for (modifierCounter = 0; modifierCounter < modifierCount; modifierCounter++) {
					cards.add(new Card(modifier,false));
				}
				modifier++;modifierCount++;	
			}
			shuffle();		
		}		
	}
	private class DefensiveDeck extends Deck{
		@Override
		public void setUp() {
			// defensive deck: 0(shuffle),1,1,1,2,2,2,2,3,3,3,3,3 ...
			cards= new LinkedList<Card>();
			for (int i = 0; i <defensives.size(); i++) {
				cards.add(defensives.get(i));
			}
			cards.add(new Card(0,true));//shuffle card
			int modifier=1;
			int modifierCount=3;
			int modifierCounter=0;
			for (int i = 0; i < defense; i++) {
				for (modifierCounter = 0; modifierCounter < modifierCount; modifierCounter++) {
					cards.add(new Card(modifier,false));
				}
				modifier++;modifierCount++;	
			}
			shuffle();		
		}
		
	}
	private class OffensiveDeck extends Deck{
		@Override
		public void setUp() {
			//offensive_deck
			cards= new LinkedList<Card>();		
			for (int i = 0; i <offensives.size(); i++) {
				cards.add(offensives.get(i));
			}
			// offesnive deck: 0(shuffle),-3,-2,-2,-1,-1,-1,0,0,0,0,1,1,1,1,1 ...
			cards.add(new Card(0,true));
			int modifier=-3;
			int modifierCount=1;
			int modifierCounter=0;
			for (int i = 0; i < offense; i++) {
				for (modifierCounter = 0; modifierCounter < modifierCount; modifierCounter++) {
					cards.add(new Card(modifier,false));
				}
				modifier++;modifierCount++;	
			}
			shuffle();
		}		
	}
	public double getMoveStamina_cost() {
		//walking a lot increases cost
		// number of 1 in this increasing movement endurance drain (example of speed=5) 1,1,1,1,1,2,2,2,2,3,3,3,4,4,5,6,7,8
		//endurance drain per tile moved increases with every tile walked and decreases with speed ...1,1,1,2,2,2,3,3,3,3,4,4,4,4,4,5....
		//TODO
		double stamina_cost_movement=2.0+(3.0*walked_tiles_this_round)/(1.0+Math.max(0, 2*speed));
		return stamina_cost_movement;
	}
	public boolean moveOneTile(Tile tile) {
		if(isReadyToMove()&&reachableTile(tile)) {			
			if(payStaminaCost(getMoveStamina_cost())) {
				this.tile.setUnit(null);
				tile.setUnit(this);
				this.tile=tile;
				player.setSelectedTile(tile);
				walked_tiles_this_round++;
				return true;
			}			
		}
		return false;
	}
	public boolean payStaminaCost(double cost) {
		double modified_cost=getModifiedStaminaCost(cost);
		if (modified_cost>stamina) {
			return false;
		}else {
			stamina-=modified_cost;
			return true;
		}
	}
	public double getModifiedStaminaCost(double cost) {
		return cost*getStaminaCostMultiplier();
	}
	public Ability getWeaponAbility() {
		if (equipment.getHand1()!=null) {
			if (equipment.getHand1().getAbilities().get(0)!=null) {
				return equipment.getHand1().getAbilities().get(0);
			}		
		}
		return fist_punch;
	}
	//interface methods
	@Override
	public float getHealth() {
		return health;
	}
	@Override
	public HexTile getHexTile() {
		// TODO Auto-generated method stub
		return tile;
	}
	@Override
	public int getImageNumber() {
		return image_number;
	}
	@Override
	public float getMaxHealth() {
		return calcMaxHp();
	}
	@Override
	public boolean isFleeing() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isReadyToMove() {
		//TODO
		if (stamina>getMoveStamina_cost()) {
			if (player.getGame().getBattle().getActiveWarrior()==this) {
				return true;
			}			
		}
		return false;
	}
	@Override
	public boolean reachableTile(HexTile tile) {
		if (tile.getDistance(this.tile)==1&&tile.getUnit()==null) {
			return true;
		}
		return false;
	}
	@Override
	public void setTile(HexTile tile) {
		this.tile= tile;		
	}
	//////////////////////////////////////////
	//getters and setters
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name=name;
		
	}

	public void setPlayer(Player player) {
		// TODO Auto-generated method stub
		this.player=player;
	}

	public boolean isDead() {
		// TODO Auto-generated method stub
		if (this.getHealth()<=0) {
			return true;
		}
		return false;
	}
	
	public double getStamina() {
		return stamina;
	}
	public void setStamina(double stamina) {
		this.stamina = stamina;
	}
	public int getOffense() {
		return offense;
	}
	public void setOffense(int offense) {
		this.offense = offense;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getDexterity() {
		return dexterity;
	}
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
	public int getVitality() {
		return vitality;
	}
	public void setVitality(int vitality) {
		this.vitality = vitality;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getLevel() {
		return level;
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public LinkedList<Ability> getAbilities() {
		return abilities;
	}
	public void setAbilities(LinkedList<Ability> abilities) {
		this.abilities = abilities;
	}
	public Ability getSelected_ability() {
		return selected_ability;
	}
	public void setSelected_ability(Ability selected_ability) {
		this.selected_ability = selected_ability;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getEndurance() {
		return endurance;
	}
	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	
}
