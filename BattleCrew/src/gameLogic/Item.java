package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Item implements Serializable{
	public String getDamage_type() {
		return damage_type;
	}
	public int getNumber_of_enchantments() {
		return number_of_enchantments;
	}
	public void setNumber_of_enchantments(int number_of_enchantments) {
		this.number_of_enchantments = number_of_enchantments;
	}
	private String name;
	private int number_of_enchantments=0;
	private String[] ability_names;
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
	private String damage_type;
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
	private int learning = 0;
	//campaign stats
	protected int healer_points = 0;  // chance of healing lost units after battles
	protected int gold_bonus = 0; //money bonus
	protected int smith_points = 0; //chance of enchanting an item when buying
	protected int recruit_points = 0;
	protected int drill = 0;
	
	private int image;
	private LinkedList<String> description;
	public Item(String[] stats,Game game) {
		super();
		//generate item out of table
		//name,abilities,gold_value,droppable,category
		name=stats[0];
		damage_type = stats[1];
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
		learning = Integer.parseInt(stats[16]);
		healer_points = Integer.parseInt(stats[17]);
		gold_bonus = Integer.parseInt(stats[18]);
		smith_points = Integer.parseInt(stats[19]);
		recruit_points = Integer.parseInt(stats[20]);
		drill = Integer.parseInt(stats[21]);
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
		if (category != 7 && category != 0) {
			//allow no affixes for ammunition and consumables
			if (number_of_enchantments < 3) {
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
				
				if (precision > 0) {
					precision += affix.getPrecision();
				}
				//hero mods
				offense += affix.getOffense();
				defense += affix.getDefense();
				strength += affix.getStrength();
				dexterity += affix.getDexterity();
				vitality += affix.getVitality();		
				thorns += affix.getThorns();
				regen += affix.getRegen();
				learning = affix.getLearning();
				healer_points = affix.getHealer_points();
				gold_bonus = affix.getGold_bonus();
				smith_points = affix.getSmith_points();
				recruit_points = affix.getRecruit_points();
				drill = affix.getDrill();
				//
				return true;
			}
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
		if (category != 7 && category != 0) {
					int factor = 1;
			if (!mod) {
				factor = -1;
			}
			hero.setDefense(hero.getDefense()+factor*defense);
			hero.setOffense(hero.getOffense()+factor*offense);
			hero.setStrength(hero.getStrength()+factor*strength);
			hero.setDexterity(hero.getDexterity()+factor*dexterity);
			hero.setVitality(hero.getVitality()+factor*vitality);
			hero.setThorns(hero.getThorns()+factor*thorns);
			hero.setRegen(hero.getRegen()+factor*regen);
			hero.setLearning(hero.getLearning()+factor*learning);
			hero.setHealer_points(hero.getHealer_points()+factor*healer_points);
			hero.setGold_bonus(hero.getGold_bonus()+factor*gold_bonus);
			hero.setSmith_points(hero.getSmith_points()+factor*smith_points);
			hero.setRecruit_points(hero.getRecruit_points()+factor*recruit_points);
			hero.setDrill(hero.getDrill()+factor*drill);
		}
	
	}
	
	public void generateItemDescription() {
		//TODO
		description=new LinkedList<String>();
		description.add("cost: "+gold_value);
		description.add("weight: "+weight);
		if (damage > 0 && block == 0) {
			description.add("damage: "+damage +"("+damage_type+")");
		}
		if (range > 0) {
			description.add("range: "+range);
		}
		if(precision > 0) {
			description.add("precision: "+precision);
		}
		if (block > 0) {
			description.add("block: "+block+"%");
		}
		if (armor>0) {
			description.add("armor: +"+armor);
		}
		if (offense!=0) {
			description.add("offense: +"+offense);
		}
		if(defense!=0) {
			description.add("defense: +"+defense);
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
		if (thorns!=0) {
			description.add("thorns: +" + thorns);
		}
		if (regen!=0) {
			description.add("regen: +" + regen);
		}
		if(learning!=0) {
			description.add("learning: "+ learning);
		}
		if(healer_points!=0) {
			description.add("healing: "+ healer_points);
		}
		if(gold_bonus!=0) {
			description.add("treasuring: "+ gold_bonus);
		}
		if(smith_points!=0) {
			description.add("smithing: "+ smith_points);
		}
		if(recruit_points!=0) {
			description.add("recruiting: "+ recruit_points);
		}
		if(drill!=0) {
			description.add("drilling: "+ drill);
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
