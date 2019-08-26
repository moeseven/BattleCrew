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
		StaticImageLoader.prepareImage("./images");
		//MyStaticImageLoader.prepareImage();
		Game game = new Game();
		Player player = game.getPlayer();
		Warrior warrior = new Warrior("Herbert", player, 1);
		player.setSelectedHero(warrior);
		game.setBattle(new Battle(game, new Battlefield(10, 10, 15, game)));
		BattleWindow bw = new BattleWindow(game);
	}
	
	
}
