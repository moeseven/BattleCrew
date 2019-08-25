package gameLogic;

public class Card {
private int modifier;
private boolean shuffler;

	public Card(int modifier,boolean shuffler) {
		this.modifier = modifier;
		this.shuffler=shuffler;
	}
	//getters and setters
	
	public int getModifier() {
		return modifier;
	}

	public boolean isShuffler() {
		return shuffler;
	}

	public void setShuffler(boolean shuffler) {
		this.shuffler = shuffler;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
	
}
