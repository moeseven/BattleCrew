package gui;

import java.awt.Component;

import javax.swing.JFrame;

import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileTable;
import HexTilePlayground.GUI.HexTileTableComponent;

public class TableComponent extends HexTileTableComponent {
	BattleWindow bw;
	public TableComponent(HexTilePlayer player, HexTileTable table, BattleWindow gf) {
		super(player, table, gf, gf.get_sprite_path());
		this.bw=gf;
	}

}
