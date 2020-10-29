package gameLogic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import builders.BattleUnitBuilder;

public abstract class BattleTemplate {
//TODO this should be a set of enemies with gold and experience reward that is slightly randomized and levels up
	protected Game game;
	protected int level;
	protected int gold_reward;
	protected int experience_reward;
	private HashMap<String,UnitNAMEANDEQUIPSTRINGS> map;
	public abstract void levelup();
	public LinkedList<BattleUnit> generate_army(){
		LinkedList<BattleUnit> army = new LinkedList<BattleUnit>();
		Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        UnitNAMEANDEQUIPSTRINGS build_info = (UnitNAMEANDEQUIPSTRINGS) pair.getValue();
	        for (int i = 0; i < build_info.amount; i++) {	        	
	        	BattleUnit unit = game.unitBuilder.buildUnitbyName(build_info.unit, game.getOpponent());
	        	for (int j = 0; j < build_info.equipment.size(); j++) {
					unit.equip(game.itemBuilder.buildItembyName(build_info.equipment.get(j)));
				}
				army.add(unit);
			}
	    }
		return army;		
	}
	
}
class UnitNAMEANDEQUIPSTRINGS{
	public String unit;
	public List<String> equipment;
	public int amount;
}