package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import SpriteSheet.StaticImageLoader;
import gameLogic.Player;
import imageloader.MyStaticImageLoader;

public class ControlComponent extends JComponent {
	private Player player;
	protected int height;
	private BattleWindow bw;
	public ControlComponent(Player player,BattleWindow bw){
		this.player=player;
		this.bw=bw;
		height=12;
		setBorder(new LineBorder(Color.GREEN));
		super.setPreferredSize(new Dimension(440,height*15));
		setVisible(true);
	}
protected void paintComponent(Graphics g){
	super.paintComponent(g);
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
	lines.add("offesnive skill: "+player.getSelectedUnit().getOffense());
	lines.add("defensive skill: "+player.getSelectedUnit().getDefense());
	lines.add("strength: "+player.getSelectedUnit().getStrength());
	lines.add("dexterity: "+player.getSelectedUnit().getDexterity());
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
	g.drawImage(StaticImageLoader.getScaledImage(bw.get_sprite_path(), player.getSelectedUnit().getImageNumber(), bw.getGame().image_scale).getScaledInstance(300, 255, 5),-50,-50,null);	
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
}
