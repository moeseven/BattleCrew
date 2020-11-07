package gui.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import SpriteSheet.StaticImageLoader;
import gameLogic.CommanderChooser;
import gameLogic.Game;
import gameLogic.Game.GameState;
import gameLogic.Player;
import gui.CharcterBuilderInfoComponent;

public class CharacterBuilderWindow extends X_to_main_main_menu_window{
	private JPanel jp01;
	private JPanel jpDraw;
	private JButton buttonNextRace;
	private JButton buttonNextClass;
	private JButton buttonCreateHero;
	private CommanderChooser cb;
	protected CharacterBuilderWindow fcb;
	private CharcterBuilderInfoComponent cbi;

	public CharacterBuilderWindow(CommanderChooser cb, ViewController gc) {
		super(gc);
		this.cb=cb;
		fcb=this;
		this.setTitle("character builder");
		cbi=new CharcterBuilderInfoComponent(this);
		setSize(650,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		jp01= new JPanel();
		buttonNextRace= new JButton("race");
		buttonNextRace.addMouseListener(new ButtonRaceListener());
		jp01.add(buttonNextRace);
		buttonNextClass= new JButton("class");
		buttonNextClass.addMouseListener(new ButtonClassListener());
		jp01.add(buttonNextClass);
		buttonCreateHero= new JButton("create");
		buttonCreateHero.addMouseListener(new ButtonCreateHeroListener());
		jp01.add(buttonCreateHero);
		add(jp01, BorderLayout.SOUTH);		
		jpDraw= new JPanel();
		jpDraw.setLayout(new BorderLayout());
		jpDraw.add(cbi, BorderLayout.CENTER);
		add(jpDraw, BorderLayout.CENTER);
		getContentPane().setBackground(Color.black);
		setVisible(true);
	}
	private class ButtonRaceListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			cb.scrollThroughCharRaces();
			try {
				cb.updateHero();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cbi.repaint();
		} 
	}
	private class ButtonClassListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			cb.scrollThroughCharClasses();
			try {
				cb.updateHero();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cbi.repaint();
		} 
	}
	private class ButtonCreateHeroListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			try {
				fcb.cb.createHero(cbi.getTf().getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			//careful
			gui_controller.campaign_window = new CampaignWindow(gui_controller);
			gui_controller.getGame().getCampaign().enter_next_tile();
			try {
				gui_controller.update_view();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
	}
	public CommanderChooser getCb() {
		return cb;
	}
	public void setCb(CommanderChooser cb) {
		this.cb = cb;
	}
	

}
