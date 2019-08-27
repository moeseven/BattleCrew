package gui;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import gameLogic.Ability;
import gameLogic.Player;
import guiRectangles.ClickableRectangle;

public class MoveRectangle extends ClickableRectangle {
	protected Player player;
	public MoveRectangle(Player player, int x, int y, int length, int height) {
		super("move", x, y, length, height);
		this.player=player;
		setImageNumber(5);//move image here
		updateCaption();
	}

	@Override
	public void updateCaption() {
		setCaption(new LinkedList<String>());
		getCaption().add(name);
		getCaption().add(" ");
		getCaption().add("stamina_cost: "+player.getSelectedUnit().getModifiedStaminaCost(player.getSelectedUnit().getMoveStamina_cost())+"("+player.getSelectedUnit().getMoveStamina_cost()+")");
	}
	
	@Override
	public void onClick(MouseEvent arg0) {
		// TODO Auto-generated method stub
		player.getSelectedUnit().setSelected_ability(null);
		updateCaption();
	}

}
