package builders;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import gameLogic.Ability;
import gameLogic.BattleUnit;
import gameLogic.Game;
import gameLogic.Item;
import gameLogic.Player;
public class BattleUnitBuilder {
	private HashMap<String,String[]> map;
	private Game game;
	public BattleUnitBuilder(Game game) throws IOException {		
		super();		
		this.game=game;
		map= new HashMap<String,String[]>();
		String path ="./resources/Units.csv";
		String row;
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    map.put(data[0], data);
		}
		csvReader.close();
	}

	public BattleUnit buildUnitbyName(String name,Player player) {
		if (map.containsKey(name)) {
			return new BattleUnit(map.get(name),game,player); //all parameters needed to genarate an item
		}else {
			System.out.println("there is no unit with the name "+name);
			return new BattleUnit();
		}
		
	}
}
