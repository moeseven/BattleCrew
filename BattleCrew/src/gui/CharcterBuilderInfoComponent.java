package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.windows.CharacterBuilderWindow;
import gui.windows.ViewController;


public class CharcterBuilderInfoComponent extends JComponent implements Refreshable_gui{
	private CharacterBuilderWindow fcb;
	private JTextField tf;
	private JPanel jp;
	public CharcterBuilderInfoComponent(CharacterBuilderWindow fcb) {
		this.fcb=fcb;
		this.setLayout(new BorderLayout());
		tf=new JTextField();
		tf.setText(fcb.getCb().type_name_string);
		this.add(tf, BorderLayout.NORTH);
		jp= new JPanel();
		jp.add(new HeroStatsPaintComponent(fcb.gui_controller.getGame().getPlayer().getSelectedUnit(), this, 30, true));
		this.add(jp,BorderLayout.CENTER);
		this.setVisible(true);
	}
	public JTextField getTf() {
		return tf;
	}
	public void setTf(JTextField tf) {
		this.tf = tf;
	}
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		repaint();
	}
	@Override
	public ViewController get_gui_controller() {
		return fcb.gui_controller;
	}
	
}
