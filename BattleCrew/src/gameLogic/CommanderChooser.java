package gameLogic;

import java.util.LinkedList;

public class CommanderChooser {
	private Game game;
	public String type_name_string = "type name here";
	private Commander commander;
	private int points; //TODO give points
	LinkedList<Commander_Class> charClasses;
	LinkedList<String> charRaces;
	public static String[] COMMANDER_RACES = {"human","elf","dwarf","halfling","wizard"};
	public CommanderChooser(Game game) throws Exception {
		super();
		this.game=game;
		charClasses=new LinkedList<Commander_Class>();
		charRaces=new LinkedList<String>();
		for (Commander_Class cc : Commander_Class.values()) { 
		     charClasses.add(cc);
		}
		for (int i = 0; i < COMMANDER_RACES.length; i++) {
			charRaces.add(COMMANDER_RACES[i]);
		}
		updateHero();
	}
	public void scrollThroughCharClasses() {
		charClasses.add(charClasses.removeFirst());
	}
	public void scrollThroughCharRaces() {
		charRaces.add(charRaces.removeFirst());
	}
	public void updateHero() throws Exception {
		commander = game.builder.buildCommanderbyName(charRaces.getFirst(),charClasses.getFirst(), game.getPlayer());
		game.getPlayer().setSelectedHero(commander);
	}
	public void createHero(String name) throws Exception {
		if (!name.equals(type_name_string)) {
			commander.setName(name);
		}		
		game.getPlayer().setCommander(commander);	
		game.getPlayer().setGold(commander.wealth);
		//game.getPlayer().appointLeader(commander);
		for(int i=1; i<commander.getGroup_size() ;i++) {
			game.getPlayer().addHero(game.builder.buildUnitbyName(commander.getType(), game.getPlayer()));
		}
		game.getPlayer().addHero(commander);	
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
//classes: warrior, thief, cleric, mage
//races: human, dwarf, elf, halfling
	
}
