package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileUnit;
import gameLogic.Behaviour.Behaviour_type;
import gameLogic.Behaviour.Movespeed;

public class BattleUnit implements HexTileUnit, Serializable{
	
	

	public int getExperience() {
		return experience;
	}


	public int getResist_blunt() {
		return resist_blunt;
	}


	public void setResist_blunt(int resist_blunt) {
		this.resist_blunt = resist_blunt;
	}


	public short getAttacks_taken_last_round() {
		return attacks_taken_last_round;
	}


	public int getLearning() {
		return learning;
	}


	public void setLearning(int learning) {
		this.learning = learning;
	}


	public int getGold_bonus() {
		return gold_bonus;
	}


	public void setGold_bonus(int gold_bonus) {
		this.gold_bonus = gold_bonus;
	}


	public int getSmith_points() {
		return smith_points;
	}


	public void setSmith_points(int smith_points) {
		this.smith_points = smith_points;
	}


	public int getRecruit_points() {
		return recruit_points;
	}


	public void setRecruit_points(int recruit_points) {
		this.recruit_points = recruit_points;
	}


	public int getDrill() {
		return drill;
	}


	public void setDrill(int drill) {
		this.drill = drill;
	}


	public void setMeele_image(int meele_image) {
		this.meele_image = meele_image;
	}


	public int getRanged_image() {
		return ranged_image;
	}


	public int getMeele_image() {
		return meele_image;
	}


	public int getEnchant_chance() {
		return smith_points;
	}


	public void setEnchant_chance(int enchant_chance) {
		this.smith_points = enchant_chance;
	}


	public int getGroup_size() {
		return group_size;
	}


	public void setGroup_size(int group_size) {
		this.group_size = group_size;
	}


	public int getCommand_points() {
		return command_points;
	}


	public void setCommand_points(int command_points) {
		this.command_points = command_points;
	}


	public int getHealer_points() {
		return healer_points;
	}


	public void setHealer_points(int healer_points) {
		this.healer_points = healer_points;
	}

	public int getWealth() {
		return wealth;
	}


	public void setWealth(int wealth) {
		this.wealth = wealth;
	}

	public short getAttacks_taken_this_round() {
		return attacks_taken_this_round;
	}

	public int getRegen() {
		return regen;
	}

	public void setRegen(int regen) {
		this.regen = regen;
	}

	public int getThorns() {
		return thorns;
	}

	public void setThorns(int thorns) {
		this.thorns = thorns;
	}

	public boolean isAttacked_this_round() {
		return attacked_this_round;
	}

	public void setAttacked_this_round(boolean attacked_this_round) {
		this.attacked_this_round = attacked_this_round;
	}

	//other
	protected Player player;
	private String name;
	protected String type;
	private int image_number;
	private int ranged_image,meele_image;
	//stats
	protected int vitality;
	protected int strength;
	protected int dexterity;
	protected int size;
	
	private int move_speed;
	
	protected int protection;
	protected int endurance;
	protected int wisdom;
	protected int spell_power;
	protected int base_offense;
	protected int base_defense;
	private int offense;
	private int defense;
	protected int precision;
	protected int courage;
	protected int recovery = 5;
	protected int learning = 1;
	
	private int resist_cold;
	private int resist_heat;
	private int resist_bleed;
	private int resist_mind;
	private int resist_stun;
	private int resist_poison;
	
	//weapon types
	private int resist_blunt;
	private int resist_slash;
	private int resist_pirce;
	
	protected int weapon_skill;
	private int base_damage = 1;
	private boolean equippable = true; //TODO
	private int thorns;
	private int regen=0;
	private int salary=5;
	private Equipment equipment;
	
	// leader skills
	protected int wealth = 600; // starting money
	protected int group_size = 1; // amount of warriors in the team
	
	
	//job effects
	protected int command_points = 3; // number of Warriors that can be fielded	
	protected int healer_points = 1;  // chance of healing lost units after battles
	protected int gold_bonus = 1; //money bonus
	protected int smith_points = 1; //chance of enchanting an item when buying
	protected int recruit_points = 1;
	protected int drill = 60;
	
	//unused
	
