package gameLogic.campaignTiles;

import gameLogic.Campaign;
import gameLogic.Game.GameState;

public class VictoryTile extends CampaignTile {

	@Override
	public void enter(Campaign campaign) {
		// TODO Auto-generated method stub
		campaign.getGame().getPlayer().earn_score(campaign.getGame().getPlayer().getScore());
		campaign.getGame().set_state(GameState.GameOver);
	}



}
