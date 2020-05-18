package gameLogic;

import java.util.ArrayList;
import java.util.LinkedList;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileUnit;
import gameLogic.Behaviour.Behaviour_type;
import gameLogic.Behaviour.Movespeed;

public class BattleUnit implements HexTileUnit{
	
	//other
	private Player player;
	private String name;
	private int image_number;

	//stats
	private int vitality;
	private int strength;
	private int dexterity;
	private int size;
	
	private int move_speed;
	
	private int protection;
	private int endurance;
	private int wisdom;
	private int spell_power;
	private int offense;
	private int defense;
	private int precision;
	private int courage;
	
	private int resist_cold;
	private int resist_heat;
	private int resist_bleed;
	private int resist_mind;
	private int resist_stun;
	private int resist_poison;
	private int resist_bash;
	private int resist_slash;
	private int resist_pirce;
	
	private int meele_skill;
	private int base_damage = 10;
	
	private Equipment equipment;
	//dynamic stats
	private double health;
	private double fatigue;
	private double mana;
	private double fear;
	private boolean stunned;
	private int tiles_moved_this_round;
	private HexTile tile;
	
	//other things
	private Behaviour_type behaviour;
	private boolean battle_participant;
	private BattleUnit target;
	
	public BattleUnit(String[] stats,Game game, Player player) {
		super();
		this.player = player;
		equipment = new Equipment(this);
		health = 100;
		name=stats[0];
		image_number =Integer.parseInt(stats[1]);
		
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
		offense = Integer.parseInt(stats[11]);
		defense = Integer.parseInt(stats[12]);
		precision = Integer.parseInt(stats[13]);
		courage = Integer.parseInt(stats[14]);
		
		resist_cold = Integer.parseInt(stats[15]);
		resist_heat = Integer.parseInt(stats[16]);
		resist_bleed = Integer.parseInt(stats[17]);
		resist_mind = Integer.parseInt(stats[18]);
		resist_stun = Integer.parseInt(stats[19]);
		resist_poison = Integer.parseInt(stats[20]);
		resist_bash = Integer.parseInt(stats[21]);
		resist_slash = Integer.parseInt(stats[22]);
        resist_pirce = Integer.parseInt(stats[23]);
        
        meele_skill = Integer.parseInt(stats[24]);
	}
	
	public BattleUnit() {
		super();
	}
	//
	
	/*
	 * initialize for battle
	 */
	public void battle_begin() {
		health = 100;
		fatigue = 0;
		mana = 100;
		fear = 0;
		tiles_moved_this_round = 0;
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
	
	/*
	 * reset sats for new round
	 */
	public void round_begin() {
		//gain fatigue modified by weight and already performed actions
		exhaust(BattleCalculations.calc_movement_exhaustion(this));
		tiles_moved_this_round = 0;
	}
	
	
	public void exhaust(double e) {
		fatigue+=e;
		if (fatigue>100) {
			fatigue = 100;
		}
	}
	
	/*
	 * attack enemy with main Hand weapon
	 */
	public boolean basic_attack(BattleUnit target) {
		if (equipment.getHand1()!=null) {
			if (equipment.getHand1().getRange()>2) {
				//this is a ranged weapon
				ArrayList<BattleUnit> adjacent_enemies = get_adjacent_enemies();
				if (adjacent_enemies.size()>0) {
					target = adjacent_enemies.get(0);
					BattleCalculations.perform_meele_attack(this, target);
				}else {
					BattleCalculations.perform_ranged_attack(this, target);
				}			
				return true;
			}
			//this is a meele attack
			BattleCalculations.perform_meele_attack(this, target);
			return true;
		}
		
		return false;
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

	public boolean get_attacked_meele(BattleUnit attacker) {
		//TODO
		return false;
	}
	
	public void take_damage(double damage) {
		if (damage > 0) {
			health -= damage;
			player.getGame().log.addLine(name+" took "+(int) damage+"% damage!");
			if (isDead()) {
				die();
			}
		}
	}
	
	private void die() {
		this.tile.setUnit(null);
		equipment.unequipAll();		
		player.getGame().log.addLine(name+" died!");
		player.getHeroes().remove(this);
	}
	
	public boolean isDead() {
		// TODO Auto-generated method stub
		if (this.getHealth()<=0) {
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
	
	
	
	public LinkedList<String> generateStatLines(){
		//paint Hero info all interesting stats about the hero
		LinkedList<String> lines=new LinkedList<String>();
		lines.add("");
		lines.add(name);
		lines.add("");
		lines.add("health: "+(int)(getHealth())+"%");
		lines.add("fatigue: "+(int)(getFatigue())+"%");
		lines.add("fear: "+(int)getFear()+"%");
		//lines.add("moral: "+player.getSelectedHero().getStress()+"/"+player.getSelectedHero().getStressCap());
		lines.add("");
		//main stats
		lines.add("damage: "+(int) BattleCalculations.calc_minimum_damage(this)+"-"+(int) BattleCalculations.calc_maximum_damage(this));
		lines.add("offese: "+(int) BattleCalculations.get_fatigue_corrected_offense_skill(this)+" ("+BattleCalculations.get_meele_attack_skill(this)+")");
		lines.add("defense: "+(int) BattleCalculations.get_fatigue_corrected_defense_skill(this)+" ("+BattleCalculations.get_meele_defense_skill(this)+")");
		lines.add("precision: "+ (int) BattleCalculations.get_fatigue_corrected_precision(this)  +"("+precision+")");
		lines.add("");
		lines.add("armor: "+getArmor());
		lines.add("");
		lines.add("speed: "+getMove_speed());
		lines.add("endurance: "+getEndurance());
		lines.add("");
		lines.add("strength: "+getStrength());
		lines.add("dexterity: "+getDexterity());		
		lines.add("vitality: "+getVitality());
			
		
		//defensive
		
		
		//TODO lines.add("experience: "+player.getSelectedHero().getExperience()+"/"+GameEquations.experienceThresholdForLevelUp(player.getSelectedHero().getLevel()));		
		//Quirks
		lines.add("");
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

	public int getResist_bash() {
		return resist_bash;
	}

	public void setResist_bash(int resist_bash) {
		this.resist_bash = resist_bash;
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

	public int getMeele_skill() {
		return meele_skill;
	}

	public void setMeele_skill(int meele_skill) {
		this.meele_skill = meele_skill;
	}
	
}
