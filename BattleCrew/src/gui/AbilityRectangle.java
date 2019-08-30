package gui;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

import gameLogic.Ability;
import gameLogic.Player;
import guiRectangles.ClickableRectangle;

public class AbilityRectangle extends ClickableRectangle {
	protected Ability ability;
	protected Player player;
	public AbilityRectangle(Ability ability,Player player, int x, int y, int length, int height) {
		super(ability.getName(), x, y, length, height);
		this.ability=ability;
		this.player=player;
		setImageNumber(ability.getImage_number());
		updateCaption();
	}

	@Override
	public void updateCaption() {
		setCaption(new LinkedList<String>());
		getCaption().add(ability.getName());			
		if (ability.isUsed()) {
			if (ability.getDamage_target()>0) {
				getCaption().add("damage rolls: "+ability.getDamage_target()+" +"+ability.getOffensive_roll().roll_value+" -"+ability.getDefensive_roll().roll_value+" = "+(ability.getDamage_target()+ability.getOffensive_roll().roll_value-ability.getDefensive_roll().roll_value));
			}
			
		}else {
			getCaption().add("");
			if (ability.getDamage_target()>0) {
				getCaption().add("damage: "+ability.getDamage_target());
			}
			getCaption().add("stamina_cost: "+((int)(10*player.getSelectedUnit().getModifiedStaminaCost(ability.getStamina_cost()))/10.0)+"("+ability.getStamina_cost()+")");
				if (ability.getDexterity_demand()>0) {
					getCaption().add("dexterity demand: "+ability.getDexterity_demand());
				}
			}
	}
	
	@Override
	public void onClick(MouseEvent arg0) {
		// TODO Auto-generated method stub
		player.getSelectedUnit().setSelected_ability(ability);
		updateCaption();
	}

}
