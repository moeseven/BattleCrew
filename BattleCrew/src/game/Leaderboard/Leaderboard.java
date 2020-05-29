package game.Leaderboard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;


public class Leaderboard implements Serializable{
private LinkedList<LeaderBoardEntry> leaderboardEntries;
private int maxEntries=20;
public Leaderboard() {
	super();
	leaderboardEntries=new LinkedList<LeaderBoardEntry>();
	addLeaderboardEntryInRightOrder(new LeaderBoardEntry(60, "Gollum", 1000));
	addLeaderboardEntryInRightOrder(new LeaderBoardEntry(200, "Klöftile", 20));
	addLeaderboardEntryInRightOrder(new LeaderBoardEntry(59, "Argeil", 1010));
}
	public void addLeaderboardEntryInRightOrder(LeaderBoardEntry newEntry) {
		LinkedList<LeaderBoardEntry> intermediateList=new LinkedList<LeaderBoardEntry>();
		int rightPosition=0;
		for (int i = 0; i < leaderboardEntries.size(); i++) {
			if (leaderboardEntries.get(i).getPoints()>=newEntry.getPoints()) {
				rightPosition=i+1;
			}
		}
		leaderboardEntries.add(rightPosition, newEntry);
		if (leaderboardEntries.size()>maxEntries) {
			leaderboardEntries.removeLast();
		}
	}
	public static Leaderboard loadLeaderboard() {
		Leaderboard returnValue= new Leaderboard();
		//load from file;
		ObjectInputStream ois=null;
		try {
			ois=new ObjectInputStream(new FileInputStream("./saves/leaderboard.dat"));
			returnValue=(Leaderboard)ois.readObject();
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
				ois.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		return returnValue;
	}
	public void writeToFile() {
		//save leaderboard
		ObjectOutputStream oos=null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("./saves/leaderboard.dat"));
			oos.writeObject(this);
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
	public LinkedList<LeaderBoardEntry> getLeaderboardEntries() {
		return leaderboardEntries;
	}
	public void setLeaderboardEntries(LinkedList<LeaderBoardEntry> leaderboardEntries) {
		this.leaderboardEntries = leaderboardEntries;
	}
	
}