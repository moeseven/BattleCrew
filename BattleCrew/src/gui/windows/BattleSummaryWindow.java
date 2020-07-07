package gui.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import gameLogic.Game;


public class BattleSummaryWindow extends X_to_main_main_menu_window{
	private JButton claim_rewards_button;
	public BattleSummaryWindow(ViewController gui_controller){
		super(gui_controller);
		this.gui_controller = gui_controller;
		setSize(550, 350);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		add(new SummaryComponent(),BorderLayout.CENTER);
		claim_rewards_button = new JButton("claim rewards");
		claim_rewards_button.addMouseListener(new ButtonClaimListener());
		add(claim_rewards_button, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private class ButtonClaimListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			//claim rewards
			//exp
			int survivors = 0;
			for (int i = 0; i < gui_controller.getGame().getPlayer().getHeroes().size(); i++) {
				if (gui_controller.getGame().getPlayer().getHeroes().get(i).isBattle_participant()) {
					survivors++;
				}else {
					//regen
					gui_controller.getGame().getPlayer().getHeroes().get(i).recover();
				}
			}
			for (int i = 0; i < gui_controller.getGame().getPlayer().getHeroes().size(); i++) {
				if (gui_controller.getGame().getPlayer().getHeroes().get(i).isBattle_participant()) {
					gui_controller.getGame().getPlayer().getHeroes().get(i).gain_experience(gui_controller.getGame().getOpponent().getExperience_reward()/survivors);
				}
			}
			for (int i = 0; i < gui_controller.getGame().getPlayer().getHeroes().size(); i++) {
				gui_controller.getGame().getPlayer().getHeroes().get(i).setBattle_participant(false);
				
			}
			//gold
			gui_controller.getGame().getPlayer().gainGold(gui_controller.getGame().getOpponent().getGold_reward());
			//score
			if (gui_controller.getGame().getBattle().getWinner() == gui_controller.getGame().getPlayer()) {
				gui_controller.getGame().getPlayer().earn_score(gui_controller.getGame().getOpponent().getScore());
				gui_controller.getGame().getCampaign().enter_next_tile();
			}
			
			//
			
			gui_controller.update_view();
		} 
	}
	
	private class SummaryComponent extends JComponent{

		public SummaryComponent() {
			// TODO Auto-generated constructor stub
			
		}
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
			g.drawString("Battle Summary", 130, 50);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 22)); 
			g.drawString("total exp: " + gui_controller.getGame().getOpponent().getExperience_reward(), 10, 190);
			g.drawString("gold reward: " + gui_controller.getGame().getOpponent().getGold_reward(), 10, 210);
			g.drawString("Score: +" + gui_controller.getGame().getOpponent().getScore(), 50, 230);
		}
	
		
		
	}
	
}
