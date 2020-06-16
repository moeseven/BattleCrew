package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import gameLogic.City;
import gui.windows.CampaignWindow;
import gui.windows.ViewController;
import guiRectangles.RectangleClicker;

public class RectangleCampaignManagementMenu extends JComponent implements Refreshable_gui{
	private RectangleClicker rectangle_clicker;
	private JButton shop_button,warriors_button,prepare_battle_button;
	private JButton rest_button, train_button, earn_button, recruit_button, leadership_button, enchant_button, score_button; //city actions (use up action points)
	private CampaignWindow cw;
	public RectangleCampaignManagementMenu(CampaignWindow cw) {	
		this.cw=cw;
		super.setPreferredSize(new Dimension(800,400));
		setLayout(new GridLayout(3,4));
		shop_button= new ShopButton();
		prepare_battle_button= new PrepareBattleButton();
		rest_button = new RestButton();
		train_button = new LearnButton();
		earn_button = new EarnButton();
		recruit_button = new RecruitButton();
		score_button = new ScoreButton();
		warriors_button = new WarriorsButton();
		leadership_button = new LeadershipButton();
		enchant_button = new EnchantButton();
		add(warriors_button);
		add(shop_button);
		add(prepare_battle_button);
		add(rest_button);
		add(train_button);
		add(earn_button);
		add(enchant_button);
		add(recruit_button);
		add(leadership_button);
		add(score_button);
		rectangle_clicker=new RectangleClicker();
	}
	private void addRectangles() {
		//shop, tavern, available battles, Warrior management,
	}
	private class RestButton extends JButton{
		public RestButton() {
			setName("recover");
			this.setText("recover");
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
	private class EarnButton extends JButton{
		public EarnButton() {
			setName("earn gold");
			this.setText("earn gold");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new SpecificMouseListener());
		}
		private class SpecificMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					City.earn_money(cw.getGame().getPlayer());
					handle_action_point_buttons_visibility();
				}
			} 
		}
	}
	private class RecruitButton extends JButton{
		public RecruitButton() {
			setName("recruit");
			this.setText("recruit");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new SpecificMouseListener());
		}
		private class SpecificMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					City.hire_new_recruit(cw.getGame().getPlayer());
					handle_action_point_buttons_visibility();
				}
				cw.gui_controller.refresh_gui();
			} 
		}
		
	}
	
	private class LeadershipButton extends JButton{
		public LeadershipButton() {
			setName("leadership");
			this.setText("improve leadership");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new SpecificMouseListener());
		}
		private class SpecificMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					City.improve_leadership(cw.getGame().getPlayer());
					handle_action_point_buttons_visibility();
				}
			} 
		}
	}
	private class ScoreButton extends JButton{
		public ScoreButton() {
			setName("score");
			this.setText("score");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new SpecificMouseListener());
		}
		private class SpecificMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					City.get_prestige(cw.getGame().getPlayer());
					handle_action_point_buttons_visibility();
				}
			} 
		}
	}
	private class EnchantButton extends JButton{
		public EnchantButton() {
			setName("enchant");
			this.setText("improve enchant skill");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new SpecificMouseListener());
		}
		private class SpecificMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					City.learn_enchanting(cw.getGame().getPlayer());
					handle_action_point_buttons_visibility();
				}
			} 
		}
	}
	private class LearnButton extends JButton{
		public LearnButton() {
			setName("study");
			this.setText("training");
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
					prepare_battle_button.setVisible(true);
					warriors_button.setVisible(true);
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
					//TODO open shop here
					cw.setState(2);
					cw.showAccurateComponent();
					warriors_button.setVisible(false);
					shop_button.setVisible(true);
					
				}
			} 
		}
	}
	private class PrepareBattleButton extends JButton{
		public PrepareBattleButton() {
			setName("leave city");
			this.setText("leave city");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new PrepareButtonMouseListener());
		}
		private class PrepareButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					//TODO open battle prepare here
					cw.gui_controller.getGame().getCampaign().enter_next_tile();
					cw.gui_controller.update_view();
				}
			} 
		}
	}
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		if (cw.getGame().getPlayer().getAction_points() <= 0) {
			rest_button.setVisible(false);
			train_button.setVisible(false);
			earn_button.setVisible(false);
			recruit_button.setVisible(false);
			score_button.setVisible(false);
			enchant_button.setVisible(false);
		}else {
			rest_button.setVisible(true);
			train_button.setVisible(true);
			earn_button.setVisible(true);
			recruit_button.setVisible(true);
			score_button.setVisible(true);
			enchant_button.setVisible(true);
		}
		if (cw.getGame().getPlayer().getAction_points() <= 2) {
			leadership_button.setVisible(false);
		}else {
			leadership_button.setVisible(true);
		}
	}
	@Override
	public ViewController get_gui_controller() {
		return cw.gui_controller;
	}
}
