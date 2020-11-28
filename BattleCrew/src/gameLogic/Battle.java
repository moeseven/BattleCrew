package gameLogic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import HexTilePlayground.HexTile;
import pathfinding.Pathfinder;

public class Battle implements Serializable{
	public LinkedList<BattleUnit> getBattleParticipants() {
		return battleParticipants;
	}
	public Player getAttacker() {
		return attacker;
	}
	public Player getDefender() {
		return defender;
	}
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
		//save attacker army to file
		if (defender.king_of_the_hill_player) {
			game.getCampaign().getBattle_templates().removeLast();
			Player challenger = new Player(game, true);
			for (int i = 0; i < attacker.getHeroes().size(); i++) {
				if (attacker.getHeroes().get(i).isBattle_participant()) {
					challenger.getHeroes().addFirst(attacker.getHeroes().get(i));
				}
			}			
			int gold = 0;
			for (int i = 0; i < challenger.getHeroes().size(); i++) {
				for(int j = 0; j<challenger.getHeroes().get(i).getEquipment().getAllEquippedItems().size();j++) {
					gold += challenger.getHeroes().get(i).getEquipment().getAllEquippedItems().get(j).getGold_value();
				}
			}
			challenger.king_of_the_hill_player = true;
			challenger.setGold_reward(gold);
			challenger.setScore(attacker.getScore());
			challenger.setExperience_reward(attacker.getCommander().getExperience());
			ObjectOutputStream oos=null;
			try {
				oos = new ObjectOutputStream(new FileOutputStream("./saves/challenger.dat"));
				oos.writeObject(challenger);
			} catch (FileNotFoundException e1) {			
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			finally{
				try {
					oos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
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
//		for (int i = 0; i < defender.getHeroes().size(); i++) {
//			if (defender.getHeroes().get(i).isBattle_participant()) {
//				battleParticipants.add(defender.getHeroes().get(i));
//				defender.getHeroes().get(i).setTile(battleField.getTiles().get(battleField.getTable_size_y()*i));
//				battleField.getTiles().get(battleField.getTable_size_y()*i).setUnit(defender.getHeroes().get(i));
//				defender.getHeroes().get(i).battle_begin();
//			}
//
//		}
		for (int i = 0; i < game.getEnemyTable().getTiles().size(); i++) {
			if (game.getEnemyTable().getTiles().get(i).getUnit()!=null) {
				if (game.getEnemyTable().getTiles().get(i).getUnit() instanceof BattleUnit) {
					BattleUnit warrior =(BattleUnit) game.getEnemyTable().getTiles().get(i).getUnit();
					battleParticipants.add(warrior);
					warrior.setTile(battleField.getTiles().get(game.getEnemyTable().translateTileIndex(battleField.getTable_size_x(), battleField.getTable_size_y(), i)));
					battleField.getTiles().get(game.getEnemyTable().translateTileIndex(battleField.getTable_size_x(), battleField.getTable_size_y(), i)).setUnit(warrior);
					warrior.battle_begin();
				}
				
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
				//become king of the hill if this is a king of the hill battle
				if (defender.king_of_the_hill_player) {
					//load from file;
					ObjectInputStream ois=null;ObjectOutputStream oos=null;
					try {
						ois=new ObjectInputStream(new FileInputStream("./saves/challenger.dat"));
						Player p = (Player) ois.readObject();
						oos = new ObjectOutputStream(new FileOutputStream("./saves/king_of_the_hill.dat"));
						oos.writeObject(p);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					finally{
						try {
							ois.close();oos.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
					}
				}
			}		
		}
		if (winner!=null) {
			end_battle();
			return true;
		}
		return false;
	}
	public void end_battle() {
		for (int i = 0; i < battleParticipants.size(); i++) {
			battleParticipants.get(i).debuff();
		}
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
