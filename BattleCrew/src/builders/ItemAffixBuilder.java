package builders;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import gameLogic.Ability;
import gameLogic.Game;
import gameLogic.Item;
import gameLogic.ItemAffix;
public class ItemAffixBuilder implements Serializable{
	private HashMap<String,String[]> map;
	private Game game;
	public ItemAffixBuilder(Game game) throws IOException {		
		super();		
		this.game=game;
		map= new HashMap<String,String[]>();
		String path ="./resources/ItemAffix.csv";
		String row;
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		while ((row = csvReader.readLine()) != null) {

		    String[] data = row.split(",");
		    map.put(data[0], data);
		}			//don't use first row
		csvReader.close();
		map.remove("name");
	}

	public ItemAffix buildAffixbyName(String name) {
		if (map.containsKey(name)) {
			return new ItemAffix(map.get(name),game); //all parameters needed to genarate an item
		}else {
			System.out.println("there is no item with the name "+name);
			return new ItemAffix();
		}
		
	}
	public ItemAffix random_affix() {
		int random =  (int) (1+Math.random()* (map.size()-1));
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			if (random == 0) {
				return buildAffixbyName(entry.getKey());
			}
		   random--;
		}
		return buildAffixbyName("");
	}
}
