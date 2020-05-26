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
import gameLogic.Item;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;


public class HeroStatsPaintComponent extends JComponent{
		private JPanel jp;
		private JScrollPane sp;
		private CampaignWindow gw;
		public RectangleClicker rc;
		private int offset=200;
		private int offset_horizontal=0;
		private boolean vertical = true;
		private int component_height_lines=14;
		public HeroStatsPaintComponent(CampaignWindow sw, int offset, boolean vertical){
			this.gw=sw;
			this.vertical=true;
			this.offset = offset;
			if (!vertical) {
				offset_horizontal = 200;
			}
			setBorder(new LineBorder(Color.YELLOW));
			super.setPreferredSize(new Dimension(700,200+offset_horizontal));
			MyMouseListener ml = new MyMouseListener();
			super.addMouseListener(ml);
			setLayout(new BorderLayout());
			setVisible(true);
			if(gw.getGame().getPlayer().getInventory().getInventory_list().size()>0) {
				gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getInventory().getInventory_list().getFirst().get(0));
			}
			//rectangles
			rc=new RectangleClicker();
			//potion
			rc.addRect(new ClickableRectangle("potion",60+offset,142+offset_horizontal,50,34) {
				@Override
				public void onClick(MouseEvent e) {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getPotion()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getPotion());
					}
				}
				@Override
				public void updateCaption() {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getPotion()!=null) {
						caption.removeFirst();
						caption.addFirst("");
						this.setImageNumber(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getPotion().getImage());
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
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHead()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHead());
						if (e.isMetaDown()) {
							gw.getGame().getPlayer().getSelectedUnit().getEquipment().unequipHead();
						}
						
					}else {
						gw.getGame().getPlayer().getSelectedUnit().equip(gw.getGame().getPlayer().getSelectedItem());
					}
				}
				@Override
				public void updateCaption() {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHead()!=null) {
						caption.removeFirst();
						caption.addFirst("");
						//caption.addFirst(gw.getGame().getPlayer().getSelectedHero().getEquipment().getHead().getName());
						this.setImageNumber(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHead().getImage());
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
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getBody()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getBody());
						if (e.getButton() == 3) {
							gw.getGame().getPlayer().getSelectedUnit().getEquipment().unequipBody();
						}
						
					}else {
						gw.getGame().getPlayer().getSelectedUnit().equip(gw.getGame().getPlayer().getSelectedItem());
					}
				}
				@Override
				public void updateCaption() {
					// TODO Auto-generated method stub					
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getBody()!=null) {
						caption.removeFirst();	
						caption.addFirst("");
						//caption.addFirst(gw.getGame().getPlayer().getSelectedHero().getEquipment().getBody().getName());
						this.setImageNumber(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getBody().getImage());
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
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand1()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand1());
						if (e.getButton() == 3) {
							gw.getGame().getPlayer().getSelectedUnit().getEquipment().unequipHand1();
						}
						
					}else {
						gw.getGame().getPlayer().getSelectedUnit().equip(gw.getGame().getPlayer().getSelectedItem());
					}
				}
				@Override
				public void updateCaption() {				
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand1()!=null) {
						caption.removeFirst();	
						caption.addFirst("");
						//caption.addFirst(gw.getGame().getPlayer().getSelectedHero().getEquipment().getHand1().getName());
						this.setImageNumber(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand1().getImage());
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
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand2()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand2());
						if (e.getButton() == 3) {
							gw.getGame().getPlayer().getSelectedUnit().getEquipment().unequipHand2();
						}
						
					}else {
						gw.getGame().getPlayer().getSelectedUnit().equip(gw.getGame().getPlayer().getSelectedItem());
					}
				}
				@Override
				public void updateCaption() {					
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand2()!=null) {
						caption.removeFirst();	
						caption.addFirst("");
						//caption.addFirst(gw.getGame().getPlayer().getSelectedHero().getEquipment().getHand2().getName());
						if (! (gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand2().getCategory()==3)) {
							//not a two handed weapon
							this.setImageNumber(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand2().getImage());
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
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing1()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing1());
						if (e.getButton() == 3) {
							gw.getGame().getPlayer().getSelectedUnit().getEquipment().unequipRing1();
						}
						
					}else {
						gw.getGame().getPlayer().getSelectedUnit().equip(gw.getGame().getPlayer().getSelectedItem());
					}
				}
				@Override
				public void updateCaption() {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing1()!=null) {
						caption.removeFirst();
						caption.addFirst("");					
						this.setImageNumber(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing1().getImage());
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
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing2()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing2());
						if (e.getButton() == 3) {
							gw.getGame().getPlayer().getSelectedUnit().getEquipment().unequipRing2();
						}
						
					}else {
						gw.getGame().getPlayer().getSelectedUnit().equip(gw.getGame().getPlayer().getSelectedItem());
					}
				}
				@Override
				public void updateCaption() {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing2()!=null) {
						caption.removeFirst();
						caption.addFirst("");					
						this.setImageNumber(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing2().getImage());
					}else {
						this.setImageNumber(1);
						caption.removeFirst();
						caption.addFirst(name);
					}					
				}		
			});
			rc.updateCaptions();
			
			//amunition
			rc.addRect(new ClickableRectangle("amo",115+offset,10+offset_horizontal,50,40) {
				@Override
				public void onClick(MouseEvent e) {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getAmunition().size()>0) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getAmunition().get(0));
						if (e.getButton() == 3) {
							gw.getGame().getPlayer().getSelectedUnit().getEquipment().unequipAmunition();
						}						
					}else {
						gw.getGame().getPlayer().getSelectedUnit().equip(gw.getGame().getPlayer().getSelectedItem());
					}
				}
				@Override
				public void updateCaption() {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getAmunition().size()>0) {
						caption.removeFirst();
						caption.addFirst(""+gw.getGame().getPlayer().getSelectedUnit().getEquipment().getAmunition().size());					
						this.setImageNumber(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getAmunition().get(0).getImage());
					}else {
						this.setImageNumber(1);
						caption.removeFirst();
						caption.addFirst(name);
					}					
				}		
			});
			rc.updateCaptions();
		}

	private class MyMouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){	
			if(e.getButton()==1){
				//get equipment position from click
				rc.triggerClick(e);												
			}else{
				if (e.getButton()==3){
					//new CardView(card);
				}
			}
			rc.updateCaptions();
			gw.refresh();
		} 
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//paint Hero info all interesting stats about the hero
		LinkedList<String> lines=gw.getGame().getPlayer().getSelectedUnit().generateStatLines();
		//g.drawImage(StaticImageLoader.getScaledImage(gw.getSprite_path(), gw.getGame().getPlayer().getSelectedUnit().getImageNumber(), gw.getGame().image_scale).getScaledInstance(300, 255, 5),-50,-5,null);	
		int y_offset;
		int x_offset;
		if (vertical) {
			y_offset = 0;
			x_offset = 200;
			for(int i=0; i<lines.size();i++) {
				if(i<=component_height_lines+1) {
					g.drawString(lines.get(i), offset+x_offset, y_offset+10+12*i);
				}else {
					g.drawString(lines.get(i), offset+x_offset+140, y_offset+10+12*(i-component_height_lines));
				}			
			}
		}else {
			for(int i=0; i<lines.size();i++) {
				g.drawString(lines.get(i), 10, 200 + 10+12*i);		
			}
		}
		
		g.drawImage(StaticImageLoader.getScaledImage(gw.getSprite_path(), gw.getGame().getPlayer().getSelectedUnit().getImageNumber(),gw.getGame().getImage_scale()).getScaledInstance(240,204, 2),-30,0,null);
		for(int i=0; i<rc.rectAngles.size();i++) {
				if (rc.rectAngles.get(i).getImageNumber()!=1) {
					g.drawImage(StaticImageLoader.getScaledImage(gw.getSprite_path(), rc.rectAngles.get(i).getImageNumber(),gw.getGame().getImage_scale()).getScaledInstance(120,102, 2),rc.rectAngles.get(i).getX()-35,rc.rectAngles.get(i).getY()-20,null);
				}
		}
		rc.paintRectangles(g);
	}
}

