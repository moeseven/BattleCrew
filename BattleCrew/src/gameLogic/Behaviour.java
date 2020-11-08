package gameLogic;

import java.util.Iterator;

import HexTilePlayground.HexTile;
import pathfinding.Pathfinder;
import pathfinding.PathfinderField;

public class Behaviour {
	public static enum Movespeed {
		  SLOW,
		  WALK,
		  CHARGE
	}
	public static enum Behaviour_type {
		ATTACK_CLOSEST_ENEMY,
		ATTACK_REAR,
		CONSERVATIVE_ATTACKING,
		FLANK
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
				if(Math.random()*100 < warrior.getFatigue()) {					
					BattleCalculations.realax_one_round(warrior);
				}else {
					switch (warrior.getBehaviour()) {
					case ATTACK_CLOSEST_ENEMY:
						tactic_move_to_and_attack_closest_enemy(warrior, battle, Movespeed.WALK);
						break;
					case CONSERVATIVE_ATTACKING:
						if (warrior.getAttacks_taken_last_round()>1 || (warrior.getAttacks_taken_last_round()>0 && warrior.getHealth()<30)) {
							if (warrior.getTile()!=warrior.getRetreat_tile()) {
								move_towards_tile(battle, warrior, warrior.getRetreat_tile(), Movespeed.WALK);
							}else {
								tactic_move_to_and_attack_closest_enemy(warrior, battle, Movespeed.WALK);
							}
							
						}else {
							tactic_move_to_and_attack_closest_enemy(warrior, battle, Movespeed.WALK);
						}
						break;
					case ATTACK_REAR:
						 tactic_attack_rear(warrior, battle, Movespeed.WALK);
						break;
					case FLANK:
						tactic_flank(warrior, battle, Movespeed.WALK);
						break;
		
					default:
						tactic_move_to_and_attack_closest_enemy(warrior, battle, Movespeed.WALK);
						break;
					}					
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
	
	public static void tactic_flank(BattleUnit warrior, Battle battle, Movespeed speed) {
		//move to right or left center of battlefield and then change tactic to attack closest enemy
		boolean enemy_close = false;
		for (int i = 0; i < battle.getBattleField().get_adjacent_tiles(warrior.getTile()).size(); i++) {
			if (battle.getBattleField().get_adjacent_tiles(warrior.getTile()).get(i).getUnit() != null) {
				if (battle.getBattleField().get_adjacent_tiles(warrior.getHexTile()).get(i).getUnit().getPlayer() != warrior.getPlayer()) {
					enemy_close = true;
				}
			}			
		}
		if (enemy_close) {
			tactic_move_to_and_attack_closest_enemy(warrior, battle, speed);
		}
		int right_most_x = battle.getBattleField().getTable_size_x()-1;
		int flanking_turnpoint_y = battle.getBattleField().getTable_size_y()/2;
		HexTile tile;
		if (warrior.getTile().getx() < battle.getBattleField().getTable_size_x()/2) {
			//left flank
			tile = battle.getBattleField().getTiles().get(battle.getBattleField().getTable_size_y()/2);
		}else {
			//right flank
			tile = battle.getBattleField().getTiles().get(right_most_x * battle.getBattleField().getTable_size_y()+flanking_turnpoint_y);
		}
		if (move_towards_tile(battle, warrior, tile, speed)) {
			warrior.setBehaviour(Behaviour_type.ATTACK_CLOSEST_ENEMY);
		}
	}
	
	public static void tactic_attack_rear(BattleUnit warrior, Battle battle, Movespeed speed) {
		//attack rearmost enemy
		if (warrior.getAttacks_taken_last_round()>0) {
			tactic_move_to_and_attack_closest_enemy(warrior, battle, speed);
		}else {
			//TODO		
			if(warrior.getPlayer() == battle.getAttacker() && battle.defender.getHeroes().size()>0) {
				warrior.setTarget(battle.defender.getHeroes().getFirst());
				for (Iterator iterator = battle.defender.getHeroes().iterator(); iterator.hasNext();) {
					BattleUnit type = (BattleUnit) iterator.next();
					if (type.getTile().gety()<warrior.getTarget().getTile().gety()) {
						warrior.setTarget(type);
					}
				}
				
			}else {
				if (battle.attacker.getHeroes().size()>0) {
					warrior.setTarget(battle.attacker.getHeroes().getFirst());
					for (Iterator iterator = battle.attacker.getHeroes().iterator(); iterator.hasNext();) {
						BattleUnit type = (BattleUnit) iterator.next();
						if (type.getTile().gety()<warrior.getTarget().getTile().gety()) {
							warrior.setTarget(type);
						}
					}
				}			
			}
			move_towards_tile(battle, warrior, warrior.getTarget().getTile(), speed);
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
		if (active_warrior.basic_attack(active_warrior.getTarget())) {
			//attack worked out
		}else {
			//change weapon
			active_warrior.getEquipment().swapWeapons();
		}
		
	}
}
