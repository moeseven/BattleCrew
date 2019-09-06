package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameLogic.Game;

public class CampaignWindow extends JFrame{
	private Game game;
	private BattleWindow bw;
	private String sprite_path="./images";
	private MainMenu mm;
	private JPanel warrior_inspection;
	private WarriorsReadyForBattleComponent warriors_battle;
	private HeroStatsPaintComponent warrior_stats;
	private HeroInventoryPaintComponent warrior_inventory;
	public CampaignWindow(Game game,MainMenu mm) {
		this.mm=mm;
		setTitle("campaign");
		this.game=game;		
		this.setSize(1300, 680);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());	
		add(new WarriorsReadyForBattleComponent(this), BorderLayout.NORTH);
		//add(new RectangleCampaignManagementMenu(),BorderLayout.CENTER);
		setLocation(10, 10);
		setUpWarriorInspectionPanel();
		this.setVisible(true);
	}
	private void setUpWarriorInspectionPanel() {
		warrior_stats= new HeroStatsPaintComponent(this);
		warrior_inventory= new HeroInventoryPaintComponent(this);
		warrior_inspection= new JPanel();
		warrior_inspection.setLayout(new BorderLayout());
		warrior_inspection.add(warrior_inventory, BorderLayout.EAST);
		warrior_inspection.add(warrior_stats, BorderLayout.WEST);
		add(warrior_inspection,BorderLayout.SOUTH);
	}
	public void refresh() {
		warrior_stats.rc.updateCaptions();
		warrior_inventory.rc.updateCaptions();
		warrior_stats.repaint();
		warrior_inventory.repaint();
		repaint();
	}
	
	
	//getters and setters
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public void setUpFightWindow() {
		bw=new BattleWindow(game,this);
		setVisible(false);
	}
	public void openMenu() {
		this.setVisible(false);
		mm.setVisible(true);
	}
	public String getSprite_path() {
		return sprite_path;
	}
	public void setSprite_path(String sprite_path) {
		this.sprite_path = sprite_path;
	}
	
}
