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
	
	public static void behave(BattleUnit warrior, Battle battle) {
		potion_check_and_use(warrior);
		if (warrior.getBehaviour() == null) {
			warrior.setBehaviour(Behaviour_type.ATTACK_CLOSEST_ENEMY);
		}else {
			if (warrior.getFear()>=100) {
				//flee you fools
				retreat(battle, warrior);				
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
	}
	
	public static void potion_check_and_use(BattleUnit warrior) {
		if (warrior.getEquipment().getPotion() != null) {
			if ((warrior.getMaxHealth()-warrior.getHealth())/10*warrior.getVitality() > warrior.getEquipment().getPotion().getDamage()) {
				warrior.getEquipment().drinkPotion();
			}
		}
		
	}
	
	
	public static void tactic_move_to_and_attack_closest_enemy(BattleUnit warrior, Battle battle, Movespeed speed) {
		find_closest_target(warrior, battle);
		if (move_into_attack_range(warrior, battle, speed)) {
			attack(warrior);
		}
	}
	
	public static void find_closest_target(BattleUnit warrior, Battle battle) {
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
	public static boolean move_into_attack_range(BattleUnit active_warrior, Battle battle, Movespeed speed) {		
		if (!active_warrior.getTarget().is_unable_to_fight() && active_warrior.getTarget() != null) {
			//check distance to target (in range?)
			if (in_range_for_attack(active_warrior)) {
				return true;
			}else {
				//if not in range find path and move along it and check for range after every step				
				if (battle.pathfinder.find_path((PathfinderField) active_warrior.getHexTile(), (PathfinderField) active_warrior.getTarget().getHexTile(), 3)) {	
					for (int j = 0; j < Math.min(active_warrior.get_movepoints_from_movement_mode(speed),battle.pathfinder.get_best_path().getPath().size()); j++) {
						active_warrior.move((Tile)battle.pathfinder.get_best_path().getPath().get(j));
						if (in_range_for_attack(active_warrior)) {
							return true;
						}
					}					
				}
			}
			
			
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param battle
	 * @param warrior
	 * @param tile (destination)
	 * @param speed (speed mode)
	 * @return true if destination is reached
	 */
	public static boolean move_towards_tile(Battle battle, BattleUnit warrior, HexTile tile, Movespeed speed) {
		if (battle.pathfinder.find_path((PathfinderField) warrior.getHexTile(), (PathfinderField) tile, 3)) {	
			for (int j = 0; j < Math.min(warrior.get_movepoints_from_movement_mode(speed),battle.pathfinder.get_best_path().getPath().size()); j++) {
				warrior.move((Tile)battle.pathfinder.get_best_path().getPath().get(j));
				if (warrior.getTile() == tile) {
					return true;
				}
			}					
		}
		return false;
	}
	
	/**
	 * 
	 * @param battle
	 * @param warrior
	 * @return true if warrior actually retreated
	 */
	public static boolean retreat(Battle battle, BattleUnit warrior) {
		if (warrior.getTile() == warrior.getRetreat_tile()) {
			//battle.battleParticipants.remove(warrior);
			warrior.setFled(true);
			warrior.getTile().setUnit(null);
		}else {
			move_towards_tile(battle, warrior, warrior.getRetreat_tile(), Movespeed.CHARGE);
		}
		return false;
	}
	
	private static boolean in_range_for_attack(BattleUnit active_warrior) {
		if ( BattleCalculations.calc_actual_attack_range(active_warrior) >= active_warrior.getHexTile().getDistance(active_warrior.getTarget().getHexTile())) {
				return true;
		}		
		return false;
	}
	
	
	public static void attack(BattleUnit active_warrior) {
		// TODO attack!
		active_warrior.basic_attack(active_warrior.getTarget());
	}
}
