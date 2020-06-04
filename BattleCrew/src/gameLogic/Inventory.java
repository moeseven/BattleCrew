package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Inventory implements Serializable{
	private HashMap<String, ArrayList<Item>> inventory_map;
	private LinkedList<ArrayList<Item>> inventory_list;
	public Inventory() {
		inventory_map = new HashMap<String, ArrayList<Item>>();
		inventory_list = new LinkedList<ArrayList<Item>>();
	}
	public boolean add(Item item) {
		if (!inventory_map.containsKey(item.getName())) {
			ArrayList<Item> array_list = new ArrayList<Item>();
			inventory_map.put(item.getName(),array_list);
			inventory_list.add(array_list);
		}
		inventory_map.get(item.getName()).add(item);
		return true;
	}
	public boolean remove(Item item) {
		if (inventory_map.containsKey(item.getName())) {
			inventory_map.get(item.getName()).remove(item);
			if (inventory_map.get(item.getName()).size() == 0) {
				inventory_list.remove(inventory_map.get(item.getName()));
				inventory_map.remove(item.getName());				
			}
			return true;
		}
		return false;
	}
	public boolean contains(Item item) {
		if (item!=null) {
			for (int i = 0; i < inventory_list.size(); i++) {
				for (int j = 0; j < inventory_list.get(i).size(); j++) {
					if (item.equals(inventory_list.get(i).get(j))) {
						return true;
					}
				}
			}
		}				
		return false;
	}
	
	
//	/**
//	 * 
//	 * @return number of different items in the inventory
//	 */
//	public int size() {
//		return inventory_map.size();
//	}
	public HashMap<String, ArrayList<Item>> getInventory_map() {
		return inventory_map;
	}
	public LinkedList<ArrayList<Item>> getInventory_list() {
		return inventory_list;
	}
	
	
}
