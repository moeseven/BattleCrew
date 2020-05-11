package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import gameLogic.Game;


public class BattleWindow extends JFrame implements ActionListener{
	private int battle_tick_time = 1000;
	private Timer battle_tick_timer = new Timer(battle_tick_time, this);
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
		battle_tick_timer.start();
	}
	public void battleOver(){
		if(game.getBattle().getWinner()!=null){
			dispose();
			campaign_window.dispose();
			campaign_window= new CampaignWindow(game, null);
			campaign_window.setVisible(true);
			battle_tick_timer.stop();
			campaign_window.destory_battle_window();
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
	
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub	
		if (game.getBattle().started) {
			game.getBattle().battle_tick();		
		}
		battle_tick_timer.restart();
		repaint();
		battleOver();
	}
	
}
