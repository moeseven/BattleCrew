package main;

import SpriteSheet.StaticImageLoader;
import gameLogic.Battle;
import gameLogic.Battlefield;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.Warrior;
import gui.BattleWindow;
import imageloader.MyStaticImageLoader;

public class Main {
	public static void main(String[] args) {				
		Game game = new Game(2);
		//StaticImageLoader.prepareImage("./images",game.image_scale);
		Player player = game.getPlayer();
		Player defender = new Player(game);
		Warrior warrior = new Warrior("Herbert", player, 1);
		player.setSelectedHero(warrior);
		game.startExampleBattle(player, defender);
		player.setSelectedHero(warrior);
		player.setSelectedTile(game.getBattle().getBattleField().getTiles().get(0));
		player.getSelectedTile().setUnit(warrior);
		warrior.setTile(player.getSelectedTile());
		BattleWindow bw = new BattleWindow(game);
	}
	
	
}
