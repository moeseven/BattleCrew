package gameLogic;

import HexTilePlayground.HexTile;
import pathfinding.Pathfinder;
import pathfinding.PathfinderField;

public class Behaviour {
	enum Movespeed {
		  SLOW,
		  WALK,
		  CHARGE
	}
	enum Behaviour_type {
		ATTACK_CLOSEST_ENEMY
	}
	
	public static void behave(Warrior warrior, Battle battle) {
		if (warrior.getBehaviour() == null) {
			warrior.setBehaviour(Behaviour_type.ATTACK_CLOSEST_ENEMY);
		}else {
			switch (warrior.getBehaviour()) {
			case ATTACK_CLOSEST_ENEMY:
				tactic_move_to_and_attack_closest_enemy(warrior, battle, Movespeed.WALK);
				break;

			default:
				tactic_move_to_and_attack_closest_enemy(warrior, battle, Movespeed.WALK);
				break;
			}
		}		
	}

	
	
	public static void tactic_move_to_and_attack_closest_enemy(Warrior warrior, Battle battle, Movespeed speed) {
		find_closest_target(warrior, battle);
		if (move_into_attack_range(warrior, battle, speed)) {
			attack(warrior);
		}
	}
	
	public static void find_closest_target(Warrior warrior, Battle battle) {
		//get closest target
		int smallest_distance=Integer.MAX_VALUE;
		for (int i = 0; i < battle.battleParticipants.size(); i++) {
			if (battle.battleParticipants.get(i).getPlayer()!= warrior.getPlayer()) {
				if (warrior.getHexTile().getDistance(battle.battleParticipants.get(i).getHexTile())<smallest_distance) {
					smallest_distance=warrior.getHexTile().getDistance(battle.battleParticipants.get(i).getHexTile());
					warrior.setTarget(battle.battleParticipants.get(i));
				}
			}			
		}
	}
	/*
	 * this method should handle the finding of a path towards the target so it can be attacked
	 */
	public static boolean move_into_attack_range(Warrior active_warrior, Battle battle, Movespeed speed) {		
		if (!active_warrior.getTarget().isDead() && active_warrior.getTarget() != null) {
			//check distance to target (in range?)
			if (in_range_for_attack(active_warrior)) {
				return true;
			}else {
				//if not in range find path and move along it and check for range after every step				
				if (battle.pathfinder.find_path((PathfinderField) active_warrior.getHexTile(), (PathfinderField) active_warrior.getTarget().getHexTile())) {	
					for (int j = 0; j < Math.min(active_warrior.get_movepoints_from_movement_mode(speed),battle.pathfinder.get_best_path().getPath().size()); j++) {
						active_warrior.moveOneTile((Tile)battle.pathfinder.get_best_path().getPath().get(j));
						if (in_range_for_attack(active_warrior)) {
							return true;
						}
					}					
				}
			}
			
			
		}
		return false;
	}
	
	private static boolean in_range_for_attack(Warrior active_warrior) {
		if (active_warrior.getWeaponAbility().getRange() >= active_warrior.getHexTile().getDistance(active_warrior.getTarget().getHexTile())) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public static void attack(Warrior active_warrior) {
		// TODO attack!
		active_warrior.useMainHand(active_warrior.getTarget());
		if (active_warrior.getWeaponAbility().isAfter_roll_status()) {
			active_warrior.getWeaponAbility().applyAbilityAfterRoll();
		}
	}
}
