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
import gui.windows.ViewController.View;

public class CampaignWindow extends X_to_main_main_menu_window implements Refreshable_gui{
	private JPanel warrior_inspection;
	private WarriorsReadyForBattleComponent warriors_battle;
	private ShopinterfaceComponent shop;
	private WarriorCampaignComponent warriors;
	private HeroStatsPaintComponent warrior_stats;
	private HeroInventoryPaintComponent warrior_inventory;
	private int state; //0: shop, 1: battlepreparation, 2: warriors
	public CampaignWindow(ViewController gc) {
		super(gc);
		state=2;
		setTitle("campaign");	
		this.setSize(1300, 680);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setLayout(new BorderLayout());	
		warriors_battle=new WarriorsReadyForBattleComponent(this);
		shop= new ShopinterfaceComponent(this, new Shop(gc.getGame()));
		warriors = new WarriorCampaignComponent(this);
		showAccurateComponent();
		add(new RectangleCampaignManagementMenu(this),BorderLayout.CENTER);
		setLocation(10, 10);
		setUpWarriorInspectionPanel();
	}
	public void showAccurateComponent() {
		remove(warriors_battle);
		remove(shop);
		remove(warriors);
		if (state==0) {
			add(shop,BorderLayout.NORTH);
		}else {
			if (state==1) {
				add(warriors_battle, BorderLayout.NORTH);
			}else {
				if (state == 2) {
					add(warriors,BorderLayout.NORTH);
				}
			}
		}
		repaint();
	}
	private void setUpWarriorInspectionPanel() {
		warrior_stats= new HeroStatsPaintComponent(gui_controller.getGame().getPlayer().getSelectedUnit(),this,200,true,true);
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
	
	
	//getters and setters
	public void setUpFightWindow() {
		//gui_controller.battle_window = new BattleWindow(gui_controller);
		gui_controller.setView(View.Battle);
	}
	
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