package gameLogic;

import java.io.Serializable;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileTable;

public class WarriorsReadyForBattleTable extends HexTileTable implements Serializable{

	public WarriorsReadyForBattleTable(int table_size_x, int table_size_y, double hex_size, Object game) {
		super(table_size_x, table_size_y, hex_size, game);
		// TODO Auto-generated constructor stub
		
	}
	public Integer translateTileIndex(int target_size_x, int target_size_y,int origin_index) {
		//deployment at the middle south part of the map
		int x_difference=target_size_x-table_size_x;
		int y_difference=target_size_y-table_size_y;
		int x_shift=x_difference/2;
		int index=x_shift*target_size_y+(origin_index/table_size_y+1)*y_difference+origin_index;
		return index;
	}
	@Override
	protected HexTile generateTile(int x, int y) {
		// TODO Auto-generated method stub
		return new WarriorsReadyForBattleTile(this, x, y, hex_size, "meadow");
	}
	private class WarriorsReadyForBattleTile extends HexTile{		
		public WarriorsReadyForBattleTile(HexTileTable table, int x, int y, double hex_size, String ground) {
			super(table, x, y, hex_size, ground);
			// TODO Auto-generated constructor stub
			if (ground.equals("meadow")) {
				this.setImageNumber(60);
			}
		}

		public BattleUnit getWarrior() {
			// TODO Auto-generated method stub
			return (BattleUnit) getUnit();
		}

		@Override
		public void triggerLeftClick(HexTilePlayer player) {
			Player p = (Player) player;
			// TODO Auto-generated method stub
			if (getWarrior()==null) {
				int units_in_preparation = 0;				
				for (int i = 0; i < table.getTiles().size(); i++) {
					if (table.getTiles().get(i).getUnit()!=null) {
						units_in_preparation++;
						if (table.getTiles().get(i).getUnit()==player.getSelectedUnit()||table.getTiles().get(i).getUnit().getHealth()<=0) {
							table.getTiles().get(i).setUnit(null);
						}
					}
					
				}
				
				if (p.getCommander().getCommand_points()>units_in_preparation) {
					setUnit(player.getSelectedUnit());
					p.getHeroes().add(p.getHeroes().removeFirst());
					p.setSelectedHero(p.getHeroes().getFirst());
				}else {
					p.getGame().log.addLine("your commander can't field more units!");
				}
			}else {
				player.setSelectedUnit(getUnit());
			}
		}

		@Override
		public void triggerRightClick(HexTilePlayer player) {
			// TODO Auto-generated method stub
			if (getUnit()!=null) {
				setUnit(null);
			}
			
		}
	}
}
