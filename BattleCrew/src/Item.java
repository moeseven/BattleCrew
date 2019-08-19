
public abstract class Item {

	public boolean droppable;

	public int getCategory() {
		// TODO Auto-generated method stub
		return 0;
	}

	public abstract void mod(Hero hero);

	public int getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract void demod(Hero hero);

}
