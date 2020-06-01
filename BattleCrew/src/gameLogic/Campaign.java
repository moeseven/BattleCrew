package gameLogic;

import java.util.LinkedList;

import gameLogic.campaignTiles.CampaignTile;

public class Campaign {
	private LinkedList<CampaignTile> campaign_tiles;
	private int current_tile_index = 0;
	private Game game;
	public Campaign(Game game) {
		this.game=game;
		campaign_tiles = new LinkedList<CampaignTile>();
		// add campaign tiles here
		
		enter_next_tile();
	}
	public boolean enter_next_tile() {
		if (current_tile_index < campaign_tiles.size()) {
			campaign_tiles.get(current_tile_index).enter(this);
			current_tile_index++;
			return true;
		}
		return false;
	}
	
	public CampaignTile get_current_tile() {
		return campaign_tiles.get(current_tile_index);
	}
	
}
