package gameLogic;

import java.util.LinkedList;

public class BattleTemplateKing extends BattleTemplate {

	@Override
	public LinkedList<String> generate_description_lines() {
		LinkedList<String> lines=new LinkedList<String>();		
		lines.add("king of the hill");
		lines.add("bounty");
		lines.add("gold: "+ game.getKing_of_the_hill().getGold_reward());
		lines.add("experience: "+game.getKing_of_the_hill().getExperience_reward());
		lines.add("score: "+ game.getKing_of_the_hill().getScore());
		return lines;
	}

	@Override
	public Player generate_enemy_general_with_army() throws Exception {
		if (game.getKing_of_the_hill() != null) {
			return game.getKing_of_the_hill();
		}else {
			Player p = super.generate_enemy_general_with_army();
			p.king_of_the_hill_player = true;
			return p;
		}
		
	}

	public BattleTemplateKing(String[] stats, Game game) throws Exception {
		super(game.builder.getMap_army().get("king"), game);
		// actual template is not used this is just for convinience
		
	}
}
