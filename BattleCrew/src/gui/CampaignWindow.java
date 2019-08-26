package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameLogic.Game;

public class CampaignWindow extends JFrame{
	private Game game;
	private StatsWindow sw;
	private BattleWindow bw;
	private MainMenu mm;
	private GuiRoom guiRoom;
	public CampaignWindow(Game game,MainMenu mm) {
		this.mm=mm;
		setTitle("room");
		this.game=game;		
		this.setSize(1300, 680);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocation(10, 10);
		guiRoom=new GuiRoom(this);
		add(guiRoom,BorderLayout.NORTH);
		this.setVisible(true);
		this.sw=new StatsWindow(game,this);
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public void setUpFightWindow() {
		bw=new BattleWindow(game,this);
	}
	public void windowswitch() {
		if(bw!=null){
			bw.setVisible(false);
		}		
		sw.myUpdate();
		sw.setVisible(true);
		this.setVisible(false);
	}
	public void openMenu() {
		this.setVisible(false);
		mm.setVisible(true);
	}
	public StatsWindow getSw() {
		return sw;
	}
	public void setSw(StatsWindow sw) {
		this.sw = sw;
	}
	public FightWindow getFw() {
		return bw;
	}
	public void setFw(BattleWindow bw) {
		this.bw = bw;
	}
	public GuiRoom getGuiRoom() {
		return guiRoom;
	}
	public void setGr(GuiRoom gr) {
		this.guiRoom = gr;
	}
	
}
