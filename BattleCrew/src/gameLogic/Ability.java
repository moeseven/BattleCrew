package gameLogic;

import java.util.HashMap;
import java.util.LinkedList;

public class Ability {	
	private String name;
	private int stamina_cost;
	private int damage_target;
	private int heal_target;
	private int stamina_damage;
	private int range;
	private int dexterity_demand;
	private boolean self_target_allowed;
	private boolean has_target;
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
	
}
