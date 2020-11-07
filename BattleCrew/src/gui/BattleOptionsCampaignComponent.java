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

public class BattleOptionsCampaignComponent extends JComponent implements Refreshable_gui{
	private RectangleClicker rc;
	private ViewControlledWindow window;
	static int BATTLE_SIZE_X = 300;
	static int BATTLE_SIZE_Y = 105;
	static int NEXT_ROW_THRESHOLD = 2;
	public BattleOptionsCampaignComponent(ViewControlledWindow window) {
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
	for (int i = 0; i < window.get_view_controller().getGame().getCampaign().getBattle_templates().size(); i++) {
		int y_positon = 0;
		int x_position = BATTLE_SIZE_X*i;
		if (i > NEXT_ROW_THRESHOLD-1) {
			if (i > 2*NEXT_ROW_THRESHOLD-1) {
				y_positon += 2*BATTLE_SIZE_Y;
				x_position = x_position - 2*NEXT_ROW_THRESHOLD*BATTLE_SIZE_X;
			}else {
				y_positon += BATTLE_SIZE_Y;
				x_position = x_position - NEXT_ROW_THRESHOLD*BATTLE_SIZE_X;
			}
			
		}
		ClickableRectangle r = new ClickableRectangle(window.get_view_controller().getGame().getCampaign().getBattle_templates().get(i).getName(),x_position,y_positon,BATTLE_SIZE_X,BATTLE_SIZE_Y) {
			@Override
			public void onClick(MouseEvent e) {
				int battle_index = this.getX()/BATTLE_SIZE_X;
				if (this.getY() >= BATTLE_SIZE_Y) {
					battle_index = battle_index + NEXT_ROW_THRESHOLD;
				}
				window.get_view_controller().getGame().getCampaign().setSelectedBattle(window.get_view_controller().getGame().getCampaign().getBattle_templates().get(battle_index));
				caption=window.get_view_controller().getGame().getCampaign().getBattle_templates().get(battle_index).generate_description_lines();
			}
			@Override
			public void updateCaption() {
				
			}		
		};
		//r.setImageNumber(window.get_view_controller().getGame().getPlayer().getHeroes().get(i).getImage_number());
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
	rc.paintRectangles(g);
	for(int i=0; i<rc.rectAngles.size();i++) {
		g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,rc.getRectAngles().get(i).getImageNumber(), window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY(),null);
		if (window.get_view_controller().getGame().getCampaign().getSelectedBattle() == window.get_view_controller().getGame().getCampaign().getBattle_templates().get(i)) {
			g.drawImage(StaticImageLoader.getScaledImage(Resources.IMAGE_PATH,391, window.get_view_controller().image_scale).getScaledInstance(120, 102, 2),-30+rc.rectAngles.get(i).getX(),rc.rectAngles.get(i).getY()+15,null);
		}
	}
	g.drawString("Round: " + window.get_view_controller().getGame().getCampaign().getRound_counter(), BATTLE_SIZE_X*2 +100, 10);
}

@Override
public ViewController get_gui_controller() {
	// TODO Auto-generated method stub
	return window.get_view_controller();
}
}
