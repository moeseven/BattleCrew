package gui.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import gameLogic.Game;
import gui.ControlComponent;
import gui.Refreshable_gui;
import gui.TableComponent;

/**
 * shows a summary of the battle
 * @author Moritz
 *
 */
//TODO
public class BattleSummaryWindow extends X_to_main_main_menu_window implements Refreshable_gui{
	private ViewController gui_controller;
	public BattleSummaryWindow(ViewController gc){
		super(gc);
		setExtendedState(MAXIMIZED_BOTH); 
		setLayout(new BorderLayout());
		
	}
	
	public void refresh() {
		//TODO
	}

	@Override
	public ViewController get_gui_controller() {
		return gui_controller;
	}
	
	
}
