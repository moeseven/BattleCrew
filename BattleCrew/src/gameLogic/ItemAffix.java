package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ItemAffix implements Serializable{
	public int getThorns() {
		return thorns;
	}
	public int getStrength() {
		return strength;
	}
	public int getDexterity() {
		return dexterity;
	}
	public int getVitality() {
		return vitality;
	}
	public int getOffense() {
		return offense;
	}
	public int getDefense() {
		return defense;
	}
	private String name;
	private int category;
	private int weight;
	private int damage;
	private int range;
	private int block;
	private int precision;
	//mods
	private int armor;
	private int offense;
	private int defense;
	private int strength;
	private int dexterity;
	private int vitality;
	private int thorns;
	//
	public ItemAffix(String[] stats,Game game) {
		super();

		//name,abilities,gold_value,droppable,category
		name=stats[0];
		weight= Integer.parseInt(stats[1]);
		armor= Integer.parseInt(stats[2]);
		defense=Integer.parseInt(stats[3]);
		offense=Integer.parseInt(stats[4]);
		damage = Integer.parseInt(stats[5]);
		precision = Integer.parseInt(stats[6]);
		block = Integer.parseInt(stats[7]);
		strength = Integer.parseInt(stats[8]);
		dexterity = Integer.parseInt(stats[9]);
		vitality = Integer.parseInt(stats[10]);
		thorns = Integer.parseInt(stats[11]);
	}
	public ItemAffix() {
		name= "unknown";
	}

	public int getCategory() {
		// TODO Auto-generated method stub
		return category;
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
