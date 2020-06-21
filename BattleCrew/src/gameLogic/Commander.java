package gameLogic;

import java.util.LinkedList;

import game.Leaderboard.LeaderBoardEntry;
import game.Leaderboard.Leaderboard;

public class Commander extends BattleUnit {






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

	private int action_points = 3; // number of actions in the city
	private int command_points = 3; // number of Warriors that can be fielded
	private int healer_points = 5;  // chance of healing lost units after battles
	private int recover_points = 0; // recovering of stamina/fear/health
	private int wealth = 200; // starting money
	private int gold_bonus = 0; //money bonus
	private int group_size = 5; // amount of warriors in the team
	private int enchant_chance = 5; //chance of enchanting an item when buying
	private int recruit_foreign_chance = 20;
	
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
			break;
		case SupplyMaster:
			recover_points+=5;
			vitality++;
			break;
		case Looter:
			dexterity++;
			gold_bonus+=10;
			break;
		case Healer:
			vitality++;
			healer_points+=35;
			break;
		case Warrior:
			vitality += 4;
			dexterity += 1;
			weapon_skill += 2;
			strength += 2;
			base_defense+=5;
			base_offense+=10;
			break;
		case Tactician:
			group_size+=4;
			break;
		case Noble:
			wealth += 500;
			break;
		case Hero:
			player.earn_score(150);
			break;
		case Enchanter:
			enchant_chance += 15;
			break;
		case Developer:
			action_points ++;
			wealth = wealth/2;
			enchant_chance --;
			break;
		default:
			break;
		}
		switch (type) {
		case "human":
			player.earn_score(15);
			gain_experience(100);
			recruit_foreign_chance += 5;
			setImage_number(39);
			break;
		case "elf":
			enchant_chance += 3;
			healer_points += 5;
			group_size--;
			setImage_number(122);
			break;
		case "dwarf":
			wealth += 100;
			command_points--;
			group_size--;
			setImage_number(38);
			break;
		case "halfling":
			dexterity+=3;
			command_points++;
			group_size++;			
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
		lines.add("Commander -" + commander_class);
		lines.add("");
		lines.add("command points: " + command_points+"/"+group_size);
		if (recover_points > 0) {
			lines.add("recover skill: " + "+" + recover_points + "%");
		}		
		if (healer_points > 0) {
			lines.add("revive chance: " + "+" + healer_points + "%");
		}
		if (enchant_chance > 0) {
			lines.add("enchant skill: "+ enchant_chance + "%");
		}
		return lines;
	}
	@Override
	public void lvl_up() {
		super.lvl_up();
		//command_points++;
		player.gain_action_points(1);
	}
	@Override
	public void die() {
		// TODO Game over if commander dies
		super.die();
		player.getGame().game_over();
	}
	
	
}
