package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import gameLogic.City;
import gameLogic.Game.GameState;
import gui.windows.CampaignWindow;
import gui.windows.ViewController;
import guiRectangles.RectangleClicker;

public class RectangleCampaignManagementMenu extends JComponent implements Refreshable_gui{
	private RectangleClicker rectangle_clicker;
	private JButton shop_button,warriors_button,prepare_battle_button,battles_button;
	private JButton rest_button, train_button, earn_button, recruit_button, leadership_button, enchant_button, score_button; //city actions (use up action points)
	private JButton appoint_healer_button, appoint_smith_button, appoint_treasurer_button, appoint_champion_button, appoint_recruiter_button, appoint_commander_button;//job buttons
	private JPanel appoint_panel, pay_action_panel, view_change_panel;
	private CampaignWindow cw;
	private static int APPOINT_COST = 100;
	public RectangleCampaignManagementMenu(CampaignWindow cw) {	
		this.cw=cw;
		super.setPreferredSize(new Dimension(800,400));
		setLayout(new GridLayout(1,3));
		shop_button= new ShopButton();
		prepare_battle_button= new PrepareBattleButton();
		prepare_battle_button.setVisible(false);
		rest_button = new RestButton();
		train_button = new LearnButton();		
		recruit_button = new RecruitButton();		
		warriors_button = new WarriorsButton();
		battles_button = new BattlesButton();
		//
		appoint_champion_button = new AppointChampionButton();
		appoint_commander_button = new AppointCommanderButton();
		appoint_healer_button = new AppointHealerButton();
		appoint_recruiter_button = new AppointRecruiterButton();
		appoint_smith_button = new AppointSmithButton();
		appoint_treasurer_button = new AppointTreasurerButton();
		//
		appoint_panel = new JPanel();
		appoint_panel.setLayout(new GridLayout(3,2));
		appoint_panel.add(appoint_champion_button);
		appoint_panel.add(appoint_commander_button);
		appoint_panel.add(appoint_healer_button);
		appoint_panel.add(appoint_recruiter_button);
		appoint_panel.add(appoint_smith_button);
		appoint_panel.add(appoint_treasurer_button);
		//
		pay_action_panel = new JPanel();
		pay_action_panel.setLayout(new GridLayout(3,1));
		pay_action_panel.add(rest_button);
		pay_action_panel.add(train_button);
		pay_action_panel.add(recruit_button);
		
		view_change_panel = new JPanel();
		view_change_panel.setLayout(new GridLayout(4,1));
		view_change_panel.add(warriors_button);
		view_change_panel.add(shop_button);
		view_change_panel.add(battles_button);
		view_change_panel.add(prepare_battle_button);
		add(appoint_panel);
		add(pay_action_panel);
		add(view_change_panel);
		
		rectangle_clicker=new RectangleClicker();
	}
	private void addRectangles() {
		//shop, tavern, available battles, Warrior management,
	}
	private class RestButton extends JButton{
		public RestButton() {
			setName("recover");
			this.setText("heal"+"("+City.RECOVER_COST+"gold)");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new SpecificMouseListener());
		}
		private class SpecificMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					City.rest(cw.getGame().getPlayer());
					handle_action_point_buttons_visibility();
				}
			} 
		}
	}
//	private class EarnButton extends JButton{
//		public EarnButton() {
//			setName("earn interest");
//			this.setText("5% interest"+"("+cw.getGame().getPlayer().getCommander().getEarn_cost()+"gold)");
//			setPreferredSize(new Dimension(100, 40));
//			addMouseListener(new SpecificMouseListener());
//		}
//		private class SpecificMouseListener extends MouseAdapter{
//			public void mousePressed(MouseEvent e){	
//				if(e.getButton()==1){
//					City.earn_money(cw.getGame().getPlayer());
//					handle_action_point_buttons_visibility();
//				}
//			} 
//		}
//	}
	private class RecruitButton extends JButton{
		public RecruitButton() {
			setName("recruit");
			this.setText("recruit"+"("+City.RECRUIT_COST+"gold)");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new SpecificMouseListener());
		}
		private class SpecificMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					try {
						City.hire_new_recruit(cw.getGame().getPlayer());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					handle_action_point_buttons_visibility();
				}
				cw.gui_controller.refresh_gui();
			} 
		}
		
	}
	