	//dynamic stats
	private double health;
	private double fatigue = 0;
	private double mana = 0;
	private double fear = 0;
	private boolean stunned;
	private int tiles_moved_this_round;
	private boolean attacked_this_round;
	private short attacks_taken_last_round=0;
	public short attacks_taken_this_round=0;
	private HexTile tile;
	private HexTile retreat_tile;
	private boolean fled;
	
	private int experience = 0;
	private int exp_value = 25;
	protected int level = 1;
	
	//statistics
	public int accumulated_sallery=0;
	public double damage_dealt=0;
	public double damage_blocked=0;
	public int evaded_attacks=0;
	public int missed_attacks=0;
	public double damage_absorbed=0;
	public int meele_attacks_attempted = 0;
	public int meele_attacks_defended=0;
	public int target_of_a_meele_attack=0;
	public int meele_attacks_landed=0;
	public int ranged_attacks_landed=0;
	public int ranged_attacks_attempted=0;
	public int kills = 0;

	//other things
	private Behaviour_type behaviour = Behaviour_type.ATTACK_CLOSEST_ENEMY;
	private boolean battle_participant;
	private BattleUnit target;
	
	static private double STAT_VARIANCE;
	public BattleUnit(String[] stats,Game game, Player player, Random random) {
		super();
		this.player = player;
		equipment = new Equipment(this);
		health = 100;
		type=stats[0];
		meele_image = Integer.parseInt(stats[1]);
		
		//stats
		vitality = Integer.parseInt(stats[2]);
		strength = Integer.parseInt(stats[3]);
		dexterity = Integer.parseInt(stats[4]);
		
		size = Integer.parseInt(stats[5]);
		
		move_speed = Integer.parseInt(stats[6]);
		
		protection = Integer.parseInt(stats[7]);
		endurance = Integer.parseInt(stats[8]);
		wisdom = Integer.parseInt(stats[9]);
		spell_power = Integer.parseInt(stats[10]);
		base_offense = Integer.parseInt(stats[11]);
		base_defense = Integer.parseInt(stats[12]);
		//
		offense=0;
		defense=0;
		//
		precision = Integer.parseInt(stats[13]);
		courage = Integer.parseInt(stats[14]);
		
		resist_cold = Integer.parseInt(stats[15]);
		resist_heat = Integer.parseInt(stats[16]);
		resist_bleed = Integer.parseInt(stats[17]);
		resist_mind = Integer.parseInt(stats[18]);
		resist_stun = Integer.parseInt(stats[19]);
		resist_poison = Integer.parseInt(stats[20]);
		
		//weapon types
		resist_blunt = Integer.parseInt(stats[21]);
		resist_slash = Integer.parseInt(stats[22]);
        resist_pirce = Integer.parseInt(stats[23]);
        
        weapon_skill = Integer.parseInt(stats[24]);
        equippable = Boolean.parseBoolean(stats[25]);
        recovery = Integer.parseInt(stats[26]);
        thorns = Integer.parseInt(stats[27]);
        exp_value = Integer.parseInt(stats[28]);
        regen = Integer.parseInt(stats[29]); 
        ranged_image = Integer.parseInt(stats[30]);
        healer_points = Integer.parseInt(stats[31]);
        smith_points = Integer.parseInt(stats[32]);
        gold_bonus = Integer.parseInt(stats[33]);
        command_points = Integer.parseInt(stats[34]);
        recruit_points = Integer.parseInt(stats[35]);
        salary = Integer.parseInt(stats[36]);
        drill = Integer.parseInt(stats[37]);
        learning = Integer.parseInt(stats[38]);
        name = player.getGame().builder.generate_name(type);
        image_number = meele_image;
	}
	public void randomizeStats(Random random) {
		//TODO
		vitality += Math.min(3, Math.max(-3, random.nextGaussian()));
		strength  +=Math.min(3, Math.max(-3, random.nextGaussian()));
		dexterity  +=Math.min(3, Math.max(-3, random.nextGaussian()));
		size  +=Math.min(3, Math.max(-3, 0.5*random.nextGaussian()));
		endurance  +=Math.min(4, Math.max(-4, random.nextGaussian()));
		wisdom  +=Math.min(3, Math.max(-3, random.nextGaussian()));
		spell_power +=Math.min(4, Math.max(-4, random.nextGaussian()));
		base_offense  +=Math.min(4, Math.max(-4, random.nextGaussian()));
		base_defense += Math.min(4, Math.max(-4, random.nextGaussian()));
		precision += Math.min(4, Math.max(-4, random.nextGaussian()));
		courage += Math.min(4, Math.max(-4, random.nextGaussian()));
		weapon_skill += Math.min(3, Math.max(-3, random.nextGaussian()));
		recovery += Math.min(3, Math.max(-3, random.nextGaussian()));
		healer_points +=Math.min(3, Math.max(-3, random.nextGaussian()));
		smith_points +=Math.min(3, Math.max(-3, random.nextGaussian()));
		gold_bonus +=Math.min(3, Math.max(-3, random.nextGaussian()));
		command_points +=Math.min(3, Math.max(-3, random.nextGaussian()));
		recruit_points +=Math.min(3, Math.max(-3, random.nextGaussian()));
		salary +=Math.min(3, Math.max(-3, random.nextGaussian()));
		drill +=Math.min(15, Math.max(-15, 4*random.nextGaussian()));
		learning +=Math.min(10, Math.max(-10, 3*random.nextGaussian()));
		
	}
	
