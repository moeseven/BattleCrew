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


public class GameOverWindow extends X_to_main_main_menu_window{
	public GameOverWindow(ViewController gui_controller){
		super(gui_controller);
		this.gui_controller = gui_controller;
		setSize(550, 350);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		setLayout(new BorderLayout());
		add(new GameOverComponent());
		
		setVisible(true);
	}
	
	private class GameOverComponent extends JComponent{
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setFont(new Font("TimesRoman", Font.BOLD, 32)); 
			g.drawString("Game Over!", 130, 100);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 23)); 
			g.drawString("Commander: " + gui_controller.getGame().getPlayer().getCommander().getName()+" (Level: "+gui_controller.getGame().getPlayer().getCommander().getLevel()+" "+gui_controller.getGame().getPlayer().getCommander().getType()+")", 10, 210);
			g.drawString("Score: " + gui_controller.getGame().getPlayer().getScore(), 50, 240);
		}
	
		
		
	}
	
}
