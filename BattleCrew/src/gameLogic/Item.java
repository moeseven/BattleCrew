package gameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Item {
	private String name;
	private String[] ability_names;
	private LinkedList<Ability> abilities;
	private int gold_value;
	public boolean droppable;	
	private int category;
	private int weight;
	private boolean attack_ability;
	private int damage;
	private int range;
	private int block;
	private int precision;
	//mods
	private int armor;
	private int offense;
	private int defense;
	//
	private int image;
	private LinkedList<String> description;
	public Item(String[] stats,Game game) {
		super();
		abilities=new LinkedList<Ability>();
		//generate item out of table
		//name,abilities,gold_value,droppable,category
		name=stats[0];
		String[] ability_names = stats[1].split(";");
		for (int i = 0; i < ability_names.length; i++) {
			if (!ability_names[i].equals("")) {
				abilities.add(game.abilityBuilder.buildAbilitybyName(ability_names[i]));
			}			
		}
		gold_value= Integer.parseInt(stats[2]);
		droppable= Boolean.parseBoolean(stats[3]);
		category= Integer.parseInt(stats[4]);
		weight= Integer.parseInt(stats[5]);
		armor= Integer.parseInt(stats[6]);
		image= Integer.parseInt(stats[7]);
		defense=Integer.parseInt(stats[8]);
		offense=Integer.parseInt(stats[9]);
		damage = Integer.parseInt(stats[11]);
		range = Integer.parseInt(stats[12]);
		precision = Integer.parseInt(stats[13]);
		block = Integer.parseInt(stats[14]);
		
		attack_ability=Boolean.parseBoolean(stats[10]);
		if (attack_ability) {
			List<String> subArray = new ArrayList<String>();
			subArray.add(name);
			subArray.addAll(Arrays.asList(stats).subList(11, stats.length));
			String[] ability_stats = subArray.toArray(new String[0]);
//			for (Iterator iterator = subArray.iterator(); iterator.hasNext();) {
//				String string = (String) iterator.next();
//				System.out.println(string);
//			}			
			abilities.add(new Ability(ability_stats));
		}
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
		hero.setDefense(hero.getDefense()+defense);
		hero.setOffense(hero.getOffense()+offense);
	}
	public void demod(Warrior hero) {
		hero.setArmor(hero.getArmor()-armor);
		hero.setDefense(hero.getDefense()-defense);
		hero.setOffense(hero.getOffense()-offense);
	}
	
	public void resetAbilityCooldowns() {
		for (int i = 0; i < abilities.size(); i++) {
			abilities.get(i).refresh();
		}
	}
	public void generateItemDescription() {
		//TODO
		description=new LinkedList<String>();
		description.add("cost: "+gold_value);
		description.add("weight: "+weight);
		if (damage > 0) {
			description.add("damage: "+damage);
		}
		if (range > 0) {
			description.add("range: "+range);
		}
		if (abilities.size()>0) {
			if (abilities.get(0).getDamage_target()>0) {
				description.add("damage: "+damage);
			}
			if (abilities.get(0).getRange()>0) {
				description.add("range: "+abilities.get(0).getRange());			
						}
			if (abilities.get(0).getDexterity_demand()>0) {
				description.add("dexterity demand: "+ abilities.get(0).getDexterity_demand());
			}
		}
		if (armor>0) {
			description.add("armor: +"+armor);
		}
		if(defense!=0) {
			description.add("defense: +"+defense);
		}
		if (offense!=0) {
			description.add("offense: +"+offense);
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
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public LinkedList<String> getDescription() {
		return description;
	}
	public void setDescription(LinkedList<String> description) {
		this.description = description;
	}
	public int getGold_value() {
		return gold_value;
	}
	public void setGold_value(int gold_value) {
		this.gold_value = gold_value;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getDamage() {
		return damage;
	}
	public int getRange() {
		return range;
	}
	public int getArmor() {
		return armor;
	}
	public int getBlock() {
		return block;
	}
	public int getPrecision() {
		return precision;
	}
	
	
	

}