//	private class LeadershipButton extends JButton{
//		public LeadershipButton() {
//			setName("leadership");
//			this.setText("improve leadership"+"("+cw.getGame().getPlayer().getCommander().getCommand_cost()+"ap)");
//			setPreferredSize(new Dimension(100, 40));
//			addMouseListener(new SpecificMouseListener());
//		}
//		private class SpecificMouseListener extends MouseAdapter{
//			public void mousePressed(MouseEvent e){	
//				if(e.getButton()==1){
//					City.improve_leadership(cw.getGame().getPlayer());
//					handle_action_point_buttons_visibility();
//				}
//			} 
//		}
//	}
//	private class ScoreButton extends JButton{
//		public ScoreButton() {
//			setName("score");
//			this.setText("score"+"("+cw.getGame().getPlayer().getCommander().getPrestige_cost()+"ap)");
//			setPreferredSize(new Dimension(100, 40));
//			addMouseListener(new SpecificMouseListener());
//		}
//		private class SpecificMouseListener extends MouseAdapter{
//			public void mousePressed(MouseEvent e){	
//				if(e.getButton()==1){
//					City.get_prestige(cw.getGame().getPlayer());
//					handle_action_point_buttons_visibility();
//				}
//			} 
//		}
//	}
//	private class EnchantButton extends JButton{
//		public EnchantButton() {
//			setName("enchant");
//			this.setText("improve enchant skill"+"("+cw.getGame().getPlayer().getCommander().getEnchant_cost()+"ap)");
//			setPreferredSize(new Dimension(100, 40));
//			addMouseListener(new SpecificMouseListener());
//		}
//		private class SpecificMouseListener extends MouseAdapter{
//			public void mousePressed(MouseEvent e){	
//				if(e.getButton()==1){
//					City.learn_enchanting(cw.getGame().getPlayer());
//					handle_action_point_buttons_visibility();
//				}
//			} 
//		}
//	}
	private class LearnButton extends JButton{
		public LearnButton() {
			setName("study");
			this.setText("drill"+"("+City.TRAIN_COST+"gold)");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new SpecificMouseListener());
		}
		private class SpecificMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					City.practice(cw.getGame().getPlayer());
					handle_action_point_buttons_visibility();
				}
			} 
		}
	}
	private void handle_action_point_buttons_visibility() {
		cw.refresh();
		
	}
	private class ShopButton extends JButton{
		public ShopButton() {
			setName("shop");
			this.setText("shop");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new ShopButtonMouseListener());
		}
		private class ShopButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					//TODO open shop here
					cw.setState(0);
					cw.showAccurateComponent();
					shop_button.setVisible(false);
					warriors_button.setVisible(true);
					prepare_battle_button.setVisible(false);
					battles_button.setVisible(true);
				}
			} 
		}
	}
	private class WarriorsButton extends JButton{
		public WarriorsButton() {
			setName("warriors");
			this.setText("warriors");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new ShopButtonMouseListener());
		}
		private class ShopButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					//TODO show warriors here
					cw.setState(2);
					cw.showAccurateComponent();
					warriors_button.setVisible(false);
					shop_button.setVisible(true);
					prepare_battle_button.setVisible(false);
					battles_button.setVisible(true);
				}
			} 
		}
	}
	private class BattlesButton extends JButton{
		public BattlesButton() {
			setName("battles");
			this.setText("battles");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new ShopButtonMouseListener());
		}
		private class ShopButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					//show battle options here
					cw.setState(1);
					cw.showAccurateComponent();
					battles_button.setVisible(false);
					prepare_battle_button.setVisible(true);
					warriors_button.setVisible(true);
					shop_button.setVisible(true);
				}
			} 
		}
	}
	private class PrepareBattleButton extends JButton{
		public PrepareBattleButton() {
			setName("leave city");
			this.setText("to battle!");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new PrepareButtonMouseListener());
		}
		private class PrepareButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					//open battle prepare for selected battle here
					//cw.gui_controller.getGame().getCampaign().enter_next_tile();
					try {
						cw.gui_controller.getGame().setOpponent(cw.gui_controller.getGame().getCampaign().getSelectedBattle().generate_enemy_general_with_army());
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					cw.gui_controller.getGame().getEnemyTable().clear_table(); // remove old units
					//place new units randomly
					ArrayList<Integer> available_positions = new ArrayList<Integer>();
					for (int i = 0; i < cw.gui_controller.getGame().getEnemyTable().getTiles().size(); i++) {
						available_positions.add(i);
					}
					for (int i = 0; i <cw.gui_controller.getGame().getOpponent().getHeroes().size(); i++) {
						int random = (int) (Math.random()*available_positions.size());
						cw.gui_controller.getGame().getEnemyTable().getTiles().get(available_positions.remove(random)).setUnit(cw.gui_controller.getGame().getOpponent().getHeroes().get(i));
					}
					cw.gui_controller.getGame().set_state(GameState.BattlePrepare);
					try {
						cw.gui_controller.update_view();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} 
		}
	}
	
	private class AppointHealerButton extends JButton{
		public AppointHealerButton() {
			setName("appoint healer");
			this.setText("appoint healer"+"("+APPOINT_COST+"gold)");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new MyMouseListener());
		}
		private class MyMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					if(cw.gui_controller.getGame().getPlayer().getSelectedUnit()!=cw.gui_controller.getGame().getPlayer().getHealer()) {
						if(cw.gui_controller.getGame().getPlayer().pay_gold(APPOINT_COST)) {
							cw.gui_controller.getGame().getPlayer().appointHealer(cw.gui_controller.getGame().getPlayer().getSelectedUnit());
							cw.refresh();
						}
					}							
				}
			} 
		}
	}
	private class AppointCommanderButton extends JButton{
		public AppointCommanderButton() {
			setName("appoint commander");
			this.setText("appoint commander"+"("+APPOINT_COST+"gold)");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new MyMouseListener());
		}
		private class MyMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(cw.gui_controller.getGame().getPlayer().getSelectedUnit()!=cw.gui_controller.getGame().getPlayer().getLeader()) {
					if(e.getButton()==1){
						if(cw.gui_controller.getGame().getPlayer().pay_gold(APPOINT_COST)) {
							cw.gui_controller.getGame().getPlayer().appointLeader(cw.gui_controller.getGame().getPlayer().getSelectedUnit());
							cw.refresh();
						}
						
					}
				}
				
			} 
		}
	}
	private class AppointRecruiterButton extends JButton{
		public AppointRecruiterButton() {
			setName("appoint recruiter");
			this.setText("appoint recruiter"+"("+APPOINT_COST+"gold)");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new MyMouseListener());
		}
		private class MyMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					if(cw.gui_controller.getGame().getPlayer().getSelectedUnit()!=cw.gui_controller.getGame().getPlayer().getRecruiter()) {
						if(cw.gui_controller.getGame().getPlayer().pay_gold(APPOINT_COST)) {
							cw.gui_controller.getGame().getPlayer().appointRecruiter(cw.gui_controller.getGame().getPlayer().getSelectedUnit());
							cw.refresh();
						}
					}
					
					
				}
			} 
		}
	}
	private class AppointSmithButton extends JButton{
		public AppointSmithButton() {
			setName("appoint smith");
			this.setText("appoint smith"+"("+APPOINT_COST+"gold)");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new MyMouseListener());
		}
		private class MyMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(cw.gui_controller.getGame().getPlayer().getSelectedUnit()!=cw.gui_controller.getGame().getPlayer().getSmith()) {
					if(e.getButton()==1){
						if(cw.gui_controller.getGame().getPlayer().pay_gold(APPOINT_COST)) {
							cw.gui_controller.getGame().getPlayer().appointSmith(cw.gui_controller.getGame().getPlayer().getSelectedUnit());
							cw.refresh();
						}
						
					}
				}
				
			} 
		}
	}
	private class AppointTreasurerButton extends JButton{
		public AppointTreasurerButton() {
			setName("appoint treasurer");
			this.setText("appoint treasurer"+"("+APPOINT_COST+"gold)");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new MyMouseListener());
		}
		private class MyMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					if(cw.gui_controller.getGame().getPlayer().getSelectedUnit()!=cw.gui_controller.getGame().getPlayer().getTreasurer()) {
						if(cw.gui_controller.getGame().getPlayer().pay_gold(APPOINT_COST)) {
							cw.gui_controller.getGame().getPlayer().appointTreasurer(cw.gui_controller.getGame().getPlayer().getSelectedUnit());
							cw.refresh();
						}
					}
					
					
				}
			} 
		}
	}
	private class AppointChampionButton extends JButton{
		public AppointChampionButton() {
			setName("appoint champion");
			this.setText("appoint champion"+"("+APPOINT_COST+"gold)");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new MyMouseListener());
		}
		private class MyMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					if(cw.gui_controller.getGame().getPlayer().getSelectedUnit()!=cw.gui_controller.getGame().getPlayer().getChampion()) {
						if(cw.gui_controller.getGame().getPlayer().pay_gold(APPOINT_COST)) {
							cw.gui_controller.getGame().getPlayer().appointChampion(cw.gui_controller.getGame().getPlayer().getSelectedUnit());
							cw.refresh();
						}
					}
					
					
				}
			} 
		}
	}
	
	@Override
	public void refresh() {
//		// TODO Auto-generated method stub
//		if (cw.getGame().getPlayer().getAction_points() <= 0) {
//			rest_button.setVisible(false);
//			
//			earn_button.setVisible(false);
//			recruit_button.setVisible(false);
//			score_button.setVisible(false);
//			enchant_button.setVisible(false);
//		}else {
//			rest_button.setVisible(true);
//			
//			earn_button.setVisible(true);
//			recruit_button.setVisible(true);
//			score_button.setVisible(true);
//			enchant_button.setVisible(true);
//		}
//		if (cw.getGame().getPlayer().getAction_points() <= 1) {
//			train_button.setVisible(false);
//		}else {
//			train_button.setVisible(true);
//		}
//		if (cw.getGame().getPlayer().getAction_points() <= 2) {
//			leadership_button.setVisible(false);
//		}else {
//			leadership_button.setVisible(true);
//		}
	}
	@Override
	public ViewController get_gui_controller() {
		return cw.gui_controller;
	}
}
