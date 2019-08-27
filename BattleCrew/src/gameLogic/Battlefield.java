package gameLogic;

import java.util.LinkedList;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTileTable;

public class Battlefield extends HexTileTable {
	private Game g;
	public Battlefield(int table_size_x, int table_size_y, double hex_size,Game game) {
		//size 30 is standard
		super(table_size_x, table_size_y, hex_size, game);
		// TODO Auto-generated constructor stub
		this.g=game;
	}

	@Override
	protected HexTile generateTile(int x, int y) {
		// TODO Auto-generated method stub
		return new Tile(this, x, y, hex_size, "meadow");
	}
	public Game getGame() {
		return g;
	}
}
