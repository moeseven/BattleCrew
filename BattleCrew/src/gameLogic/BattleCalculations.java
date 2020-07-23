package gameLogic;

import java.util.ArrayList;


public class BattleCalculations {
	
	public static double MINIMUM_DAMAGE_FACTOR = 0.6;
	public static double MAXIMUM_DAMAGE_FACTOR = 1;
	private static double MEELE_HIT_CHANCE_BASE = 0.5;
	private static double MEELE_HIT_CHANCE_MIN = 0.05;
	private static double WEIGHT_EXHAUSTION_FACTOR = 0.0002;
	private static int WEIGHT_WITHOUT_EQUIPMENT = 10000;
	private static int BASE_EVASION = 20;
	private static double ARMOR_EFFECTIVENESS = 0.8;
	
	
	public static double calc_movement_exhaustion(BattleUnit unit) {
		double exhaustion;
		exhaustion = 0.0 + unit.getTiles_moved_this_round()/unit.getMove_speed();
		if (unit.getTiles_moved_this_round() > unit.getMove_speed()) {
			exhaustion *= 2;
		}			
		return exhaustion*weight_endurance_exhaustion_factor(unit.getEquipment().getTotalWeight(),unit.getEndurance());
	}
	
	public static double calc_attack_exhaustion(BattleUnit unit) {
		double exhaustion = 3;
		int weight = unit.getEquipment().getTotalWeight();
		if (unit.getEquipment().getHand1() != null) {
			weight += 2 * unit.getEquipment().getHand1().getWeight();
		}
		return exhaustion * weight_endurance_exhaustion_factor(weight, unit.getEndurance());		
	}
	
	public static double getting_attacked_exhaustion(BattleUnit attacker_unit, BattleUnit attacked_unit) {
		double exhaustion = 1 *attacker_unit.getStrength()/attacked_unit.getStrength();
		return exhaustion * weight_endurance_exhaustion_factor(attacked_unit.getEquipment().getTotalWeight(), attacked_unit.getEndurance());		
	}
	
	public static double weight_endurance_exhaustion_factor(int weight, int endurance) {
		return WEIGHT_EXHAUSTION_FACTOR*(WEIGHT_WITHOUT_EQUIPMENT+weight)/endurance;
	}
	
	public static boolean calc_attack_ranged_hit(BattleUnit attacker, BattleUnit defender) {
		attacker.ranged_attacks_attempted++; //stats update
		if (Math.random()< calc_attack_ranged_actual_hit_chance(attacker, defender)) {
			attacker.ranged_attacks_landed++;
			return true;
		}
		defender.getPlayer().getGame().log.addLine("Miss!");
		return false;
	}
	
	public static double calc_attack_ranged_actual_hit_chance(BattleUnit attacker, BattleUnit defender) {
		double chance = calc_attack_ranged_base_hit_chance(attacker);
		if (attacker.getEquipment().getHand1() != null) {
			chance -= Math.max(0, attacker.getTile().getDistance(defender.getTile())-(attacker.getEquipment().getHand1().getRange()/3));	
						
		}
		chance += (defender.getSize()-10.0)/100;					
		return chance;
	}
	public static double calc_attack_ranged_base_hit_chance(BattleUnit warrior) {
		double chance = 0;
		if (warrior.getEquipment().getHand1() != null) {
			chance = (warrior.getEquipment().getHand1().getPrecision()+get_fatigue_fear_corrected_accuracy(warrior)-10)/100.0;
		}
		return chance;
	}
	
	
	
	public static boolean calc_attack_meele_hit(BattleUnit attacker, BattleUnit defender) {
		double attack_vs_defense = get_fatigue_fear_corrected_offense_skill(attacker)-get_fatigue_fear_corrected_defense_skill(defender);
		double chance = Math.min(MEELE_HIT_CHANCE_MIN, MEELE_HIT_CHANCE_BASE + attack_vs_defense/100);
		if (Math.random()<chance) {
			attacker.meele_attacks_landed++;
			return true;
		}
		defender.meele_attacks_defended++;
		return false;
	}
	
	public static boolean evade(BattleUnit attacker, BattleUnit defender) {
		int hit_roll = (int) (Math.random()*100.0);
		int evade_chance = (int) (BASE_EVASION+get_weight_corrected_dexterity(defender)-get_weight_corrected_dexterity(attacker));
		if (evade_chance > hit_roll) {
			defender.evaded_attacks++;
			attacker.missed_attacks++;
			return true;
		}
		return false;
	}
	
	
	public static boolean shield_hit(BattleUnit defender, boolean ranged) {
		if (defender.getEquipment().getHand2()!=null) {
			double block_chance = defender.getEquipment().getHand2().getBlock();
			if (ranged) {
				block_chance += 5; // shields have + 5 % effectiveness vs ranged
			}
			if (Math.random() < block_chance/100.0) {
				defender.getPlayer().getGame().log.addLine("Blocked!");
				return true;
				
			}
		}		
		return false;
	}
	
