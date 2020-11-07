package gameLogic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BattleTemplate {
public String getName() {
		return name;
	}
	//TODO this should be a set of enemies with gold and experience reward that is slightly randomized and levels up
	protected Game game;
	protected int level;
	protected int gold_reward;
	protected int exp_reward;
	protected int score_reward;
	String[] units;
	String[][] items;
	protected double[] amount;
	protected double[] grow_numbers;
	protected int exp_grow;
	protected int gold_grow;
	protected int score_grow;
	protected String name;
	//protected LinkedList<String> lines;
	private HashMap<String,List<String>> map;
	
	public BattleTemplate(String[] stats,Game game) {
		super();
		this.game = game;
		level = 1;
		//go through resource file
		name = stats[0];
		units = stats[1].split(";"); //unit names
		String[] help_string = stats[2].split(";"); //item names
		items = new String[help_string.length][];
		for(int i = 0; i<help_string.length;i++) {//number of units
			String[] equipment = help_string[i].split("&");
			items[i] = equipment;
		}
		help_string = stats[3].split(";"); 
		amount = new double[help_string.length];
		for(int i = 0; i<help_string.length;i++) {//number of units
			amount[i] = Double.parseDouble(help_string[i]);
		}		
		help_string = stats[4].split(";"); 
		grow_numbers = new double[help_string.length];
		for(int i = 0; i<help_string.length;i++) {//increase in numbers with lvlup
			grow_numbers[i] = Double.parseDouble(help_string[i]);
		}		
		exp_grow = Integer.parseInt(stats[5]);
		gold_grow = Integer.parseInt(stats[6]);
		score_grow = Integer.parseInt(stats[7]);
		exp_reward = Integer.parseInt(stats[8]);
		gold_reward = Integer.parseInt(stats[9]);
		score_reward = Integer.parseInt(stats[10]);
	}
	public void levelup() {
		level++;
		gold_reward+=gold_grow;
		exp_grow+=exp_grow;
		score_reward*=score_grow;
		for (int i = 0; i < amount.length; i++) {
			amount[i] = amount[i] + grow_numbers[i];
		}
	}
	/**
	 * better not use this, use 'generate_enemy_general_with_army()' instead
	 * @return
	 * @throws Exception
	 */
	public LinkedList<BattleUnit> generate_army() throws Exception{
		Player defender = new Player(game,true);
		LinkedList<BattleUnit> army = new LinkedList<BattleUnit>();
		for(int unit_type = 0; unit_type<units.length;unit_type++) {
			for(int number = 0; number<amount[unit_type];number++) {
				//make unit
				BattleUnit u = game.builder.buildUnitbyName(units[unit_type], defender);
				//equip unit
				for(int item = 0; item<items[unit_type].length;item++) {
					Item e = game.builder.buildItembyName(items[unit_type][item]);
					if(e.getAmunitionType()!=null || e.getAmunitionType().equals("0") == false) {
						for (int j = 0; j < 30; j++) {
							u.equip(game.builder.buildItembyName(e.getAmunitionType()));
						}						
					}
					u.equip(e);					
				}
				//add unit to army
				army.add(u);
			}
		}	   
		return army;		
	}
	public Player generate_enemy_general_with_army() throws Exception {
		Player defender = new Player(game,true);
		LinkedList<BattleUnit> army = new LinkedList<BattleUnit>();
		for(int unit_type = 0; unit_type<units.length;unit_type++) {
			for(int number = 0; number<(int)(amount[unit_type]);number++) {
				//make unit
				BattleUnit u = game.builder.buildUnitbyName(units[unit_type], defender);
				//equip unit
				for(int item = 0; item<items[unit_type].length;item++) {
					Item e = game.builder.buildItembyName(items[unit_type][item]);
					if(e.getAmunitionType()!=null) {
						if (e.getAmunitionType().equals("0") == false) {
							for (int j = 0; j < 30; j++) {
								u.getEquipment().equipItemDirectly(game.builder.buildItembyName(e.getAmunitionType()));
							}
						}											
					}
					u.getEquipment().equipItemDirectly(e);				
				}
				//add unit to army
				defender.addHero(u);
			}
		}	
		defender.setGold_reward(gold_reward);
		defender.setExperience_reward(exp_reward);
		defender.setScore(score_reward);
		return defender;
	}
	public LinkedList<String> generate_description_lines(){
		LinkedList<String> lines=new LinkedList<String>();		
		lines.add(name + " (level "+level+")");
		lines.add("bounty");
		lines.add("gold: "+gold_reward);
		lines.add("experience: "+exp_reward);
		lines.add("score: "+score_reward);
		return lines;
	}
}
