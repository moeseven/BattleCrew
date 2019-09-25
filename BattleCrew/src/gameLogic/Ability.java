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
	private boolean friendly;
	private LinkedList<CheckableConditions> conditions;
	private boolean used;
	private boolean after_roll_status;
	private boolean missed;
	//
	private Roll offensive_roll,defensive_roll;
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
		friendly=Boolean.parseBoolean(stats[10]);
	}
	public boolean attempt(Warrior origin_warrior, Warrior target_warrior) {
		//TODO
		if (checkStamina(origin_warrior)&&!used) {
			if (checkRange(origin_warrior, target_warrior)) {
				if (checkLegalTarget(origin_warrior, target_warrior)) {
					//log
					origin_warrior.getPlayer().getGame().log.addLine(origin_warrior.getName()+" uses "+name+" on "+target_warrior.getName());
					use(origin_warrior, target_warrior);
				}
			}
		}
		return true;
	}
	public boolean use(Warrior origin_warrior, Warrior target_warrior) {
		//TODO
		
		origin_warrior.setActions_this_round(origin_warrior.getActions_this_round()+1);//increase stamina cost of further actions this turn
		//pay cost
		payStaminaCost(origin_warrior);
		used=true;
		after_roll_status=true;
		missed=!origin_warrior.isAHit(this, target_warrior);
		if (damage_target>0&&has_target&&!missed) {
			offensive_roll=new Roll(origin_warrior, origin_warrior.offensiveRoll());
			defensive_roll=new Roll(target_warrior, target_warrior.defensiveRoll());
		}
		
		return true;
	}
	public boolean applyAbilityAfterRoll() {
		if (damage_target>0&&has_target&&!missed) {
			defensive_roll.warrior.takeDamage(damage_target+offensive_roll.roll_value-defensive_roll.roll_value);
		}
		after_roll_status=false;
		return true;
	}

	public boolean checkRange(Warrior origin_warrior,Warrior target_warrior) {
		if (origin_warrior.getHexTile().getDistance(target_warrior.getHexTile())<=range) {
			return true;
		}
		if (origin_warrior.getPlayer()==origin_warrior.getPlayer().getGame().getPlayer()) {
			origin_warrior.getPlayer().getGame().log.addLine("target is too far away!");
		}
		
		return false;
	}
	public boolean checkStamina(Warrior origin_warrior) {
		if(origin_warrior.getStamina()>=origin_warrior.getModifiedStaminaCost(stamina_cost)) {
			return true;
		}
		if (origin_warrior.getPlayer()==origin_warrior.getPlayer().getGame().getPlayer()) {
			origin_warrior.getPlayer().getGame().log.addLine("not enough stamina!");
		}
		
		return false;
	}
	public boolean checkLegalTarget(Warrior origin_warrior,Warrior target_warrior) {
		//TODO
		if (target_warrior.isDead()||origin_warrior.isDead()) {
			return false;
		}
		if (origin_warrior==target_warrior) {
			if (!self_target_allowed) {	
				if (origin_warrior.getPlayer()==origin_warrior.getPlayer().getGame().getPlayer()) {
					origin_warrior.getPlayer().getGame().log.addLine("can not target self!");
				}
				
				return false;
			}
		}
		if (origin_warrior.getPlayer()==target_warrior.getPlayer()) {
			if (!friendly) {
				if (origin_warrior.getPlayer()==origin_warrior.getPlayer().getGame().getPlayer()) {
					origin_warrior.getPlayer().getGame().log.addLine("can not target ally!");
				}
				
				return false;
			}
		}		
		return true;
	}
	public void payStaminaCost(Warrior origin_warrior) {
		origin_warrior.setStamina(origin_warrior.getStamina()-(stamina_cost*origin_warrior.getStaminaCostMultiplier()));
	}
	public void refresh() {
		used=false;
		missed=false;
		after_roll_status=false;
		offensive_roll=null;
		defensive_roll=null;
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
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	public boolean isAfter_roll_status() {
		return after_roll_status;
	}
	public void setAfter_roll_status(boolean after_roll_status) {
		this.after_roll_status = after_roll_status;
	}

	public Roll getOffensive_roll() {
		return offensive_roll;
	}
	public Roll getDefensive_roll() {
		return defensive_roll;
	}

	public class Roll {
		public int roll_value;
		public Warrior warrior;
		public boolean rerolled;
		public Roll(Warrior warrior, int roll_value) {
			this.roll_value=roll_value;
			this.warrior=warrior;
			rerolled=false;
		}
		public void reroll() {
			roll_value=warrior.offensiveRoll();
		}
	}
	public void tryOffesniveReroll() {
		// TODO Auto-generated method stub
		if (offensive_roll.warrior.getDexterity()>defensive_roll.warrior.getDexterity()&&!offensive_roll.rerolled) {
			offensive_roll.reroll();
			offensive_roll.rerolled=true;
		} else {

		}
	}
	public boolean isMissed() {
		return missed;
	}
	public void setMissed(boolean missed) {
		this.missed = missed;
	}

	
	
}

