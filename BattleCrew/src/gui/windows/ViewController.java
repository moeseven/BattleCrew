package gui.windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gameLogic.CommanderChooser;
import gameLogic.Game;
import gameLogic.Game.GameState;
import gui.Refreshable_gui;

/**
 * This should track all windows
 * and only have the active window visible
 * if the game is over show leaderboard
 * @author Moritz
 *
 */
public class ViewController {
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	public double image_scale = 1;
	public BattleWindow battle_window;
	public BattlePrepareWindow battle_prepare_window;
	public BattleSummaryWindow battle_summary_window;
	public CampaignWindow campaign_window;
	public MainMenu main_menu;
	public LeaderboardWindow leader_board;
	public CharacterBuilderWindow character_builder;
	public GameOverWindow game_over;
	private Game game;
	Set<Refreshable_gui> refreshables;
	public ViewController() throws Exception {
		this.game= new Game();
		main_menu = new MainMenu(this);
		refreshables = new HashSet<Refreshable_gui>();
	}
	
	public void erase_windows() {
		battle_window = null;
		battle_prepare_window = null;
		battle_summary_window = null;
		campaign_window = null;
		leader_board = null;
		character_builder = null;
		game_over = null;
	}
	
	public void register_refreshable(Refreshable_gui refreshable) {
		refreshables.add(refreshable);
	}
	public void update_view() throws Exception {
		if (game.get_state() == GameState.Menu) {
			show_menu();
		}else {
			if (game.get_state() == GameState.GameOver) {
				show_game_over();			
		}else {
			switch (game.get_state()) {
			case Leaderboard:
				show_leaderboard();
				break;
			case CharacterCreation:
				show_character_creation();
				break;
			case BattlePrepare:
				show_prepare_battle();
				break;
			case Battle:
				show_battle();
				break;
			case BattleSummary:
				show_battle_summary();
				break;
			case City:
				show_campaign();
				break;
			default:
				break;
			}
		}
		}		
		
	}


	private void hide_all_windows() {
		if (character_builder != null) {
			character_builder.setVisible(false);
		}
		if (campaign_window != null) {
			campaign_window.setVisible(false);
		}
		if (leader_board != null) {
			leader_board.setVisible(false);
		}
		if (main_menu != null) {
			main_menu.setVisible(false);
		}
		if (battle_prepare_window != null) {
			battle_prepare_window.setVisible(false);
		}
		if (battle_summary_window != null) {
			battle_summary_window.setVisible(false);
		}
		if (battle_window != null) {
			battle_window.setVisible(false);
		}
		if (game_over != null) {
			game_over.setVisible(false);
		}
	}
	
	/**
	 * call this after a click action
	 * in order to refresh all refreshable views
	 */
	public void refresh_gui() {
		for (Refreshable_gui r : refreshables) {
		    r.refresh();
		}
		//TODO add all here
		if (battle_summary_window != null) {
			if (battle_summary_window.isVisible()) {
				battle_summary_window.refresh();
			}
		}
		if (battle_prepare_window != null) {
			if (battle_prepare_window.isVisible()) {
				battle_prepare_window.refresh();
			}
		}
		if (battle_window != null) {
			if (battle_window.isVisible()) {
				battle_window.refresh();
			}
		}
		if (campaign_window != null) {
			if (campaign_window.isVisible()) {
				campaign_window.refresh();
			}
		}
		
	}
	
	public void load_game() {
		erase_windows();
		//load from file;
		ObjectInputStream ois=null;
		try {
			ois=new ObjectInputStream(new FileInputStream("./saves/game.dat"));
			game=(Game)ois.readObject();
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
	}
	
	public void save_game() {
		//save game;
		ObjectOutputStream oos=null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("./saves/game.dat"));
			oos.writeObject(game);
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
	
	public void new_game() throws Exception {
		erase_windows();
		game = new Game();
		character_builder = new CharacterBuilderWindow(new CommanderChooser(game), this);
		game.set_state(GameState.CharacterCreation);
		update_view();
	}
	
    public void start_game() throws Exception {
    	if(game.getPlayer().getHeroes().size()>0 && game.get_state() != GameState.GameOver) {
			game.getPlayer().setSelectedHero(game.getPlayer().getHeroes().getFirst());	
			game.set_state(game.previous_game_state());
		}else {
			game = new Game();
			game.set_state(GameState.CharacterCreation);
		}
		update_view();
    }
    

	private void show_game_over() {
		hide_all_windows();
		if (game_over == null) {
			game_over = new GameOverWindow(this);
		}else {
			game_over.repaint();
		}		
		game_over.setVisible(true);
	}

	private void show_menu() {
		hide_all_windows();
		main_menu.setVisible(true);	
	}
	
	private void show_prepare_battle() {
		battle_prepare_window = null; // it did not update
		if (battle_prepare_window == null) {
			battle_prepare_window = new BattlePrepareWindow(this);
		}
		hide_all_windows();
		battle_prepare_window.setVisible(true);
		
	}

	private void show_battle() {
		if (battle_window == null) {
			battle_window = new BattleWindow(this);
		}
		hide_all_windows();
		battle_window.setVisible(true);
		
	}
	
	private void show_battle_summary() {
		if (battle_summary_window == null) {
			battle_summary_window = new BattleSummaryWindow(this);
		}
		hide_all_windows();
		battle_summary_window.setVisible(true);
		
	}

	private void show_campaign() {
		if (campaign_window == null) {
			campaign_window = new CampaignWindow(this);
		}
		hide_all_windows();
		campaign_window.refresh();
		campaign_window.setVisible(true);
	}
	private void show_leaderboard() {
		if (leader_board == null) {
			leader_board = new LeaderboardWindow(this, false);
		}
		hide_all_windows();
		leader_board.setVisible(true);
	}
	
	private void show_character_creation() throws Exception {
		if (character_builder == null) {
			character_builder = new CharacterBuilderWindow(new CommanderChooser(game),this);
		}
		hide_all_windows();
		character_builder.setVisible(true);
	}
}
