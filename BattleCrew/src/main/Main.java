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
		StaticImageLoader.prepareImage("./images",game.image_scale);
		Player player = game.getPlayer();		
		player.addHero(new Warrior("Herbert", player, 1));
		player.addHero(new Warrior("Wumpus", player, 7));
		player.addHero(new Warrior("Jüdel", player, 2));
		player.getHeroes().getFirst().getEquipment().equipHand1(game.itemBuilder.buildItembyName("longsword"));
		player.getHeroes().get(1).getEquipment().equipHand1(game.itemBuilder.buildItembyName("shortbow"));
		Player defender = new Player(game,true);
		defender.addHero(new Warrior("Koshof", defender, 2));
		defender.getHeroes().getFirst().getEquipment().equipBody(game.itemBuilder.buildItembyName("leatherarmor"));
		
		player.setSelectedHero(player.getHeroes().getFirst());
		game.startExampleBattle(player, defender);
		//player.setSelectedTile(game.getBattle().getBattleField().getTiles().get(0));
		//put enemy in better place
		defender.getHeroes().getFirst().getHexTile().setUnit(null);
		defender.getHeroes().getFirst().setTile(game.getBattle().getBattleField().getTiles().get(25));
		game.getBattle().getBattleField().getTiles().get(25).setUnit(defender.getHeroes().getFirst());
		BattleWindow bw = new BattleWindow(game);
	}
	
	
}
