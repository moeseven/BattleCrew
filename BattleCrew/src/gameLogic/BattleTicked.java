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
		if (warrior.isDead()) {
			battleParticipants.remove(warrior);
			return true;
		}else {
			return false;
		}
	}

}
