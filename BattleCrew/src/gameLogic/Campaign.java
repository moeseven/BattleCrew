package gameLogic;

import java.io.Serializable;
import java.util.LinkedList;

import gameLogic.Game.GameState;
import gameLogic.campaignTiles.BattleTile;
import gameLogic.campaignTiles.CampaignTile;
import gameLogic.campaignTiles.TownTile;
import gameLogic.campaignTiles.VictoryTile;

public class Campaign implements Serializable{
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
		campaign_tiles.add(new VictoryTile());
		campaign_tiles.add(new TownTile());
		Player defender = new Player(game,true);
		for (int i = 0; i < 4; i++) {
			defender.addHero(game.unitBuilder.buildUnitbyName("giant_rat", defender));	
		}	
		campaign_tiles.add(new BattleTile(defender,220,200,105));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i <4; i++) {
			defender.addHero(game.unitBuilder.buildUnitbyName("zombie", defender));	
		}	
		campaign_tiles.add(new BattleTile(defender,240,250,125));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i < 5; i++) {
			BattleUnit goblin = game.unitBuilder.buildUnitbyName("goblin", defender);
			goblin.getEquipment().equipItemDirectly(game.itemBuilder.buildItembyName("skimtar"));
			goblin.getEquipment().equipItemDirectly(game.itemBuilder.buildItembyName("armor"));
			defender.addHero(goblin);	
		}
		campaign_tiles.add(new BattleTile(defender,470,350,145));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i < 2; i++) {
			BattleUnit goblin = game.unitBuilder.buildUnitbyName("thornbiter", defender);
			defender.addHero(goblin);	
		}
		campaign_tiles.add(new BattleTile(defender,270,470,165));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i < 7; i++) {
			BattleUnit goblin = game.unitBuilder.buildUnitbyName("goblin", defender);
			goblin.getEquipment().equipItemDirectly(game.itemBuilder.buildItembyName("longsword"));
			goblin.getEquipment().equipItemDirectly(game.itemBuilder.buildItembyName("armor"));
			defender.addHero(goblin);	
		}
		campaign_tiles.add(new BattleTile(defender,670,550,145));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i < 6; i++) {
			BattleUnit goblin = game.unitBuilder.buildUnitbyName("goblin", defender);
			goblin.getEquipment().equipItemDirectly(game.itemBuilder.buildItembyName("longsword"));
			goblin.getEquipment().equipItemDirectly(game.itemBuilder.buildItembyName("armor"));
			goblin.getEquipment().equipItemDirectly(game.itemBuilder.buildItembyName("buckler"));
			goblin.setImage_number(46);
			defender.addHero(goblin);	
		}
		for (int i = 0; i < 2; i++) {
			BattleUnit goblin = game.unitBuilder.buildUnitbyName("goblin", defender);
			goblin.getEquipment().equipItemDirectly(game.itemBuilder.buildItembyName("shortbow"));
			goblin.setImage_number(41);
			for (int j = 0; j < 15; j++) {
				goblin.getEquipment().equipItemDirectly(game.itemBuilder.buildItembyName("arrow"));
			}			
			defender.addHero(goblin);	
		}
		campaign_tiles.add(new BattleTile(defender,770,650,160));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		defender.addHero(game.unitBuilder.buildUnitbyName("cave_troll", defender));	
		campaign_tiles.add(new BattleTile(defender,330,750,325));
		campaign_tiles.add(new VictoryTile());
		campaign_tiles.add(new VictoryTile());
	}
	
	public boolean enter_next_tile() {
		if (current_tile_index < campaign_tiles.size()-1 && game.get_state() != GameState.GameOver) {			
			current_tile_index++;
			campaign_tiles.get(current_tile_index).enter(this);
			return true;
		}
		return false;
	}
	
	public CampaignTile get_current_tile() {
		return campaign_tiles.get(current_tile_index);
	}
	
}
