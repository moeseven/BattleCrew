package gameLogic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import HexTilePlayground.HexTile;
import pathfinding.Pathfinder;

public class Battle {
	protected LinkedList<BattleUnit> battleParticipants;
	protected Battlefield battleField;
	protected Player attacker,defender;
	protected Player winner=null;
	protected Game game;
	public boolean started = false;
	public Pathfinder pathfinder;

	public Battle(Game game, Battlefield battlefield,Player attacker, Player defender) {
		this.game=game;
		this.battleField=battlefield;
		this.attacker=attacker;
		this.defender=defender;
		pathfinder = new Pathfinder(battlefield);
		battleParticipants= new LinkedList<BattleUnit>();
		for (int i = 0; i < game.getPrepareTable().getTiles().size(); i++) {
			if (game.getPrepareTable().getTiles().get(i).getUnit()!=null) {
				if (game.getPrepareTable().getTiles().get(i).getUnit() instanceof BattleUnit) {
					BattleUnit warrior= (BattleUnit) game.getPrepareTable().getTiles().get(i).getUnit();
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
		attacker.setSelectedTile(battleParticipants.getFirst().getHexTile());
		defender.setSelectedTile(battleParticipants.getFirst().getHexTile());
	}
	public void start() {
		started = true;
	}
	protected void placeAttackersOrderly() {
		for (int i = 0; i < game.getPrepareTable().getTiles().size(); i++) {
			if (game.getPrepareTable().getTiles().get(i).getUnit()!=null) {
				if (game.getPrepareTable().getTiles().get(i).getUnit() instanceof BattleUnit) {
					BattleUnit warrior =(BattleUnit) game.getPrepareTable().getTiles().get(i).getUnit();
					battleParticipants.add(warrior);
					warrior.setTile(battleField.getTiles().get(game.getPrepareTable().translateTileIndex(battleField.getTable_size_x(), battleField.getTable_size_y(), i)));
					battleField.getTiles().get(game.getPrepareTable().translateTileIndex(battleField.getTable_size_x(), battleField.getTable_size_y(), i)).setUnit(warrior);
					warrior.battle_begin();
				}
				
			}
		}
	}
	protected void placeDefendersOrderly() {
		for (int i = 0; i < defender.getHeroes().size(); i++) {
			if (defender.getHeroes().get(i).isBattle_participant()) {
				battleParticipants.add(defender.getHeroes().get(i));
				defender.getHeroes().get(i).setTile(battleField.getTiles().get(battleField.getTable_size_y()*i));
				battleField.getTiles().get(battleField.getTable_size_y()*i).setUnit(defender.getHeroes().get(i));
				defender.getHeroes().get(i).battle_begin();
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
		if (getActiveWarrior().is_unable_to_fight()) {
			battleParticipants.removeFirst();
			if (tryEndBattle()) {
				
			}else {
				endActiveWarriorTurn();
			}
		}else {
			getActiveWarrior().round_begin();		
			battleParticipants.add(battleParticipants.removeFirst());
			selectActiveWarriorForPlayer();
		}
		
	}
	public boolean tryEndBattle() {
		// TODO Auto-generated method stub
		int count_attacker=0;
		int count_defender=0;
		for (int i = 0; i < battleParticipants.size(); i++) {
			if (battleParticipants.get(i).getPlayer()==attacker) {
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
			end_battle();
			return true;
		}
		return false;
	}
	public void end_battle() {
//		for (int i = 0; i < attacker.getHeroes().size(); i++) {
//			attacker.getHeroes().get(i).setBattle_participant(false);
//				
//		}
//		for (int i = 0; i < defender.getHeroes().size(); i++) {
//			defender.getHeroes().get(i).setBattle_participant(false);
//		}
		for (int i = 0; i < game.getPrepareTable().getTiles().size(); i++) {
			if (game.getPrepareTable().getTiles().get(i).getUnit()!=null) {
				if (game.getPrepareTable().getTiles().get(i).getUnit() instanceof BattleUnit) {
					game.getPrepareTable().getTiles().get(i).setUnit(null);					
				}
				
			}
		}
	}

	public void setHeroTrunOrder() {
		Collections.shuffle(battleParticipants);
		BattleUnit fastest_warrior=battleParticipants.getFirst();
		int warrior_count=battleParticipants.size();
		LinkedList<BattleUnit> sortHelpList= new LinkedList<BattleUnit>();
		while(sortHelpList.size()<warrior_count) {
			fastest_warrior=battleParticipants.getFirst();
			for (int i = 0; i < battleParticipants.size(); i++) {
				if (battleParticipants.get(i).getMove_speed()>=fastest_warrior.getMove_speed()) {
					if (battleParticipants.get(i).getMove_speed()>fastest_warrior.getMove_speed()) {
						fastest_warrior=battleParticipants.get(i);
					}else {//equal
						if (fastest_warrior.getFatigue()>battleParticipants.get(i).getFatigue()) {
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
	public BattleUnit getActiveWarrior() {
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
