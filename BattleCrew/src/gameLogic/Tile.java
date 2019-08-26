package gameLogic;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileTable;

public class Tile extends HexTile {
	public Tile(Battlefield table, int x, int y, double hex_size, String ground) {
		super(table, x, y, hex_size, ground);
		if (ground.equals("meadow")) {
			this.setImageNumber(60);
		}
	}

	@Override
	public void triggerLeftClick(HexTilePlayer player) {
		// TODO Auto-generated method stub
		player.setSelectedTile(this);
		if (this.getUnit()!=null) {
			player.setSelectedUnit(this.getUnit());
		}
	}

	@Override
	public void triggerRightClick(HexTilePlayer arg0) {
		// TODO Auto-generated method stub
		
	}


}
