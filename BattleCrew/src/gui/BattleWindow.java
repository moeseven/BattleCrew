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
	private TableComponent table_component;
	private ControlComponent control_component;
	public BattleWindow(Game game){
		this.game=game;
		setExtendedState(MAXIMIZED_BOTH); 
		setLayout(new BorderLayout());
		//sp= new JScrollPane(new TableComponent(game,this));
		control_component=new ControlComponent(game.getPlayer(), this);
		table_component= new TableComponent(game.getPlayer(),game.getBattle().getBattleField(),this);
		add(table_component,BorderLayout.CENTER);
		add(control_component,BorderLayout.SOUTH);
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
	public Game getGame() {
		return game;
	}
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		control_component.getRectangleClicker().updateCaptions();
		super.repaint();
		
	}
	
}
