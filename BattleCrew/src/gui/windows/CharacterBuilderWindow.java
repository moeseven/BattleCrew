package gui.windows;

import java.awt.BorderLayout;
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
		setVisible(true);
	}
	private class ButtonRaceListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			cb.scrollThroughCharRaces();
			cb.updateHero();
			cbi.repaint();
		} 
	}
	private class ButtonClassListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			cb.scrollThroughCharClasses();
			cb.updateHero();
			cbi.repaint();
		} 
	}
	private class ButtonCreateHeroListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			fcb.cb.createHero(cbi.getTf().getText());			
			gui_controller.campaign_window = new CampaignWindow(gui_controller);
			gui_controller.getGame().getCampaign().enter_next_tile();
			gui_controller.update_view();
		} 
	}
	public CommanderChooser getCb() {
		return cb;
	}
	public void setCb(CommanderChooser cb) {
		this.cb = cb;
	}
	

}
