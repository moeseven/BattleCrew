package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import SpriteSheet.StaticImageLoader;
import gameLogic.Ability;
import gameLogic.Player;
import gui.windows.BattleWindow;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;
import imageloader.MyStaticImageLoader;
import javafx.scene.layout.Border;

public class ControlComponent extends JComponent {
	private Player player;
	protected int height;
	private BattleWindow battle_window;
	private LogComponent log;
	private PauseButton pause_button;
	private HeroStatsPaintComponent hero_stat;
	public ControlComponent(Player player,BattleWindow bw){
		this.player=player;
		this.battle_window=bw;
		height=13;
		setLayout(new BorderLayout());
		log=new LogComponent(player.getGame().log);
		add(log,BorderLayout.SOUTH);
		pause_button = new PauseButton();
		add(pause_button, BorderLayout.NORTH);
		hero_stat = new HeroStatsPaintComponent(player.getSelectedUnit(),bw, 0, false);
		add(hero_stat, BorderLayout.CENTER);
		setBorder(new LineBorder(Color.GREEN));
		super.setPreferredSize(new Dimension(190,(height-1)*15+500));
		
		//addAbilities();
		addMouseListener(new MyMouseListener());
		setVisible(true);
	}
	private void pause_unpause() {
		if (battle_window.isPaused()) {
			battle_window.unpause();
			pause_button.setText("pause");
		}else {
			battle_window.pause();
			pause_button.setText("play");
		}
	}
	private class PauseButton extends JButton{
		public PauseButton() {
			setName("pause");
			this.setText("pause");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new PauseButtonMouseListener());
		}
		private class PauseButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					pause_unpause();					
				}
			} 
		}
	}
	public void refresh() {
		hero_stat.rc.updateCaptions();
	}
