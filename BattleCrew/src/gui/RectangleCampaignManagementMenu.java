package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import guiRectangles.RectangleClicker;

public class RectangleCampaignManagementMenu extends JComponent {
	private RectangleClicker rectangle_clicker;
	private JButton shop_button;
	private CampaignWindow cw;
	public RectangleCampaignManagementMenu(CampaignWindow cw) {	
		setLayout(new GridLayout());
		shop_button= new JButton("shop");
		add(shop_button);
		rectangle_clicker=new RectangleClicker();
	}
	private void addRectangles() {
		//shop, tavern, available battles, Warrior management,
	}
	private class ShopButton extends JButton{
		public ShopButton() {
			setName("shop");
			this.setText("shop");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new ShopButtonMouseListener());
		}
		private class ShopButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					//TODO open shop here
					
				}
			} 
		}
	}
	private class PrepareBattleButton extends JButton{
		public PrepareBattleButton() {
			setName("shop");
			this.setText("shop");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new PrepareButtonMouseListener());
		}
		private class PrepareButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					//TODO open shop here
					
				}
			} 
		}
	}
}
