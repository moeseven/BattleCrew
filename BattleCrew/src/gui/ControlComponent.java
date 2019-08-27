package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import SpriteSheet.StaticImageLoader;
import gameLogic.Player;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;
import imageloader.MyStaticImageLoader;

public class ControlComponent extends JComponent {
	private Player player;
	protected int height;
	private BattleWindow battle_window;
	private RectangleClicker rectangle_clicker;
	public ControlComponent(Player player,BattleWindow bw){
		this.player=player;
		this.battle_window=bw;
		height=13;
		setBorder(new LineBorder(Color.GREEN));
		super.setPreferredSize(new Dimension(440,(height-1)*15));
		addAbilities();
		setVisible(true);
	}
	public void addAbilities() {
		int RECT_X_SIZE=190;
		int RECT_Y_SIZE=120;
		int ABILITIES_START_X=300;
		rectangle_clicker= new RectangleClicker();
		rectangle_clicker.addRect(new MoveRectangle(player, ABILITIES_START_X, 20, RECT_X_SIZE, RECT_Y_SIZE));
		for (int i = 0; i < player.getSelectedUnit().getAbilities().size(); i++) {
			rectangle_clicker.addRect(new AbilityRectangle(player.getSelectedUnit().getAbilities().get(i),player, ABILITIES_START_X+(1+i)*RECT_X_SIZE, 20, RECT_X_SIZE, RECT_Y_SIZE));
		}
		rectangle_clicker.addRect(new ClickableRectangle("end turn", ABILITIES_START_X+RECT_X_SIZE*(player.getSelectedUnit().getAbilities().size()+1), 20, RECT_X_SIZE, RECT_Y_SIZE) {
			
			@Override
			public void updateCaption() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//select next active unit
			}
		});
	}
protected void paintComponent(Graphics g){
	super.paintComponent(g);
	rectangle_clicker.paintRectangles(g);
	//paint Hero info all interesting stats about the hero
	LinkedList<String> lines=new LinkedList<String>();
	lines.add("");
	lines.add(player.getSelectedUnit().getName()+"  (Level "+player.getSelectedUnit().getLevel()+")");
	lines.add("");
	lines.add("health: "+(int)(player.getSelectedUnit().getHealth())+"/"+player.getSelectedUnit().calcMaxHp());
	lines.add("stamina: "+(int)(player.getSelectedUnit().getStamina())+"/"+player.getSelectedUnit().calcMaxStamina());
	//lines.add("moral: "+player.getSelectedHero().getStress()+"/"+player.getSelectedHero().getStressCap());
	lines.add("");
	//main stats
	lines.add("speed: "+player.getSelectedUnit().getSpeed());
	lines.add("offesnive skill: "+player.getSelectedUnit().getOffense());
	lines.add("defensive skill: "+player.getSelectedUnit().getDefense());
	lines.add("strength: "+player.getSelectedUnit().getStrength());
	lines.add("dexterity: "+player.getSelectedUnit().getDexterity());
	lines.add("endurance: "+player.getSelectedUnit().getEndurance());
	lines.add("vitality: "+player.getSelectedUnit().getVitality());		
	lines.add("");
	//defensive
	lines.add("armor: "+player.getSelectedUnit().getArmor());
	
	//TODO lines.add("experience: "+player.getSelectedHero().getExperience()+"/"+GameEquations.experienceThresholdForLevelUp(player.getSelectedHero().getLevel()));		
	//Quirks
	lines.add("");
//TODO if(player.getSelectedHero().getQuirks().size()>0) { 
//		lines.add("Quirks:");
//		for(int a=0; a<player.getSelectedHero().getQuirks().size();a++) {
//			String quirkString=player.getSelectedHero().getQuirks().get(a).getName()+"(";
//			for(int b=0; b<player.getSelectedHero().getQuirks().get(a).getDescription().size();b++) {
//				quirkString+=player.getSelectedHero().getQuirks().get(a).getDescription().get(b);
//			}
//			quirkString+=")";
//			lines.add(quirkString);
//		}
//		lines.add("");
//	}
	g.drawImage(StaticImageLoader.getScaledImage(battle_window.get_sprite_path(), player.getSelectedUnit().getImageNumber(), battle_window.getGame().image_scale).getScaledInstance(300, 255, 5),-50,-50,null);	
	//g.drawImage(MyStaticImageLoader.getImage(player.getSelectedUnit().getImageNumber()).getScaledInstance(300, 255, 5),200,0,null);	
	//
	for(int i=0; i<lines.size();i++) {
		if(i<=height+1) {
			g.drawString(lines.get(i), 160, 10+12*i);
		}else {
			g.drawString(lines.get(i), 300, 10+12*(i-height+2));
		}
		
	}
	
}
public RectangleClicker getRectangleClicker() {
	return rectangle_clicker;
}
}
