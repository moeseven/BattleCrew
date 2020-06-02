package gameLogic.campaignTiles;

import gameLogic.Campaign;
import gameLogic.Player;
import gameLogic.Game.GameState;

public class BattleTile extends CampaignTile {
	private Player opponent;
	public BattleTile(Player opponent) {
		this.opponent = opponent;
	}
	@Override
	public void enter(Campaign campaign) {
		// TODO Auto-generated method stub
		campaign.getGame().setOpponent(opponent);
		campaign.getGame().set_state(GameState.BattlePrepare);
	}



}
