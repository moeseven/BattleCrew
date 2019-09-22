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
	private JButton shop_button,prepare_battle_button;
	private CampaignWindow cw;
	public RectangleCampaignManagementMenu(CampaignWindow cw) {	
		this.cw=cw;
		setLayout(new GridLayout());
		shop_button= new ShopButton();
		prepare_battle_button= new PrepareBattleButton();
		add(shop_button);
		add(prepare_battle_button);
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
					cw.setState(0);
					cw.showAccurateComponent();
					shop_button.setVisible(false);
					prepare_battle_button.setVisible(true);
				}
			} 
		}
	}
	private class PrepareBattleButton extends JButton{
		public PrepareBattleButton() {
			setName("prepare battle");
			this.setText("prepare battle");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new PrepareButtonMouseListener());
		}
		private class PrepareButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					//TODO open battle prepare here
					cw.setState(1);
					cw.showAccurateComponent();
					prepare_battle_button.setVisible(false);
					shop_button.setVisible(true);
				}
			} 
		}
	}
}
