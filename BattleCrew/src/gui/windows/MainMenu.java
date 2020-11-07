package gui.windows;


import java.awt.BorderLayout;
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

import game.Leaderboard.Leaderboard;
import gameLogic.CommanderChooser;
import gameLogic.Game;
import gameLogic.Game.GameState;



public class MainMenu extends X_to_main_main_menu_window{
	private JPanel jp01;
	private JButton buttonStart;
	private JButton button_new_game;
	private JButton buttonCharacterBuilder;
	private JButton buttonSaveGame;
	private JButton buttonLoadGame;
	private JButton buttonShowLeaderboard;
	//protected StatsWindow gw;
	protected MainMenu mm;
	public MainMenu(ViewController gc){
		super(gc);
		this.setTitle("Menu");
		setSize(650,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		mm=this;
		jp01= new JPanel();
		buttonSaveGame= new JButton("Save");
		buttonSaveGame.addMouseListener(new ButtonSaveUserListener());
		buttonLoadGame= new JButton("Load");
		buttonLoadGame.addMouseListener(new ButtonLoadUserListener());
		buttonStart=new JButton("continue");
		buttonStart.addMouseListener(new ButtonStartListener());
		buttonShowLeaderboard=new JButton("view leaderboard");
		buttonShowLeaderboard.addMouseListener(new ButtonShowLeaderboardListener());
		buttonCharacterBuilder= new JButton("new game");
		buttonCharacterBuilder.addMouseListener(new ButtonBuildCharacterListener());
		jp01.add(buttonStart);
		jp01.add(buttonCharacterBuilder);
		//jp01.add(buttonCharacterBuilder); integrated in start of game
		jp01.add(buttonSaveGame);
		jp01.add(buttonLoadGame);
		jp01.add(buttonShowLeaderboard);
		
		add(jp01);
		setVisible(true);
	}
	private class ButtonShowLeaderboardListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			gui_controller.getGame().set_state(GameState.Leaderboard);
			try {
				gui_controller.update_view();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//gui_controller.leader_board = new LeaderboardWindow(gui_controller,false);
		} 
	}
	private class ButtonStartListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			try {
				gui_controller.start_game();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
	}
	private class ButtonBuildCharacterListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			try {
				gui_controller.new_game();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
	}
	private class ButtonSaveUserListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			gui_controller.save_game();
		}
	}
	private class ButtonLoadUserListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			gui_controller.load_game();
		} 
	}
}
