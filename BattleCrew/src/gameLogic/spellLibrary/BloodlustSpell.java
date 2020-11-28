package gameLogic.spellLibrary;

import gameLogic.Battle;
import gameLogic.BattleUnit;
import gameLogic.Spell;

public class BloodlustSpell extends Spell {

	public BloodlustSpell() {
		name = "bloodlust";
		manacost = 10;
		exhaustion = 50;
		study_cost = 100;
		study_power = 5;
	}

	public boolean cast_spell(BattleUnit caster, Battle battle) throws Exception {
		if (check_and_pay_mana(caster)) {
			casting_exhaustion(caster);
			battle.getBattleParticipants();
			for (int i = 0; i < battle.getBattleParticipants().size(); i++) {
				if (battle.getBattleParticipants().get(i).getPlayer()==caster.getPlayer()) {
					battle.getBattleParticipants().get(i).buff(caster.getPlayer().getGame().builder.buildBuffbyName("bloodlust", caster.getSpell_power()/10, caster.getWisdom()));
				}
			}
			return true;
		}else {
			return false;
		}
		
	}

}
