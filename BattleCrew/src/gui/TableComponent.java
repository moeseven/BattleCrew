package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileTable;
import HexTilePlayground.GUI.HexTileTableComponent;

public class TableComponent extends HexTileTableComponent {
	@Override
	public void on_click(MouseEvent e) {
		// TODO Auto-generated method stub
		super.on_click(e);
		bw.refresh();
	}
	BattleWindow bw;
	public TableComponent(HexTilePlayer player, HexTileTable table, BattleWindow gf) {
		super(player, table, gf, gf.get_sprite_path());
		this.bw=gf;
		this.setBackground(Color.green);
	}
	
}
