package gameLogic;

import java.util.LinkedList;

import game.Leaderboard.LeaderBoardEntry;
import game.Leaderboard.Leaderboard;

public class Commander extends BattleUnit {



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


	private int command_points = 3; // number of Warriors that can be fielded
	private int healer_points = 5;  // chance of healing lost units after battles
	private int recover_points = 0; // recovering of stamina/fear/health
	private int wealth = 200; // starting money
	private int gold_bonus = 0; //money bonus
	private int group_size = 5; // amount of warriors in the team
	
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
			recover_points+=3;
			break;
		case Looter:
			gold_bonus+=10;
			break;
		case Healer:
			healer_points+=10;
			break;
		case Warrior:
			vitality++;
			weapon_skill++;
			base_defense+=5;
			base_offense+=8;
			break;
		case Tactician:
			group_size+=4;
			break;
		case Noble:
			wealth += 500;
			break;
		default:
			break;
		}
	}
	public void gain_Gold(int gold) {		
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
		if (wealth > 0) {
			lines.add("sarting gold: "+wealth);
		}
		return lines;
	}
	
	@Override
	public void die() {
		// TODO Game over if commander dies
		super.die();
		player.getGame().game_over();
	}
	
	
}
