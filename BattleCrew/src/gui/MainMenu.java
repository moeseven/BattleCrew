package gui;


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



public class MainMenu extends JFrame{
	private JPanel jp01;
	private JButton buttonStart;
	private JButton buttonCharacterBuilder;
	private JButton buttonSaveGame;
	private JButton buttonLoadGame;
	private JButton buttonShowLeaderboard;
	private Game game;
	//protected StatsWindow gw;
	protected CampaignWindow campaign_window;
	protected MainMenu mm;
	public MainMenu(){
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
		buttonStart=new JButton("Play");
		buttonStart.addMouseListener(new ButtonStartListener());
		buttonShowLeaderboard=new JButton("view leaderboard");
		buttonShowLeaderboard.addMouseListener(new ButtonShowLeaderboardListener());
		buttonCharacterBuilder= new JButton("Charakter Builder");
		buttonCharacterBuilder.addMouseListener(new ButtonBuildCharacterListener());
		jp01.add(buttonStart);
		//jp01.add(buttonCharacterBuilder); integrated in start of game
		jp01.add(buttonSaveGame);
		jp01.add(buttonLoadGame);
		jp01.add(buttonShowLeaderboard);
		add(jp01);
		game = new Game(1);
		setVisible(true);
	}
	private class ButtonShowLeaderboardListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			new LeaderboardWindow(Leaderboard.loadLeaderboard(),game,false);
		} 
	}
	private class ButtonStartListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			if(game.getPlayer().getHeroes().size()>0) {
				game.getPlayer().setSelectedHero(game.getPlayer().getHeroes().getFirst());		
				campaign_window=new CampaignWindow(game,mm);
				
			}else {
				new FrameCharacterBuilder(new CommanderChooser(game),mm);
			}
			mm.setVisible(false);
		} 
	}
	private class ButtonBuildCharacterListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			new FrameCharacterBuilder(new CommanderChooser(game),mm);
		} 
	}
	private class ButtonSaveUserListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			//save game;
			ObjectOutputStream oos=null;
			try {
				oos = new ObjectOutputStream(new FileOutputStream("./saves/game.dat"));
				oos.writeObject(game);
			} catch (FileNotFoundException e1) {			
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			finally{
				try {
					oos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} 
	}
	private class ButtonLoadUserListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			//load from file;
			ObjectInputStream ois=null;
			try {
				ois=new ObjectInputStream(new FileInputStream("./saves/game.dat"));
				game=(Game)ois.readObject();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally{
				try {
					ois.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		} 
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
}
