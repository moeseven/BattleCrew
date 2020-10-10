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





	public void setCommander_class(Commander_Class commander_class) {
		this.commander_class = commander_class;
	}


	public int getRecruit_foreign_chance() {
		return recruit_points;
	}


	public Commander_Class getCommander_class() {
		return commander_class;
	}


	
	
	private int score_gain = 100;
	
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
			wealth += 50;
			break;
		case Thief:
			dexterity+=5;
			precision+=5;
			gold_bonus+=10;
			break;
		case Healer:
			vitality++;
			healer_points+=35;
			break;
		case Warrior:
			vitality += 3;
			dexterity += 1;
			weapon_skill += 2;
			strength += 2;
			base_defense+=4;
			base_offense+=8;
			recovery+=7;
			break;
		case Tactician:
			recruit_points += 10;
			break;
		case Noble:
			wealth += 500;
			break;
		case Hero:
			score_gain += 10;	
			break;
		case Enchanter:
			smith_points += 7;
			break;
		case Developer:
			wealth += 10;
			gold_bonus+=4;
			score_gain +=4;
			break;
		default:
			break;
		}
		switch (type) {
		case "human":
			setMeele_image(39);
			setImage_number(39);
			break;
		case "elf":
			group_size--;
			setMeele_image(122);
			setImage_number(122);
			break;
		case "dwarf":
			wealth += 200;
			setMeele_image(38);
			setImage_number(38);
			break;
		case "halfling":
			group_size+=2;		
			setMeele_image(31);
			setImage_number(31);
			break;
		default:
			break;
		}
	}


	@Override
	public LinkedList<String> generateStatLines() {
		LinkedList<String> lines = super.generateStatLines();
		lines.add("");
		lines.add(""+commander_class);
		lines.add("");
		lines.add("units: " + group_size);	
		return lines;
	}
	
	@Override
	public void die() {
		// TODO Game over if commander dies
		super.die();
		player.getGame().game_over();
	}
	
	
}
