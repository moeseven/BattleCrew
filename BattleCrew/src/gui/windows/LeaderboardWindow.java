package gui.windows;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.Leaderboard.LeaderBoardEntry;
import game.Leaderboard.Leaderboard;
import gameLogic.Game;




public class LeaderboardWindow extends X_to_main_main_menu_window{
	private JPanel jp01;
	protected Leaderboard lb;
	protected boolean askName;
	public LeaderboardWindow(ViewController gui_controller, boolean askName){
		super(gui_controller);
		this.setTitle("Leaderboard");
		this.lb = Leaderboard.loadLeaderboard();
		this.askName=askName;
		setSize(650,500);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		jp01= new LeaderBoardPanel();
		add(jp01,BorderLayout.CENTER);
		setVisible(true);
	}
	public void upadate() {
		remove(jp01);
		jp01= new LeaderBoardPanel();
		add(jp01,BorderLayout.CENTER);
		revalidate();
	}
	private class LeaderBoardPanel extends JPanel{
		private JTextField text;
		private JButton applyButton;
		public LeaderBoardPanel() {
			super();
			if (askName) {
				text=new JTextField("");	
				text.setPreferredSize(new Dimension(70, 16));
				applyButton= new JButton("ok");
				applyButton.addMouseListener(new ButtonOKListener());
				add(text);
				add(applyButton);
			}		
		}
		private class ButtonOKListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){
				askName=false;
				lb.addLeaderboardEntryInRightOrder(new LeaderBoardEntry(gui_controller.getGame()));
				lb.writeToFile();
				upadate();
			} 
		}
		protected void paintComponent(Graphics g){
			super.paintComponent(g);		
			g.setColor(Color.black);
			if (askName) {
				g.drawString("Congratulations you won the Alpha!",20, 11);
				g.drawString("Type your name to join the Leaderboard:",20, 22);
			}else {
				for (int i = 0; i < lb.getLeaderboardEntries().size(); i++) {
					g.drawString(lb.getLeaderboardEntries().get(i).getPlayerName(),140, 20+i*20);
					g.drawString("Rank "+(i+1),30, 20+i*20);
					int divisor=10;int decimalCounter=1;
					while (lb.getLeaderboardEntries().get(i).getPoints()/divisor>=1) {
						divisor*=10;	
						decimalCounter++;
					}			
					
					g.drawString(lb.getLeaderboardEntries().get(i).getPoints()+"",250-decimalCounter*6, 20+i*20);
					g.drawString(" points       (level "+lb.getLeaderboardEntries().get(i).getLevel()+" "+lb.getLeaderboardEntries().get(i).getCommander_race()+" - "+lb.getLeaderboardEntries().get(i).getCommander_class()+")",260, 20+i*20);
				}
			}
			
		}
	}

}
