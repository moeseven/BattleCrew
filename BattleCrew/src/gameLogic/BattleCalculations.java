package gameLogic;

public class BattleCalculations {
	
	
	
	public static double calc_movement_exhaustion(BattleUnit unit) {
		double exhaustion;
		exhaustion = 0.0 + unit.getTiles_moved_this_round()/unit.getMove_speed();
		if (unit.getTiles_moved_this_round() > unit.getMove_speed()) {
			exhaustion *= 2;
		}			
		return exhaustion*weight_endurance_exhaustion_factor(unit.getEquipment().getTotalWeight(),unit.getEndurance());
	}
	
	public static double calc_attack_exhaustion(BattleUnit unit) {
		double exhaustion = 3 * unit.getAttacks_used_this_round();
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
		return (20.0+weight)/(4*endurance);
	}
	
	public static boolean calc_attack_ranged_hit(BattleUnit attacker, BattleUnit defender) {
		if (Math.random()< calc_attack_ranged_actual_hit_chance(attacker, defender)) {
			return true;
		}
		defender.getPlayer().getGame().log.addLine("Miss!");
		return false;
	}
	
	public static double calc_attack_ranged_actual_hit_chance(BattleUnit attacker, BattleUnit defender) {
		double chance = 0;
		if (attacker.getEquipment().getHand1() != null) {
			chance = attacker.getEquipment().getHand1().getPrecision()/100.0;
		}
		chance *= attacker.getPrecision()/10.0;
		chance *= defender.getSize()/10.0;
		chance -= attacker.getTile().getDistance(defender.getTile())/200.0;
		return chance;
	}
	
	public static boolean calc_attack_meele_hit(BattleUnit attacker, BattleUnit defender) {
		double chance = get_fatigue_corrected_defense_skill(defender)/(Math.max(1, (get_fatigue_corrected_offense_skill(attacker))+get_fatigue_corrected_defense_skill(defender)));
		if (Math.random()>chance) {
			return true;
		}
		return false;
	}
	
	public static boolean shield_hit(BattleUnit defender, boolean ranged) {
		if (defender.getEquipment().getHand2()!=null) {
			double block_chance = defender.getEquipment().getHand2().getBlock();
			if (ranged) {
				block_chance *= 1.5; // shields have + 50 % effectiveness vs ranged
			}
			if (Math.random() < block_chance/100.0) {
				defender.getPlayer().getGame().log.addLine("Blocked!");
				return true;
				
			}
		}		
		return false;
	}
	
	public static double calc_damage(BattleUnit warrior) {
		double damage = warrior.getBase_damage();
		if (warrior.getEquipment().getHand1() != null) {
			damage = warrior.getEquipment().getHand1().getDamage();
		}
		damage *= warrior.getStrength()/10.0;
		return damage;
	}
	
	public static double roll_damage(BattleUnit attacker, BattleUnit defender) {
		double damage = calc_damage(attacker);
		double reduction = 0;
		if (Math.random() < attacker.getDexterity()/100.0) {
			//Head hit
			damage *= 2;		
			defender.getPlayer().getGame().log.addLine("hit on the head!");
			if (defender.getEquipment().getHead() != null) {
				reduction = defender.getEquipment().getHead().getArmor()/100.0;
			}			
		}else {
			if (defender.getEquipment().getBody() != null) {
				reduction = defender.getEquipment().getBody().getArmor()/100.0;
			}
		}
		damage *=  (1-reduction);
		damage = 10 * damage /defender.getVitality();
		return damage;
	}
	
	public static void perform_ranged_attack(BattleUnit attacker, BattleUnit defender) {
		defender.getPlayer().getGame().log.addLine(attacker.getName()+" takes a shot at "+defender.getName());
		attacker.exhaust(calc_attack_exhaustion(attacker));
		if (calc_attack_ranged_hit(attacker, defender)) {
			if (!shield_hit(defender, true)) {
				defender.take_damage(roll_damage(attacker, defender));
			}
		}
	}
	
	public static void perform_meele_attack(BattleUnit attacker, BattleUnit defender) {
		defender.getPlayer().getGame().log.addLine(attacker.getName()+" engages in meele with "+defender.getName());
		defender.get_attacked_meele(attacker);
		attacker.exhaust(calc_attack_exhaustion(attacker));
		defender.exhaust(getting_attacked_exhaustion(attacker, defender));
		if (calc_attack_meele_hit(attacker, defender)) {
			defender.getPlayer().getGame().log.addLine(attacker.getName()+" strikes at "+defender.getName());
			if (!shield_hit(defender, false)) {			
				defender.take_damage(roll_damage(attacker, defender));
			}
		}
	}
	
	public static double get_fatigue_corrected_offense_skill(BattleUnit warrior) {
		if (warrior.getFatigue()>50) {
			return  (get_meele_attack_skill(warrior)*(1-(150.0-warrior.getFatigue())/100.0));
		}else {
			return get_meele_attack_skill(warrior);
		}
	}
	public static double get_fatigue_corrected_defense_skill(BattleUnit warrior) {
		if (warrior.getFatigue()>40) {
			return (get_meele_defense_skill(warrior)*(1-(140.0-warrior.getFatigue())/100.0));
		}else {
			return get_meele_defense_skill(warrior);
		}
	}
	
	public static int get_meele_attack_skill(BattleUnit warrior) {
		return (int) (warrior.getOffense()*(warrior.getMeele_skill()/10.0));
	}
	
	public static int get_meele_defense_skill(BattleUnit warrior) {
		return (int) (warrior.getDefense()*(warrior.getMeele_skill()/10.0));
	}
}
