package gameLogic;

import java.util.Collections;
import java.util.LinkedList;

public class Battle {
	private LinkedList<Warrior> battleParticipants;
	private Battlefield battleField;
	private Player attacker,defender;
	private Player winner=null;
	private Game game;
	public Battle(Game game, Battlefield battlefield,Player attacker, Player defender) {
		this.battleField=battlefield;
		this.attacker=attacker;
		this.defender=defender;
		battleParticipants= new LinkedList<Warrior>();
		//place warriors and add them to battlefield
		for (int i = 0; i < attacker.getHeroes().size(); i++) {
			battleParticipants.add(attacker.getHeroes().get(i));
			attacker.getHeroes().get(i).setTile(battlefield.getTiles().get(i));
			battlefield.getTiles().get(i).setUnit(attacker.getHeroes().get(i));
			attacker.getHeroes().get(i).battleBegin();
		}
		for (int i = 0; i < defender.getHeroes().size(); i++) {
			battleParticipants.add(defender.getHeroes().get(i));
			defender.getHeroes().get(i).setTile(battlefield.getTiles().get(battlefield.getTiles().size()-(1+i)));
			battlefield.getTiles().get(battlefield.getTiles().size()-(1+i)).setUnit(defender.getHeroes().get(i));
			defender.getHeroes().get(i).battleBegin();
		}
		
		//sort 
		setHeroTrunOrder();
		selectActiveWarriorForPlayer();
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
			if (defender.getHeroes().size()==0||attacker.getHeroes().size()==0) {
				endBattle();
			}else {
				endActiveWarriorTurn();
			}
		}else {
			getActiveWarrior().roundBegin();		
			battleParticipants.add(battleParticipants.removeFirst());
			selectActiveWarriorForPlayer();
			if (getActiveWarrior().getPlayer().isAI()) {
				runAI();
			}
		}
		
	}
	private void endBattle() {
		// TODO Auto-generated method stub
		if (attacker.getHeroes().size()==0) {
			winner=defender;
		}else {
			winner=attacker;
		}
	}
	private void runAI() {
		//TODO
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
