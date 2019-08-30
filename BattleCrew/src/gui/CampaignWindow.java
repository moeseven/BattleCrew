package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameLogic.Game;

public class CampaignWindow extends JFrame{
	private Game game;
	private BattleWindow bw;
	private String sprite_path="./images";
	private MainMenu mm;
	public CampaignWindow(Game game,MainMenu mm) {
		this.mm=mm;
		setTitle("room");
		this.game=game;		
		this.setSize(1300, 680);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(new HeroInventoryPaintComponent(this), BorderLayout.CENTER);
		setLocation(10, 10);
		this.setVisible(true);
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public void setUpFightWindow() {
		bw=new BattleWindow(game,this);
		setVisible(false);
	}
	public void openMenu() {
		this.setVisible(false);
		mm.setVisible(true);
	}
	public String getSprite_path() {
		return sprite_path;
	}
	public void setSprite_path(String sprite_path) {
		this.sprite_path = sprite_path;
	}
	
}
