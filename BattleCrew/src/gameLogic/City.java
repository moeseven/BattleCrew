package gameLogic;

public class City {
	
	/**
	 * all units recover some hp and exhaustion
	 * @param player
	 */
	public static boolean rest(Player player) {
		if (player.pay_action_points(1)) {
			player.recover_warriors();
			return true;
		}
		return false;
		
	}
	
	/**
	 * get extra score
	 */
	public static boolean get_prestige(Player player) {
		if (player.pay_action_points(1)) {
			player.earn_score(10);
			return true;
		}
		return false;
	}
	
	public static boolean hire_new_recruit(Player player) {
		//TODO this should be more interesting
		if (player.pay_action_points(1)) {
			if (player.getGroupSize() <= player.getHeroes().size()) {
				player.getCommander().setGroup_size(player.getGroupSize()+1);
			}
			//some chance of getting a recruit from another race
			double roll = Math.random();
			if (roll < 0.2) {
				player.addHero(player.getGame().unitBuilder.buildUnitbyName(CommanderChooser.COMMANDER_RACES[(int) (Math.random()*CommanderChooser.COMMANDER_RACES.length)], player));
			}else {
				player.addHero(player.getGame().unitBuilder.buildUnitbyName(player.getCommander().getType(), player));
			}			
			return true;
		}
		return false;	
	}
	
	public static boolean earn_money(Player player) {
		if (player.pay_action_points(1)) {
			player.gainGold(50);
			return true;
		}
		return false;
		
	}
	
	public static boolean practice(Player player) {
		if (player.pay_action_points(1)) {
			player.getSelectedUnit().gain_experience(100);
			return true;
		}
		return false;
		
	}
}
