package gameLogic.campaignTiles;

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
		campaign.getGame().set_state(GameState.BattlePrepare);
	}



}
