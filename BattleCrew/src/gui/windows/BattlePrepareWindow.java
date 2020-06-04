package gui.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameLogic.Game;
import gameLogic.Game.GameState;
import gameLogic.Shop;
import gui.HeroInventoryPaintComponent;
import gui.HeroStatsPaintComponent;
import gui.RectangleCampaignManagementMenu;
import gui.Refreshable_gui;
import gui.ShopinterfaceComponent;
import gui.WarriorCampaignComponent;
import gui.WarriorsReadyForBattleComponent;

public class BattlePrepareWindow extends X_to_main_main_menu_window implements Refreshable_gui{
	private JPanel warrior_inspection;
	private WarriorsReadyForBattleComponent warriors_battle;
	private WarriorCampaignComponent warriors;
	private HeroStatsPaintComponent warrior_stats;
	private HeroInventoryPaintComponent warrior_inventory;
	private int state; //0: shop, 1: battlepreparation, 2: warriors
	public BattlePrepareWindow(ViewController gc) {
		super(gc);
		state=2;
		setTitle("prepare for battle");	
		this.setSize(1300, 680);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setLayout(new BorderLayout());	
		warriors_battle=new WarriorsReadyForBattleComponent(this);
		add(warriors_battle, BorderLayout.NORTH);
		setLocationRelativeTo(null);
		setUpWarriorInspectionPanel();
	}
	
	private void setUpWarriorInspectionPanel() {
		warrior_stats= new HeroStatsPaintComponent(gui_controller.getGame().getPlayer().getSelectedUnit(),this,200,true,true,true);
		warrior_inventory= new HeroInventoryPaintComponent(this);
		warrior_inspection= new JPanel();
		warrior_inspection.setLayout(new BorderLayout());
		warrior_inspection.add(warrior_inventory, BorderLayout.EAST);
		warrior_inspection.add(warrior_stats, BorderLayout.WEST);
		add(warrior_inspection,BorderLayout.SOUTH);
	
	}
	public void refresh() {
		warrior_stats.rc.updateCaptions();
		warrior_inventory.refresh();
		warrior_stats.repaint();
		warrior_inventory.repaint();
		repaint();
	}
	
	
	
//	public void setUpFightWindow() {
//		//gui_controller.battle_window = new BattleWindow(gui_controller);
//		gui_controller.update_view();
//	}
	//getters and setters
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Game getGame() {
		return gui_controller.getGame();
	}
	@Override
	public ViewController get_gui_controller() {
		return gui_controller;
	}
}
