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
		height=24;
		setBorder(new LineBorder(Color.GREEN));
		super.setPreferredSize(new Dimension(440,height*15));
		setVisible(true);
	}
protected void paintComponent(Graphics g){
	super.paintComponent(g);
	//paint Hero info all interesting stats about the hero
	LinkedList<String> lines=new LinkedList<String>();
	lines.add(player.getSelectedHero().getName()+" ("+player.getSelectedHero().getName()+", "+player.getSelectedHero().getName()+")");
	lines.add("");
	lines.add("health: "+player.getSelectedHero().getHealth()+"/"+player.getSelectedHero().calcMaxHp()+" ("+player.getSelectedHero().getLevel()+")");
	//lines.add("moral: "+player.getSelectedHero().getStress()+"/"+player.getSelectedHero().getStressCap());
	lines.add("");
	//main stats
	lines.add("offesnive skill: "+player.getSelectedHero().getOffense());
	lines.add("defensive skill: "+player.getSelectedHero().getDefense());
	lines.add("strength: "+player.getSelectedHero().getStrength());
	lines.add("dexterity: "+player.getSelectedHero().getDexterity());
	lines.add("vitality: "+player.getSelectedHero().getVitality());		
	lines.add("");
	//			
	lines.add("");
	//defensive
	lines.add("armor: "+player.getSelectedHero().getArmor());
	lines.add("Level: "+player.getSelectedHero().getLevel());
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
	//g.drawImage(StaticImageLoader.getImage(bw.get_sprite_path(), player.getSelectedHero().getImageNumber()).getScaledInstance(300, 255, 5),200,0,null);	
	g.drawImage(MyStaticImageLoader.getImage(player.getSelectedHero().getImageNumber()).getScaledInstance(300, 255, 5),200,0,null);	
	//
	for(int i=0; i<lines.size();i++) {
		if(i<=height+1) {
			g.drawString(lines.get(i), 10, 10+12*i);
		}else {
			g.drawString(lines.get(i), 150, 10+12*(i-height+2));
		}
		
	}
	
}
}
