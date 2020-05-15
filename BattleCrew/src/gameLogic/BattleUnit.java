package gameLogic;

import HexTilePlayground.HexTile;
import HexTilePlayground.HexTilePlayer;
import HexTilePlayground.HexTileUnit;
import gameLogic.Behaviour.Behaviour_type;

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
	
	private int base_damage = 10;
	
	private Equipment equipment;
	//dynamic stats
	private double health;
	private double fatigue;
	private double mana;
	private double fear;
	private boolean stunned;
	private int attacks_used_this_round;
	private int tiles_moved_this_round;
	private HexTile tile;
	
	//other things
	private Behaviour_type behaviour;
	
	//
	
	/*
	 * initialize for battle
	 */
	public void battle_begin() {
		health = 100;
		fatigue = 0;
		mana = 100;
		fear = 0;
		attacks_used_this_round = 0;
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
		exhaustion();
		attacks_used_this_round = 0;
		tiles_moved_this_round = 0;
	}
	
	/*
	 * gain fatigue modified by weight and already performed actions
	 */
	public void exhaustion() {
		//TODO
		fatigue += BattleCalculations.calc_movement_exhaustion(this);
		
	}
	
	
	/*
	 * attack enemy with main Hand weapon
	 */
	public boolean basic_attack_meele(BattleUnit target) {
		//TODO
		return false;
	}

	public boolean get_attacked_meele(BattleUnit attacker) {
		//TODO
		return false;
	}
	
	
	
	//HexTile_unit
	
	@Override
	public int getImageNumber() {
		return image_number;
	}

	@Override
	public HexTilePlayer getPlayer() {	
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

	public int getProtection() {
		return protection;
	}

	public void setProtection(int protection) {
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

	public int getAttacks_used_this_round() {
		return attacks_used_this_round;
	}

	public void setAttacks_used_this_round(int attacks_used_this_round) {
		this.attacks_used_this_round = attacks_used_this_round;
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
	
	
	
	
}
