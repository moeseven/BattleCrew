package builders;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import gameLogic.Ability;
public class AbilityBuilder {
	private HashMap<String,String[]> map;
	public AbilityBuilder() throws IOException {		
		super();		
		map= new HashMap<String,String[]>();
		String path ="./resources/Abilities.csv";
		String row;
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    map.put(data[0], data);
		}
		csvReader.close();
	}

	public Ability buildAbilitybyName(String name) {
		if (map.containsKey(name)) {
			return new Ability(map.get(name)); //all parameters needed to genarate an ability
		}else {
			
			return new Ability();
		}
		
	}
}
