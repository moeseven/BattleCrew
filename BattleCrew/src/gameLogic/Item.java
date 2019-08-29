package gameLogic;

import java.util.LinkedList;

public class Item {
	private String name;
	private String[] ability_names;
	private LinkedList<Ability> abilities;
	private int gold_value;
	public boolean droppable;	
	private int category;
	private int weight;
	private int armor;
	private int image;
	private Game game;
	public Item(String[] stats,Game game) {
		super();
		this.game= game;
		abilities=new LinkedList<Ability>();
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
		weight= Integer.parseInt(stats[5]);
		armor= Integer.parseInt(stats[6]);
		image= Integer.parseInt(stats[7]);
	}
	public Item() {
		name= "unknown";
	}

	public int getCategory() {
		// TODO Auto-generated method stub
		return category;
	}

	public void mod(Warrior hero) {
		hero.setArmor(hero.getArmor()+armor);
	}
	public void demod(Warrior hero) {
		hero.setArmor(hero.getArmor()-armor);
	}
	public void resetAbilityCooldowns() {
		for (int i = 0; i < abilities.size(); i++) {
			abilities.get(i).refresh();
		}
	}
	//getters and setters
	public int getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public LinkedList<Ability> getAbilities() {
		return abilities;
	}
	public void setAbilities(LinkedList<Ability> abilities) {
		this.abilities = abilities;
	}
	
	

}