	public static double damage_reduced_by_block(double damage, BattleUnit shield_bearer) {
		double after_block_damage = damage;
		if (shield_bearer.getEquipment().getHand2()!=null) {
			after_block_damage = Math.max(0, damage - shield_bearer.getEquipment().getHand2().getDamage()); //for shileds use damage field for damage block amount
			shield_bearer.damage_blocked+=damage-after_block_damage;
		}
		return after_block_damage;
	}
	
	public static double calc_maximum_damage(BattleUnit warrior) {
		double damage = MAXIMUM_DAMAGE_FACTOR;
		if (warrior.getEquipment().getHand1() != null) {
			damage *= Math.max(warrior.getBase_damage(), warrior.getEquipment().getHand1().getDamage()*warrior.getWeapon_skill()/10);
			if (warrior.getEquipment().getHand1() == warrior.getEquipment().getHand2()) {
				damage += warrior.getStrength()*0.33;
			}
		}else {
			damage *= warrior.getBase_damage();
		}
		damage += warrior.getStrength();
		return damage;
	}
	public static double calc_minimum_damage(BattleUnit warrior) {
		return calc_maximum_damage(warrior)*MINIMUM_DAMAGE_FACTOR;
	}
	
	public static double roll_damage(BattleUnit attacker, BattleUnit defender) {
		//TODO factor in size
		double damage = calc_maximum_damage(attacker);
		return roll_damage(damage,attacker,defender);
	}
	
	private static double roll_damage(double max_damage, BattleUnit attacker, BattleUnit defender) {
		double damage = max_damage*(MINIMUM_DAMAGE_FACTOR+(MAXIMUM_DAMAGE_FACTOR-MINIMUM_DAMAGE_FACTOR)*Math.random()); //60%-100% damage range
		//shield hit
		if (shield_hit(defender, false)) {
			damage = damage_reduced_by_block(damage, defender);
		}
		//absobed damage
		defender.damage_absorbed += damage;
		//head hit?
		if (Math.random()*100 < (attacker.getSize()-defender.getSize()+get_weight_corrected_dexterity(attacker))) {
			//Head hit (use head armor)					
			defender.getPlayer().getGame().log.addLine("hit on the head!");
			if (defender.getEquipment().getHead() != null) {
				damage = calc_damage_reduced_by_armor(damage, defender.getEquipment().getHead().getArmor()+defender.getArmor());
			}
			damage *= 2;			
		}else {
			//body hit (use body armor)
			if (defender.getEquipment().getBody() != null) {
				damage = calc_damage_reduced_by_armor(damage, defender.getEquipment().getBody().getArmor()+defender.getArmor());
			}
		}
		return damage;
	}
	
	public static double roll_ranged_damage(BattleUnit attacker, BattleUnit defender) {
		double damage = calc_amunition_damage(attacker) *(MINIMUM_DAMAGE_FACTOR+(MAXIMUM_DAMAGE_FACTOR-MINIMUM_DAMAGE_FACTOR)*Math.random()); //60%-100% damage range
		//shield hit
		if (shield_hit(defender, true)) {
			damage = damage_reduced_by_block(damage, defender);
		}
		//absobed damage
		defender.damage_absorbed += damage;
		if (Math.random()*100 < get_weight_corrected_dexterity(attacker)) {
			//Head hit				
			defender.getPlayer().getGame().log.addLine("hit on the head!");
			if (defender.getEquipment().getHead() != null) {
				damage = calc_damage_reduced_by_armor(damage, defender.getEquipment().getHead().getArmor()+defender.getArmor());
			}
			damage *= 2;				
		}else {
			//body hit
			if (defender.getEquipment().getBody() != null) {
				damage = calc_damage_reduced_by_armor(damage, defender.getEquipment().getBody().getArmor()+defender.getArmor());
			}
		}
		return damage;
	}
	
	public static double calc_damage_reduced_by_armor(double damage, int armor) {
		double armor_dampered_damage = Math.min(Math.max(damage,armor),armor);
		damage = (damage - armor_dampered_damage) + armor_dampered_damage * (1-ARMOR_EFFECTIVENESS);
		return damage;
	}
	
	public static double calc_amunition_damage(BattleUnit warrior) {
		if (warrior.getEquipment().getAmunition().size()>0) {
			return warrior.getEquipment().getAmunition().get(0).getDamage()*(warrior.getWeapon_skill()/10.0);
		}else {
			return 0;
		}
		
	}
	
