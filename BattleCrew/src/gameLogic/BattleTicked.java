package gameLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import HexTilePlayground.HexTile;

public class BattleTicked  extends Battle{
	public BattleTicked(Game game, Battlefield battlefield,Player attacker, Player defender) {
		super(game, battlefield, attacker, defender);	
	}

	public void battle_tick() {
		// TODO Auto-generated method stub
		for (int w = 0; w < battleParticipants.size(); w++) {
			run_ai(battleParticipants.get(w));
		}
		tryEndBattle();
	}
	private void run_ai(Warrior warrior) {
		warrior.roundBegin();
		
		if (!deathCheck(warrior)) {
			for (int turns = 0; turns <3; turns++) {
				//get target for attack
				int smallest_distance=Integer.MAX_VALUE;
				Warrior target_warrior=null;
				for (int i = 0; i < battleParticipants.size(); i++) {
					if (battleParticipants.get(i).getPlayer()!= warrior.getPlayer()) {
						if (warrior.getHexTile().getDistance(battleParticipants.get(i).getHexTile())<smallest_distance) {
							smallest_distance=warrior.getHexTile().getDistance(battleParticipants.get(i).getHexTile());
							target_warrior=battleParticipants.get(i);
						}
					}			
				}
				if (target_warrior!=null) {
					//move
					if (warrior.getHexTile().getDistance(target_warrior.getHexTile())>warrior.getWeaponAbility().getRange()) {
						//move towards target here
						int minimum_distance=Integer.MAX_VALUE;
						LinkedList<HexTile> tiles= warrior.getHexTile().getAdjacentTiles();
						HexTile bestTile = tiles.getFirst();				
						for (int i = 0; i < tiles.size(); i++) {
							if (minimum_distance>tiles.get(i).getDistance(target_warrior.getHexTile())) {
								minimum_distance=tiles.get(i).getDistance(target_warrior.getHexTile());
								if (tiles.get(i).getUnit()==null) {
									bestTile=tiles.get(i);
								}
							}
						}
						if (bestTile instanceof Tile) {
							Tile move_tile= (Tile) bestTile;
							warrior.moveOneTile(move_tile);
						}
						if (warrior.getStamina()<0.8*warrior.calcMaxStamina()) {
							turns=2;
						}
					}else {
						//hit
						warrior.useMainHand(target_warrior);
						if (warrior.getWeaponAbility().isAfter_roll_status()) {
							warrior.getWeaponAbility().applyAbilityAfterRoll();
						}
						
					}				
					
				}
			}
		}		
	}
			

	public boolean deathCheck(Warrior warrior) {
		if (warrior.isDead()) {
			battleParticipants.remove(warrior);
			return true;
		}else {
			return false;
		}
	}

}
