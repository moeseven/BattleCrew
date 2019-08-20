package gameLogic;

import java.util.HashMap;

public class Ability {	
	private int stamina_cost;
	private int damage_target;
	private int heal_target;
	private int stamina_damage;
	private int range;
	private double miss_base;
	private double miss_per_distance;
	private int uses_per_round;
	private boolean self_target_allowed;
	private boolean has_target;
	public Ability(int uses) {
		super();
		this.uses_per_round = uses;
	}
	public Ability(String[] stats) {
		super();
		//generate ability out of table
	}
}
