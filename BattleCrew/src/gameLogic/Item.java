package gameLogic;

import java.util.LinkedList;

public class Item {
	private String name;
	private String[] ability_names;
	private LinkedList<Ability> abilities;
	private int gold_value;
	public boolean droppable;	
	private int category;
	
	private Game game;
	public Item(String[] stats,Game game) {
		super();
		this.game= game;
		//generate item out of table
		//name,abilities,gold_value,droppable,category
		name=stats[0];
		String[] ability_names = stats[1].split(";");
		for (int i = 0; i < ability_names.length; i++) {
			abilities.add(game.abilityBuilder.buildAbilitybyName(ability_names[i]));
		}
		gold_value= Integer.parseInt(stats[2]);
		droppable= Boolean.parseBoolean(stats[3]);
		category= Integer.parseInt(stats[4]);
	}
	public Item() {
		name= "unknown";
	}

	public int getCategory() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void mod(Warrior hero) {
		
	}
	public void demod(Warrior hero) {
	
	}
	public void resetAbilityCooldowns() {
		for (int i = 0; i < abilities.size(); i++) {
			abilities.get(i).setUsed(false);
		}
	}
	//getters and setters
	public int getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	public LinkedList<Ability> getAbilities() {
		return abilities;
	}
	public void setAbilities(LinkedList<Ability> abilities) {
		this.abilities = abilities;
	}
	
	

}
