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
		private int x_offset=200;
		private int component_height_lines=16;
		public HeroStatsPaintComponent(CampaignWindow sw){
			this.gw=sw;
			setBorder(new LineBorder(Color.YELLOW));
			super.setPreferredSize(new Dimension(700,200));
			MyMouseListener ml = new MyMouseListener();
			super.addMouseListener(ml);
			setLayout(new BorderLayout());
			setVisible(true);
			if(gw.getGame().getPlayer().getInventory().size()>0) {
				gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getInventory().getFirst());
			}
			//rectangles
			rc=new RectangleClicker();
			//potion
			rc.addRect(new ClickableRectangle("potion",60+x_offset,142,50,34) {
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
			rc.addRect(new ClickableRectangle("head",60+x_offset,10,50,50) {
				@Override
				public void onClick(MouseEvent e) {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHead()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHead());
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
			rc.addRect(new ClickableRectangle("body",60+x_offset,70,50,70) {
				@Override
				public void onClick(MouseEvent e) {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getBody()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getBody());
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
			rc.addRect(new ClickableRectangle("hand1",5+x_offset,70,50,70) {
				@Override
				public void onClick(MouseEvent e) {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand1()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand1());
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
			rc.addRect(new ClickableRectangle("hand2",115+x_offset,70,50,70) {
				@Override
				public void onClick(MouseEvent e) {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand2()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand2());
					}
				}
				@Override
				public void updateCaption() {					
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand2()!=null) {
						caption.removeFirst();	
						caption.addFirst("");
						//caption.addFirst(gw.getGame().getPlayer().getSelectedHero().getEquipment().getHand2().getName());
						this.setImageNumber(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getHand2().getImage());
					}else {
						this.setImageNumber(1);
						caption.removeFirst();
						caption.addFirst(name);
					}
				}		
			});
			//ring1
			rc.addRect(new ClickableRectangle("ring1",15+x_offset,142,30,30) {
				@Override
				public void onClick(MouseEvent e) {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing1()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing1());
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
			rc.addRect(new ClickableRectangle("ring2",125+x_offset,142,30,30) {
				@Override
				public void onClick(MouseEvent e) {
					if(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing2()!=null) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getSelectedUnit().getEquipment().getRing2());
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
		for(int i=0; i<lines.size();i++) {
			if(i<=component_height_lines+1) {
				g.drawString(lines.get(i), x_offset+200, 10+12*i);
			}else {
				g.drawString(lines.get(i), x_offset+340, 10+12*(i-component_height_lines+2));
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

