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
import gameLogic.Spell;
import gui.windows.CampaignWindow;
import gui.windows.ViewControlledWindow;
import gui.windows.ViewController;
import guiRectangles.ClickableRectangle;
import guiRectangles.RectangleClicker;

public class MagicSchoolComponent extends JComponent implements Refreshable_gui{
	private RectangleClicker rc;
	private ViewControlledWindow window;
	private Spell selectedSpell;
	static int SHOP_ITEM_SIZE_X = 85;
	static int SHOP_ITEM_SIZE_Y = 90;
	static int NEXT_ROW_THRESHOLD = 9;
	public MagicSchoolComponent(ViewControlledWindow window) {
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
	for (int i = 0; i < window.get_view_controller().getGame().getShop().getSpells().size(); i++) {
		int y_positon = 0;
		int x_position = SHOP_ITEM_SIZE_X*i;
		if (i > NEXT_ROW_THRESHOLD-1) {
			if (i > 2*NEXT_ROW_THRESHOLD-1) {
				y_positon += 2*SHOP_ITEM_SIZE_Y;
				x_position = x_position - 2*NEXT_ROW_THRESHOLD*SHOP_ITEM_SIZE_X;
			}else {
				y_positon += SHOP_ITEM_SIZE_Y;
				x_position = x_position - NEXT_ROW_THRESHOLD*SHOP_ITEM_SIZE_X;
			}
			
		}
		ClickableRectangle r = new ClickableRectangle(window.get_view_controller().getGame().getShop().getSpells().get(i).getName(),x_position,y_positon,SHOP_ITEM_SIZE_X,SHOP_ITEM_SIZE_Y) {
			@Override
			public void onClick(MouseEvent e) {
				int item_index = this.getX()/SHOP_ITEM_SIZE_X;
				if (this.getY() >= SHOP_ITEM_SIZE_Y) {
					if (this.getY() >= 2*SHOP_ITEM_SIZE_Y) {
						item_index = item_index + 2*NEXT_ROW_THRESHOLD;
					}else {
						item_index = item_index + NEXT_ROW_THRESHOLD;
					}					
				}
				selectedSpell = window.get_view_controller().getGame().getShop().getSpells().get(item_index);				
			}
			@Override
			public void updateCaption() {
			}		
		};
		//TODO images for spells
		r.setImageNumber(1);
		rc.addRect(r);
	}

		rc.addRect(new ClickableRectangle("study spell",910,10,85,30) {
			@Override
			public void onClick(MouseEvent e) {			
				if(selectedSpell!=null) {
					selectedSpell.studySpell(window.get_view_controller().getGame().getPlayer().getSelectedUnit());
				}
			}
			@Override
			public void updateCaption() {
								
			}		
		});
		rc.addRect(new ClickableRectangle("flip through spells",910,40,85,30) {
			@Override
			public void onClick(MouseEvent e) {			
				window.get_view_controller().getGame().getPlayer().getSelectedUnit().getSpellbook().addLast(window.get_view_controller().getGame().getPlayer().getSelectedUnit().getSpellbook().removeFirst());
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
	rc.paintRectangles(g);
	for(int i=0; i<rc.rectAngles.size()-2;i++) {
		g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,rc.getRectAngles().get(i).getImageNumber(), window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY(),null);
		if (window.get_view_controller().getGame().getShop().getSpells().get(i) == selectedSpell) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,391, window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY()+15,null);
		}
	}
	//Spellbook of selected hero
	g.drawString("Spellbook:", 500, 30);
	for (int i = 0; i < window.get_view_controller().getGame().getPlayer().getSelectedUnit().getSpellbook().size(); i++) {
		g.drawString(window.get_view_controller().getGame().getPlayer().getSelectedUnit().getSpellbook().get(i).getName(), 500, 50+i*20);
	}

}

@Override
public ViewController get_gui_controller() {
	// TODO Auto-generated method stub
	return window.get_view_controller();
}
}
