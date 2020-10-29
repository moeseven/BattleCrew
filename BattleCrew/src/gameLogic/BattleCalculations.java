package gameLogic;

import java.util.ArrayList;


public class BattleCalculations {
	
	public static double MINIMUM_DAMAGE_FACTOR = 0.6;
	public static double MAXIMUM_DAMAGE_FACTOR = 1;
	private static double MEELE_HIT_CHANCE_BASE = 0.5;
	private static double MEELE_HIT_CHANCE_MIN = 0.05;
	private static double WEIGHT_EXHAUSTION_FACTOR = 0.00005;
	private static int WEIGHT_PER_SIZE = 8000;
	private static int BASE_EVASION = 10;
	private static double ARMOR_EFFECTIVENESS = 0.8;
	private static int HARASSMENT_VALUE_DEFENSE = 5;
	private static int FATIGUE_EFFECT_THRESHOLD = 60;
	private static double WEIGHT_DEX_PENALTY_FACTOR = 0.0005;
	private static double STRENGTH_FACTOR_RANGED = .33;
	private static double STRENGTH_FACTOR_ONEHAND = 1;
	private static double STRENGTH_FACTOR_TWOHAND = 1.33;
	
	public static double calc_movement_exhaustion(BattleUnit unit) {
		double exhaustion;
		exhaustion = 0.0 + unit.getTiles_moved_this_round()/unit.getMove_speed();
		if (unit.getTiles_moved_this_round() > unit.getMove_speed()) {
			exhaustion *= 2;
		}			
		return exhaustion*weight_endurance_exhaustion_factor(unit);
	}
	
	public static double calc_attack_exhaustion(BattleUnit unit) {
		double exhaustion = 3;
		return exhaustion * weight_endurance_exhaustion_factor(unit);		
	}
	
	public static double getting_attacked_exhaustion(BattleUnit attacker_unit, BattleUnit attacked_unit) {
		double exhaustion = 1 *get_battle_strength(attacker_unit)/get_battle_strength(attacked_unit);
		return exhaustion * weight_endurance_exhaustion_factor(attacked_unit);		
	}
	
	public static double weight_endurance_exhaustion_factor(BattleUnit unit) {
		return (WEIGHT_EXHAUSTION_FACTOR/unit.getEndurance())*get_effective_weight(unit);
	}
	
	public static boolean calc_attack_ranged_hit(BattleUnit attacker, BattleUnit defender) {
		attacker.ranged_attacks_attempted++; //stats update
		if (Math.random()*100< calc_attack_ranged_actual_hit_chance(attacker, defender)) {
			attacker.ranged_attacks_landed++;
			return true;
		}
		defender.getPlayer().getGame().log.addLine("Miss!");
		return false;
	}
	
	public static double calc_attack_ranged_actual_hit_chance(BattleUnit attacker, BattleUnit defender) {
		double chance = calc_attack_ranged_base_hit_chance(attacker);
		if (attacker.getEquipment().getHand1() != null) {
			chance -= Math.max(0, attacker.getTile().getDistance(defender.getTile())-(attacker.getEquipment().getHand1().getRange()/3.0));							
		}
		chance += (defender.getSize()-10.0)/100;					
		return chance;
	}
	public static double calc_attack_ranged_base_hit_chance(BattleUnit warrior) {
		double chance = 0;
		if (warrior.getEquipment().getHand1() != null) {
			chance = warrior.getEquipment().getHand1().getPrecision()+get_combat_accuracy(warrior)-10;
		}
		return chance;
	}
	
	public static double calc_meele_hit_chance(BattleUnit attacker, BattleUnit defender) {
		double attack_vs_defense = get_combat_offense_skill(attacker)-get_combat_defense_skill(defender);
		double chance = Math.max(MEELE_HIT_CHANCE_MIN, MEELE_HIT_CHANCE_BASE + attack_vs_defense/100.0);
		return chance;
	}
	
	public static boolean calc_attack_meele_hit(BattleUnit attacker, BattleUnit defender) {
		double chance = calc_meele_hit_chance(attacker, defender);
		if (Math.random()<chance) {
			attacker.meele_attacks_landed++;
			return true;
		}
		defender.meele_attacks_defended++;
		return false;
	}
	
