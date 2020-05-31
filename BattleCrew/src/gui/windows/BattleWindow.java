package gui.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import gameLogic.Game;
import gameLogic.Game.GameState;
import gui.ControlComponent;
import gui.Refreshable_gui;
import gui.TableComponent;
import gui.windows.ViewController.View;


public class BattleWindow extends X_to_main_main_menu_window implements ActionListener, Refreshable_gui{
	private boolean paused = false;
	private int battle_tick_time = 1000;
	private Timer battle_tick_timer = new Timer(battle_tick_time, this);
	private JScrollPane sp;
	private TableComponent table_component;
	private ControlComponent control_component;
	public BattleWindow(ViewController gc){
		super(gc);
		setExtendedState(MAXIMIZED_BOTH); 
		setLayout(new BorderLayout());
		//sp= new JScrollPane(new TableComponent(game,this));
		control_component=new ControlComponent(gui_controller.getGame().getPlayer(), this);
		table_component= new TableComponent(gui_controller.getGame().getPlayer(),gui_controller.getGame().getBattle().getBattleField(),this);
		add(table_component,BorderLayout.CENTER);
		add(control_component,BorderLayout.EAST);
		
		battle_tick_timer.start();
	}
	public void battleOver(){
		if(gui_controller.getGame().getBattle().getWinner()!=null){
//			dispose();
//			gui_controller.campaign_window.dispose();
//			gui_controller.campaign_window = new CampaignWindow(gui_controller);
			gui_controller.setView(View.City);
			battle_tick_timer.stop();
		}
	}
	public void refresh() {
		control_component.refresh();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub	
		if (gui_controller.getGame().getBattle().started) {
			gui_controller.getGame().getBattle().battle_tick();		
		}
		battle_tick_timer.restart();
		control_component.refresh();
		control_component.repaint();
		
		repaint();
		battleOver();
	}
	public void pause() {
		paused=true;
		battle_tick_timer.stop();
	}
	public void unpause() {
		paused=false;
		battle_tick_timer.restart();
	}
	public boolean isPaused() {
		return paused;
	}
	
}
