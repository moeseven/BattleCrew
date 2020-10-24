package builders;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import gameLogic.Ability;
import gameLogic.BattleUnit;
import gameLogic.Commander;
import gameLogic.Commander_Class;
import gameLogic.Game;
import gameLogic.Item;
import gameLogic.Player;
public class BattleUnitBuilder implements Serializable{
	private HashMap<String,String[]> map;
	private Game game;
	private Random random;
	public BattleUnitBuilder(Game game) throws IOException {		
		super();		
		this.game=game;
		random = new Random();
		double test = random.nextGaussian();
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
		BattleUnit unit;
		if (map.containsKey(name)) {
			unit = new BattleUnit(map.get(name),game,player,random); //all parameters needed to genarate an item
		}else {
			System.out.println("there is no unit with the name "+name);
			unit = new BattleUnit();
		}
		unit.randomizeStats(random);
		return unit;
	}
	public Commander buildCommanderbyName(String name, Commander_Class commander_class, Player player) {
		if (map.containsKey(name)) {
			return new Commander(map.get(name),commander_class,game,player, random); //all parameters needed to genarate an item
		}else {
			System.out.println("there is no unit with the name "+name);
			return new Commander(map.get("human"),commander_class, game, player, random);
		}
		
	}
}
