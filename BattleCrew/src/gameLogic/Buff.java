package gameLogic;

public class Buff{
	public String getName() {
		return name;
	}

	private String name;
	private int image;
	private int armor=0;
	private int defense=0;
	private int offense = 0;
	private int strength = 0;
	private int dexterity = 0;
	private int vitality = 0;
	private int thorns = 0;
	private int regen = 0;
	private int courage = 0;
	//
	public int duration = 0;
	public Buff(String[] stats, double factor, int duration) {
		name=stats[0];
		image = (int) (Integer.parseInt(stats[1])*factor);
		armor= (int) (Integer.parseInt(stats[2])*factor);
		defense= (int) (Integer.parseInt(stats[3])*factor);
		offense= (int) (Integer.parseInt(stats[4])*factor);
		thorns= (int) (Integer.parseInt(stats[5])*factor);
		strength= (int) (Integer.parseInt(stats[6])*factor);
		dexterity= (int) (Integer.parseInt(stats[7])*factor);
		vitality=(int) (Integer.parseInt(stats[8])*factor);
		regen=(int) (Integer.parseInt(stats[9])*factor);
		courage = (int) (Integer.parseInt(stats[10])*factor);
		//
		this.duration = duration;
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
			hero.setThorns(hero.getThorns()+factor*thorns);
			hero.setRegen(hero.getRegen()+factor*regen);
			hero.setCourage(hero.getCourage()+factor*courage);
	}
}
