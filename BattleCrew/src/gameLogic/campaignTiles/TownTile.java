package gameLogic.campaignTiles;

import gameLogic.Campaign;
import gameLogic.Game.GameState;

public class TownTile extends CampaignTile {

	@Override
	public void enter(Campaign campaign) {
		// TODO Auto-generated method stub
		campaign.getGame().getPlayer().recover_warriors();
		campaign.getGame().set_state(GameState.City);
	}



}
