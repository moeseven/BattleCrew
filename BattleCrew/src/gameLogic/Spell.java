package gameLogic;

import java.io.Serializable;

public abstract class Spell implements Serializable {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getManacost() {
		return manacost;
	}
	public void setManacost(int manacost) {
		this.manacost = manacost;
	}
	public int getExhaustion() {
		return exhaustion;
	}
	public void setExhaustion(int exhaustion) {
		this.exhaustion = exhaustion;
	}
	public int getStudy_power() {
		return study_power;
	}
	public void setStudy_power(int study_power) {
		this.study_power = study_power;
	}
	public int getStudy_cost() {
		return study_cost;
	}
	public void setStudy_cost(int study_cost) {
		this.study_cost = study_cost;
	}
	protected String name;
	protected int manacost;
	protected int exhaustion;
	protected int study_power;
	protected int study_cost;
	
	public abstract boolean cast_spell(BattleUnit caster, Battle battle) throws Exception;
	
	protected boolean check_and_pay_mana(BattleUnit caster) {
		if (caster.getMana()>manacost) {
			caster.gain_mana(-manacost);
			return true;
		}else {
			return false;
		}
		
	}
	protected void casting_exhaustion(BattleUnit caster) {
		caster.getPlayer().getGame().log.addLine(caster.getName()+" casts "+name);
		caster.exhaust(exhaustion*BattleCalculations.weight_endurance_exhaustion_factor(caster));
	}
	public boolean studySpell(BattleUnit caster) {
		if (caster.getWisdom() >= manacost) {
			if (caster.getSpell_power() >= study_power) {
				if (caster.getPlayer().getGold()>= study_cost) {
					if (!caster.getSpellbook().contains(this)) {
						caster.getSpellbook().add(this);
						caster.getPlayer().pay_gold(study_cost);
						return true;
					}					
				}else {
					caster.getPlayer().getGame().log.addLine("not enough gold");
				}
			}else {
				caster.getPlayer().getGame().log.addLine("not enough spellpower");
			}
		}else {
			caster.getPlayer().getGame().log.addLine("not enough wisdom");
		}
		return false;
	}
}
