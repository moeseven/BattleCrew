import java.util.Collections;
import java.util.LinkedList;

public class Warrior {
	private Player player;
	private LinkedList<Card> offensive_deck,defensive_deck;
	private LinkedList<Card> offensives,defensives;
	private LinkedList<Ability> abilities;
	private Equipment equipment;
	private int health_max;
	private int health;
	private int stamina_max;
	private int stamina;
	//stats
	private int strength; //~maximum stamina use per round	
	private int endurance; //~stamina_max
	private int vitality; //~health_max
	private int dexterity; //~deck size (avg card quality increases with size)
	// offesnive deck: -3,-2,-2,-1,-1,-1,0,0,0,0,1,1,1,1,1 ...
	// defesnive deck: 0,0,0,1,1,2 (does not grow)
	public Warrior(Player player) {
		this.player=player;
		abilities= new LinkedList<Ability>();
		equipment=new Equipment(this);
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

}
