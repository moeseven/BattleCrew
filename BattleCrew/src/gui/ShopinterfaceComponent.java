package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import SpriteSheet.StaticImageLoader;
import gameLogic.Item;
import gameLogic.Shop;
import gui.windows.CampaignWindow;
import gui.windows.ViewController;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;

public class ShopinterfaceComponent extends JComponent implements Refreshable_gui{
	private RectangleClicker rc;
	private CampaignWindow campaign_window;
	private Shop shop;
	private ShopItmesComponent sic;
	public ShopinterfaceComponent(CampaignWindow campaignWindow, Shop s) {
	this.campaign_window=campaignWindow;
	this.sic = new ShopItmesComponent(campaignWindow);
	sic.setVisible(true);
	this.shop=s;
	setBorder(new LineBorder(Color.WHITE));
	setLayout(new GridLayout(1, 2));
	add(sic); //TODO not displayed             
	super.setPreferredSize(new Dimension(600,350));
	MyMouseListener ml = new MyMouseListener();
	super.addMouseListener(ml);
	setLayout(new BorderLayout());
	setVisible(true);
	//rectangles
	rc=new RectangleClicker();
	//Inventory player
	rc.addRect(new ClickableRectangle("inventory",375,80,90,20) {
		@Override
		public void onClick(MouseEvent e) {	
		}
		@Override
		public void updateCaption() {					
		}		
	});
	
	
	rc.addRect(new ClickableRectangle("<-",375,100,45,20) {
		@Override
		public void onClick(MouseEvent e) {
			if(campaign_window.getGame().getPlayer().getInventory().getInventory_list().size()>0) {				
				if(campaign_window.getGame().getPlayer().getInventory().getInventory_list().size()>1) {
					campaign_window.getGame().getPlayer().getInventory().getInventory_list().addLast(campaign_window.getGame().getPlayer().getInventory().getInventory_list().removeFirst());
				}
				campaign_window.getGame().getPlayer().setSelectedItem(campaign_window.getGame().getPlayer().getInventory().getInventory_list().getFirst().get(0));
			}					
				
		}
		@Override
		public void updateCaption() {					
		}		
	});
	rc.addRect(new ClickableRectangle("->",420,100,45,20) {
		@Override
		public void onClick(MouseEvent e) {
			if(campaign_window.getGame().getPlayer().getInventory().getInventory_list().size()>0) {				
				if(campaign_window.getGame().getPlayer().getInventory().getInventory_list().size()>1) {
					campaign_window.getGame().getPlayer().getInventory().getInventory_list().addFirst(campaign_window.getGame().getPlayer().getInventory().getInventory_list().removeLast());
				}
				campaign_window.getGame().getPlayer().setSelectedItem(campaign_window.getGame().getPlayer().getInventory().getInventory_list().getFirst().get(0));
			}					
				
		}
		@Override
		public void updateCaption() {					
		}		
	});
	//Inventory shop
	rc.addRect(new ClickableRectangle("shop",465,80,90,20) {
		@Override
		public void onClick(MouseEvent e) {									
		}

		@Override
		public void updateCaption() {
		}		
	});
	rc.addRect(new ClickableRectangle("<-",465,100,45,20) {
		@Override
		public void onClick(MouseEvent e) {
			// TODO Auto-generated method stub
			if(shop.getInventory().getInventory_list().size()>0) {
				if(shop.getInventory().getInventory_list().size()>1) {
					shop.getInventory().getInventory_list().addLast(shop.getInventory().getInventory_list().removeFirst());								
				}
				campaign_window.getGame().getPlayer().setSelectedItem(shop.getInventory().getInventory_list().getFirst().get(0));	
			}					
				
		}
		@Override
		public void updateCaption() {
			// TODO Auto-generated method stub					
		}		
	});
	rc.addRect(new ClickableRectangle("->",510,100,45,20) {
		@Override
		public void onClick(MouseEvent e) {
			// TODO Auto-generated method stub
			if(shop.getInventory().getInventory_list().size()>0) {			
				if(shop.getInventory().getInventory_list().size()>1) {
					shop.getInventory().getInventory_list().addFirst(shop.getInventory().getInventory_list().removeLast());		
					}
				campaign_window.getGame().getPlayer().setSelectedItem(shop.getInventory().getInventory_list().getFirst().get(0));
			}					
				
		}
		@Override
		public void updateCaption() {
			// TODO Auto-generated method stub					
		}		
	});
	
	//item description
	rc.addRect(new ClickableRectangle("description",305,10,250,110) {
		@Override
		public void onClick(MouseEvent e) {

		}
		@Override
		public void updateCaption() {
			// TODO Auto-generated method stub
			setFirstLineColor(Color.black);
			if(campaign_window.getGame().getPlayer().getSelectedItem()!=null) {
				campaign_window.getGame().getPlayer().getSelectedItem().generateItemDescription();						
				caption=campaign_window.getGame().getPlayer().getSelectedItem().getDescription();
				caption.addFirst(campaign_window.getGame().getPlayer().getSelectedItem().getName());
				//color
//				if(campaign_window.getGame().getPlayer().getSelectedItem().getNumberOfSuffixes()>0) {
//					setFirstLineColor(Color.blue);
//					if(campaign_window.getGame().getPlayer().getSelectedItem().getNumberOfSuffixes()>1) {
//						setFirstLineColor(Color.orange);
//						if(campaign_window.getGame().getPlayer().getSelectedItem().getNumberOfSuffixes()>2) {
//							setFirstLineColor(Color.PINK);
//						}
//					}
//				}
				//
			}else {
				caption=new LinkedList<String>();
			}					
		}		
	});
	//sell
	rc.addRect(new ClickableRectangle("sell",510,35,45,20) {
		@Override
		public void onClick(MouseEvent e) {
			// TODO Auto-generated method stub
			shop.sell_item(campaignWindow.getGame().getPlayer(), campaign_window.getGame().getPlayer().getSelectedItem());

		}
		@Override
		public void updateCaption() {
			// TODO Auto-generated method stub
			if(campaign_window.getGame().getPlayer().getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
				this.setFirstLineColor(Color.black);
			}else {
				if(shop.getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
					this.setFirstLineColor(Color.GRAY);
				}			
			}					
		}		
	});
	rc.addRect(new ClickableRectangle("buy",510,10,55,30) {
		@Override
		public void onClick(MouseEvent e) {
			// TODO Auto-generated method stub
			if(campaign_window.getGame().getPlayer().getSelectedItem()!=null) {
				if(!campaign_window.getGame().getPlayer().getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
					if(shop.getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
						buy(campaign_window.getGame().getPlayer().getSelectedItem()); //buy item
						if (campaign_window.getGame().getPlayer().getSelectedItem().getCategory()==7 && shop.getInventory().getInventory_map().containsKey(campaign_window.getGame().getPlayer().getSelectedItem().getName())) { //x10 if ammunition
							if (shop.getInventory().getInventory_map().get(campaign_window.getGame().getPlayer().getSelectedItem().getName()).size()>10) {
								for (int i = 0; i < 9; i++) {
									campaign_window.getGame().getPlayer().setSelectedItem(shop.getInventory().getInventory_map().get(campaign_window.getGame().getPlayer().getSelectedItem().getName()).get(0));
									buy(campaign_window.getGame().getPlayer().getSelectedItem());
								}
							}
															
						}
						if(shop.getInventory().getInventory_list().size()>0) {
							campaign_window.getGame().getPlayer().setSelectedItem(shop.getInventory().getInventory_list().getFirst().get(0));
						}
					}
				}				
			}

		}
		private boolean buy(Item item){
			return shop.buy_item(campaignWindow.getGame().getPlayer(), item);
		}
		@Override
		public void updateCaption() {
			// TODO Auto-generated method stub
			if(campaign_window.getGame().getPlayer().getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
				this.setFirstLineColor(Color.GRAY);
			}else {
				if(shop.getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
					this.setFirstLineColor(Color.black);
				}			
			}					
		}		
	});
	//gold
	rc.addRect(new ClickableRectangle("gold",305,120,250,20) {
		@Override
		public void onClick(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void updateCaption() {
			// TODO Auto-generated method stub	
			if(campaign_window.getGame().getPlayer().getSelectedItem()!=null) {
				if(shop.getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
					caption.removeFirst();
					caption.addFirst("gold: "+campaign_window.getGame().getPlayer().getGold()+" ("+campaign_window.getGame().getPlayer().getSelectedItem().getGold_value()+")");	
				}else {
					caption.removeFirst();
					caption.addFirst("gold: "+campaign_window.getGame().getPlayer().getGold()+" ("+(int)(campaign_window.getGame().getPlayer().getSelectedItem().getGold_value()/3.5)+")");	
				}
				
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
			rc.updateCaptions();
			//campaign_window.refresh();
			revalidate();
			repaint();			
		}else{
			if (e.getButton()==3){
				//new CardView(card);
			}
		}
		get_gui_controller().refresh_gui();
	} 
}
protected void paintComponent(Graphics g){
	super.paintComponent(g);				
	g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,shop.getImageNumber(), campaign_window.gui_controller.image_scale).getScaledInstance(180, 153, 3),-40,0,null);
	if (campaign_window.getGame().getPlayer().getSelectedItem()!=null) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH, campaign_window.getGame().getPlayer().getSelectedItem().getImage(), campaign_window.gui_controller.image_scale).getScaledInstance(180,153, 3),150,10,null);				
	}
	for(int i=0; i<rc.rectAngles.size();i++) {
		g.drawRect(rc.rectAngles.get(i).getX(), rc.rectAngles.get(i).getY(), rc.rectAngles.get(i).getLength(), rc.rectAngles.get(i).getHeight());
		
		for(int a=0; a<rc.rectAngles.get(i).getCaption().size();a++) {
			g.setColor(Color.black);
			if(a==0) {
				g.setColor(rc.rectAngles.get(i).getFirstLineColor());
			}
			g.drawString(rc.rectAngles.get(i).getCaption().get(a), rc.rectAngles.get(i).getX()+3, rc.rectAngles.get(i).getY()+11+a*11);
		}
	}
}
@Override
public void refresh() {
	rc.updateCaptions();
	repaint();
	
}
@Override
public ViewController get_gui_controller() {
	return campaign_window.gui_controller;
}
}
