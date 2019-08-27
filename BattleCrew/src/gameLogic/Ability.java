package gameLogic;

import java.util.HashMap;
import java.util.LinkedList;

public class Ability {	
	private String name;
	private boolean is_ready;
	private int image_number;
	private int stamina_cost;
	private int damage_target;
	private int heal_target;
	private int stamina_damage;
	private int range;
	private int dexterity_demand;
	private boolean self_target_allowed;
	private boolean has_target;
	private boolean ground_target;
	private LinkedList<CheckableConditions> conditions;
	//
	private int offensive_roll,defensive_roll;
	private int last_range_check=0;
	public Ability() {
		super();
		name= "unknown";
	}
	public Ability(String[] stats) {
		super();
		//generate ability out of table
		//name,stamina_cost,damage_target,heal_target,stamina_damage,range,dexterity_demand,self_target_allowed,has_target
		name= stats[0];
		stamina_cost= Integer.parseInt(stats[1]);
		damage_target= Integer.parseInt(stats[2]);
		heal_target= Integer.parseInt(stats[3]);
		stamina_damage= Integer.parseInt(stats[4]);
		range= Integer.parseInt(stats[5]);
		dexterity_demand= Integer.parseInt(stats[6]);
		self_target_allowed= Boolean.parseBoolean(stats[7]);
		has_target=Boolean.parseBoolean(stats[8]);
		ground_target=Boolean.parseBoolean(stats[9]);
	}
	public boolean attempt(Warrior origin_warrior, Warrior target_warrior) {
		//TODO
		return true;
	}
	public boolean use(Warrior origin_warrior, Warrior target_warrior) {
		//TODO
		//pay cost
		origin_warrior.setStamina(origin_warrior.getStamina()-(stamina_cost*origin_warrior.getStaminaCostMultiplier()));
		if (damage_target>0&&has_target) {
			offensive_roll=origin_warrior.offensiveRoll();
			defensive_roll=target_warrior.defensiveRoll();
		}
		return true;
	}

	public boolean checkRange(Warrior origin_warrior,Warrior target_warrior) {
		//TODO
		return true;
	}
	public boolean checkStamina(Warrior origin_warrior,Warrior target_warrior) {
		//TODO
		return true;
	}
	public boolean checkLegalTarget(Warrior origin_warrior,Warrior target_warrior) {
		//TODO
		return true;
	}
	//getters and setters
	public int getDexterity_demand() {
		return dexterity_demand;
	}
	public void setDexterity_demand(int dexterity_demand) {
		this.dexterity_demand = dexterity_demand;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStamina_cost() {
		return stamina_cost;
	}
	public void setStamina_cost(int stamina_cost) {
		this.stamina_cost = stamina_cost;
	}
	public int getDamage_target() {
		return damage_target;
	}
	public void setDamage_target(int damage_target) {
		this.damage_target = damage_target;
	}
	public int getHeal_target() {
		return heal_target;
	}
	public void setHeal_target(int heal_target) {
		this.heal_target = heal_target;
	}
	public int getStamina_damage() {
		return stamina_damage;
	}
	public void setStamina_damage(int stamina_damage) {
		this.stamina_damage = stamina_damage;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public boolean isSelf_target_allowed() {
		return self_target_allowed;
	}
	public void setSelf_target_allowed(boolean self_target_allowed) {
		this.self_target_allowed = self_target_allowed;
	}
	public boolean isHas_target() {
		return has_target;
	}
	public void setHas_target(boolean has_target) {
		this.has_target = has_target;
	}
	public int getImage_number() {
		return image_number;
	}
	public void setImage_number(int image_number) {
		this.image_number = image_number;
	}
	
}
