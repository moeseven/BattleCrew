package gameLogic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import HexTilePlayground.HexTile;

public class Battle {
	private LinkedList<Warrior> battleParticipants;
	private Battlefield battleField;
	private Player attacker,defender;
	private Player winner=null;
	private Game game;
	public Battle(Game game, Battlefield battlefield,Player attacker, Player defender) {
		this.game=game;
		this.battleField=battlefield;
		this.attacker=attacker;
		this.defender=defender;
		battleParticipants= new LinkedList<Warrior>();
		for (int i = 0; i < game.getPrepareTable().getTiles().size(); i++) {
			if (game.getPrepareTable().getTiles().get(i).getUnit()!=null) {
				if (game.getPrepareTable().getTiles().get(i).getUnit() instanceof Warrior) {
					Warrior warrior= (Warrior) game.getPrepareTable().getTiles().get(i).getUnit();
					warrior.setBattle_participant(true);
				}
				
			}
			
		}
		//place warriors and add them to battlefield
//		for (int i = 0; i < attacker.getHeroes().size(); i++) {
//			if (attacker.getHeroes().get(i).isBattle_participant()) {
//				battleParticipants.add(attacker.getHeroes().get(i));
//				attacker.getHeroes().get(i).setTile(battlefield.getTiles().get(i));
//				battlefield.getTiles().get(i).setUnit(attacker.getHeroes().get(i));
//				attacker.getHeroes().get(i).battleBegin();
//			}
//			
//		}
//		for (int i = 0; i < defender.getHeroes().size(); i++) {
//			if (defender.getHeroes().get(i).isBattle_participant()) {
//				battleParticipants.add(defender.getHeroes().get(i));
//				defender.getHeroes().get(i).setTile(battlefield.getTiles().get(battlefield.getTiles().size()-(1+i)));
//				battlefield.getTiles().get(battlefield.getTiles().size()-(1+i)).setUnit(defender.getHeroes().get(i));
//				defender.getHeroes().get(i).battleBegin();
//			}
//
//		}
		placeAttackersOrderly();
		placeDefendersOrderly();
		//sort 
		setHeroTrunOrder();		
	}
	public void start() {
		battleParticipants.addFirst(battleParticipants.removeLast());
		selectActiveWarriorForPlayer();
		endActiveWarriorTurn();
	}
	private void placeAttackersOrderly() {
		for (int i = 0; i < game.getPrepareTable().getTiles().size(); i++) {
			if (game.getPrepareTable().getTiles().get(i).getUnit()!=null) {
				if (game.getPrepareTable().getTiles().get(i).getUnit() instanceof Warrior) {
					Warrior warrior =(Warrior) game.getPrepareTable().getTiles().get(i).getUnit();
					battleParticipants.add(warrior);
					warrior.setTile(battleField.getTiles().get(game.getPrepareTable().translateTileIndex(battleField.getTable_size_x(), battleField.getTable_size_y(), i)));
					battleField.getTiles().get(game.getPrepareTable().translateTileIndex(battleField.getTable_size_x(), battleField.getTable_size_y(), i)).setUnit(warrior);
					warrior.battleBegin();
				}
				
			}
		}
	}
	private void placeDefendersOrderly() {
		for (int i = 0; i < defender.getHeroes().size(); i++) {
			if (defender.getHeroes().get(i).isBattle_participant()) {
				battleParticipants.add(defender.getHeroes().get(i));
				defender.getHeroes().get(i).setTile(battleField.getTiles().get(battleField.getTable_size_y()*i));
				battleField.getTiles().get(battleField.getTable_size_y()*i).setUnit(defender.getHeroes().get(i));
				defender.getHeroes().get(i).battleBegin();
			}

		}
	}
	private void selectActiveWarriorForPlayer() {
		attacker.setSelectedTile(getActiveWarrior().getHexTile());
		attacker.setSelectedHero(getActiveWarrior());
		defender.setSelectedTile(getActiveWarrior().getHexTile());
		defender.setSelectedHero(getActiveWarrior());
	}
	public void endActiveWarriorTurn() {
		if (getActiveWarrior().isDead()) {
			battleParticipants.removeFirst();
			if (tryEndBattle()) {
				
			}else {
				endActiveWarriorTurn();
			}
		}else {
			getActiveWarrior().roundBegin();		
			battleParticipants.add(battleParticipants.removeFirst());
			selectActiveWarriorForPlayer();
			
			runAI();
		}
		
	}
	private boolean tryEndBattle() {
		// TODO Auto-generated method stub
		int count_attacker=0;
		int count_defender=0;
		for (int i = 0; i < battleParticipants.size(); i++) {
			if (battleParticipants.get(i).getPlayer()!=attacker) {
				count_attacker++;
			}else {
				count_defender++;
			}
		}
		if (count_attacker==0) {
			winner=defender;
		}else {
			if (count_defender==0) {
				winner=attacker;
			}		
		}
		if (winner!=null) {
			attacker.removeDeadHeroesFromRoster();
			defender.removeDeadHeroesFromRoster();
			for (int i = 0; i < attacker.getHeroes().size(); i++) {
				attacker.getHeroes().get(i).setBattle_participant(false);
					
			}
			for (int i = 0; i < defender.getHeroes().size(); i++) {
				defender.getHeroes().get(i).setBattle_participant(false);
			}
			
			return true;
		}
		return false;
	}
	private void runAI() {
		//TODO
		for (int turns = 0; turns <3; turns++) {
			//get target for attack
			int smallest_distance=Integer.MAX_VALUE;
			Warrior target_warrior=null;
			for (int i = 0; i < battleParticipants.size(); i++) {
				if (battleParticipants.get(i).getPlayer()!=getActiveWarrior().getPlayer()) {
					if (getActiveWarrior().getHexTile().getDistance(battleParticipants.get(i).getHexTile())<smallest_distance) {
						smallest_distance=getActiveWarrior().getHexTile().getDistance(battleParticipants.get(i).getHexTile());
						target_warrior=battleParticipants.get(i);
					}
				}			
			}
			if (target_warrior!=null) {
				//move
				if (getActiveWarrior().getHexTile().getDistance(target_warrior.getHexTile())>getActiveWarrior().getWeaponAbility().getRange()) {
					//move towards target here
					int minimum_distance=Integer.MAX_VALUE;
					LinkedList<HexTile> tiles= getActiveWarrior().getHexTile().getAdjacentTiles();
					HexTile bestTile = tiles.getFirst();				
					for (int i = 0; i < tiles.size(); i++) {
						if (minimum_distance>tiles.get(i).getDistance(target_warrior.getHexTile())) {
							minimum_distance=tiles.get(i).getDistance(target_warrior.getHexTile());
							if (tiles.get(i).getUnit()==null) {
								bestTile=tiles.get(i);
							}
						}
					}
					if (bestTile instanceof Tile) {
						Tile move_tile= (Tile) bestTile;
						getActiveWarrior().moveOneTile(move_tile);
					}
					if (getActiveWarrior().getStamina()<0.8*getActiveWarrior().calcMaxStamina()) {
						turns=2;
					}
				}else {
					//hit
					getActiveWarrior().useMainHand(target_warrior);
					if (getActiveWarrior().getWeaponAbility().isAfter_roll_status()) {
						getActiveWarrior().getWeaponAbility().applyAbilityAfterRoll();
					}
					
				}				
				
			}
			
			
		}
		
		endActiveWarriorTurn();
	}
	public void setHeroTrunOrder() {
		Collections.shuffle(battleParticipants);
		Warrior fastest_warrior=battleParticipants.getFirst();
		int warrior_count=battleParticipants.size();
		LinkedList<Warrior> sortHelpList= new LinkedList<Warrior>();
		while(sortHelpList.size()<warrior_count) {
			fastest_warrior=battleParticipants.getFirst();
			for (int i = 0; i < battleParticipants.size(); i++) {
				if (battleParticipants.get(i).getSpeed()>=fastest_warrior.getSpeed()) {
					if (battleParticipants.get(i).getSpeed()>fastest_warrior.getSpeed()) {
						fastest_warrior=battleParticipants.get(i);
					}else {//equal
						if (fastest_warrior.getStamina()<battleParticipants.get(i).getEndurance()) {
							fastest_warrior=battleParticipants.get(i);
						}
					}
				}
			}
			battleParticipants.remove(fastest_warrior);
			sortHelpList.add(fastest_warrior);
		}
		battleParticipants=sortHelpList;		
	}
	//getters and setters
	public Warrior getActiveWarrior() {
		return battleParticipants.getFirst();
	}
	public Battlefield getBattleField() {
		return battleField;
	}
	public void setBattleField(Battlefield battleField) {
		this.battleField = battleField;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	

	public Player getWinner() {
		return winner;
	}
	public void setWinner(Player winner) {
		this.winner = winner;
	}

}
