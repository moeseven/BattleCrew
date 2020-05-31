package gui.windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import gameLogic.CommanderChooser;
import gameLogic.Game;
import gameLogic.Game.GameState;

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

	public BattleWindow battle_window;
	public CampaignWindow campaign_window;
	public MainMenu main_menu;
	public LeaderboardWindow leader_board;
	public CharacterBuilderWindow character_builder;
	public GameOverWindow game_over;
	private Game game;
	public enum View{
		Menu,
		Leaderboard,
		Battle,
		City,
		CharacterCreation,
		GameOver
	}
	private View view = View.Menu;
	public ViewController() {
		this.game= new Game(1);
		main_menu = new MainMenu(this);
	}
	
	public void setView(View v) {
		switch (v) {
		case Battle:
			game.set_state(GameState.Battle);
			break;
		case City:
			game.set_state(GameState.City);
			break;
		default:
			break;
		}
		view = v;
		update_view();
	}
	private void update_view() {
		if (view == View.Menu) {
			if (game.get_state() == GameState.GameOver) {
				game = new Game(1);
			}
			show_menu();
		}else {
			if (game.get_state() == GameState.GameOver) {
				view = View.GameOver;
				show_game_over();			
		}else {
			switch (view) {
			case Leaderboard:
				show_leaderboard();
				break;
			case CharacterCreation:
				show_character_creation();
				break;
			default:
				switch (game.get_state()) {
				case Battle:
					show_battle();
					break;
				case City:
					show_campaign();
					break;
				default:
					break;
				}
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
		if (battle_window != null) {
			battle_window.setVisible(false);
		}
		if (game_over != null) {
			game_over.setVisible(false);
		}
	}
	

	
	public void load_game() {
		//TODO make stuff serializable
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
		//TODO make stuff serializable
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
	
    public void start_game() {
    	if(game.getPlayer().getHeroes().size()>0 && game.get_state() != GameState.GameOver) {
			game.getPlayer().setSelectedHero(game.getPlayer().getHeroes().getFirst());	
			switch (game.get_state()) {
			case Battle:
				view = View.Battle;
				break;
			case City:
				view = View.City;
			default:
				break;
			}
		}else {
			game = new Game(1);
			character_builder = new CharacterBuilderWindow(new CommanderChooser(game),this);
			view = View.CharacterCreation;
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

	private void show_battle() {
		if (battle_window == null) {
			battle_window = new BattleWindow(this);
		}
		hide_all_windows();
		battle_window.setVisible(true);
		
	}

	private void show_campaign() {
		if (campaign_window == null) {
			campaign_window = new CampaignWindow(this);
		}
		hide_all_windows();
		campaign_window.setVisible(true);
	}
	private void show_leaderboard() {
		if (leader_board == null) {
			leader_board = new LeaderboardWindow(this, false);
		}
		hide_all_windows();
		leader_board.setVisible(true);
	}
	
	private void show_character_creation() {
		hide_all_windows();
		character_builder.setVisible(true);
	}
}
