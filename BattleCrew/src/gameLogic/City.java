package gameLogic;

public class City {
	
	/**
	 * all units recover some hp and exhaustion
	 * @param player
	 */
	public static void rest(Player player) {
		player.recover_warriors();
		
	}
	
	/**
	 * get extra score
	 */
	public static void get_prestige(Player player) {
		player.earn_score(10);
	}
	
	public static void hire_new_recruit(Player player) {
		//TODO this should be more interesting
		if (player.getGroupSize() <= player.getHeroes().size()) {
			player.getCommander().setGroup_size(player.getGroupSize()+1);
		}
		player.addHero(player.getGame().unitBuilder.buildUnitbyName(player.getCommander().getType(), player));
	}
	
	public static void earn_money(Player player) {
		player.gainGold(50);
	}
	
	public static void practice(Player player) {
		player.getSelectedUnit().gain_experience(100);
	}
}