	public static int calc_actual_attack_range(BattleUnit warrior) {
		if (warrior.getEquipment().getHand1() == null) {
			return 1;
		}else {
			ArrayList<BattleUnit> adjacent_enemies = warrior.get_adjacent_enemies();
			if (adjacent_enemies.size()>0) {
				return 1;
			}
			if (warrior.getEquipment().getHand1().getAmunitionType().equals("0")) {
				return warrior.getEquipment().getHand1().getRange();
			}else {
				if (amunition_ready(warrior)) {
					return warrior.getEquipment().getHand1().getRange();
				}else {
					return 1;
				}
			}
		}
	}
	public static boolean amunition_ready(BattleUnit warrior) {
		if (warrior.getEquipment().getHand1()!=null && warrior.getEquipment().getAmunition().size()>0) {
			if (warrior.getEquipment().getAmunition().get(0).getName().equals(warrior.getEquipment().getHand1().getAmunitionType())) {
				return true;
			}
		}
		return false;
	}
	public static boolean use_amunition(BattleUnit warrior) {
		if (amunition_ready(warrior)) {
			warrior.getEquipment().getAmunition().remove(0);
			return true;
		}
		return false;
	}
	
	public static void perform_ranged_attack(BattleUnit attacker, BattleUnit defender) {
		if (amunition_ready(attacker)) {
			use_amunition(attacker);
			defender.getPlayer().getGame().log.addLine(attacker.getName()+" takes a shot at "+defender.getName());
			attacker.exhaust(calc_attack_exhaustion(attacker));
			if (calc_attack_ranged_hit(attacker, defender)) {
				if (!evade(attacker, defender)) {
					defender.take_damage(roll_ranged_damage(attacker, defender),attacker);
					
				}
			}
		}
		
	}
	
	public static void perform_meele_attack(BattleUnit attacker, BattleUnit defender) {
		defender.getPlayer().getGame().log.addLine(attacker.getName()+" engages "+defender.getName());
		//stats update
		defender.target_of_a_meele_attack++;
		attacker.meele_attacks_attempted++;
		//
		attacker.exhaust(calc_attack_exhaustion(attacker));
		defender.exhaust(getting_attacked_exhaustion(attacker, defender));
		if (calc_attack_meele_hit(attacker, defender)) {
			defender.getPlayer().getGame().log.addLine(attacker.getName()+" strikes at "+defender.getName());
			if (!evade(attacker, defender)) {
				//thorns
				if (defender.getThorns() > 0) {
					defender.getPlayer().getGame().log.addLine("thorns");
					thorn_damage(attacker, defender);
				}				
				defender.take_damage(roll_damage(attacker, defender),attacker);
			}
		}
	}
	
	/**
	 * heavy equipment reduces dexterity
	 * @param warrior
	 * @return dexterity
	 */
	public static double get_weight_corrected_dexterity(BattleUnit warrior) {
		return warrior.getDexterity()*WEIGHT_WITHOUT_EQUIPMENT/(WEIGHT_WITHOUT_EQUIPMENT+warrior.getEquipment().getTotalWeight());
	}
	
	private static int FATIGUE_EFFECT_THRESHOLD = 80;
	private static double FEAR_EFFECTIVENESS = 0.005;
	
	public static double get_fatigue_fear_corrected_offense_skill(BattleUnit warrior) {
		double ret_val = get_meele_attack_skill(warrior);
		if (warrior.getFatigue()>FATIGUE_EFFECT_THRESHOLD) {
			ret_val *= (1-((warrior.getFatigue()-FATIGUE_EFFECT_THRESHOLD))/(100-FATIGUE_EFFECT_THRESHOLD));
		}
		ret_val *= Math.max(1-(warrior.getFear()*FEAR_EFFECTIVENESS),0);
		return ret_val;
	}
	public static double get_fatigue_fear_corrected_defense_skill(BattleUnit warrior) {
		if (warrior.getFatigue()>FATIGUE_EFFECT_THRESHOLD) {
			return (get_meele_defense_skill(warrior)*(1-((warrior.getFatigue()-FATIGUE_EFFECT_THRESHOLD))/(100-FATIGUE_EFFECT_THRESHOLD)));
		}else {
			return get_meele_defense_skill(warrior);
		}
	}
	public static double get_fatigue_fear_corrected_accuracy(BattleUnit warrior) {
		double ret_val = warrior.getPrecision();
		if (warrior.getFatigue()>FATIGUE_EFFECT_THRESHOLD) {
			ret_val *= (1-((warrior.getFatigue()-FATIGUE_EFFECT_THRESHOLD))/(100-FATIGUE_EFFECT_THRESHOLD));
		}
		ret_val *= Math.max(1-(warrior.getFear()*FEAR_EFFECTIVENESS),0);
		return ret_val;
	}
	
	
	public static int get_meele_attack_skill(BattleUnit warrior) {
		return (int) (warrior.getOffense()*(warrior.getWeapon_skill()/10.0))+warrior.getBase_offense();
	}
	
	public static int get_meele_defense_skill(BattleUnit warrior) {
		return (int) (warrior.getDefense()*(warrior.getWeapon_skill()/10.0))+warrior.getBase_defense();
	}
	
	public static void thorn_damage(BattleUnit attacker, BattleUnit defender) {
		double thorn = defender.getThorns();
		//factor in armor
		thorn = thorn * (1-attacker.getArmor()/2.0);
		attacker.take_damage(thorn, defender);
	}
}
