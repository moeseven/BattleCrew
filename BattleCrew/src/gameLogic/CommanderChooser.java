package gameLogic;

import java.util.LinkedList;

import builders.BattleUnitBuilder;

public class CommanderChooser {
	private Game game;
	private Commander commander;
	private int points; //TODO give points
	LinkedList<Commander_Class> charClasses;
	LinkedList<String> charRaces;
	public CommanderChooser(Game game) {
		super();
		this.game=game;
		charClasses=new LinkedList<Commander_Class>();
		charRaces=new LinkedList<String>();
		for (Commander_Class cc : Commander_Class.values()) { 
		     charClasses.add(cc);
		}
		charRaces.add("human");
		charRaces.add("elf");
		charRaces.add("dwarf");
		updateHero();
	}
	public void scrollThroughCharClasses() {
		charClasses.add(charClasses.removeFirst());
	}
	public void scrollThroughCharRaces() {
		charRaces.add(charRaces.removeFirst());
	}
	public void updateHero() {
		commander = game.unitBuilder.buildCommanderbyName(charRaces.getFirst(),charClasses.getFirst(), game.getPlayer());
		game.getPlayer().setSelectedHero(commander);
	}
	public void createHero(String name) {
		commander.setName(name);
		game.getPlayer().setCommander(commander);		
		for(int i=1; i<commander.getGroup_size() ;i++) {
			game.getPlayer().addHero(game.unitBuilder.buildUnitbyName(commander.getType(), game.getPlayer()));
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
