package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileTable;
import HexTilePlayground.GUI.HexTileTableComponent;
import SpriteSheet.StaticImageLoader;
import gameLogic.BattleUnit;
import gameLogic.WarriorsReadyForBattleTable;
import gui.windows.BattlePrepareWindow;
import gui.windows.BattleWindow;
import gui.windows.CampaignWindow;


public class WarriorsReadyForBattleComponent extends JComponent{
	BattlePrepareWindow battle_prepare_window;
	JPanel battle_tables;
	JPanel behaviour_panel;
	
	public WarriorsReadyForBattleComponent(BattlePrepareWindow cw) {
		super();
		this.battle_prepare_window = cw;
		setBorder(new LineBorder(Color.YELLOW));
		setLayout(new BorderLayout());
		battle_tables = new JPanel();
		battle_tables.setLayout(new BorderLayout());
		battle_tables.add(new WarriorsReadyForBattleTableComponent(cw.gui_controller.getGame().getPlayer(), cw.gui_controller.getGame().getPrepareTable(), cw),BorderLayout.CENTER);
		battle_tables.add(new WarriorsReadyForBattleTableComponent(cw.gui_controller.getGame().getOpponent(), cw.gui_controller.getGame().getEnemyTable(), cw),BorderLayout.NORTH);
		//TODO add option to set behaviour of individual warriors
		//drop text box Borderlayout.Line_END
		add(battle_tables, BorderLayout.CENTER);	
		behaviour_panel = new JPanel();
		behaviour_panel.setLayout(new BorderLayout());
		behaviour_panel.add(new StartBattleButton(),BorderLayout.NORTH);
		add(behaviour_panel, BorderLayout.NORTH);
		
		add(new WarriorCampaignComponent(cw), BorderLayout.LINE_END);
		//add(new AvailableHeroList(), BorderLayout.LINE_END);
		setSize(new Dimension(800,430));
		addMouseListener(new MyMouseListener());
		setVisible(true);
	}
	private class StartBattleButton extends JButton{
		public StartBattleButton() {
			setName("start battle!");
			this.setText("start battle!");
			setPreferredSize(new Dimension(100, 40));
			addMouseListener(new StartBattleButtonMouseListener());
		}
		private class StartBattleButtonMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					//start battle
					battle_prepare_window.gui_controller.getGame().enter_battle(battle_prepare_window.gui_controller.getGame().getOpponent());
					battle_prepare_window.gui_controller.battle_window = new BattleWindow(battle_prepare_window.gui_controller);
					battle_prepare_window.gui_controller.update_view();

				}
			} 
		}
	}
	private class AvailableHeroList extends JComponent{
		private static final int ListItemLength = 120;
		private static final int ListItemHeight =40;
		private EntryBox entry_box;
		public AvailableHeroList() {
			setVisible(true);
			setPreferredSize(new Dimension(400,400));
			setLayout(new BorderLayout());
			entry_box= new EntryBox();  
			remakeListEntries();
			add(entry_box,BorderLayout.NORTH);			
		}
		public void remakeListEntries() {
			this.removeAll(); 
			for (int i = 0; i < battle_prepare_window.gui_controller.getGame().getPlayer().getHeroes().size(); i++) {
				entry_box.add(new HeroListItem(battle_prepare_window.gui_controller.getGame().getPlayer().getHeroes().get(i)));
			}
		}
		private class EntryBox extends JComponent{
			public EntryBox() {
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				setPreferredSize(new Dimension(100,100));
				setVisible(true);
			}
		}
	}
	private class HeroListItem extends JComponent{
		private BattleUnit warrior;
		public HeroListItem(BattleUnit warrior) {
			setPreferredSize(new Dimension(100,40));
			setVisible(true);
			addMouseListener(new HeroListItemMouseListener());
			this.warrior=warrior;
		}
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawString(warrior.getName(), 5, 15);
		}
		private class HeroListItemMouseListener extends MouseAdapter{
			public void mousePressed(MouseEvent e){	
				if(e.getButton()==1){
					battle_prepare_window.gui_controller.getGame().getPlayer().setSelectedHero(warrior);			
					battle_prepare_window.refresh();
				}
			} 
		}
	}
	private class MyMouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){	
			battle_prepare_window.refresh();
		} 
	}
	//////////////////////////////////////////////////////////////////////
	

	private class WarriorsReadyForBattleTableComponent extends HexTileTableComponent{

		public WarriorsReadyForBattleTableComponent(HexTilePlayer player, HexTileTable table, BattlePrepareWindow gf) {
			super(player, table, gf, Resources.IMAGE_PATH);
			// TODO Auto-generated constructor stub
			addMouseListener(new MyMouseListener());
			setPreferredSize(new Dimension(400,250));
		}

		@Override
		protected void paintComponent(Graphics g) {
				for(int i=0; i<table.getTiles().size();i++){
					HexTile f=table.getTiles().get(i);
					g.drawImage(StaticImageLoader.getScaledImage(sprite_path,f.getImageNumber(), table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);				
					g.drawPolygon(f.getPolygon());
					if(f.getUnit()!=null){
						g.drawImage(StaticImageLoader.getScaledImage(sprite_path,f.getUnit().getImageNumber(), table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);
						if(f.getUnit().getHealth()/f.getUnit().getMaxHealth()<0.9){
							if((float)f.getUnit().getHealth()/f.getUnit().getMaxHealth()<0.8){
								if((float)f.getUnit().getHealth()/f.getUnit().getMaxHealth()<0.7){
									if((float)f.getUnit().getHealth()/f.getUnit().getMaxHealth()<0.6){
										if((float)f.getUnit().getHealth()/f.getUnit().getMaxHealth()<0.5){
											if((float)f.getUnit().getHealth()/f.getUnit().getMaxHealth()<0.4){
												if((float)f.getUnit().getHealth()/f.getUnit().getMaxHealth()<0.3){
													if((float)f.getUnit().getHealth()/f.getUnit().getMaxHealth()<0.2){
														if((float)f.getUnit().getHealth()/f.getUnit().getMaxHealth()<0.1){
															g.drawImage(StaticImageLoader.getScaledImage(sprite_path,11,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);
														}else{g.drawImage(StaticImageLoader.getScaledImage(sprite_path,10,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);}
													}else{g.drawImage(StaticImageLoader.getScaledImage(sprite_path,9,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);}
												}else{g.drawImage(StaticImageLoader.getScaledImage(sprite_path,8,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);}
											}else{g.drawImage(StaticImageLoader.getScaledImage(sprite_path,7,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);}
										}else{g.drawImage(StaticImageLoader.getScaledImage(sprite_path,6,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);}
									}else{g.drawImage(StaticImageLoader.getScaledImage(sprite_path,5,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);}
								}else{g.drawImage(StaticImageLoader.getScaledImage(sprite_path,4,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);}
							}else{g.drawImage(StaticImageLoader.getScaledImage(sprite_path,3,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);}
						}else{g.drawImage(StaticImageLoader.getScaledImage(sprite_path,2,table.getHex_size()),f.getPolygon().xpoints[0]-(int)(0.5*hex_pixel_size),f.getPolygon().ypoints[0],null);}
					}
				}
		}
		
		
	}

}
