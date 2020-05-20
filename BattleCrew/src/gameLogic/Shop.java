package gameLogic;

import java.util.LinkedList;

public class Shop {
	private Game game;
	private int imageNumber=382;
	private Inventory inventory;
	public Shop(Game game) {
		this.game=game;
		inventory = new Inventory();
		inventory.add(game.itemBuilder.buildItembyName("shortsword"));
		inventory.add(game.itemBuilder.buildItembyName("leatherarmor"));
		inventory.add(game.itemBuilder.buildItembyName("longbow"));
		inventory.add(game.itemBuilder.buildItembyName("slingshot"));
		inventory.add(game.itemBuilder.buildItembyName("broadsword"));
		inventory.add(game.itemBuilder.buildItembyName("helebard"));
		inventory.add(game.itemBuilder.buildItembyName("leathercap"));
		inventory.add(game.itemBuilder.buildItembyName("buckler"));
		inventory.add(game.itemBuilder.buildItembyName("shield"));
		for (int i = 0; i < 100; i++) {
			inventory.add(game.itemBuilder.buildItembyName("arrow"));
		}
	}
	public int getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
}
