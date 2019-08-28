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
		player.addHero(new Warrior("Herbert", player, 1));
		player.addHero(new Warrior("Wumpus", player, 4));
		player.addHero(new Warrior("Jüdel", player, 2));
		Player defender = new Player(game,true);
		defender.addHero(new Warrior("Koshof", defender, 22));
		player.setSelectedHero(player.getHeroes().getFirst());
		game.startExampleBattle(player, defender);
		player.setSelectedTile(game.getBattle().getBattleField().getTiles().get(0));
		BattleWindow bw = new BattleWindow(game);
	}
	
	
}
