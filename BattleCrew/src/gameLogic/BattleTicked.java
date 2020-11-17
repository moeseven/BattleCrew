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
	
	/**
	 * this is for fear calculations
	 */
	private int calc_vitality_sums(Player player) {
		int sum = 0;
		for (int w = 0; w < battleParticipants.size(); w++) {
			if (battleParticipants.get(w).getPlayer() == player) {
				sum += battleParticipants.get(w).getVitality();
			}
		}
		return sum;
	}

	public void battle_tick() {
		// TODO Auto-generated method stub
		for (int w = 0; w < battleParticipants.size(); w++) {
			BattleUnit warrior = battleParticipants.get(w);
			if (!deathCheck(warrior)) {
				warrior.round_begin();
				Behaviour.behave(warrior, this);
			}			
			//run_ai(battleParticipants.get(w));
		}
		tryEndBattle();
	}
			

	public boolean deathCheck(BattleUnit warrior) {
		if (warrior.is_unable_to_fight()||warrior.isFled()) {
			//TODO strike fear into their hearts	
			double total_vit = calc_vitality_sums(warrior.getPlayer());
			battleParticipants.remove(warrior);
			for (int w = 0; w < battleParticipants.size(); w++) {
				if (warrior.getPlayer() == battleParticipants.get(w).getPlayer()) {
					battleParticipants.get(w).frighten(warrior.getVitality()/(total_vit+1.0)*.75);
				}else {
					//maybe gain some moral back through kills
					battleParticipants.get(w).gain_courage(warrior.getVitality()/(total_vit+1.0)*25);
				}
				
			}
			return true;
		}else {
			return false;
		}
	}

}
