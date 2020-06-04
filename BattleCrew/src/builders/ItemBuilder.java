package builders;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import gameLogic.Ability;
import gameLogic.Game;
import gameLogic.Item;
public class ItemBuilder implements Serializable{
	private HashMap<String,String[]> map;
	private Game game;
	public ItemBuilder(Game game) throws IOException {		
		super();		
		this.game=game;
		map= new HashMap<String,String[]>();
		String path ="./resources/Items.csv";
		String row;
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    map.put(data[0], data);
		}
		csvReader.close();
	}

	public Item buildItembyName(String name) {
		if (map.containsKey(name)) {
			return new Item(map.get(name),game); //all parameters needed to genarate an item
		}else {
			System.out.println("there is no item with the name "+name);
			return new Item();
		}
		
	}
}
