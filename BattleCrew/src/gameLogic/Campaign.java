package gameLogic;

import java.util.LinkedList;

import gameLogic.campaignTiles.BattleTile;
import gameLogic.campaignTiles.CampaignTile;
import gameLogic.campaignTiles.TownTile;

public class Campaign {
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	private LinkedList<CampaignTile> campaign_tiles;
	private int current_tile_index = 0;
	private Game game;
	public Campaign(Game game) {
		this.game=game;
		campaign_tiles = new LinkedList<CampaignTile>();
		// add campaign tiles here
		standard_campaign_setup();
	}
	
	private void standard_campaign_setup() {
		campaign_tiles.add(new TownTile());
		Player defender = new Player(game,true);
		for (int i = 0; i < 10; i++) {
			defender.addHero(game.unitBuilder.buildUnitbyName("giant_rat", defender));	
		}	
		campaign_tiles.add(new BattleTile(defender,20,200,105));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i < 10; i++) {
			defender.addHero(game.unitBuilder.buildUnitbyName("zombie", defender));	
		}	
		campaign_tiles.add(new BattleTile(defender,70,250,125));
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