	public BattleUnit() {
		super();
	}
	//
	
	/*
	 * initialize for battle
	 */
	public void battle_begin() {
		fled = false;
		fear = 0;
		tiles_moved_this_round = 0;
		retreat_tile =tile;
		attacked_this_round = false;
		attacks_taken_last_round = 0;
		attacks_taken_this_round = 0;
	}
	
	/*
	 * move to tile if adjacent
	 * suffer exhaustion
	 */
	public boolean move(Tile tile) {
		//TODO move to adjacent tile if possible and exhaust doing so
		if (isReadyToMove()) {
			if (reachableTile(tile)) {
				tiles_moved_this_round++;
				this.tile.setUnit(null);
				tile.setUnit(this);
				this.tile=tile;
				return true;
			}
			
		}
		return false;
	}
	
	public void recover() {		
		heal(regen*10);
		heal_percent(recovery);
		relax(recovery*1.5);	
	}
	
	/*
	 * reset sats for new round
	 */
	public void round_begin() {
		//gain fatigue modified by weight and already performed actions
		exhaust(BattleCalculations.calc_movement_exhaustion(this));
		relax(0.5);
		double battle_fright = 0.013;
		if (player.getLeader() != null) {
			battle_fright = 0.01 + 0.01/player.getLeader().command_points;
		}
		frighten(battle_fright);
		//regen
		heal(regen);
		tiles_moved_this_round = 0;
		attacked_this_round = false;
		attacks_taken_last_round = attacks_taken_this_round;
		attacks_taken_this_round = 0;
	}
	
	
	public void exhaust(double e) {
		fatigue+=e;
		if (fatigue>100) {
			fatigue = 100;
		}
	}
	public void relax(double r) {
		if (r > 0) {
			fatigue -= r;
			if (fatigue < 0) {
				fatigue = 0;
			}
		}
	}
	public void frighten(double f) {
		f = Math.max(0, f * (10.0/courage)*100);
		fear+=f;
		if (fear>200) {
			die();
		}
	}
	public void gain_courage(double c) {
		if (c > 0) {
			fear -= c;
			if (fear<0) {
				fear = 0;
			}
		}
	}
	public void pay_salary() {
		if(this.getPlayer().pay_gold(salary)) {
			accumulated_sallery += salary;
		}else {
			if (courage > 5) {
				courage --;
				//no salary!
			}else {
				player.getHeroes().remove(this);
			}
		}
	}
	public void heal(double h) {
		if (h > 0) {
			health += h/vitality*10;
			if (health>100) {
				health = 100;
			}
		}
	}
	public void heal_percent(double h) {
		if (h > 0) {
			health += h;
			if (health>100) {
				health = 100;
			}
		}
		
	}
	public static int experience_threshold_for_next_level(int level) {
		if(level==1) {
			return 0;
		}else {
			return experience_threshold_for_next_level(level-1)+80+10*level;
		}		
	}
	
