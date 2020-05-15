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
		return false;
	}
	
	public static double calc_attack_ranged_actual_hit_chance(BattleUnit attacker, BattleUnit defender) {
		double chance = 0;
		if (attacker.getEquipment().getHand1() != null) {
			chance = attacker.getEquipment().getHand1().getPrecision()/100.0;
		}
		chance *= attacker.getPrecision()/10;
		chance *= defender.getSize()/10;
		chance -= attacker.getTile().getDistance(defender.getTile())/200.0;
		return chance;
	}
	
	public static boolean calc_attack_meele_hit(BattleUnit attacker, BattleUnit defender) {
		if (Math.random()>defender.getDefense()/(attacker.getOffense()+defender.getDefense())) {
			return true;
		}
		return false;
	}
	
	public static boolean shield_hit(BattleUnit defender, boolean ranged) {
		double block_chance = defender.getEquipment().getHand2().getBlock();
		if (ranged) {
			block_chance *= 1.5; // shields have + 50 % effectiveness vs ranged
		}
		if (Math.random() < block_chance/100.0) {
			return true;
		}
		return false;
	}
	
	public static double calc_damage(BattleUnit attacker, BattleUnit defender) {
		double damage = attacker.getBase_damage();
		if (attacker.getEquipment().getHand1() != null) {
			damage = attacker.getEquipment().getHand1().getDamage();
		}
		damage *= attacker.getStrength()/10;
		double reduction = 0;
		if (Math.random() < attacker.getDexterity()/100.0) {
			//Head hit
			damage *= 2;			
			if (defender.getEquipment().getHead() != null) {
				reduction = defender.getEquipment().getHead().getArmor()/100.0;
			}			
		}else {
			if (defender.getEquipment().getBody() != null) {
				reduction = defender.getEquipment().getBody().getArmor()/100.0;
			}
		}
		damage *=  (1-reduction);
		damage = damage/defender.getVitality();
		return damage;
	}

}
