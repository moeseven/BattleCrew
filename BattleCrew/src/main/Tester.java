package main;

import gameLogic.BattleUnit;
import gui.windows.GameOverWindow;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BattleUnit test = new BattleUnit();
		//new GameOverWindow(null);
		System.out.println(""+BattleUnit.experience_threshold_for_next_level(2));
		System.out.println(""+BattleUnit.experience_threshold_for_next_level(3));
		System.out.println(""+BattleUnit.experience_threshold_for_next_level(4));
		System.out.println(""+BattleUnit.experience_threshold_for_next_level(5));
		System.out.println(""+BattleUnit.experience_threshold_for_next_level(6));
		System.out.println(""+BattleUnit.experience_threshold_for_next_level(7));
	}

}