//	public void addAbilities() {
//		int RECT_X_SIZE=190;
//		int RECT_Y_SIZE=120;
//		int ABILITIES_START_X=10;
//		int ABILITIES_START_Y=280+height*15;
//		rectangle_clicker= new RectangleClicker();
//		ClickableRectangle moveangle= new ClickableRectangle("move", ABILITIES_START_X, ABILITIES_START_Y, RECT_X_SIZE, RECT_Y_SIZE) {			
//			@Override
//			public void updateCaption() {
//				setCaption(new LinkedList<String>());
//				getCaption().add(name);
//				getCaption().add(" ");
//				getCaption().add("stamina_cost: "+((int)(10*player.getSelectedUnit().getModifiedStaminaCost(player.getSelectedUnit().getMoveStamina_cost())))/10.0);
//			}
//			
//			@Override
//			public void onClick(MouseEvent arg0) {
//				player.getSelectedUnit().setSelected_ability(null);
//				rectangle_clicker.setSelectedRectangle(this);
//				updateCaption();
//			}
//		};
//		rectangle_clicker.addRect(moveangle);
//		rectangle_clicker.setSelectedRectangle(moveangle);
//		rectangle_clicker.addRect(new ClickableRectangle("use mainhand", ABILITIES_START_X, ABILITIES_START_Y+RECT_Y_SIZE*(1), RECT_X_SIZE, RECT_Y_SIZE) {			
//			@Override
//			public void updateCaption() {
//				caption=generateAbilityRectangleCaption(player.getSelectedUnit().getWeaponAbility());
//			}			
//			@Override
//			public void onClick(MouseEvent e) {
//				if (player.getSelectedUnit().getWeaponAbility().isAfter_roll_status()) {
//					if (e.getButton()==1) {
//						player.getSelectedUnit().getWeaponAbility().applyAbilityAfterRoll();
//					}else {
//						if (e.getButton()==3) {
//							// reroll if dexterity is high player.getSelectedUnit().getWeaponAbility().
//							player.getSelectedUnit().getWeaponAbility().tryOffesniveReroll();
//						}
//					}						
//				}else {
//					rectangle_clicker.setSelectedRectangle(this);
//					player.getSelectedUnit().setSelected_ability(player.getSelectedUnit().getWeaponAbility());
//				}
//				
//				
//				
//			}
//		});
//		for (int i = 0; i < player.getSelectedUnit().getAbilities().size(); i++) {
//			rectangle_clicker.addRect(new AbilityRectangle(player.getSelectedUnit().getAbilities().get(i),player, ABILITIES_START_X+(2+i)*RECT_X_SIZE, 20, RECT_X_SIZE, RECT_Y_SIZE));
//		}
//		rectangle_clicker.addRect(new ClickableRectangle("end turn", ABILITIES_START_X+RECT_X_SIZE*(player.getSelectedUnit().getAbilities().size()+2), 20, RECT_X_SIZE, RECT_Y_SIZE) {
//			
//			@Override
//			public void updateCaption() {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onClick(MouseEvent e) {
//				// TODO Auto-generated method stub
//				//select next active unit
//				battle_window.getGame().getBattle().endActiveWarriorTurn();
//				rectangle_clicker.setSelectedRectangle(this);
//			}
//		});
//		for (int i = 0; i < rectangle_clicker.getRectAngles().size(); i++) {
//			rectangle_clicker.getRectAngles().get(i).updateCaption();
//		}
//	}
//	public LinkedList<String> generateAbilityRectangleCaption(Ability ability){
//		LinkedList<String> caption=new LinkedList<String>();
//		caption.add(ability.getName());		
//		if (ability.isUsed()) {			
//			if (ability.isAfter_roll_status()) {
//				if (ability.isMissed()) {	
//					caption.add("missed!");
//					caption.add("(hit chance depends on dexterity)");
//				}else {
//					if (ability.getDamage_target()>0) {	
//						int damage=(ability.getDamage_target()+ability.getOffensive_roll().roll_value-ability.getDefensive_roll().roll_value-ability.getDefensive_roll().warrior.getArmor());
//						String rollString="damage rolls: ("+ability.getDamage_target();
//						if (ability.getOffensive_roll().roll_value>=0) {
//							rollString+=" +"+ability.getOffensive_roll().roll_value;
//						}else {
//							rollString+=ability.getOffensive_roll().roll_value;
//						}
//						rollString+=") -> ("+ability.getDefensive_roll().warrior.getArmor()+" + "+ability.getDefensive_roll().roll_value+")  = "+damage;
//						caption.add(rollString);
//					}
//				}
//				
//			}else {
//				if (ability.getDamage_target()>0&&!ability.isMissed()) {
//					int damage=(ability.getDamage_target()+ability.getOffensive_roll().roll_value-ability.getDefensive_roll().roll_value-ability.getDefensive_roll().warrior.getArmor());
//					caption.add(Math.max(0,damage)+" damage dealt.");
//					String diceLine = "dice( ";
//					for (int i = 0; i < player.getSelectedUnit().getOffensive_deck().size(); i++) {
//						diceLine+=player.getSelectedUnit().getOffensive_deck().get(i).getModifier()+" ; ";
//					}
//					diceLine+=")";
//					caption.add(diceLine);	
//				}
//			}
//			
//			
//		}else {
//			caption.add("");
//			if (ability.getDamage_target()>0) {
//				caption.add("damage: "+ability.getDamage_target());
//			}
//			caption.add("stamina_cost: "+((int)(10*player.getSelectedUnit().getModifiedStaminaCost(ability.getStamina_cost()))/10.0)+"("+ability.getStamina_cost()+")");
//				if (ability.getDexterity_demand()>0) {
//					caption.add("dexterity demand: "+ability.getDexterity_demand());
//				}
//			}
//		return caption;
//	}
protected void paintComponent(Graphics g){
	super.paintComponent(g);
	log.refresh();
	//rectangle_clicker.paintRectangles(g);
	//paint Hero info all interesting stats about the hero
	LinkedList<String> lines=player.getSelectedUnit().generateStatLines();
	for(int i=0; i<lines.size();i++) {
		g.drawString(lines.get(i), 30, 420 + 10+12*i);		
	}
	
	
}
//public RectangleClicker getRectangleClicker() {
//	return rectangle_clicker;
//}
private class MyMouseListener extends MouseAdapter{
	public void mousePressed(MouseEvent e){	
		if(e.getButton()==1){
			//get equipment position from click
//			rectangle_clicker.triggerClick(e);
//			rectangle_clicker.updateCaptions();
			revalidate();
			repaint();		
			battle_window.repaint();
		}else{
			if (e.getButton()==3){
				//new CardView(card);
			}
		}
	} 
}
}