	public void gain_experience(int exp) {
		int exp_missing_for_lvlup = experience_threshold_for_next_level(level+1)-experience;
		if(exp_missing_for_lvlup <= exp) {
			experience = experience_threshold_for_next_level(level+1);
			lvl_up();	
			gain_experience(exp - exp_missing_for_lvlup);
		}else {
			experience += exp;
		}
	}
	
	public void lvl_up() {
		level++;
		salary++;
		vitality++;
		increase_random_stat();
		increase_random_stat();
		if (player.getChampion() == this) {
			salary++;
			increase_random_stat();
			base_offense++;
			base_defense++;
			vitality++;
		}
		if (player.getHealer() == this) {
			healer_points++;
			wisdom++;
		}
		if (player.getSmith() == this) {
			smith_points++;
			strength++;
		}
		if (player.getTreasurer() == this) {
			gold_bonus++;
			
		}
		if (player.getLeader() == this) {			
			command_points++;
			courage++;
		}
		if (player.getRecruiter() == this) {
			recruit_points++;
			endurance++;
		}
	}
	
	public void increase_random_stat() {
		int random_stat = (int) (Math.random()*19);
		double random_factor = (Math.random()*100+learning)/100.0;
//		if (player.getCommander() != null) {
//			random_factor += player.getCommander().getDrill()/100.0;
//		}
		switch (random_stat) {
		case 0:
			spell_power+=6*random_factor;
			break;
		case 1:
			wisdom+=6*random_factor;
			break;
		case 2:
			precision+=6*random_factor;
			break;
		case 3:
			courage+=6*random_factor;
			break;
		case 4:
			recovery+=6*random_factor;
			break;
		case 5:
			dexterity+=6*random_factor;
			break;
		case 6:
			strength+=5*random_factor;
			break;
		case 7:
			endurance+=6*random_factor;
			break;
		case 8:
			weapon_skill+=3*random_factor;
			break;
		case 9:
		    base_offense+=6*random_factor;
			break;
		case 10:
			base_defense+=6*random_factor;
			break;
		//campaign skills
		case 11:
			smith_points+=5*random_factor;
			break;
		case 12:
			gold_bonus+=5*random_factor;
			break;
		case 13:
			healer_points+=5*random_factor;
			break;
		case 14:
			drill+=15*random_factor;
			break;
		case 15:
			command_points+=2*random_factor;
			break;
		case 16:
			recruit_points+=6*random_factor;
			break;
		case 17:
			learning += 15*random_factor;
			break;
		case 18:
			vitality += 5*random_factor;
			break;
		default:
			//unlucky
			break;
		}		
	}
	/*
	 * attack enemy with main Hand weapon
	 */
	public boolean basic_attack(BattleUnit target) {
		if (this.getEquipment().getHand1()!=null) {
			if (this.getEquipment().getHand1().getAmunitionType().equals("0")) {
				//meele weapon used
				BattleCalculations.perform_meele_attack(this, target);
				//harassment				
			}else {
				if (BattleCalculations.perform_ranged_attack(this, target)) {
					
				}else {
					return false;
				}
			}
		}else {
			BattleCalculations.perform_meele_attack(this, target);
		}
				
		attacked_this_round = true;
		return true;
	}
	
	public ArrayList<BattleUnit> get_adjacent_enemies(){
		ArrayList<BattleUnit> adjacent_enemies = new ArrayList<BattleUnit>();
		LinkedList<HexTile> adjacent_tiles = tile.getAdjacentTiles();
		for (int i = 0; i < adjacent_tiles.size(); i++) {
			BattleUnit tested_unit = (BattleUnit) adjacent_tiles.get(i).getUnit();
			if (tested_unit!=null) {
				if (tested_unit.getPlayer()!=player) {
					adjacent_enemies.add(tested_unit);
				}
			}
		}
		return adjacent_enemies;
	}

	
	public void take_damage(double damage,BattleUnit attacker, String damage_type) {	
		//resistances
		switch (damage_type) {
		case "blunt":
			damage*=(100-resist_blunt)/100.0;
			break;
		case "pierce":
			damage*=(100-resist_pirce)/100.0;
			break;
		case "slash":
			damage*=(100-resist_slash)/100.0;	
			break;
		default:
			System.out.println("unkown damage type: "+damage_type);
			break;
		}
		if (damage > 0) {
			attacker.add_to_damage_dealt(damage);
			player.getGame().log.addLine(name+" took "+(int) damage+" damage!");
			damage = damage*10/vitality;
			damage = Math.min(damage, health);			
			health -= damage;			
			if (health<=0) {
				//give exp to killer;
				attacker.gain_experience(exp_value);
				attacker.kills++;
				die();
			}
		}
	}
	
