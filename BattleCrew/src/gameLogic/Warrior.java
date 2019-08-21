package gameLogic;
import java.util.Collections;
import java.util.LinkedList;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileUnit;

public class Warrior implements HexTileUnit{
	private Player player;
	private LinkedList<Card> offensive_deck,defensive_deck;
	private LinkedList<Card> offensives,defensives;
	private LinkedList<Ability> abilities;
	private Equipment equipment;
	private int health;
	private int stamina;
	private int walked_tiles_this_round;	
	private int round_stamina;
	private int speed;// number of 1 in this increasing movement endurance drain (example of speed=5) 1,1,1,1,1,2,2,2,2,3,3,3,4,4,5,6,7,8
	//endurance drain per tile moved increases with every tile walked and decreases with speed ...1,1,1,2,2,2,3,3,3,3,4,4,4,4,4,5....
	//stats
	private int strength; //~maximum stamina use per round	
	private int endurance; //~stamina_max=2*endurance
	private int vitality; //~health_max=3*vitality
	private int dexterity; //~deck size (avg card quality increases with size)
	// offesnive deck: -3,-2,-2,-1,-1,-1,0,0,0,0,1,1,1,1,1 ...
	// defesnive deck: 0,0,0,1,1,2 (does not grow)
	public Warrior(Player player) {
		this.player=player;
		abilities= new LinkedList<Ability>();
		equipment=new Equipment(this);
	}
	public void initialize() {
		health=3*vitality;
		stamina=3*endurance;
		walked_tiles_this_round=0;
	}
	public void setUpDecks() {
		setUpDefensiveDeck();
		setUpOffensiveDeck();
	}
	public void setUpOffensiveDeck() {
		//offensive_deck
		offensive_deck= new LinkedList<Card>();		
		for (int i = 0; i <offensives.size(); i++) {
			offensive_deck.add(offensives.get(i));
		}
		// offesnive deck: -3,-2,-2,-1,-1,-1,0,0,0,0,1,1,1,1,1 ...
		int modifier=-3;
		int modifierCount=1;
		int modifierCounter=0;
		for (int i = 0; i < dexterity; i++) {
			for (modifierCounter = 0; modifierCounter < modifierCount; modifierCounter++) {
				offensive_deck.add(new Card(modifier));
			}
			modifier++;modifierCount++;	
		}
		Collections.shuffle(offensive_deck);
	}
	public void setUpDefensiveDeck() {
		//defensive_deck
		defensive_deck= new LinkedList<Card>();
		for (int i = 0; i <defensives.size(); i++) {
			defensive_deck.add(defensives.get(i));
		}
		for (int i = 0; i < 3; i++) {//3*zero
			defensive_deck.add(new Card(0));
		}
		for (int i = 0; i < 2; i++) {//2*one
			defensive_deck.add(new Card(1));
		}
		defensive_deck.add(new Card(2));//1*two
		Collections.shuffle(defensive_deck);
	}
	public void roundBegin() {
		stamina+=2+(endurance/3.0+round_stamina/1.5);
		if(stamina>3*endurance) {
			stamina=3*endurance;
		}
		round_stamina=strength;
		walked_tiles_this_round=0;
	}
	
	//getters and setters
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}

	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public float getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public HexTile getHexTile() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getImageNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isFleeing() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isReadyToMove() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean reachableTile(HexTile arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setTile(HexTile arg0) {
		// TODO Auto-generated method stub
		
	}

}
