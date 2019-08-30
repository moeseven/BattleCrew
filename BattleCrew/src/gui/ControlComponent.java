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

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import SpriteSheet.StaticImageLoader;
import gameLogic.Ability;
import gameLogic.Player;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;
import imageloader.MyStaticImageLoader;
import javafx.scene.layout.Border;

public class ControlComponent extends JComponent {
	private Player player;
	protected int height;
	private BattleWindow battle_window;
	private RectangleClicker rectangle_clicker;
	public ControlComponent(Player player,BattleWindow bw){
		this.player=player;
		this.battle_window=bw;
		height=13;
		setLayout(new BorderLayout());
		add(new LogComponent(player.getGame().log),BorderLayout.EAST);
		setBorder(new LineBorder(Color.GREEN));
		super.setPreferredSize(new Dimension(440,(height-1)*15));
		addAbilities();
		addMouseListener(new MyMouseListener());
		setVisible(true);
	}
	public void addAbilities() {
		int RECT_X_SIZE=190;
		int RECT_Y_SIZE=120;
		int ABILITIES_START_X=340;
		rectangle_clicker= new RectangleClicker();
		ClickableRectangle moveangle= new ClickableRectangle("move", ABILITIES_START_X, 20, RECT_X_SIZE, RECT_Y_SIZE) {			
			@Override
			public void updateCaption() {
				setCaption(new LinkedList<String>());
				getCaption().add(name);
				getCaption().add(" ");
				getCaption().add("stamina_cost: "+((int)(10*player.getSelectedUnit().getModifiedStaminaCost(player.getSelectedUnit().getMoveStamina_cost())))/10.0);
			}
			
			@Override
			public void onClick(MouseEvent arg0) {
				player.getSelectedUnit().setSelected_ability(null);
				rectangle_clicker.setSelectedRectangle(this);
				updateCaption();
			}
		};
		rectangle_clicker.addRect(moveangle);
		rectangle_clicker.setSelectedRectangle(moveangle);
		rectangle_clicker.addRect(new ClickableRectangle("use mainhand", ABILITIES_START_X+RECT_X_SIZE*(1), 20, RECT_X_SIZE, RECT_Y_SIZE) {			
			@Override
			public void updateCaption() {
				caption=generateAbilityRectangleCaption(player.getSelectedUnit().getWeaponAbility());
			}			
			@Override
			public void onClick(MouseEvent e) {
				if (player.getSelectedUnit().getWeaponAbility().isAfter_roll_status()) {
					if (e.getButton()==1) {
						player.getSelectedUnit().getWeaponAbility().applyAbilityAfterRoll();
					}else {
						if (e.getButton()==3) {
							// reroll if dexterity is high player.getSelectedUnit().getWeaponAbility().
							player.getSelectedUnit().getWeaponAbility().tryOffesniveReroll();
						}
					}						
				}else {
					rectangle_clicker.setSelectedRectangle(this);
					player.getSelectedUnit().setSelected_ability(player.getSelectedUnit().getWeaponAbility());
				}
				
				
				
			}
		});
		for (int i = 0; i < player.getSelectedUnit().getAbilities().size(); i++) {
			rectangle_clicker.addRect(new AbilityRectangle(player.getSelectedUnit().getAbilities().get(i),player, ABILITIES_START_X+(2+i)*RECT_X_SIZE, 20, RECT_X_SIZE, RECT_Y_SIZE));
		}
		rectangle_clicker.addRect(new ClickableRectangle("end turn", ABILITIES_START_X+RECT_X_SIZE*(player.getSelectedUnit().getAbilities().size()+2), 20, RECT_X_SIZE, RECT_Y_SIZE) {
			
			@Override
			public void updateCaption() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClick(MouseEvent e) {
				// TODO Auto-generated method stub
				//select next active unit
				battle_window.getGame().getBattle().endActiveWarriorTurn();
				rectangle_clicker.setSelectedRectangle(this);
			}
		});
		for (int i = 0; i < rectangle_clicker.getRectAngles().size(); i++) {
			rectangle_clicker.getRectAngles().get(i).updateCaption();
		}
	}
	public LinkedList<String> generateAbilityRectangleCaption(Ability ability){
		LinkedList<String> caption=new LinkedList<String>();
		caption.add(ability.getName());		
		if (ability.isUsed()) {			
			if (ability.isAfter_roll_status()) {
				if (ability.isMissed()) {	
					caption.add("missed!");
					caption.add("(hit chance depends on dexterity)");
				}else {
					if (ability.getDamage_target()>0) {	
						int damage=(ability.getDamage_target()+ability.getOffensive_roll().roll_value-ability.getDefensive_roll().roll_value-ability.getDefensive_roll().warrior.getArmor());
						String rollString="damage rolls: ("+ability.getDamage_target();
						if (ability.getOffensive_roll().roll_value>=0) {
							rollString+=" +"+ability.getOffensive_roll().roll_value;
						}else {
							rollString+=ability.getOffensive_roll().roll_value;
						}
						rollString+=") -> ("+ability.getDefensive_roll().warrior.getArmor()+" + "+ability.getDefensive_roll().roll_value+")  = "+damage;
						caption.add(rollString);
					}
				}
				
			}else {
				if (ability.getDamage_target()>0&&!ability.isMissed()) {
					int damage=(ability.getDamage_target()+ability.getOffensive_roll().roll_value-ability.getDefensive_roll().roll_value-ability.getDefensive_roll().warrior.getArmor());
					caption.add(Math.max(0,damage)+" damage dealt.");
					String diceLine = "dice( ";
					for (int i = 0; i < player.getSelectedUnit().getOffensive_deck().size(); i++) {
						diceLine+=player.getSelectedUnit().getOffensive_deck().get(i).getModifier()+" ; ";
					}
					diceLine+=")";
					caption.add(diceLine);	
				}
			}
			
			
		}else {
			caption.add("");
			if (ability.getDamage_target()>0) {
				caption.add("damage: "+ability.getDamage_target());
			}
			caption.add("stamina_cost: "+((int)(10*player.getSelectedUnit().getModifiedStaminaCost(ability.getStamina_cost()))/10.0)+"("+ability.getStamina_cost()+")");
				if (ability.getDexterity_demand()>0) {
					caption.add("dexterity demand: "+ability.getDexterity_demand());
				}
			}
		return caption;
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
	g.drawImage(StaticImageLoader.getScaledImage(battle_window.get_sprite_path(), player.getSelectedUnit().getImageNumber(), battle_window.getGame().image_scale).getScaledInstance(300, 255, 5),-50,-5,null);		
	for(int i=0; i<lines.size();i++) {
		if(i<=height+1) {
			g.drawString(lines.get(i), 200, 10+12*i);
		}else {
			g.drawString(lines.get(i), 340, 10+12*(i-height+2));
		}
		
	}
	
	
}
public RectangleClicker getRectangleClicker() {
	return rectangle_clicker;
}
private class MyMouseListener extends MouseAdapter{
	public void mousePressed(MouseEvent e){	
		if(e.getButton()==1){
			//get equipment position from click
			rectangle_clicker.triggerClick(e);
			rectangle_clicker.updateCaptions();
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
