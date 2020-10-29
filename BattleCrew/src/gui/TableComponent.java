package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileTable;
import HexTilePlayground.GUI.HexTileTableComponent;
import SpriteSheet.StaticImageLoader;
import gameLogic.BattleUnit;
import gameLogic.Commander;
import gameLogic.Player;
import gameLogic.Tile;
import gui.windows.BattleWindow;

public class TableComponent extends HexTileTableComponent {
	@Override
	public void paint_inside_tile(Graphics g, HexTile f, int x_start, int y_start) {
		// TODO Auto-generated method stub
		super.paint_inside_tile(g, f, x_start, y_start);	
		// show coordinates
//		g.drawString(f.getx()+"/"+f.gety(), x_start+20, y_start+45);
//		g.drawString(f.getX()+"/"+f.getY()+"/"+f.getZ(), x_start+5, y_start+25);
		//
		
		if (f.getUnit() != null) {
			
			BattleUnit warrior = (BattleUnit) f.getUnit();
			if(warrior.getTiles_moved_this_round()>0){
				g.drawImage(StaticImageLoader.getScaledImage(sprite_path,0, table.getHex_size()),x_start, y_start,null);
			}
			if(f.getUnit().isFleeing()){
				g.drawImage(StaticImageLoader.getScaledImage(sprite_path,14, table.getHex_size()),x_start, y_start,null);
			}
			
			show_proffession(table,f,g,x_start,y_start,sprite_path);
			// attack direction image numbers:
			/*
			 * north: 395
			 * south: 394
			 * north/east: 396
			 * north/west: 399
			 * south/east: 397
			 * south/west: 398
			 * east: 393
			 * west: 392
			 */
			
			if (warrior.isAttacked_this_round() && !warrior.getTarget().is_unable_to_fight()) {
				HexTile origin = warrior.getTile();
				HexTile target = warrior.getTarget().getTile();
				if (target.getY() >= origin.getY()) {
					if (target.getY() == origin.getY()) {
						//north or south
						if (target.getX() > origin.getX()) {
							//north
							g.drawImage(StaticImageLoader.getScaledImage(sprite_path,395, table.getHex_size()),x_start, y_start,null);
						}else {
							//south
							g.drawImage(StaticImageLoader.getScaledImage(sprite_path,394, table.getHex_size()),x_start, y_start,null);
						}
					}else {
						//not west
						if (target.getX() >= origin.getX()) {
							if (target.getX() == origin.getX()) {
								//south/east
								g.drawImage(StaticImageLoader.getScaledImage(sprite_path,397, table.getHex_size()),x_start, y_start,null);
							}else {
								//not west, not south
								if (target.getZ() >= origin.getZ()) {
									if (target.getZ() == origin.getZ()) {
										//north/east
										g.drawImage(StaticImageLoader.getScaledImage(sprite_path,396, table.getHex_size()),x_start, y_start,null);
									}else {
										//east
										g.drawImage(StaticImageLoader.getScaledImage(sprite_path,393, table.getHex_size()),x_start, y_start,null);
									}
								}
							}
							
						}else {
							//south
							g.drawImage(StaticImageLoader.getScaledImage(sprite_path,394, table.getHex_size()),x_start, y_start,null);
						}
					}
					
				}else {
					//not east
					if (target.getX() <= origin.getX()) {
						if (target.getX() == origin.getX()) {
							//north/west
							g.drawImage(StaticImageLoader.getScaledImage(sprite_path,399, table.getHex_size()),x_start, y_start,null);
						}else {
							//not east, not north
							if (target.getZ() <= origin.getZ()) {
								if (target.getZ() == origin.getZ()) {
									//south/west
									g.drawImage(StaticImageLoader.getScaledImage(sprite_path,398, table.getHex_size()),x_start, y_start,null);
								}else {
									//west
									g.drawImage(StaticImageLoader.getScaledImage(sprite_path,392, table.getHex_size()),x_start, y_start,null);
								}
							}
						}
						
					}else {
						//north
						g.drawImage(StaticImageLoader.getScaledImage(sprite_path,395, table.getHex_size()),x_start, y_start,null);
					}
				}
			}
		}
	}
	public static void show_proffession(HexTileTable table, HexTile f,Graphics g, int x_start, int y_start,String sprite_path) {
		//show different jobs
		if (f.getUnit() != null) {
			if (f.getUnit() instanceof Commander) {
				g.drawImage(StaticImageLoader.getScaledImage(sprite_path,391, table.getHex_size()),x_start, y_start,null);
			}
			Player player = (Player) f.getUnit().getPlayer();
			if (player.getHealer() == f.getUnit()) {
				g.drawImage(StaticImageLoader.getScaledImage(sprite_path,390, table.getHex_size()),x_start, y_start,null);
			}
			if (player.getSmith() == f.getUnit()) {
				g.drawImage(StaticImageLoader.getScaledImage(sprite_path,389, table.getHex_size()),x_start, y_start,null);
			}
			if (player.getTreasurer() == f.getUnit()) {
				g.drawImage(StaticImageLoader.getScaledImage(sprite_path,388, table.getHex_size()),x_start, y_start,null);
			}
			if (player.getLeader() == f.getUnit()) {
				g.drawImage(StaticImageLoader.getScaledImage(sprite_path,387, table.getHex_size()),x_start, y_start,null);
			}
			if (player.getChampion() == f.getUnit()) {
				g.drawImage(StaticImageLoader.getScaledImage(sprite_path,386, table.getHex_size()),x_start, y_start,null);
			}
			if (player.getRecruiter() == f.getUnit()) {
				g.drawImage(StaticImageLoader.getScaledImage(sprite_path,385, table.getHex_size()),x_start, y_start,null);
			}
			if (f.getUnit() == player.getGame().getPlayer().getSelectedUnit()) {
				g.setColor(Color.YELLOW);
				g.drawPolygon(f.getPolygon());
				g.setColor(Color.BLACK);
			}
		}		
	}
	@Override
	public void on_click(MouseEvent e) {
		// TODO Auto-generated method stub
		super.on_click(e);
		bw.refresh();
	}
	BattleWindow bw;
	public TableComponent(HexTilePlayer player, HexTileTable table, BattleWindow gf) {
		super(player, table, gf,Resources.IMAGE_PATH);
		this.bw=gf;
	}
	
	
}
