package gameLogic;

public class City {
	
	public static int RECOVER_COST = 50;
	public static int PRESTIGE_COST = 50;
	public static int TRAIN_COST = 100;
	public static int ENCHANT_COST = 50;
	public static int RECRUIT_COST = 50;
	
	/**
	 * all units recover some hp and exhaustion
	 * @param player
	 */
	public static boolean rest(Player player) {
		if (player.pay_gold(RECOVER_COST)){
			player.getSelectedUnit().heal_percent(100);
			return true;
		}
		return false;
		
	}
	
	/**
	 * get extra score
	 */
	public static boolean get_prestige(Player player) {
		if (player.pay_gold(PRESTIGE_COST)) {
			player.earn_score(10);
			return true;
		}
		return false;
	}
	
	public static boolean improve_leadership(Player player) {
		if (player.pay_gold(PRESTIGE_COST)) {
			player.getCommander().setCommand_points(player.getCommander().getCommand_points()+1);
			return true;
		}
		return false;
	}
	
	public static boolean hire_new_recruit(Player player) {
		//TODO this should be more interesting
		if (player.pay_gold(RECRUIT_COST)) {
			if (player.getGroupSize() <= player.getHeroes().size()) {
				player.getCommander().setGroup_size(player.getGroupSize()+1);
			}
			//some chance of getting a recruit from another race
			double roll = Math.random()*100;
			BattleUnit recruit;
			if (roll < 18) {
				recruit = player.getGame().unitBuilder.buildUnitbyName(CommanderChooser.COMMANDER_RACES[(int) (Math.random()*CommanderChooser.COMMANDER_RACES.length)], player);
				
			}else {
				recruit = player.getGame().unitBuilder.buildUnitbyName(player.getCommander().getType(), player);
			}
			if (player.getRecruiter() != null) {
				roll = Math.random()*100;
				if(roll < player.getRecruiter().recruit_points) {
					switch (recruit.getType()) {
					case "human":
						recruit.setImage_number(39);
						break;
					case "elf":
						recruit.setImage_number(122);
						break;
					case "dwarf":
						recruit.setImage_number(38);
						break;
					case "halfling":		
						recruit.setImage_number(31);
						break;
					default:
						break;
					}
					recruit.increase_random_stat();
				}
			}
			player.addHero(recruit);			
			return true;
		}
		return false;	
	}
	
	public static boolean earn_money(Player player) {
		if (player.pay_gold(100)) {
			//interest
			player.gainGold((int) (player.getGold()*0.05));
			return true;
		}
		return false;
		
	}
	
	public static boolean practice(Player player) {
		if (player.pay_gold(TRAIN_COST)) {
			for (int i = 0; i < player.getHeroes().size(); i++) {
				if (player.getHeroes().get(i).getFatigue() < 90) {					
					player.getHeroes().get(i).exhaust(15);
					player.getHeroes().get(i).gain_experience(player.getCommander().getDrill());					
				}
				
			}
			return true;
		}
		return false;
		
	}
	public static boolean learn_enchanting(Player player) {
		if (player.pay_gold(ENCHANT_COST)) {
			player.getCommander().setEnchant_chance(player.getCommander().getEnchant_chance()+1);
		}		
		return false;
		
	}
}
