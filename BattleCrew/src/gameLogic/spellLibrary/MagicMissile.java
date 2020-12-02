package gameLogic.spellLibrary;

import gameLogic.Battle;
import gameLogic.BattleUnit;
import gameLogic.Behaviour;
import gameLogic.Spell;

public class MagicMissile extends Spell {

	public MagicMissile() {
		name = "magic missile";
		manacost = 1;
		exhaustion = 7;
		study_cost = 75;
		study_power = 3;
	}

	public boolean cast_spell(BattleUnit caster, Battle battle) throws Exception {
		if (check_and_pay_mana(caster)) {
			casting_exhaustion(caster);
			Behaviour.find_closest_target(caster, battle);
			battle.getBattleParticipants();
			if (Math.random()*100 < 50 + caster.getPrecision()) {
				//hit
				caster.getTarget().take_damage(2*caster.getSpell_power(), caster, "magic");
			}
			return true;
		}else {
			return false;
		}
		
	}

}
