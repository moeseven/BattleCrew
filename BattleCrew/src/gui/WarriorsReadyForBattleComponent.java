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
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileTable;
import HexTilePlayground.GUI.HexTileTableComponent;
import SpriteSheet.StaticImageLoader;
import gameLogic.Warrior;
import gameLogic.WarriorsReadyForBattleTable;


public class WarriorsReadyForBattleComponent extends JComponent{
	CampaignWindow cw;
	

	public WarriorsReadyForBattleComponent(CampaignWindow cw) {
		super();
		this.cw = cw;
		setBorder(new LineBorder(Color.YELLOW));
		setLayout(new BorderLayout());
		add(new WarriorsReadyForBattleTableComponent(cw.getGame().getPlayer(), cw.getGame().getPrepareTable(), cw),BorderLayout.CENTER);
		add(new StartBattleButton(),BorderLayout.NORTH);
		add(new AvailableHeroList(), BorderLayout.LINE_END);
		setPreferredSize(new Dimension(800,400));
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
					cw.getGame().startExampleBattle();
					new BattleWindow(cw.getGame(), cw);
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
			add(entry_box,BorderLayout.CENTER);			
		}
		public void remakeListEntries() {
			this.removeAll(); 
			for (int i = 0; i < cw.getGame().getPlayer().getHeroes().size(); i++) {
				entry_box.add(new HeroListItem(cw.getGame().getPlayer().getHeroes().get(i)));
			}
		}
		private class EntryBox extends JComponent{
			public EntryBox() {
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				setPreferredSize(new Dimension(100,40));
				setVisible(true);
			}
		}
	}
	private class HeroListItem extends JComponent{
		private Warrior warrior;
		public HeroListItem(Warrior warrior) {
			setPreferredSize(new Dimension(100,30));
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
					cw.getGame().getPlayer().setSelectedHero(warrior);			
					cw.refresh();
				}
			} 
		}
	}
	private class MyMouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){	
			cw.refresh();
		} 
	}
	//////////////////////////////////////////////////////////////////////
	

	private class WarriorsReadyForBattleTableComponent extends HexTileTableComponent{

		public WarriorsReadyForBattleTableComponent(HexTilePlayer player, HexTileTable table, CampaignWindow gf) {
			super(player, table, gf, gf.getSprite_path());
			// TODO Auto-generated constructor stub
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
