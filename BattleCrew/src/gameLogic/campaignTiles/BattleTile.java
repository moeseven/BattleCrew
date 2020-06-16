package gameLogic.campaignTiles;

import java.util.ArrayList;

import gameLogic.Campaign;
import gameLogic.Player;
import gameLogic.Game.GameState;

public class BattleTile extends CampaignTile {
	private Player opponent;
	public BattleTile(Player opponent, int gold_reward, int experience_reward, int score) {
		this.opponent = opponent;
		opponent.setGold_reward(gold_reward);
		opponent.setScore(score);
		opponent.setExperience_reward(experience_reward);
	}
	@Override
	public void enter(Campaign campaign) {
		// TODO Auto-generated method stub
		campaign.getGame().setOpponent(opponent);
		campaign.getGame().getEnemyTable().clear_table(); // remove old units
		//place new units randomly
		ArrayList<Integer> available_positions = new ArrayList<Integer>();
		for (int i = 0; i < campaign.getGame().getEnemyTable().getTiles().size(); i++) {
			available_positions.add(i);
		}
		for (int i = 0; i < opponent.getHeroes().size(); i++) {
			int random = (int) (Math.random()*available_positions.size());
			campaign.getGame().getEnemyTable().getTiles().get(available_positions.remove(random)).setUnit(opponent.getHeroes().get(i));
		}
		campaign.getGame().set_state(GameState.BattlePrepare);
	}



}