	public void die() {		
		this.tile.setUnit(null);
		equipment.unequipAll();
		if (player.getHealer()!=null) {
			if (Math.random() < player.getHealer().getHealer_points()/100.0) { //recover chance
				health = 1;
				player.getGame().log.addLine(name+" is wounded");
			}else {
				player.getGame().log.addLine(name+" died!");
				player.getHeroes().remove(this);
			}
		}else {
			player.getGame().log.addLine(name+" died!");
			player.getHeroes().remove(this);
		}
		//player.setSelectedHero(player.getHeroes().getFirst());
	}
	
	public boolean is_unable_to_fight() {
		// TODO Auto-generated method stub
		if (this.getHealth()<=1) {
			return true;
		}
		return false;
	}
	
	
	public int get_movepoints_from_movement_mode(Movespeed ms) {
		switch (ms) {
		case SLOW:
			return 1;
		case WALK:
			return move_speed;
		case CHARGE:
			return move_speed + 1;
		default:
			return move_speed;
		}
	}
	
	public boolean equip(Item item) {
		if (equippable) {
			return equipment.equipItem(item);
		}else {
			return false;
		}
	}
	public LinkedList<String> generate_statistics_lines(){
		LinkedList<String> lines=new LinkedList<String>();		
		lines.add("statistics:");
		lines.add("");
		lines.add("acumulated salary: "+accumulated_sallery);
		lines.add("damage dealt: "+(int)damage_dealt);
		lines.add("attacks evaded: " +(int)evaded_attacks);
		lines.add("damage blocked: " +(int)damage_blocked);
		lines.add("damage absorbed: "+(int)damage_absorbed);
		lines.add("meele hits: "+ meele_attacks_landed);
		lines.add("meele defends: "+ meele_attacks_defended);
		if (ranged_attacks_attempted > 0) {
			double hit_ratio = ranged_attacks_landed/(ranged_attacks_attempted*1.0);
			lines.add("ranged hits: "+ ranged_attacks_landed + "("+(int) (hit_ratio*100)+"%)");
		}		
		lines.add("missed attacks: "+ missed_attacks);
		lines.add("kills: " + kills);
		lines.add("");
		return lines;
	}
	public LinkedList<String> generateStatLines(){
		//paint Hero info all interesting stats about the hero
		LinkedList<String> lines=new LinkedList<String>();
		lines.add(name+" (Level "+level+" "+type+")");
		lines.add("vitality: "+vitality+"/"+(int)(getHealth())+"%");
		lines.add("endurance: "+endurance+"/"+(int)(100-getFatigue())+"%");
		lines.add("moral: "+courage+"/"+(int) (100-getFear())+"%");
		lines.add("size: "+size);
		lines.add("");
		//main stats
		
		//damage line
		String damage_line = "damage: " + (int) BattleCalculations.calc_minimum_damage(this)+"-"+(int) BattleCalculations.calc_maximum_damage(this);
		if (equipment.getHand1()!=null) {
			if (equipment.getHand1().getRange()>2 && BattleCalculations.amunition_ready(this)) {
				damage_line = "damage: " + (int) (BattleCalculations.calc_amunition_damage(this)*BattleCalculations.MINIMUM_DAMAGE_FACTOR)+"-"+(int) (BattleCalculations.calc_amunition_damage(this)*BattleCalculations.MAXIMUM_DAMAGE_FACTOR)+" ("+ getEquipment().getAmunition().get(0).getDamage_type()+")";
			}else {
				damage_line += " ("+BattleCalculations.get_meele_damage_type(this)+")";	
			}
		}else{
			damage_line+=" ("+BattleCalculations.get_meele_damage_type(this)+")";		
		}
		lines.add(damage_line);
		lines.add("offese: "+ BattleCalculations.get_meele_attack_skill(this));
		lines.add("defense: "+ BattleCalculations.get_meele_defense_skill(this));
		
		//precison and accuracy
		String concat_string = ""+(int) BattleCalculations.get_combat_accuracy(this);
		if (equipment.getHand1()!=null) {
			if (equipment.getHand1().getRange()>2) {
				concat_string = (int)(BattleCalculations.calc_attack_ranged_base_hit_chance(this))+"% ("+concat_string+")";
			}
		}
		lines.add("precision: "+concat_string);
		//lines.add("courage: "+courage);
		lines.add("strength: "+getStrength());
		lines.add("dexterity: "+(int) BattleCalculations.get_battle_dexterity(this));	
		lines.add("weapon skill: " + weapon_skill);
		lines.add("resistances:");
		if (resist_blunt>0) {
			lines.add("blunt: " + resist_blunt+"%");
		}
		if (resist_slash>0) {
			lines.add("slash: " + resist_slash+"%");	
		}
		if (resist_pirce>0) {
			lines.add("pierce: " + resist_pirce+"%");
		}		
		int a_body = protection,a_head=protection,a_shield=0;
		String armor_line = "armor: ";
		if (this.getEquipment().getHead()!=null) {
			a_head += this.getEquipment().getHead().getArmor();
		}
		
		if (this.getEquipment().getBody()!=null) {
			a_body += this.getEquipment().getBody().getArmor();
		}
		armor_line+= a_head+"/"+a_body;
		if (this.getEquipment().getHand2()!=null) {
			if (this.getEquipment().getHand2().getBlock()>0) {
				armor_line+= "/";
				armor_line += this.getEquipment().getHand2().getArmor()+"("+this.getEquipment().getHand2().getBlock()+"%)";
			}			
		}
		lines.add(armor_line);		
		if (thorns > 0) {
			lines.add("thorns: "+thorns);
		}
		lines.add("");
		if(getMove_speed() > 1) {
			lines.add("speed: "+getMove_speed());
		}		
		//lines.add("endurance: "+getEndurance());
		lines.add("recovery: "+recovery);
		if (regen > 0) {
			lines.add("regen: "+regen);
		}
		lines.add("learning: " + learning);	
		lines.add("");
		lines.add("commanding: " + command_points+"/"+drill +" drill");		
		lines.add("healing: " + healer_points);					
		lines.add("smithing: " + smith_points);		
		lines.add("recruiting: " + recruit_points);	
		lines.add("treasuring: " + gold_bonus);
		lines.add("salary: "+salary);
		
		//defensive
		
		
		//TODO lines.add("experience: "+player.getSelectedHero().getExperience()+"/"+GameEquations.experienceThresholdForLevelUp(player.getSelectedHero().getLevel()));		
		//Quirks
		//lines.add("");
	//TODO if(player.getSelectedHero().getQuirks().size()>0) { 
//			lines.add("Quirks:");
//			for(int a=0; a<player.getSelectedHero().getQuirks().size();a++) {
//				String quirkString=player.getSelectedHero().getQuirks().get(a).getName()+"(";
//				for(int b=0; b<player.getSelectedHero().getQuirks().get(a).getDescription().size();b++) {
//					quirkString+=player.getSelectedHero().getQuirks().get(a).getDescription().get(b);
//				}
//				quirkString+=")";
//				lines.add(quirkString);
//			}
//			lines.add("");
//		}
		return lines;
	}
	
