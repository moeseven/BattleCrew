package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import gameLogic.Game;
import gameLogic.Item;

public class NameGenerator implements Serializable{
		private HashMap<String,String[]> map;
		private Game game;
		public NameGenerator(Game game) throws IOException {		
			super();		
			this.game=game;
			map= new HashMap<String,String[]>();
			String path ="./resources/Names.csv";
			String row;
			BufferedReader csvReader = new BufferedReader(new FileReader(path));
			while ((row = csvReader.readLine()) != null) {
			    String[] data = row.split(",");
			    map.put(data[0], data);
			}
			csvReader.close();
		}

		public String generate_name(String type) {
			if (map.containsKey(type)) {
				int random =  (int) (1+Math.random()* (map.get(type).length-1));
				return map.get(type)[random]; //all parameters needed to generate a name
			}else {
				return type+"(no name)";
			}
			
		}
}
