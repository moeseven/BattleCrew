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
import gui.windows.ViewController;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;

public class WarriorCampaignComponent extends JComponent implements Refreshable_gui{
	private RectangleClicker rc;
	private ViewControlledWindow window;
	static int WARRIOR_SIZE_X = 100;
	static int WARRIOR_SIZE_Y = 105;
	static int NEXT_ROW_THRESHOLD = 6;
	public WarriorCampaignComponent(ViewControlledWindow window) {
	this.window=window;
	setBorder(new LineBorder(Color.WHITE));
	super.setPreferredSize(new Dimension(600,350));
	MyMouseListener ml = new MyMouseListener();
	super.addMouseListener(ml);
	setLayout(new BorderLayout());
	setVisible(true);
	//rectangles
	set_up_rectangles();
	
	
}
	
public void set_up_rectangles() {
	rc=new RectangleClicker();
	for (int i = 0; i < window.get_view_controller().getGame().getPlayer().getHeroes().size(); i++) {
		int y_positon = 0;
		int x_position = WARRIOR_SIZE_X*i;
		if (i > NEXT_ROW_THRESHOLD-1) {
			if (i > 2*NEXT_ROW_THRESHOLD-1) {
				y_positon += 2*WARRIOR_SIZE_Y;
				x_position = x_position - 2*NEXT_ROW_THRESHOLD*WARRIOR_SIZE_X;
			}else {
				y_positon += WARRIOR_SIZE_Y;
				x_position = x_position - NEXT_ROW_THRESHOLD*WARRIOR_SIZE_X;
			}
			
		}
		ClickableRectangle r = new ClickableRectangle(window.get_view_controller().getGame().getPlayer().getHeroes().get(i).getName(),x_position,y_positon,WARRIOR_SIZE_X,WARRIOR_SIZE_Y) {
			@Override
			public void onClick(MouseEvent e) {
				int hero_index = this.getX()/WARRIOR_SIZE_X;
				if (this.getY() >= WARRIOR_SIZE_Y) {
					if(this.getY() >= WARRIOR_SIZE_Y*2) {
						hero_index = hero_index + 2*NEXT_ROW_THRESHOLD;
					}else {
						hero_index = hero_index + NEXT_ROW_THRESHOLD;
					}					
				}
				window.get_view_controller().getGame().getPlayer().setSelectedHero(window.get_view_controller().getGame().getPlayer().getHeroes().get(hero_index));				
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
	set_up_rectangles();
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
		g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,rc.getRectAngles().get(i).getImageNumber(), window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY(),null);
		if (window.get_view_controller().getGame().getPlayer().getHeroes().get(i) == window.get_view_controller().getGame().getPlayer().getCommander()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,391, window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY()+15,null);
		}
		if (window.get_view_controller().getGame().getPlayer().getHeroes().get(i) == window.get_view_controller().getGame().getPlayer().getRecruiter()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,385, window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY()+15,null);
		}
		if (window.get_view_controller().getGame().getPlayer().getHeroes().get(i) == window.get_view_controller().getGame().getPlayer().getHealer()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,390, window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY()+15,null);
		}
		if (window.get_view_controller().getGame().getPlayer().getHeroes().get(i) == window.get_view_controller().getGame().getPlayer().getSmith()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,389, window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY()+15,null);
		}
		if (window.get_view_controller().getGame().getPlayer().getHeroes().get(i) == window.get_view_controller().getGame().getPlayer().getTreasurer()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,388, window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY()+15,null);
		}
		if (window.get_view_controller().getGame().getPlayer().getHeroes().get(i) == window.get_view_controller().getGame().getPlayer().getLeader()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,387, window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY()+15,null);
		}
		if (window.get_view_controller().getGame().getPlayer().getHeroes().get(i) == window.get_view_controller().getGame().getPlayer().getChampion()) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,386, window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY()+15,null);
		}
	}
}

@Override
public ViewController get_gui_controller() {
	// TODO Auto-generated method stub
	return window.get_view_controller();
}
}
