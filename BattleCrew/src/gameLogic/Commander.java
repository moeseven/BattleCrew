package gameLogic;

import java.util.LinkedList;

import game.Leaderboard.LeaderBoardEntry;
import game.Leaderboard.Leaderboard;

public class Commander extends BattleUnit {






	public int getScore_gain() {
		return score_gain;
	}


	public void setScore_gain(int score_gain) {
		this.score_gain = score_gain;
	}


	public int getDrill() {
		return drill;
	}


	public void setDrill(int drill) {
		this.drill = drill;
	}


	public int getTrain_cost() {
		return train_cost;
	}


	public void setTrain_cost(int train_cost) {
		this.train_cost = train_cost;
	}


	public int getEarn_cost() {
		return earn_cost;
	}


	public void setEarn_cost(int earn_cost) {
		this.earn_cost = earn_cost;
	}


	public int getPrestige_cost() {
		return prestige_cost;
	}


	public void setPrestige_cost(int prestige_cost) {
		this.prestige_cost = prestige_cost;
	}


	public int getRecruit_cost() {
		return recruit_cost;
	}


	public void setRecruit_cost(int recruit_cost) {
		this.recruit_cost = recruit_cost;
	}


	public int getCommand_cost() {
		return command_cost;
	}


	public void setCommand_cost(int command_cost) {
		this.command_cost = command_cost;
	}


	public int getEnchant_cost() {
		return enchant_cost;
	}


	public void setEnchant_cost(int enchant_cost) {
		this.enchant_cost = enchant_cost;
	}


	public int getRecover_cost() {
		return recover_cost;
	}


	public void setRecover_cost(int recover_cost) {
		this.recover_cost = recover_cost;
	}


	public void setCommander_class(Commander_Class commander_class) {
		this.commander_class = commander_class;
	}


	public int getRecruit_foreign_chance() {
		return recruit_foreign_chance;
	}


	public Commander_Class getCommander_class() {
		return commander_class;
	}


	public int getAction_points() {
		return action_points;
	}


	public void setAction_points(int action_points) {
		this.action_points = action_points;
	}


	public int getEnchant_chance() {
		return enchant_chance;
	}


	public void setEnchant_chance(int enchant_chance) {
		this.enchant_chance = enchant_chance;
	}


	public int getGroup_size() {
		return group_size;
	}


	public void setGroup_size(int group_size) {
		this.group_size = group_size;
	}


	public int getCommand_points() {
		return command_points;
	}


	public void setCommand_points(int command_points) {
		this.command_points = command_points;
	}


	public int getHealer_points() {
		return healer_points;
	}


	public void setHealer_points(int healer_points) {
		this.healer_points = healer_points;
	}


	public int getRecover_points() {
		return recover_points;
	}


	public void setRecover_points(int recover_points) {
		this.recover_points = recover_points;
	}


	public int getWealth() {
		return wealth;
	}


	public void setWealth(int wealth) {
		this.wealth = wealth;
	}
	//action costs
	private int prestige_cost = 1;
	private int earn_cost = 1;
	private int enchant_cost = 2;
	private int recover_cost = 1;
	private int recruit_cost = 3;
	private int train_cost = 5;
	private int command_cost = 6;		
	
	private int score_gain = 100;
	private int action_points = 7; // number of actions in the city
	private int command_points = 3; // number of Warriors that can be fielded
	private int healer_points = 5;  // chance of healing lost units after battles
	private int recover_points = 7; // recovering of stamina/fear/health
	private int wealth = 400; // starting money
	private int gold_bonus = 0; //money bonus
	private int group_size = 5; // amount of warriors in the team
	private int enchant_chance = 4; //chance of enchanting an item when buying
	private int recruit_foreign_chance = 18;
	private int drill = 100;
	
	private Commander_Class commander_class;
	
	public Commander(String[] stats,Commander_Class commander_class, Game game, Player player) {
		super(stats, game, player);
		this.commander_class = commander_class;
		vitality++;
		base_defense++;
		courage++;
		switch (commander_class) {
		case Leader:
			command_points++;
			command_cost--;
			break;
		case Thief:
			dexterity+=5;
			precision+=5;
			gold_bonus+=10;
			break;
		case Healer:
			vitality++;
			healer_points+=35;
			recover_points+=3;
			break;
		case Warrior:
			vitality += 1;
			protection += 3;
			dexterity += 1;
			weapon_skill += 3;
			strength += 5;
			base_defense+=8;
			base_offense+=14;
			recovery+=7;
			break;
		case Tactician:
			recruit_cost--;
			train_cost--;
			break;
		case Noble:
			wealth += 500;
			break;
		case Hero:
			score_gain += 10;	
			break;
		case Enchanter:
			enchant_chance += 5;
			enchant_cost--;
			break;
		case Developer:
			action_points ++;
			enchant_chance --;
			break;
		default:
			break;
		}
		switch (type) {
		case "human":
			recruit_foreign_chance += 5;
			drill += 20;
			setImage_number(39);
			break;
		case "elf":
			enchant_chance += 3;
			healer_points += 5;
			recover_points += 1;
			group_size--;
			setImage_number(122);
			break;
		case "dwarf":
			wealth += 200;
			recruit_cost++;
			setImage_number(38);
			break;
		case "halfling":
			command_points++;
			group_size+=2;			
			action_points++;
			setImage_number(31);
			break;
		default:
			break;
		}
	}
	public void gain_gold(int gold) {		
		if(gold>0) {
			gold= (int) (gold*(1+gold_bonus/100.0));
			player.getGame().log.addLine("gained "+gold+" gold.");			
		}else {
			player.getGame().log.addLine("lost "+(-gold)+" gold.");
		}
		wealth += gold;
	}


	@Override
	public LinkedList<String> generateStatLines() {
		LinkedList<String> lines = super.generateStatLines();
		lines.add("");
		lines.add(""+commander_class);
		lines.add("");
		lines.add("leadership: " + command_points + " (-"+ command_cost+")");
		lines.add("recruiting: " + group_size + "(-"+ recruit_cost+")");			
		lines.add("healing: " + recover_points +"%/"+healer_points + "% (-"+ recover_cost+")");				
		lines.add("drilling: " + drill + "ep (-"+ train_cost+")");
		lines.add("enchanting: " + enchant_chance + "% (-"+ enchant_cost+")");	
		lines.add("action points: " + action_points);
		return lines;
	}
	@Override
	public void lvl_up() {
		super.lvl_up();
		//command_points++;
		switch (level) {
		case 3:
			command_points++;
			break;
		case 4:
			enchant_chance++;
			break;
		case 5:
			command_points++;
			break;
		case 6:
			healer_points+=5;
			break;
		default:			
			action_points++;			
			break;
		}
		
	}
	@Override
	public void die() {
		// TODO Game over if commander dies
		super.die();
		player.getGame().game_over();
	}
	
	
}
