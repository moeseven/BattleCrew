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
import gui.windows.ViewControlledWindow;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;

public class WarriorCampaignComponent extends JComponent{
	private RectangleClicker rc;
	private ViewControlledWindow window;
	static int WARRIOR_SIZE_X = 80;
	static int WARRIOR_SIZE_Y = 140;
	public WarriorCampaignComponent(ViewControlledWindow window) {
	this.window=window;
	setBorder(new LineBorder(Color.WHITE));
	super.setPreferredSize(new Dimension(600,300));
	MyMouseListener ml = new MyMouseListener();
	super.addMouseListener(ml);
	setLayout(new BorderLayout());
	setVisible(true);
	//rectangles
	rc=new RectangleClicker();

	//Inventory player	
	for (int i = 0; i < window.get_view_controller().getGame().getPlayer().getHeroes().size(); i++) {
		ClickableRectangle r = new ClickableRectangle(window.get_view_controller().getGame().getPlayer().getHeroes().get(i).getName(),WARRIOR_SIZE_X*i,120,WARRIOR_SIZE_X,WARRIOR_SIZE_Y) {
			@Override
			public void onClick(MouseEvent e) {
				window.get_view_controller().getGame().getPlayer().setSelectedHero(window.get_view_controller().getGame().getPlayer().getHeroes().get(this.getX()/WARRIOR_SIZE_X));				
			}
			@Override
			public void updateCaption() {
			}		
		};
		r.setImageNumber(window.get_view_controller().getGame().getPlayer().getHeroes().get(i).getImage_number());
		rc.addRect(r);
	}
	
}

private class MyMouseListener extends MouseAdapter{
	public void mousePressed(MouseEvent e){	
		
		if(e.getButton()==1){
			//get equipment position from click
					
		}else{
			if (e.getButton()==3){
				//new CardView(card);
			}
		}
		rc.triggerClick(e);
		window.refresh();
	} 
}

public void refresh() {
	rc.updateCaptions();	
	revalidate();
	repaint();	
}

protected void paintComponent(Graphics g){
	super.paintComponent(g);
//	for (int i = 0; i < window.get_view_controller().getGame().getPlayer().getHeroes().size(); i++) {
//		if (window.get_view_controller().getGame().getPlayer().getSelectedUnit() == window.get_view_controller().getGame().getPlayer().getHeroes().get(i)) {
//			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,window.get_view_controller().getGame().getPlayer().getHeroes().get(i).getImage_number(), window.get_view_controller().getGame().image_scale).getScaledInstance(180, 153, 3),-20+i*90,0,null);
//			g.drawString(window.get_view_controller().getGame().getPlayer().getHeroes().get(i).getName(),40+i*90, 165);
//		}else {
//			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,window.get_view_controller().getGame().getPlayer().getHeroes().get(i).getImage_number(), window.get_view_controller().getGame().image_scale).getScaledInstance(120, 102, 2),-40+i*90,0,null);
//			g.drawString(window.get_view_controller().getGame().getPlayer().getHeroes().get(i).getName(),7+i*90, 110);
//		}
//		
//	}
	rc.paintRectangles(g);
	for(int i=0; i<rc.rectAngles.size();i++) {
		g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,rc.getRectAngles().get(i).getImageNumber(), window.get_view_controller().getGame().image_scale).getScaledInstance(120, 102, 2),-20+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY(),null);
	}
}
}
