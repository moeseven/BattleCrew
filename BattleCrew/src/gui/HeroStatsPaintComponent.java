package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import SpriteSheet.StaticImageLoader;
import gameLogic.BattleUnit;
import gameLogic.Game;
import gameLogic.Item;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;
import gameLogic.Behaviour;
import gameLogic.Behaviour.Behaviour_type;

public class HeroStatsPaintComponent extends JComponent{
		private JPanel jp;
		private JScrollPane sp;
		private Refreshable_gui gw;
		private BattleUnit warrior;
		public RectangleClicker rc;
		private int offset=200;
		private int offset_horizontal=0;
		private boolean vertical = true;
		private int component_height_lines=14;
		private boolean paint_statistics;
		private boolean paint_equipment;
		public HeroStatsPaintComponent(BattleUnit warrior, Refreshable_gui gui, int offset, boolean vertical, boolean paint_statistics, boolean paint_equipment){
			this.gw=gui;
			this.paint_statistics = paint_statistics;
			this.paint_equipment = paint_equipment;
			int x_dimension = 600;
			if (paint_statistics) {
				x_dimension = 800;
			}
			this.warrior = warrior;
			this.vertical=true;
			this.offset = offset;
			if (!vertical) {
				offset_horizontal = 200;
			}
			setBorder(new LineBorder(Color.YELLOW));
			super.setPreferredSize(new Dimension(x_dimension,200+offset_horizontal));
			MyMouseListener ml = new MyMouseListener();
			super.addMouseListener(ml);
			setLayout(new BorderLayout());
			setVisible(true);
			if(warrior.getPlayer().getInventory().getInventory_list().size()>0) {
				warrior.getPlayer().setSelectedItem(warrior.getPlayer().getInventory().getInventory_list().getFirst().get(0));
			}
			rc=new RectangleClicker();
			if (paint_equipment) {
				//rectangles
				//swap weapon
				rc.addRect(new ClickableRectangle("swap",10+offset,10+offset_horizontal,50,20) {
					@Override
					public void onClick(MouseEvent e) {
						warrior.getPlayer().getSelectedUnit().getEquipment().swapWeapons();
					}
					@Override
					public void updateCaption() {				
					}		
				});
				
				
				//potion
				rc.addRect(new ClickableRectangle("potion",60+offset,142+offset_horizontal,50,34) {
					@Override
					public void onClick(MouseEvent e) {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getPotion()!=null) {
							warrior.getPlayer().setSelectedItem(warrior.getPlayer().getSelectedUnit().getEquipment().getPotion());
						}
					}
					@Override
					public void updateCaption() {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getPotion()!=null) {
							caption.removeFirst();
							caption.addFirst("");
							this.setImageNumber(warrior.getPlayer().getSelectedUnit().getEquipment().getPotion().getImage());
						}else {
							cleanImageNumber();
							caption.removeFirst();
							caption.addFirst(name);
						}					
					}		
				});
				//head
				rc.addRect(new ClickableRectangle("head",60+offset,10+offset_horizontal,50,50) {
					@Override
					public void onClick(MouseEvent e) {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getHead()!=null) {
							warrior.getPlayer().setSelectedItem(warrior.getPlayer().getSelectedUnit().getEquipment().getHead());
							if (e.isMetaDown()) {
								warrior.getPlayer().getSelectedUnit().getEquipment().unequipHead();
							}
							
						}else {
							warrior.getPlayer().getSelectedUnit().equip(warrior.getPlayer().getSelectedItem());
						}
					}
					@Override
					public void updateCaption() {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getHead()!=null) {
							caption.removeFirst();
							caption.addFirst("");
							//caption.addFirst(gw.getGame().getPlayer().getSelectedHero().getEquipment().getHead().getName());
							this.setImageNumber(warrior.getPlayer().getSelectedUnit().getEquipment().getHead().getImage());
						}else {
							this.setImageNumber(1);
							caption.removeFirst();
							caption.addFirst(name);
						}					
					}		
				});
				//body
				rc.addRect(new ClickableRectangle("body",60+offset,70+offset_horizontal,50,70) {
					@Override
					public void onClick(MouseEvent e) {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getBody()!=null) {
							warrior.getPlayer().setSelectedItem(warrior.getPlayer().getSelectedUnit().getEquipment().getBody());
							if (e.getButton() == 3) {
								warrior.getPlayer().getSelectedUnit().getEquipment().unequipBody();
							}
							
						}else {
							warrior.getPlayer().getSelectedUnit().equip(warrior.getPlayer().getSelectedItem());
						}
					}
					@Override
					public void updateCaption() {
						// TODO Auto-generated method stub					
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getBody()!=null) {
							caption.removeFirst();	
							caption.addFirst("");
							//caption.addFirst(gw.getGame().getPlayer().getSelectedHero().getEquipment().getBody().getName());
							this.setImageNumber(warrior.getPlayer().getSelectedUnit().getEquipment().getBody().getImage());
						}else {
							this.setImageNumber(1);
							caption.removeFirst();
							caption.addFirst(name);
						}
					}		
				});
				//hand1
				rc.addRect(new ClickableRectangle("hand1",5+offset,60+offset_horizontal,50,80) {
					@Override
					public void onClick(MouseEvent e) {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getHand1()!=null) {
							warrior.getPlayer().setSelectedItem(warrior.getPlayer().getSelectedUnit().getEquipment().getHand1());
							if (e.getButton() == 3) {
								warrior.getPlayer().getSelectedUnit().getEquipment().unequipHand1();
							}
							
						}else {
							warrior.getPlayer().getSelectedUnit().equip(warrior.getPlayer().getSelectedItem());
						}
					}
					@Override
					public void updateCaption() {				
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getHand1()!=null) {
							caption.removeFirst();	
							caption.addFirst("");
							//caption.addFirst(gw.getGame().getPlayer().getSelectedHero().getEquipment().getHand1().getName());
							this.setImageNumber(warrior.getPlayer().getSelectedUnit().getEquipment().getHand1().getImage());
						}else {
							this.setImageNumber(1);
							caption.removeFirst();
							caption.addFirst(name);
						}
					}		
				});
				//hand2
				rc.addRect(new ClickableRectangle("hand2",115+offset,60+offset_horizontal,50,80) {
					@Override
					public void onClick(MouseEvent e) {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getHand2()!=null) {
							warrior.getPlayer().setSelectedItem(warrior.getPlayer().getSelectedUnit().getEquipment().getHand2());
							if (e.getButton() == 3) {
								warrior.getPlayer().getSelectedUnit().getEquipment().unequipHand2();
							}
							
						}else {
							warrior.getPlayer().getSelectedUnit().equip(warrior.getPlayer().getSelectedItem());
						}
					}
					@Override
					public void updateCaption() {					
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getHand2()!=null) {
							caption.removeFirst();	
							caption.addFirst("");
							//caption.addFirst(gw.getGame().getPlayer().getSelectedHero().getEquipment().getHand2().getName());
							if (! (warrior.getPlayer().getSelectedUnit().getEquipment().getHand2().getCategory()==3)) {
								//not a two handed weapon
								this.setImageNumber(warrior.getPlayer().getSelectedUnit().getEquipment().getHand2().getImage());
							}else {
								this.setImageNumber(16);
							}
							
						}else {
							this.setImageNumber(1);
							caption.removeFirst();
							caption.addFirst(name);
						}
					}		
				});
				//ring1
				rc.addRect(new ClickableRectangle("ring1",15+offset,142+offset_horizontal,30,30) {
					@Override
					public void onClick(MouseEvent e) {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getRing1()!=null) {
							warrior.getPlayer().setSelectedItem(warrior.getPlayer().getSelectedUnit().getEquipment().getRing1());
							if (e.getButton() == 3) {
								warrior.getPlayer().getSelectedUnit().getEquipment().unequipRing1();
							}
							
						}else {
							warrior.getPlayer().getSelectedUnit().equip(warrior.getPlayer().getSelectedItem());
						}
					}
					@Override
					public void updateCaption() {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getRing1()!=null) {
							caption.removeFirst();
							caption.addFirst("");					
							this.setImageNumber(warrior.getPlayer().getSelectedUnit().getEquipment().getRing1().getImage());
						}else {
							this.setImageNumber(1);
							caption.removeFirst();
							caption.addFirst(name);
						}					
					}		
				});
				//ring2
				rc.addRect(new ClickableRectangle("ring2",125+offset,142+offset_horizontal,30,30) {
					@Override
					public void onClick(MouseEvent e) {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getRing2()!=null) {
							warrior.getPlayer().setSelectedItem(warrior.getPlayer().getSelectedUnit().getEquipment().getRing2());
							if (e.getButton() == 3) {
								warrior.getPlayer().getSelectedUnit().getEquipment().unequipRing2();
							}
							
						}else {
							warrior.getPlayer().getSelectedUnit().equip(warrior.getPlayer().getSelectedItem());
						}
					}
					@Override
					public void updateCaption() {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getRing2()!=null) {
							caption.removeFirst();
							caption.addFirst("");					
							this.setImageNumber(warrior.getPlayer().getSelectedUnit().getEquipment().getRing2().getImage());
						}else {
							this.setImageNumber(1);
							caption.removeFirst();
							caption.addFirst(name);
						}					
					}		
				});
				
				//amunition
				rc.addRect(new ClickableRectangle("amo",115+offset,10+offset_horizontal,50,40) {
					@Override
					public void onClick(MouseEvent e) {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getAmunition().size()>0) {
							warrior.getPlayer().setSelectedItem(warrior.getPlayer().getSelectedUnit().getEquipment().getAmunition().get(0));
							if (e.getButton() == 3) {
								warrior.getPlayer().getSelectedUnit().getEquipment().unequipAmunition();
							}						
						}else {
							warrior.getPlayer().getSelectedUnit().equip(warrior.getPlayer().getSelectedItem());
						}
					}
					@Override
					public void updateCaption() {
						if(warrior.getPlayer().getSelectedUnit().getEquipment().getAmunition().size()>0) {
							caption.removeFirst();
							caption.addFirst(""+warrior.getPlayer().getSelectedUnit().getEquipment().getAmunition().size());					
							this.setImageNumber(warrior.getPlayer().getSelectedUnit().getEquipment().getAmunition().get(0).getImage());
						}else {
							this.setImageNumber(1);
							caption.removeFirst();
							caption.addFirst(name);
						}					
					}		
				});
				//battle stance
				rc.addRect(new ClickableRectangle("stance",offset,30+offset_horizontal,60,20) {
					@Override
					public void onClick(MouseEvent e) {
						if (warrior.getPlayer().getSelectedUnit().getBehaviour()!=null) {
								switch (warrior.getPlayer().getSelectedUnit().getBehaviour()) {
							case ATTACK_CLOSEST_ENEMY:
								warrior.getPlayer().getSelectedUnit().setBehaviour(Behaviour_type.CONSERVATIVE_ATTACKING);
								break;
							case ATTACK_REAR:
								warrior.getPlayer().getSelectedUnit().setBehaviour(Behaviour_type.CAST_SPELLS);
								break;
							case CONSERVATIVE_ATTACKING:
								warrior.getPlayer().getSelectedUnit().setBehaviour(Behaviour_type.ATTACK_REAR);
								break;
							case CAST_SPELLS:
								warrior.getPlayer().getSelectedUnit().setBehaviour(Behaviour_type.ATTACK_CLOSEST_ENEMY);
							default:
								warrior.getPlayer().getSelectedUnit().setBehaviour(Behaviour_type.ATTACK_CLOSEST_ENEMY);
								break;
							}
						}						
					}
					@Override
					public void updateCaption() {
						caption.removeFirst();
						if (warrior.getPlayer().getSelectedUnit().getBehaviour()!=null) {
							switch (warrior.getPlayer().getSelectedUnit().getBehaviour()) {
							case ATTACK_CLOSEST_ENEMY:							
								caption.addFirst("aggressive");
								break;
							case ATTACK_REAR:
								caption.addFirst("attack rear");
								break;
							case CONSERVATIVE_ATTACKING:
								caption.addFirst("defensive");
								break;
							case CAST_SPELLS:
								caption.addFirst("cast spells");
								break;
							default:
								break;
							}
						}else {
							caption.addFirst("stance");
						}											
					}		
				});
				rc.updateCaptions();
			}
			
		}

	private class MyMouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){	
			//get equipment position from click
			if (vertical) {
				rc.triggerClick(e);												
				rc.updateCaptions();
				gw.get_gui_controller().refresh_gui();
			}
			
		} 
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//paint Hero info all interesting stats about the hero
		LinkedList<String> lines=warrior.getPlayer().getSelectedUnit().generateStatLines();
		LinkedList<String> lines_statistics = warrior.getPlayer().getSelectedUnit().generate_statistics_lines();
		//g.drawImage(StaticImageLoader.getScaledImage(gw.getSprite_path(), gw.getGame().getPlayer().getSelectedUnit().getImageNumber(), gw.getGame().image_scale).getScaledInstance(300, 255, 5),-50,-5,null);	
		int y_offset;
		int x_offset;
		if (vertical) {
			y_offset = -12;
			x_offset = 200;
			for(int i=1; i<lines.size();i++) {
				if(i<=component_height_lines) {
					g.drawString(lines.get(i), offset+x_offset, y_offset+10+12*i);
				}else {
					g.drawString(lines.get(i), offset+x_offset+130, y_offset+10+12*(i-component_height_lines));
				}		
			}
			if (paint_statistics) {
				for(int i=0; i<lines_statistics.size();i++) {
					g.drawString(lines_statistics.get(i), offset+x_offset+260, y_offset+10+12*i);	
				}
			}
			
		}else {
			for(int i=1; i<lines.size();i++) {
				g.drawString(lines.get(i), 10, 200 + 10+12*i);		
			}
			if (paint_statistics) {
				for(int i=0; i<lines_statistics.size();i++) {
					g.drawString(lines_statistics.get(i), 10, 200+10+12*(i+lines.size()));	
				}
			}
		}
		//Name above picture
		g.drawString(lines.getFirst(), 10, 10);
		g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH, warrior.getPlayer().getSelectedUnit().getImageNumber(),gw.get_gui_controller().image_scale).getScaledInstance(240,204, 2),-30,0,null);
		if (warrior.getPlayer().getSelectedUnit() == warrior.getPlayer().getCommander()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,391, gw.get_gui_controller().image_scale).getScaledInstance(240, 204, 2),-60,+30,null);
		}
		if (warrior.getPlayer().getSelectedUnit() == warrior.getPlayer().getRecruiter()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,385, gw.get_gui_controller().image_scale).getScaledInstance(240, 204, 2),-60,+30,null);
		}
		if (warrior.getPlayer().getSelectedUnit() == warrior.getPlayer().getHealer()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,390, gw.get_gui_controller().image_scale).getScaledInstance(240, 204, 2),-60,+30,null);
		}
		if (warrior.getPlayer().getSelectedUnit() == warrior.getPlayer().getSmith()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,389, gw.get_gui_controller().image_scale).getScaledInstance(240, 204, 2),-60,+30,null);
		}
		if (warrior.getPlayer().getSelectedUnit() == warrior.getPlayer().getTreasurer()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,388, gw.get_gui_controller().image_scale).getScaledInstance(240, 204, 2),-60,+30,null);
		}
		if (warrior.getPlayer().getSelectedUnit() == warrior.getPlayer().getLeader()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,387, gw.get_gui_controller().image_scale).getScaledInstance(240, 204, 2),-60,+30,null);
		}
		if (warrior.getPlayer().getSelectedUnit() == warrior.getPlayer().getChampion()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,386, gw.get_gui_controller().image_scale).getScaledInstance(240, 204, 2),-60,+30,null);
		}
		for(int i=0; i<rc.rectAngles.size();i++) {
				if (rc.rectAngles.get(i).getImageNumber()!=1) {
					g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH, rc.rectAngles.get(i).getImageNumber(),gw.get_gui_controller().image_scale).getScaledInstance(120,102, 2),rc.rectAngles.get(i).getX()-35,rc.rectAngles.get(i).getY()-20,null);
				}
		}
		rc.paintRectangles(g);
	}
}

