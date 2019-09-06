package gameLogic;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileTable;

public class WarriorsReadyForBattleTable extends HexTileTable{

	public WarriorsReadyForBattleTable(int table_size_x, int table_size_y, double hex_size, Object game) {
		super(table_size_x, table_size_y, hex_size, game);
		// TODO Auto-generated constructor stub
		
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

		public Warrior getWarrior() {
			// TODO Auto-generated method stub
			return (Warrior) getUnit();
		}

		@Override
		public void triggerLeftClick(HexTilePlayer player) {
			// TODO Auto-generated method stub
			if (getWarrior()==null) {				
				for (int i = 0; i < table.getTiles().size(); i++) {
					if (table.getTiles().get(i).getUnit()!=null) {
						if (table.getTiles().get(i).getUnit()==player.getSelectedUnit()||table.getTiles().get(i).getUnit().getHealth()<=0) {
							table.getTiles().get(i).setUnit(null);
						}
					}
					
				}
				setUnit(player.getSelectedUnit());
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
