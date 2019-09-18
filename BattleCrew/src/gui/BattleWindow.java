package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gameLogic.Game;


public class BattleWindow extends JFrame{
	private Game game;
	private JScrollPane sp;
	private TableComponent table_component;
	private ControlComponent control_component;
	private CampaignWindow campaign_window;
	public BattleWindow(Game game, CampaignWindow campaign_window){
		this.game=game;
		this.campaign_window=campaign_window;
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
		if(game.getBattle().getWinner()!=null){
			dispose();
			campaign_window.dispose();
			campaign_window= new CampaignWindow(game, null);
			campaign_window.setVisible(true);
		}
	}
	public String get_sprite_path() {
		return campaign_window.getSprite_path();
	}
	public Game getGame() {
		return game;
	}
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		control_component.getRectangleClicker().updateCaptions();		
		super.repaint();
		battleOver();		
	}
	
}
