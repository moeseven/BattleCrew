package gameLogic;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileTable;
import pathfinding.PathfinderField;

public class Tile extends HexTile implements PathfinderField {
	private Battlefield battleField;
	public Tile(Battlefield table, int x, int y, double hex_size, String ground) {
		super(table, x, y, hex_size, ground);
		if (ground.equals("meadow")) {
			this.setImageNumber(60);
		}
		battleField=table;
	}

	@Override
	public void triggerLeftClick(HexTilePlayer player) {
		// TODO Auto-generated method stub
		if (this.getUnit()!=null) {
			player.setSelectedUnit(this.getUnit());
		}
	}

	@Override
	public void triggerRightClick(HexTilePlayer player) {
		// TODO Auto-generated method stub
		if (player instanceof Player) {
			Player p= (Player) player;
			p.getSelectedUnit().useAbility(battleField.getGame().getBattle(), this);
		}
		
		//TODO make hex tile classes more basic 
	}

	public Warrior getWarrior() {
		// TODO Auto-generated method stub
		return (Warrior) getUnit();
	}

	@Override
	public int get_path_cost() {
		// TODO Auto-generated method stub
		if (getWarrior() == null) {
			return 1;
		}else {
			return 10000;
		}
	}

	@Override
	public boolean is_pathable() {
		return true;
		
	}
	


}
