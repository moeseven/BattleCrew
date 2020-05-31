package gui.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import gameLogic.Game;
import gui.Refreshable_gui;
import gui.windows.ViewController.View;


public class X_to_main_main_menu_window extends JFrame{
	public ViewController gui_controller;
	public X_to_main_main_menu_window(ViewController gui_controller){
		this.gui_controller = gui_controller;
		setVisible(true);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		       gui_controller.setView(View.Menu);
		    }
		});

	}

	
}
