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
import gui.windows.CampaignWindow;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;

public class WarriorCampaignComponent extends JComponent{
	private RectangleClicker rc;
	private CampaignWindow campaign_window;
	public WarriorCampaignComponent(CampaignWindow campaignWindow) {
	this.campaign_window=campaignWindow;
	setBorder(new LineBorder(Color.WHITE));
	super.setPreferredSize(new Dimension(600,300));
	MyMouseListener ml = new MyMouseListener();
	super.addMouseListener(ml);
	setLayout(new BorderLayout());
	setVisible(true);
	//rectangles
	rc=new RectangleClicker();
	//Inventory player	
	
	rc.addRect(new ClickableRectangle("<-",125,120,45,20) {
		@Override
		public void onClick(MouseEvent e) {
			campaignWindow.gui_controller.getGame().getPlayer().getHeroes().add(campaignWindow.gui_controller.getGame().getPlayer().getHeroes().removeFirst());
			campaignWindow.gui_controller.getGame().getPlayer().setSelectedHero(campaignWindow.gui_controller.getGame().getPlayer().getHeroes().getFirst());
				
		}
		@Override
		public void updateCaption() {					
		}		
	});
	rc.addRect(new ClickableRectangle("->",170,120,45,20) {
		@Override
		public void onClick(MouseEvent e) {
			campaignWindow.gui_controller.getGame().getPlayer().getHeroes().addFirst(campaignWindow.gui_controller.getGame().getPlayer().getHeroes().removeLast());
			campaignWindow.gui_controller.getGame().getPlayer().setSelectedHero(campaignWindow.gui_controller.getGame().getPlayer().getHeroes().getFirst());
		}
		@Override
		public void updateCaption() {					
		}		
	});
	
}

private class MyMouseListener extends MouseAdapter{
	public void mousePressed(MouseEvent e){	
		if(e.getButton()==1){
			//get equipment position from click
			rc.triggerClick(e);
			rc.updateCaptions();
			//campaign_window.refresh();
			campaign_window.refresh();
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
	for (int i = 0; i < campaign_window.gui_controller.getGame().getPlayer().getHeroes().size(); i++) {
		if (campaign_window.gui_controller.getGame().getPlayer().getSelectedUnit() == campaign_window.gui_controller.getGame().getPlayer().getHeroes().get(i)) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,campaign_window.gui_controller.getGame().getPlayer().getHeroes().get(i).getImage_number(), campaign_window.gui_controller.getGame().image_scale).getScaledInstance(180, 153, 3),-20+i*90,0,null);
			g.drawString(campaign_window.gui_controller.getGame().getPlayer().getHeroes().get(i).getName(),40+i*90, 165);
		}else {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,campaign_window.gui_controller.getGame().getPlayer().getHeroes().get(i).getImage_number(), campaign_window.gui_controller.getGame().image_scale).getScaledInstance(120, 102, 2),-40+i*90,0,null);
			g.drawString(campaign_window.gui_controller.getGame().getPlayer().getHeroes().get(i).getName(),7+i*90, 110);
		}
		
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
