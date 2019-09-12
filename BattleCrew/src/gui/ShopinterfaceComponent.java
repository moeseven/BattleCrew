package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import SpriteSheet.StaticImageLoader;
import gameLogic.Item;
import gameLogic.Shop;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;

public class ShopinterfaceComponent extends JComponent{
	private RectangleClicker rc;
	private CampaignWindow campaign_window;
	private Shop shop;
	public ShopinterfaceComponent(CampaignWindow campaignWindow, Shop s) {
	this.campaign_window=campaignWindow;
	this.shop=s;
	setBorder(new LineBorder(Color.WHITE));
	super.setPreferredSize(new Dimension(600,200));
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
			if(campaign_window.getGame().getPlayer().getInventory().size()>0) {
				campaign_window.getGame().getPlayer().setSelectedItem(campaign_window.getGame().getPlayer().getInventory().getFirst());
				if(campaign_window.getGame().getPlayer().getInventory().size()>1) {
					campaign_window.getGame().getPlayer().getInventory().addLast(campaign_window.getGame().getPlayer().getInventory().removeFirst());
					campaign_window.getGame().getPlayer().setSelectedItem(campaign_window.getGame().getPlayer().getInventory().getFirst());
				}
			}					
				
		}
		@Override
		public void updateCaption() {					
		}		
	});
	rc.addRect(new ClickableRectangle("->",420,100,45,20) {
		@Override
		public void onClick(MouseEvent e) {
			if(campaign_window.getGame().getPlayer().getInventory().size()>0) {
				campaign_window.getGame().getPlayer().setSelectedItem(campaign_window.getGame().getPlayer().getInventory().getFirst());
				if(campaign_window.getGame().getPlayer().getInventory().size()>1) {
					campaign_window.getGame().getPlayer().getInventory().addFirst(campaign_window.getGame().getPlayer().getInventory().removeLast());
					campaign_window.getGame().getPlayer().setSelectedItem(campaign_window.getGame().getPlayer().getInventory().getFirst());
				}
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
			if(shop.getItems().size()>0) {
				campaign_window.getGame().getPlayer().setSelectedItem(shop.getItems().getFirst());
				if(shop.getItems().size()>1) {
					shop.getItems().addLast(shop.getItems().removeFirst());
					campaign_window.getGame().getPlayer().setSelectedItem(shop.getItems().getFirst());					}
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
			if(shop.getItems().size()>0) {
				campaign_window.getGame().getPlayer().setSelectedItem(shop.getItems().getFirst());
				if(shop.getItems().size()>1) {
					shop.getItems().addFirst(shop.getItems().removeLast());
					campaign_window.getGame().getPlayer().setSelectedItem(shop.getItems().getFirst());					}
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
			if(campaign_window.getGame().getPlayer().getSelectedItem()!=null) {
				Item item=campaign_window.getGame().getPlayer().getSelectedItem();
				if(campaign_window.getGame().getPlayer().getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
					//sell
					shop.getItems().add(item);
					campaign_window.getGame().getPlayer().getInventory().remove(item);
					campaign_window.getGame().getPlayer().gainGold((int) (item.getGold_value()/5.0));
					if(campaign_window.getGame().getPlayer().getInventory().size()>0) {
						campaign_window.getGame().getPlayer().setSelectedItem(campaign_window.getGame().getPlayer().getInventory().getFirst());
					}
				}	
			}

		}
		@Override
		public void updateCaption() {
			// TODO Auto-generated method stub
			if(campaign_window.getGame().getPlayer().getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
				this.setFirstLineColor(Color.black);
			}else {
				if(shop.getItems().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
					this.setFirstLineColor(Color.GRAY);
				}			
			}					
		}		
	});
	rc.addRect(new ClickableRectangle("buy",510,10,45,20) {
		@Override
		public void onClick(MouseEvent e) {
			// TODO Auto-generated method stub
			if(campaign_window.getGame().getPlayer().getSelectedItem()!=null) {
				Item item=campaign_window.getGame().getPlayer().getSelectedItem();
				if(!campaign_window.getGame().getPlayer().getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
					if(shop.getItems().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
						//buy
						if(campaign_window.getGame().getPlayer().getGold()>=campaign_window.getGame().getPlayer().getSelectedItem().getGold_value()) {
							if(campaign_window.getGame().getPlayer().addItemtoInventory(campaign_window.getGame().getPlayer().getSelectedItem())) {
								campaign_window.getGame().getPlayer().gainGold(-campaign_window.getGame().getPlayer().getSelectedItem().getGold_value());
								shop.getItems().remove(campaign_window.getGame().getPlayer().getSelectedItem());
							}								
						}
						if(shop.getItems().size()>0) {
							campaign_window.getGame().getPlayer().setSelectedItem(shop.getItems().getFirst());
						}
					}
				}				
			}

		}
		@Override
		public void updateCaption() {
			// TODO Auto-generated method stub
			if(campaign_window.getGame().getPlayer().getInventory().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
				this.setFirstLineColor(Color.GRAY);
			}else {
				if(shop.getItems().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
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
				if(shop.getItems().contains(campaign_window.getGame().getPlayer().getSelectedItem())) {
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
			revalidate();
			repaint();			
		}else{
			if (e.getButton()==3){
				//new CardView(card);
			}
		}
	} 
}
protected void paintComponent(Graphics g){
	super.paintComponent(g);				
	g.drawImage(StaticImageLoader.getScaledImage(campaign_window.getSprite_path(),shop.getImageNumber(), campaign_window.getGame().image_scale).getScaledInstance(180, 153, 3),-40,0,null);
	if (campaign_window.getGame().getPlayer().getSelectedItem()!=null) {
			g.drawImage(StaticImageLoader.getScaledImage(campaign_window.getSprite_path(), campaign_window.getGame().getPlayer().getSelectedItem().getImage(), campaign_window.getGame().image_scale).getScaledInstance(180,153, 3),150,10,null);				
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
}