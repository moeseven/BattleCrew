package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import SpriteSheet.StaticImageLoader;
import gameLogic.Game;
import gameLogic.Item;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;


public class HeroInventoryPaintComponent extends JComponent{
		private JPanel jp;
		private JScrollPane sp;
		private CampaignWindow gw;
		public RectangleClicker rc;
		private int x_offset=-90;
		private Game game;
		public HeroInventoryPaintComponent(CampaignWindow sw){
			this.gw=sw;
			game = gw.getGame();
			setBorder(new LineBorder(Color.YELLOW));
			super.setPreferredSize(new Dimension(550,200));
			addMouseListener(new MyMouseListener());
			setLayout(new BorderLayout());
			setVisible(true);
			if(game.getPlayer().getInventory().getInventory_map().size()>0) {
				game.getPlayer().setSelectedItem(game.getPlayer().getInventory().getInventory_list().getFirst().get(0));
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
					if (game.getPlayer().getInventory().getInventory_map().size()<1) {
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
					if(game.getPlayer().getInventory().getInventory_list().size()>0) {						
						if(game.getPlayer().getInventory().getInventory_list().size()>1) {
							game.getPlayer().getInventory().getInventory_list().addLast(game.getPlayer().getInventory().getInventory_list().removeFirst());
						}
						game.getPlayer().setSelectedItem(game.getPlayer().getInventory().getInventory_list().getFirst().get(0));
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
					if(game.getPlayer().getInventory().getInventory_list().size()>0) {						
						if(game.getPlayer().getInventory().getInventory_list().size()>1) {
							game.getPlayer().getInventory().getInventory_list().addFirst(game.getPlayer().getInventory().getInventory_list().removeLast());
						}
						game.getPlayer().setSelectedItem(game.getPlayer().getInventory().getInventory_list().getFirst().get(0));
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
					if(game.getPlayer().getSelectedItem()!=null) {
						if (game.getPlayer().getInventory().contains(game.getPlayer().getSelectedItem())) {
							game.getPlayer().getSelectedItem().generateItemDescription();						
							caption=game.getPlayer().getSelectedItem().getDescription();
							String composite = game.getPlayer().getSelectedItem().getName();
							
							caption.addFirst(composite+"(x"+game.getPlayer().getInventory().getInventory_map().get(game.getPlayer().getSelectedItem().getName()).size()+")");
	//						if(gw.getGame().getPlayer().getSelectedItem().getNumberOfSuffixes()>0) {
	//							setFirstLineColor(Color.blue);
	//							if(gw.getGame().getPlayer().getSelectedItem().getNumberOfSuffixes()>1) {
	//								setFirstLineColor(Color.orange);
	//								if(gw.getGame().getPlayer().getSelectedItem().getNumberOfSuffixes()>2) {
	//									setFirstLineColor(Color.PINK);
	//								}
	//							}
	//						}
							}
						
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
					
					if(game.getPlayer().getSelectedItem()!=null) {
						this.setImageNumber(game.getPlayer().getSelectedItem().getImage());
						
					}					
				}		
			});
			//use
			rc.addRect(new ClickableRectangle("use",210+x_offset,60,55,20) {
				@Override
				public void onClick(MouseEvent e) {					
					if(game.getPlayer().getSelectedItem()!=null) {
						Item itemUsable=game.getPlayer().getSelectedItem();
						if(game.getPlayer().getInventory().contains(itemUsable)) {
							//TODO
							if (itemUsable.getCategory() == 0) {
								itemUsable.mod(gw.getGame().getPlayer().getSelectedUnit());
								gw.getGame().getPlayer().getInventory().remove(itemUsable);								
							}
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
					if(game.getPlayer().getSelectedItem()!=null) {
						if(game.getPlayer().getInventory().contains(game.getPlayer().getSelectedItem())) {
							//equip
							game.getPlayer().getSelectedUnit().equip(game.getPlayer().getSelectedItem());
							if (game.getPlayer().getSelectedItem().getCategory()==7 && game.getPlayer().getInventory().getInventory_map().containsKey(game.getPlayer().getSelectedItem().getName())) { //x10 if ammunition
								if (game.getPlayer().getInventory().getInventory_map().get(game.getPlayer().getSelectedItem().getName()).size()>10) {
									for (int i = 0; i < 9; i++) {
										game.getPlayer().setSelectedItem(game.getPlayer().getInventory().getInventory_map().get(game.getPlayer().getSelectedItem().getName()).get(0));
										game.getPlayer().getSelectedUnit().equip(game.getPlayer().getSelectedItem());
									}
								}
																
							}
						}else {
							//unequip
							game.getPlayer().getSelectedUnit().getEquipment().unequipItem(game.getPlayer().getSelectedItem());
						}				
					}
					if(game.getPlayer().getInventory().getInventory_list().size()>0) {
						game.getPlayer().setSelectedItem(game.getPlayer().getInventory().getInventory_list().getFirst().get(0));
					}
					
				}
				@Override
				public void updateCaption() {					
					if(game.getPlayer().getInventory().contains(game.getPlayer().getSelectedItem())) {
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
					if (game.getPlayer().getSelectedItem()!=null) {
						caption.addFirst("gold: "+game.getPlayer().getGold()+" ("+game.getPlayer().getSelectedItem().getGold_value()+")");					
					}else {
						caption.addFirst("gold: "+game.getPlayer().getGold());					
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
					g.drawImage(StaticImageLoader.getScaledImage(gw.getSprite_path(), rc.rectAngles.get(i).getImageNumber(),game.getImage_scale()).getScaledInstance(120,102, 2),rc.rectAngles.get(i).getX()-35,rc.rectAngles.get(i).getY()-20,null);
				}
		}
		rc.paintRectangles(g);
	}
}