	public static boolean evade(BattleUnit attacker, BattleUnit defender) {
		int hit_roll = (int) (Math.random()*100.0);
		int evade_chance = (int) (BASE_EVASION+get_battle_dexterity(defender)-get_battle_dexterity(attacker)+2*(attacker.getSize()-defender.getSize()));
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
	
	public static double damage_reduced_by_shield(double damage, BattleUnit shield_bearer) {
		double after_block_damage = damage;
		if (shield_bearer.getEquipment().getHand2()!=null) {
			after_block_damage = Math.max(0, damage - shield_bearer.getEquipment().getHand2().getArmor()); //for shileds use damage field for damage block amount
			shield_bearer.damage_blocked+=damage-after_block_damage;
		}
		return after_block_damage;
	}
	
	public static double calc_maximum_damage(BattleUnit warrior) {
		double damage = MAXIMUM_DAMAGE_FACTOR;
		if (warrior.getEquipment().getHand1() != null) {
			damage *= Math.max(warrior.getBase_damage(), warrior.getEquipment().getHand1().getDamage()*warrior.getWeapon_skill()/10);
			if (warrior.getEquipment().getHand1() == warrior.getEquipment().getHand2()) {
				damage += get_battle_strength(warrior)*(STRENGTH_FACTOR_TWOHAND-1);
			}
		}		
		damage += get_battle_strength(warrior);
		//size factor
		damage*=(warrior.getSize()/10.0);
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
			damage = damage_reduced_by_shield(damage, defender);
		}
		//absobed damage
		defender.damage_absorbed += damage;
		//head hit?
		if (Math.random()*100 < (attacker.getSize()-defender.getSize()+get_battle_dexterity(attacker)/3+10)) {
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
			damage = damage_reduced_by_shield(damage, defender);
		}
		//absobed damage
		defender.damage_absorbed += damage;
		if (Math.random()*100 < get_combat_accuracy(attacker)) {
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
			return warrior.getEquipment().getAmunition().get(0).getDamage()*(warrior.getWeapon_skill()/10.0)+get_battle_strength(warrior)*STRENGTH_FACTOR_RANGED;
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
			return warrior.getEquipment().getHand1().getRange();
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
	
	public static boolean perform_ranged_attack(BattleUnit attacker, BattleUnit defender) {
		if (amunition_ready(attacker)) {
			//check distance
			if (attacker.get_adjacent_enemies().size()==0) {
				use_amunition(attacker);
				defender.getPlayer().getGame().log.addLine(attacker.getName()+" takes a shot at "+defender.getName());
				attacker.exhaust(calc_attack_exhaustion(attacker));
				if (calc_attack_ranged_hit(attacker, defender)) {
					if (!evade(attacker, defender)) {
						defender.take_damage(roll_ranged_damage(attacker, defender),attacker);						
					}
				}
				return true;
			}					
		}
		return false;
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
	public static double get_battle_dexterity(BattleUnit warrior) {
		double dex = warrior.getDexterity();
		dex *= 1-warrior.getFatigue()/100;//Fatique effect
		return dex;
	}
	public static double get_battle_strength(BattleUnit warrior) {
		double str = warrior.getStrength();
		str *= 1-warrior.getFatigue()/100;//Fatique effect
		return str;
	}
	
	
	public static double get_effective_weight(BattleUnit unit) {
		double body_weight = (WEIGHT_PER_SIZE*unit.getSize());
		double equipment_effective_weight = unit.getEquipment().getTotalWeight();
		//count weight in hands double
		if(unit.getEquipment().getHand1()!=null) {
			equipment_effective_weight += 1.2*unit.getEquipment().getHand1().getWeight();
		}
		if (unit.getEquipment().getHand2()!=unit.getEquipment().getHand1() && unit.getEquipment().getHand2()!= null) {
			equipment_effective_weight += unit.getEquipment().getHand2().getWeight();
		}
		equipment_effective_weight*=3; //worn stuff feels heavier
		return body_weight+equipment_effective_weight;
	}
	
	
	public static double get_combat_offense_skill(BattleUnit warrior) {
		double ret_val = get_meele_attack_skill(warrior)+get_battle_dexterity(warrior);
		return ret_val;
	}
	public static double get_combat_defense_skill(BattleUnit warrior) {
		double defense = Math.max(0, get_meele_defense_skill(warrior)+get_battle_dexterity(warrior)- warrior.getAttacks_taken_this_round()*HARASSMENT_VALUE_DEFENSE);
		return defense;
	}
	public static double get_combat_accuracy(BattleUnit warrior) {
		double ret_val = warrior.getPrecision();
		return ret_val;
	}
	
	public static void realax_one_round(BattleUnit warrior) {
		warrior.relax(1+2*(100-warrior.getFear())/100);
	}
	
	
	public static int get_meele_attack_skill(BattleUnit warrior) {
		return (int) (warrior.getOffense()*(warrior.getWeapon_skill()/10.0)+warrior.getBase_offense());
	}
	
	public static int get_meele_defense_skill(BattleUnit warrior) {
		return (int) (warrior.getDefense()*(warrior.getWeapon_skill()/10.0))+warrior.getBase_defense();
	}
	
	public static void thorn_damage(BattleUnit attacker, BattleUnit defender) {
		double thorn = defender.getThorns();
		//factor in armor
		attacker.take_damage(thorn, defender);
	}
}
