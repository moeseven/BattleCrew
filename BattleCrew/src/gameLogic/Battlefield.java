package gameLogic;

import java.util.ArrayList;
import java.util.LinkedList;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTileTable;
import pathfinding.*;

public class Battlefield extends HexTileTable implements PathfinderWorld{
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

	@Override
	public ArrayList<PathfinderField> get_immediatly_accessable_fields(PathfinderField arg0) {
		HexTile tile = (HexTile) arg0;
		LinkedList<HexTile> list = tile.getAdjacentTiles();
		ArrayList<PathfinderField> retVal = new ArrayList<PathfinderField>();
		for (int i = 0; i < list.size(); i++) {
			retVal.add((PathfinderField) list.get(i));
		}
		return retVal;
	}
}
