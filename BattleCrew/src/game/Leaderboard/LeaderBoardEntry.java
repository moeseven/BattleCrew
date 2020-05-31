package game.Leaderboard;

import java.io.Serializable;
import java.util.LinkedList;

import gameLogic.Game;


public class LeaderBoardEntry implements Serializable{
public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
private int level;
private String playerName;
private int points;
public LeaderBoardEntry(String playerName, int points) {
	super();
	this.playerName = playerName;
	this.points = points;
}
public LeaderBoardEntry(Game game) {
	playerName = game.getPlayer().getCommander().getName();
	points = game.getPlayer().getScore();
	level = game.getPlayer().getCommander().getLevel();
}

public String getPlayerName() {
	return playerName;
}
public void setPlayerName(String playerName) {
	this.playerName = playerName;
}
public int getPoints() {
	return points;
}
public void setPoints(int points) {
	this.points = points;
}

}
