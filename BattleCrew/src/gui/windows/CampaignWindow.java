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
import gui.BattleOptionsCampaignComponent;
import gui.HeroInventoryPaintComponent;
import gui.HeroStatsPaintComponent;
import gui.MagicSchoolComponent;
import gui.RectangleCampaignManagementMenu;
import gui.Refreshable_gui;
import gui.ShopItmesComponent;
import gui.ShopinterfaceComponent;
import gui.WarriorCampaignComponent;
import gui.WarriorsReadyForBattleComponent;


public class CampaignWindow extends X_to_main_main_menu_window implements Refreshable_gui{
	private JPanel warrior_inspection;
	private ShopItmesComponent shop_items;
	private WarriorCampaignComponent warriors;
	private MagicSchoolComponent magics;
	private BattleOptionsCampaignComponent battles;
	//private 
	private HeroStatsPaintComponent warrior_stats;
	private HeroInventoryPaintComponent warrior_inventory;
	private RectangleCampaignManagementMenu buttons;
	private int state; //0: shop, 1: battlepreparation, 2: warriors
	public CampaignWindow(ViewController gc) {
		super(gc);
		state=2;
		setTitle("campaign");	
		this.setSize(1300, 780);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());	
		shop_items = new ShopItmesComponent(this);
		magics = new MagicSchoolComponent(this);
		warriors = new WarriorCampaignComponent(this);
		battles = new BattleOptionsCampaignComponent(this);
		showAccurateComponent();
		add(buttons = new RectangleCampaignManagementMenu(this),BorderLayout.CENTER);
		setUpWarriorInspectionPanel();
	}
	public void showAccurateComponent() {
		//remove(shop);
		remove(shop_items);
		remove(warriors);
		remove(battles);
		remove(magics);
		if (state==0) {
			//add(shop,BorderLayout.NORTH);
			add(shop_items,BorderLayout.NORTH);
		}else {
			if (state==1) {
				add(battles,BorderLayout.NORTH);
			}else {
				if (state == 2) {
					add(warriors,BorderLayout.NORTH);
				}else {
					if (state == 3) {
						add(magics,BorderLayout.NORTH);
					}
				}
			}
		}
		repaint();
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
		buttons.refresh();
		warrior_stats.rc.updateCaptions();
		warrior_inventory.refresh();
		warrior_stats.repaint();
		warrior_inventory.repaint();
		warriors.refresh();
		repaint();
	}
	
	
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
