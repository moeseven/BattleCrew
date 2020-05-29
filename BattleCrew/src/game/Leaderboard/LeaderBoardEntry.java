package game.Leaderboard;

import java.io.Serializable;
import java.util.LinkedList;

import gameLogic.Game;


public class LeaderBoardEntry implements Serializable{
private int turns;
private String playerName;
private int points;
public LeaderBoardEntry(int turns, String playerName, int points) {
	super();
	this.turns = turns;
	this.playerName = playerName;
	this.points = points;
}
public LeaderBoardEntry(Game game) {
	playerName = game.getPlayer().getCommander().getName();
	points = game.getPlayer().getGold()*game.getPlayer().getCommander().getLevel();
}
public int getTurns() {
	return turns;
}
public void setTurns(int turns) {
	this.turns = turns;
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
