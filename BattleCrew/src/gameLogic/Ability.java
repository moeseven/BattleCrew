package gameLogic;

import java.util.HashMap;

public class Ability {	
	private String name;
	private int stamina_cost;
	private int damage_target;
	private int heal_target;
	private int stamina_damage;
	private int range;
	private double miss_base;
	private double miss_per_distance;
	private boolean self_target_allowed;
	private boolean has_target;
	//
	private int last_range_check=0;
	public Ability() {
		super();
		name= "unknown";
	}
	public Ability(String[] stats) {
		super();
		//generate ability out of table
		//name,stamina_cost,damage_target,heal_target,stamina_damage,range,miss_base,miss_per_distance,self_target_allowed,has_target
		name= stats[0];
		stamina_cost= Integer.parseInt(stats[1]);
		damage_target= Integer.parseInt(stats[2]);
		heal_target= Integer.parseInt(stats[3]);
		stamina_damage= Integer.parseInt(stats[4]);
		range= Integer.parseInt(stats[5]);
		miss_base= Integer.parseInt(stats[6]);
		miss_per_distance= Integer.parseInt(stats[7]);
		self_target_allowed= Boolean.parseBoolean(stats[8]);
		has_target=Boolean.parseBoolean(stats[9]);
	}
	public boolean use(Warrior origin_warrior, Warrior target_warrior) {
		//TODO
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
}
