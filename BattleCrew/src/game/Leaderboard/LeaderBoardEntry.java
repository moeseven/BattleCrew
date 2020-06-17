package game.Leaderboard;

import java.io.Serializable;
import java.util.LinkedList;

import gameLogic.Game;


public class LeaderBoardEntry implements Serializable{
public String getCommander_class() {
		return commander_class;
	}
	public String getCommander_race() {
		return commander_race;
	}
public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
private int level;
private String playerName;
private int points;
private String commander_class = "filibuster", commander_race = "orc";
public LeaderBoardEntry(String playerName, int points) {
	super();
	this.playerName = playerName;
	this.points = points;
}
public LeaderBoardEntry(Game game) {
	playerName = game.getPlayer().getCommander().getName();
	points = game.getPlayer().getScore();
	level = game.getPlayer().getCommander().getLevel();
	commander_race = game.getPlayer().getCommander().getType();
	commander_class = game.getPlayer().getCommander().getCommander_class().toString();
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
