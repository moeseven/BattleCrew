package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gameLogic.Game;


public class BattleWindow extends JFrame{
	private Game game;
	private JScrollPane sp;
	private String sprite_path="./images";
	public BattleWindow(Game game){
		this.game=game;
		setExtendedState(MAXIMIZED_BOTH); 
		setLayout(new BorderLayout());
		//sp= new JScrollPane(new TableComponent(game,this));
		add(new TableComponent(game.getPlayer(),game.getBattle().getBattleField(),this),BorderLayout.CENTER);
		add(new ControlComponent(game.getPlayer(), this),BorderLayout.SOUTH);
		setVisible(true);
	}
	public void battleOver(){
		if(game.getBattle().isOver()){
			dispose();
		}
	}
	public String get_sprite_path() {
		return sprite_path;
	}
}
