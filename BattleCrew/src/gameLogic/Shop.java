package gameLogic;

import java.util.LinkedList;

public class Shop {
	private LinkedList<Item> items;
	private Game game;
	private int imageNumber=66;
	public Shop(Game game) {
		this.game=game;
		items=new LinkedList<Item>();
		items.add(game.itemBuilder.buildItembyName("shortsword"));
		items.add(game.itemBuilder.buildItembyName("leatherarmor"));
	}
	public LinkedList<Item> getItems() {
		return items;
	}
	public void setItems(LinkedList<Item> items) {
		this.items = items;
	}
	public int getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}
	
}