	//HexTile_unit
	
	
	
	@Override
	public int getImageNumber() {
		return image_number;
	}

	@Override
	public Player getPlayer() {	
		return player;
	}

	@Override
	public boolean reachableTile(HexTile tile) {
		if (tile!=null) {
			if (tile.getDistance(this.tile)==1&&tile.getUnit()==null) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public HexTile getHexTile() {
		return tile;
	}

	@Override
	public void setTile(HexTile tile) {
		this.tile = tile;
		
	}

	@Override
	public boolean isReadyToMove() {
		if (tiles_moved_this_round <= move_speed) {
			if (fatigue < 100) {
				return true;
			}		
		}
		return false;		
	}

	@Override
	public boolean isFleeing() {
		if (fear>=100) {
			return true;
		}
		return false;
	}

	@Override
	public float getMaxHealth() {
		return 100;
	}

	@Override
	public float getHealth() {
		return (float) health;
	}
	/////////////////////////////////////////////////////////////////

	public void add_to_damage_dealt(double damage) {
		damage_dealt+= damage;
	}

	//getters and setters
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImage_number() {
		return image_number;
	}

	public void setImage_number(int image_number) {
		this.image_number = image_number;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMove_speed() {
		return move_speed;
	}

	public void setMove_speed(int move_speed) {
		this.move_speed = move_speed;
	}

	public int getArmor() {
		return protection;
	}

	public void setArmor(int protection) {
		this.protection = protection;
	}

	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

	public int getWisdom() {
		return wisdom;
	}

	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}

	public int getSpell_power() {
		return spell_power;
	}

	public void setSpell_power(int spell_power) {
		this.spell_power = spell_power;
	}

	public int getOffense() {
		return offense;
	}

	public void setOffense(int offense) {
		this.offense = offense;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getCourage() {
		return courage;
	}

	public void setCourage(int courage) {
		this.courage = courage;
	}

	public int getResist_cold() {
		return resist_cold;
	}

	public void setResist_cold(int resist_cold) {
		this.resist_cold = resist_cold;
	}

	public int getResist_heat() {
		return resist_heat;
	}

	public void setResist_heat(int resist_heat) {
		this.resist_heat = resist_heat;
	}

	public int getResist_bleed() {
		return resist_bleed;
	}

	public void setResist_bleed(int resist_bleed) {
		this.resist_bleed = resist_bleed;
	}

	public int getResist_mind() {
		return resist_mind;
	}

	public void setResist_mind(int resist_mind) {
		this.resist_mind = resist_mind;
	}

	public int getResist_stun() {
		return resist_stun;
	}

	public void setResist_stun(int resist_stun) {
		this.resist_stun = resist_stun;
	}

	public int getResist_poison() {
		return resist_poison;
	}

	public void setResist_poison(int resist_poison) {
		this.resist_poison = resist_poison;
	}



	public int getResist_slash() {
		return resist_slash;
	}

	public void setResist_slash(int resist_slash) {
		this.resist_slash = resist_slash;
	}

	public int getResist_pirce() {
		return resist_pirce;
	}

	public void setResist_pirce(int resist_pirce) {
		this.resist_pirce = resist_pirce;
	}

	public int getBase_damage() {
		return base_damage;
	}

	public void setBase_damage(int base_damage) {
		this.base_damage = base_damage;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public double getFatigue() {
		return fatigue;
	}

	public void setFatigue(double fatigue) {
		this.fatigue = fatigue;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public double getFear() {
		return fear;
	}

	public void setFear(double fear) {
		this.fear = fear;
	}

	public boolean isStunned() {
		return stunned;
	}

	public void setStunned(boolean stunned) {
		this.stunned = stunned;
	}

	public int getTiles_moved_this_round() {
		return tiles_moved_this_round;
	}

	public void setTiles_moved_this_round(int tiles_moved_this_round) {
		this.tiles_moved_this_round = tiles_moved_this_round;
	}

	public Behaviour_type getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(Behaviour_type behaviour) {
		this.behaviour = behaviour;
	}

	public HexTile getTile() {
		return tile;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setHealth(double health) {
		this.health = health;
	}
	
	public boolean isBattle_participant() {
		return battle_participant;
	}
	public void setBattle_participant(boolean battle_participant) {
		this.battle_participant = battle_participant;
	}
	public BattleUnit getTarget() {
		return target;
	}
	public void setTarget(BattleUnit target) {
		this.target = target;
	}

	public int getWeapon_skill() {
		return weapon_skill;
	}

	public void setMeele_skill(int meele_skill) {
		this.weapon_skill = meele_skill;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isFled() {
		return fled;
	}

	public void setFled(boolean fled) {
		this.fled = fled;
	}

	public int getBase_offense() {
		return base_offense;
	}

	public int getBase_defense() {
		return base_defense;
	}

	public HexTile getRetreat_tile() {
		return retreat_tile;
	}

	public void setRetreat_tile(HexTile retreat_tile) {
		this.retreat_tile = retreat_tile;
	}
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
