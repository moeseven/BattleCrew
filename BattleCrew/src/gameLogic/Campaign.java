package gameLogic;

import java.io.Serializable;
import java.util.LinkedList;

import gameLogic.Game.GameState;
import gameLogic.campaignTiles.BattleTile;
import gameLogic.campaignTiles.CampaignTile;
import gameLogic.campaignTiles.TownTile;
import gameLogic.campaignTiles.VictoryTile;

public class Campaign implements Serializable{
	public int getRound_counter() {
		return round_counter;
	}

	public void setRound_counter(int round_counter) {
		this.round_counter = round_counter;
	}

	public LinkedList<BattleTemplate> getBattle_templates() {
		return battle_templates;
	}

	public void setBattle_templates(LinkedList<BattleTemplate> battle_templates) {
		this.battle_templates = battle_templates;
	}

	public BattleTemplate getSelectedBattle() {
		return selectedBattle;
	}

	public void setSelectedBattle(BattleTemplate selectedBattle) {
		this.selectedBattle = selectedBattle;
	}
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	private static int CAMPAIGN_ROUNDS = 15;
	private LinkedList<CampaignTile> campaign_tiles;
	private LinkedList<BattleTemplate> battle_templates;
	private int round_counter = 0;
	private BattleTemplate selectedBattle;
	private Game game;
	public Campaign(Game game) throws Exception {
		this.game=game;
		campaign_tiles = new LinkedList<CampaignTile>();
		battle_templates = new LinkedList<BattleTemplate>();
		// add campaign tiles here
		standard_campaign_setup();
	}
	
	private void standard_campaign_setup() throws Exception {
		battle_templates.add(game.builder.buildBattleTemplate("goblin group"));
		battle_templates.add(game.builder.buildBattleTemplate("undead"));
		battle_templates.add(game.builder.buildBattleTemplate("animals"));
		campaign_tiles.add(new VictoryTile());
		campaign_tiles.add(new TownTile());
		Player defender = new Player(game,true);
		for (int i = 0; i < 4; i++) {
			defender.addHero(game.builder.buildUnitbyName("giant_rat", defender));	
		}	
		campaign_tiles.add(new BattleTile(defender,220,200,105));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i <4; i++) {
			defender.addHero(game.builder.buildUnitbyName("zombie", defender));	
		}	
		campaign_tiles.add(new BattleTile(defender,240,250,125));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i < 5; i++) {
			BattleUnit goblin = game.builder.buildUnitbyName("goblin", defender);
			goblin.getEquipment().equipItemDirectly(game.builder.buildItembyName("skimtar"));
			goblin.getEquipment().equipItemDirectly(game.builder.buildItembyName("armor"));
			defender.addHero(goblin);	
		}
		campaign_tiles.add(new BattleTile(defender,470,350,145));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i < 2; i++) {
			BattleUnit goblin = game.builder.buildUnitbyName("thornbiter", defender);
			defender.addHero(goblin);	
		}
		campaign_tiles.add(new BattleTile(defender,270,470,165));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i < 7; i++) {
			BattleUnit goblin = game.builder.buildUnitbyName("goblin", defender);
			goblin.getEquipment().equipItemDirectly(game.builder.buildItembyName("longsword"));
			goblin.getEquipment().equipItemDirectly(game.builder.buildItembyName("armor"));
			defender.addHero(goblin);	
		}
		campaign_tiles.add(new BattleTile(defender,670,550,145));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		for (int i = 0; i < 6; i++) {
			BattleUnit goblin = game.builder.buildUnitbyName("goblin", defender);
			goblin.getEquipment().equipItemDirectly(game.builder.buildItembyName("longsword"));
			goblin.getEquipment().equipItemDirectly(game.builder.buildItembyName("armor"));
			goblin.getEquipment().equipItemDirectly(game.builder.buildItembyName("buckler"));
			goblin.setImage_number(46);
			defender.addHero(goblin);	
		}
		for (int i = 0; i < 2; i++) {
			BattleUnit goblin = game.builder.buildUnitbyName("goblin", defender);
			goblin.getEquipment().equipItemDirectly(game.builder.buildItembyName("shortbow"));
			goblin.setImage_number(41);
			for (int j = 0; j < 15; j++) {
				goblin.getEquipment().equipItemDirectly(game.builder.buildItembyName("arrow"));
			}			
			defender.addHero(goblin);	
		}
		campaign_tiles.add(new BattleTile(defender,770,650,160));
		campaign_tiles.add(new TownTile());
		defender = new Player(game,true);
		defender.addHero(game.builder.buildUnitbyName("cave_troll", defender));	
		campaign_tiles.add(new BattleTile(defender,330,750,325));
		campaign_tiles.add(new VictoryTile());
		campaign_tiles.add(new VictoryTile());
	}
	
	public boolean enter_next_tile() {
		if (round_counter < campaign_tiles.size()-1 && game.get_state() != GameState.GameOver) {			
			round_counter++;
			if (round_counter > CAMPAIGN_ROUNDS) {
				campaign_tiles.get(0).enter(this);
			} else {
				campaign_tiles.get(1).enter(this);
			}
			
			return true;
		}
		return false;
	}
	
	public CampaignTile get_current_tile() {
		return campaign_tiles.get(round_counter);
	}
	
}
