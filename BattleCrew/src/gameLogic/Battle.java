package gameLogic;

public class Battle {
	private Warrior activeWarrior;
	private Battlefield battleField;
	private Game game;
	public Battle(Game game, Battlefield battlefield) {
		
	}
	

	//getters and setters
	public Warrior getActiveWarrior() {
		return activeWarrior;
	}
	public void setActiveWarrior(Warrior activeWarrior) {
		this.activeWarrior = activeWarrior;
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
}
