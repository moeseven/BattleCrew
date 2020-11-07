package builders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import gameLogic.BattleTemplate;
import gameLogic.BattleUnit;
import gameLogic.Commander;
import gameLogic.Commander_Class;
import gameLogic.Game;
import gameLogic.Item;
import gameLogic.ItemAffix;
import gameLogic.Player;

public class GameObjectBuilder {
	private HashMap<String,String[]> map_units;
	private HashMap<String,String[]> map_item_affixes;
	private HashMap<String,String[]> map_items;
	private HashMap<String,String[]> map_names;
	private HashMap<String,String[]> map_army;
	private Game game;
	private Random random;
	public GameObjectBuilder(Game game)throws IOException {
		super();		
		this.game=game;
		random = new Random();
		map_units= new HashMap<String,String[]>();
		String path ="./resources/Units.csv";
		String row;
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    map_units.put(data[0], data);
		}
		csvReader.close();
		map_item_affixes= new HashMap<String,String[]>();
		path ="./resources/ItemAffix.csv";
		csvReader = new BufferedReader(new FileReader(path));
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    map_item_affixes.put(data[0], data);
		}			//don't use first row
		csvReader.close();
		map_item_affixes.remove("name");
		map_items= new HashMap<String,String[]>();
		path ="./resources/Items.csv";
		csvReader = new BufferedReader(new FileReader(path));
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    map_items.put(data[0], data);
		}
		csvReader.close();
		map_names= new HashMap<String,String[]>();
		path ="./resources/Names.csv";
		csvReader = new BufferedReader(new FileReader(path));
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    map_names.put(data[0], data);
		}
		csvReader.close();
		map_army= new HashMap<String,String[]>();
		path ="./resources/Battletemplates.csv";
		csvReader = new BufferedReader(new FileReader(path));
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    map_army.put(data[0], data);
		}
		map_army.remove("name");
		csvReader.close();
	}

	public BattleUnit buildUnitbyName(String name,Player player) throws Exception {
		BattleUnit unit;
		if (map_units.containsKey(name)) {
			unit = new BattleUnit(map_units.get(name),game,player,random); //all parameters needed to genarate an item
		}else {
			throw new Exception("there is no unit with the name "+name);
		}
		unit.randomizeStats(random);
		return unit;
	}
	public Commander buildCommanderbyName(String name, Commander_Class commander_class, Player player) throws Exception{
		if (map_units.containsKey(name)) {
			return new Commander(map_units.get(name),commander_class,game,player, random); //all parameters needed to genarate an item
		}else {			
			throw new Exception("there is no unit with the name "+name);
			//return new Commander(map.get("human"),commander_class, game, player, random);
		}
		
	}

	public ItemAffix buildAffixbyName(String name) {
		if (map_item_affixes.containsKey(name)) {
			return new ItemAffix(map_item_affixes.get(name),game); //all parameters needed to genarate an item
		}else {
			System.out.println("there is no item with the name "+name);
			return new ItemAffix();
		}
		
	}
	public ItemAffix random_affix() {
		int random =  (int) (1+Math.random()* (map_item_affixes.size()-1));
		for (Map.Entry<String, String[]> entry : map_item_affixes.entrySet()) {
			if (random == 0) {
				return buildAffixbyName(entry.getKey());
			}
		   random--;
		}
		return buildAffixbyName("");
	}


	public Item buildItembyName(String name) {
		if (map_items.containsKey(name)) {
			return new Item(map_items.get(name),game); //all parameters needed to genarate an item
		}else {
			System.out.println("there is no item with the name "+name);
			return new Item();
		}
		
	}

	public String generate_name(String type) {
		if (map_names.containsKey(type)) {
			int random =  (int) (1+Math.random()* (map_names.get(type).length-1));
			int random_second =  (int) (1+Math.random()* (map_names.get(type+"_second").length-1));
			String name = map_names.get(type)[random];
			name += " "+map_names.get(type+"_second")[random_second];
			return name; //all parameters needed to generate a name
		}else {
			return type+" ";
		}		
	}

	
	public BattleTemplate buildBattleTemplate(String name) throws Exception{
		BattleTemplate template;
		if (map_army.containsKey(name)) {
			template = new BattleTemplate(map_army.get(name), game);
		}else {
			throw new Exception("there is no template with the name "+name);
		}
		return template;
	}
}
