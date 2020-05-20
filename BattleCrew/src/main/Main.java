package main;

import SpriteSheet.StaticImageLoader;
import gameLogic.Battle;
import gameLogic.Battlefield;
import gameLogic.Game;
import gameLogic.Player;
import gui.BattleWindow;
import gui.CampaignWindow;
import imageloader.MyStaticImageLoader;

public class Main {
	public static void main(String[] args) {				
		Game game = new Game(1);
		StaticImageLoader.prepareImage("./images",game.image_scale);
		Player player = game.getPlayer();		
		player.getInventory().add(game.itemBuilder.buildItembyName("shortsword"));
		player.gainGold(1000);
		for(int i=0; i<6 ;i++) {
			player.addHero(game.unitBuilder.buildUnitbyName("dwarf", player));
		}
		player.getHeroes().getFirst().getEquipment().equipHand1(game.itemBuilder.buildItembyName("longsword"));
		player.getHeroes().get(1).getEquipment().equipHand1(game.itemBuilder.buildItembyName("shortbow"));

		
		player.setSelectedHero(player.getHeroes().getFirst());
		new CampaignWindow(game, null);
		//game.startExampleBattle(player, defender);
		//player.setSelectedTile(game.getBattle().getBattleField().getTiles().get(0));
		//put enemy in better place
		//defender.getHeroes().getFirst().getHexTile().setUnit(null);
		//defender.getHeroes().getFirst().setTile(game.getBattle().getBattleField().getTiles().get(25));
		//game.getBattle().getBattleField().getTiles().get(25).setUnit(defender.getHeroes().getFirst());
		//BattleWindow bw = new BattleWindow(game,new CampaignWindow(game, null));
	}
	
	
}
