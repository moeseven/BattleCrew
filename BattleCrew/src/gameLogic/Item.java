package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Item implements Serializable{
	public int getNumber_of_enchantments() {
		return number_of_enchantments;
	}
	public void setNumber_of_enchantments(int number_of_enchantments) {
		this.number_of_enchantments = number_of_enchantments;
	}
	private String name;
	private int number_of_enchantments=0;
	private String[] ability_names;
	private LinkedList<Ability> abilities;
	private int gold_value;
	public boolean droppable;	
	private int category;
	private int weight;
	private boolean attack_ability;
	private int damage;
	private int range;
	private int block;
	private int precision;
	private String ammunition;
	//mods
	private int armor;
	private int offense;
	private int defense;
	
	private int strength = 0;
	private int dexterity = 0;
	private int vitality = 0;
	//
	private int thorns = 0;
	private int regen = 0;
	private int image;
	private LinkedList<String> description;
	public Item(String[] stats,Game game) {
		super();
		abilities=new LinkedList<Ability>();
		//generate item out of table
		//name,abilities,gold_value,droppable,category
		name=stats[0];
		String[] ability_names = stats[1].split(";");
		gold_value= Integer.parseInt(stats[2]);
		droppable= Boolean.parseBoolean(stats[3]);
		category= Integer.parseInt(stats[4]);
		weight= Integer.parseInt(stats[5]);
		armor= Integer.parseInt(stats[6]);
		image= Integer.parseInt(stats[7]);
		defense=Integer.parseInt(stats[8]);
		offense=Integer.parseInt(stats[9]);
		damage = Integer.parseInt(stats[10]);
		range = Integer.parseInt(stats[11]);
		precision = Integer.parseInt(stats[12]);
		block = Integer.parseInt(stats[13]);
		ammunition = stats[14];
		thorns = Integer.parseInt(stats[15]);
//		attack_ability=Boolean.parseBoolean(stats[10]);
//		if (attack_ability) {
//			List<String> subArray = new ArrayList<String>();
//			subArray.add(name);
//			subArray.addAll(Arrays.asList(stats).subList(11, stats.length));
//			String[] ability_stats = subArray.toArray(new String[0]);
////			for (Iterator iterator = subArray.iterator(); iterator.hasNext();) {
////				String string = (String) iterator.next();
////				System.out.println(string);
////			}			
//			abilities.add(new Ability(ability_stats));
//		}
		
		//calculate gold value

//		if (gold_value == 0) {
//			gold_value = (int) ((defense+offense)*2.0+damage*3.0+range+precision*precision/20.0+(block*block*damage)/100.0+armor*armor/100.0);
//			//gold_value = (int) (Math.pow(((defense+offense)*2.0+damage*3.0+range+precision*precision/20.0+(block*block*damage)/100.0+armor*armor/100.0), 3)/1000);
//			if (category == 3) {
//				gold_value = (int) (gold_value*0.75);
//			}
//			gold_value *= 10;
//		}
		
	}
	public Item() {
		name= "unknown";
	}

	public int getCategory() {
		// TODO Auto-generated method stub
		return category;
	}
	
	/**
	 * only call this when the item is not
	 * equipped by a hero!
	 * @param affix
	 * @return
	 */
	public boolean add_affix(ItemAffix affix) {
		//TODO make sure not to mess up hero
		if (number_of_enchantments < 2) {
			number_of_enchantments++;
			if (number_of_enchantments>1) {
				name += " and "+affix.getName();
			}else {
				name += " of "+affix.getName();
			}	
			//1: Hand1  //2: Hand2  //3: BiHand //4: Body //5: Head //6:Ring  //7: Amunition //0: Consumable
			switch (category) {
			case 1:
				damage += affix.getDamage();
				break;
			case 2:
				damage += affix.getDamage();
				if (block > 0) {
					block += affix.getBlock();
				}				
				break;
			case 3:
				//two handed gets double bonus
				defense += affix.getDefense();
				offense += affix.getOffense();
				damage += affix.getDamage();
				break;	
			case 4:
				armor += affix.getArmor();
				break;
			case 5:
				armor += affix.getArmor();
				break;
			default:
				break;
			}
			weight += weight*(affix.getWeight()/100.0);			
			offense += affix.getOffense();
			defense += affix.getDefense();
			strength += affix.getStrength();
			dexterity += affix.getDexterity();
			vitality += affix.getVitality();
			if (precision > 0) {
				precision += affix.getPrecision();
			}		
			thorns += affix.getThorns();
			regen += affix.getRegen();
			return true;
		}
				
		return false;
	}

	public void mod(BattleUnit hero) {
		mod_demod(hero, true);
	}
	public void demod(BattleUnit hero) {
		mod_demod(hero, false);
	}
	
	private void mod_demod(BattleUnit hero, boolean mod) {
		int factor = 1;
		if (!mod) {
			factor = -1;
		}
		hero.setArmor(hero.getArmor()+factor*armor);
		hero.setDefense(hero.getDefense()+factor*defense);
		hero.setOffense(hero.getOffense()+factor*offense);
		hero.setStrength(hero.getStrength()+factor*strength);
		hero.setDexterity(hero.getDexterity()+factor*dexterity);
		hero.setVitality(hero.getVitality()+factor*vitality);
	}
	
	public void resetAbilityCooldowns() {
		for (int i = 0; i < abilities.size(); i++) {
			abilities.get(i).refresh();
		}
	}
	public void generateItemDescription() {
		//TODO
		description=new LinkedList<String>();
		description.add("cost: "+gold_value);
		description.add("weight: "+weight);
		if (damage > 0 && block == 0) {
			description.add("damage: "+damage);
		}
		if (range > 0) {
			description.add("range: "+range);
		}
		if(precision > 0) {
			description.add("precision: "+precision);
		}
		if (block > 0) {
			description.add("block: "+damage+"("+block+"%)");
		}
		if (armor>0) {
			description.add("armor: +"+armor);
		}
		if(defense!=0) {
			description.add("defense: +"+defense);
		}
		if (offense!=0) {
			description.add("offense: +"+offense);
		}
		if (strength!=0) {
			description.add("strength: +" + strength);
		}
		if (dexterity!=0) {
			description.add("dexterity: +" + dexterity);
		}
		if (vitality!=0) {
			description.add("vitality: +" + vitality);
		}
	}
	//getters and setters
	public int getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public LinkedList<Ability> getAbilities() {
		return abilities;
	}
	public void setAbilities(LinkedList<Ability> abilities) {
		this.abilities = abilities;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public LinkedList<String> getDescription() {
		return description;
	}
	public void setDescription(LinkedList<String> description) {
		this.description = description;
	}
	public int getGold_value() {
		return gold_value;
	}
	public void setGold_value(int gold_value) {
		this.gold_value = gold_value;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getDamage() {
		return damage;
	}
	public int getRange() {
		return range;
	}
	public int getArmor() {
		return armor;
	}
	public int getBlock() {
		return block;
	}
	public int getPrecision() {
		return precision;
	}
	public String getAmunitionType() {
		return ammunition;
	}
	
	
	

}
