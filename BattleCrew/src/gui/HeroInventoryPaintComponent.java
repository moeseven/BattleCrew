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


public class HeroInventoryPaintComponent extends JComponent{
		private JPanel jp;
		private JScrollPane sp;
		private CampaignWindow gw;
		public RectangleClicker rc;
		private int x_offset=-90;
		public HeroInventoryPaintComponent(CampaignWindow sw){
			this.gw=sw;
			setBorder(new LineBorder(Color.YELLOW));
			super.setPreferredSize(new Dimension(550,200));
			addMouseListener(new MyMouseListener());
			setLayout(new BorderLayout());
			setVisible(true);
			if(gw.getGame().getPlayer().getInventory().size()>0) {
				gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getInventory().getFirst());
			}
			//rectangles
			rc=new RectangleClicker();
			//Inventory
			rc.addRect(new ClickableRectangle("search inventory",305+x_offset,120,110,20) {
				@Override
				public void onClick(MouseEvent e) {
				}
				@Override
				public void updateCaption() {
					caption=new LinkedList<>();
					if (gw.getGame().getPlayer().getInventory().size()<1) {
						caption.add("empty inventory");
					}else {
						caption.add(this.name);
					}
				}		
			});
			rc.addRect(new ClickableRectangle("<-",415+x_offset,120,50,20) {
				@Override
				public void onClick(MouseEvent e) {
					// TODO Auto-generated method stub
					if(gw.getGame().getPlayer().getInventory().size()>0) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getInventory().getFirst());
						if(gw.getGame().getPlayer().getInventory().size()>1) {
							gw.getGame().getPlayer().getInventory().addLast(gw.getGame().getPlayer().getInventory().removeFirst());
							gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getInventory().getFirst());
						}
					}					
						
				}
				@Override
				public void updateCaption() {
				}		
			});
			rc.addRect(new ClickableRectangle("->",465+x_offset,120,50,20) {
				@Override
				public void onClick(MouseEvent e) {
					// TODO Auto-generated method stub
					if(gw.getGame().getPlayer().getInventory().size()>0) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getInventory().getFirst());
						if(gw.getGame().getPlayer().getInventory().size()>1) {
							gw.getGame().getPlayer().getInventory().addFirst(gw.getGame().getPlayer().getInventory().removeLast());
							gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getInventory().getFirst());
						}
					}					
						
				}
				@Override
				public void updateCaption() {
				}		
			});
			//item description
			rc.addRect(new ClickableRectangle("description",305+x_offset,10,300,110) {
				@Override
				public void onClick(MouseEvent e) {

				}
				@Override
				public void updateCaption() {
					setFirstLineColor(Color.black);
					if(gw.getGame().getPlayer().getSelectedItem()!=null) {
						gw.getGame().getPlayer().getSelectedItem().generateItemDescription();						
						caption=gw.getGame().getPlayer().getSelectedItem().getDescription();
						caption.addFirst(gw.getGame().getPlayer().getSelectedItem().getName());
//						if(gw.getGame().getPlayer().getSelectedItem().getNumberOfSuffixes()>0) {
//							setFirstLineColor(Color.blue);
//							if(gw.getGame().getPlayer().getSelectedItem().getNumberOfSuffixes()>1) {
//								setFirstLineColor(Color.orange);
//								if(gw.getGame().getPlayer().getSelectedItem().getNumberOfSuffixes()>2) {
//									setFirstLineColor(Color.PINK);
//								}
//							}
//						}
					}else {
						caption=new LinkedList<String>();
					}					
				}		
			});
			//item picture
			rc.addRect(new ClickableRectangle("",515+x_offset,50,80,70) {
				@Override
				public void onClick(MouseEvent e) {

				}
				@Override
				public void updateCaption() {
					
					if(gw.getGame().getPlayer().getSelectedItem()!=null) {
						this.setImageNumber(gw.getGame().getPlayer().getSelectedItem().getImage());
						
					}					
				}		
			});
			//use
			rc.addRect(new ClickableRectangle("use",210+x_offset,60,55,20) {
				@Override
				public void onClick(MouseEvent e) {					
					if(gw.getGame().getPlayer().getSelectedItem()!=null) {
						Item itemUsable=gw.getGame().getPlayer().getSelectedItem();
						if(gw.getGame().getPlayer().getInventory().contains(itemUsable)) {
							//TODO
//							if (itemUsable instanceof ItemConsumable) {
//								itemUsable.mod(gw.getGame().getPlayer().getSelectedUnit());
//								gw.getGame().getPlayer().getInventory().remove(itemUsable);								
//							}
						}			
					}
				}
				@Override
				public void updateCaption() {
										
				}		
			});
			//equip/unequip
			rc.addRect(new ClickableRectangle("equip",210+x_offset,20,75,30) {
				@Override
				public void onClick(MouseEvent e) {
					// TODO Auto-generated method stub
					if(gw.getGame().getPlayer().getSelectedItem()!=null) {
						if(gw.getGame().getPlayer().getInventory().contains(gw.getGame().getPlayer().getSelectedItem())) {
							//equip
							gw.getGame().getPlayer().getSelectedUnit().getEquipment().equipItem(gw.getGame().getPlayer().getSelectedItem());
						}else {
							//unequip
							gw.getGame().getPlayer().getSelectedUnit().getEquipment().unequipItem(gw.getGame().getPlayer().getSelectedItem());
						}				
					}
					if(gw.getGame().getPlayer().getInventory().size()>0) {
						gw.getGame().getPlayer().setSelectedItem(gw.getGame().getPlayer().getInventory().getFirst());
					}
					
				}
				@Override
				public void updateCaption() {					
					if(gw.getGame().getPlayer().getInventory().contains(gw.getGame().getPlayer().getSelectedItem())) {
						caption.removeFirst();						
						caption.addFirst(name);
					}else {
						caption.removeFirst();
						caption.addFirst("unequip");
					}
				}		
			});
			//gold
			rc.addRect(new ClickableRectangle("gold",515+x_offset,120,90,20) {
				@Override
				public void onClick(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				@Override
				public void updateCaption() {
					caption.removeFirst();
					if (gw.getGame().getPlayer().getSelectedItem()!=null) {
						caption.addFirst("gold: "+gw.getGame().getPlayer().getGold()+" ("+gw.getGame().getPlayer().getSelectedItem().getGold_value()+")");					
					}else {
						caption.addFirst("gold: "+gw.getGame().getPlayer().getGold());					
					}
					
				}		
			});
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
		//g.drawImage(StaticImageLoader.getScaledImage(gw.getSprite_path(), gw.getGame().getPlayer().getSelectedUnit().getImageNumber(),gw.getGame().getImage_scale()).getScaledInstance(240,204, 2),-30,0,null);
		for(int i=0; i<rc.rectAngles.size();i++) {
				if (rc.rectAngles.get(i).getImageNumber()!=1) {
					g.drawImage(StaticImageLoader.getScaledImage(gw.getSprite_path(), rc.rectAngles.get(i).getImageNumber(),gw.getGame().getImage_scale()).getScaledInstance(120,102, 2),rc.rectAngles.get(i).getX()-35,rc.rectAngles.get(i).getY()-20,null);
				}
		}
		rc.paintRectangles(g);
	}
}

