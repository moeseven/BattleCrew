package gameLogic;


import java.io.Serializable;
import java.util.LinkedList;

public class Equipment implements Serializable{
	private Warrior hero;
	private Item potion=null;
	private Item hand1=null;
	private Item hand2=null;
	private Item hand1b=null;
	private Item hand2b=null;
	private Item body=null;
	private Item head=null;
	private Item ring1=null;
	private Item ring2=null;
	//1: Hand1  //2: Hand2  //3: BiHand //4: Body //5: Head //6:Ring //0: Consumable
	public Equipment(Warrior hero) {
		this.hero=hero;
	}
	public boolean equipItem(Item item) {
		boolean success=true;
		switch (item.getCategory()) {
			case 0:  equipPotion(item);break;
	        case 1:  equipHand1(item);break;
	        case 2:  equipHand2(item);break;
	        case 3:  equipBiHand(item);break;
	        case 4:  equipBody(item);break;
	        case 5:  equipHead(item);break;
	        case 6:  equipRing(item);break;
	        default: success=false;
			}
		return success;
	}

	public boolean unequipItem(Item item) {
		boolean success=true;
		switch (item.getCategory()) {
		case 0:  unequipPotion(); break;
        case 1:  unequipHand1();break;
        case 2:  unequipHand2();break;
        case 3:  unequipBiHand();break;
        case 4:  unequipBody();break;
        case 5:  unequipHead();break;
        case 6:  if (ring1==item) {
					unequipRing1();
				}else if (ring2==item) {
					unequipRing2();
				}break;      
        default: success=false;break;
		}
		return success;
	}
	public int getTotalWeight() {
		LinkedList<Item> list = getAllEquippedItems();
		int weight=0;
		for (int i = 0; i <list.size(); i++) {
			weight+=list.get(i).getWeight();
		}
		return weight;
	}
	public LinkedList<Item> getAllEquippedItems(){
		LinkedList<Item> allItems=new LinkedList<Item>();
		//only return items that are equipable to heroes dont return "teeth,claws" for example
		if(potion!=null&&potion.droppable) {
			allItems.add(potion);
		}
		if(hand1!=null&&hand1.droppable) {
			allItems.add(hand1);
		}		
		if(hand1!=hand2&&hand2!=null&&hand2.droppable) {
			allItems.add(hand2);
		}
		if(hand1b!=null&&hand1b.droppable) {
			allItems.add(hand1b);
		}		
		if(hand1b!=hand2b&&hand2b!=null&&hand2b.droppable) {
			allItems.add(hand2b);
		}
		if(head!=null&&head.droppable) {
			allItems.add(head);
		}
		if(body!=null&&body.droppable) {
			allItems.add(body);
		}
		if(ring1!=null&&ring1.droppable) {
			allItems.add(ring1);
		}
		if(ring2!=null&&ring2.droppable) {
			allItems.add(ring2);
		}
		return allItems;
	}
	public boolean drinkPotion() {
		if(potion!=null) {
			potion.mod(hero);
			potion=null;
			return true;
		}else {
			return false;
		}		
	}
	public void swapWeapons() {
		Item hand1_vol,hand2_vol;
		hand1_vol=hand1b;
		hand2_vol=hand2b;
		hand1b=hand1;
		hand2b=hand2;
		unequipHand1();
		unequipHand2();
		if (hand1_vol!=null) {
			equipHand1(hand1_vol);
		}
		if (hand2_vol!=null) {
			if(hand1_vol!=hand2_vol) {
				equipHand2(hand2_vol);
			}
		}		
	}
	//equip
	public void equipPotion(Item item) {
		if(item.getCategory()==0) {
			unequipPotion();
			hero.getPlayer().getInventory().remove(item);			
			potion=item;					
		}
	}
	
	public void equipHand1(Item item) {
		if(item.getCategory()==1) {
			unequipHand1();
			hero.getPlayer().getInventory().remove(item);			
			hand1=item;					
			item.mod(hero);
		}else {
			equipBiHand(item);
		}
	}
	public void equipHand2(Item item) {
		if(item.getCategory()==2) {
			unequipHand2();
			hand2=item;	
			hero.getPlayer().getInventory().remove(item);	
			item.mod(hero);	
		}else {
			equipBiHand(item);
		}

	}
	public void equipRing(Item item) {
		if(item.getCategory()==6) {
			if (ring1==null) {
				ring1=item;
				hero.getPlayer().getInventory().remove(item);
				item.mod(hero);
			}else if (ring2==null) {
				ring2=item;
				hero.getPlayer().getInventory().remove(item);	
				item.mod(hero);
			}else {
				//maybe unequip ring1 and replace
			}									
		}
			
	}
	public void equipBody(Item item) {
		if(item.getCategory()==4) {
			unequipBody();
			body=item;
			hero.getPlayer().getInventory().remove(item);	
			item.mod(hero);
		}
			
	}
	public void equipHead(Item item) {
		if(item.getCategory()==5) {
			unequipHead();
			head=item;	
			hero.getPlayer().getInventory().remove(item);	
			item.mod(hero);
		}
			
	}
	public void equipBiHand(Item item) {
		if(item.getCategory()==3) {
			unequipHand1();
			unequipHand2();
			hand1=item;
			hand2=item;
			hero.getPlayer().getInventory().remove(item);	
			item.mod(hero);
		}
			
	}
	//unequip
	public void unequipAll() {
		//!!! update this if a new item type is added
		unequipBiHand();
		unequipBody();
		unequipHand1();
		unequipHand2();
		unequipHead();
		unequipPotion();
		unequipRing1();
		unequipRing2();
	}
	public void unequipPotion() {
		if(potion!=null) {
			hero.getPlayer().getInventory().add(potion);
		}		
		potion=null;
	}
	public void unequipHand1() {
		if(hand1!=null) {
			hand1.demod(hero);
			hero.getPlayer().getInventory().add(hand1);		
			if(hand1==hand2) {//clean biHand removal
			hand2=null;
		}	
		}
	
		hand1=null;
	}
	public void unequipHand2() {
		if(hand2!=null) {
			hand2.demod(hero);
			hero.getPlayer().getInventory().add(hand2);		
			if(hand1==hand2) {//clean biHand removal
				hand1=null;
			}
		}		
		hand2=null;
	}
	public void unequipBiHand() {
		if(hand2!=null) {
			hand2.demod(hero);
			hero.getPlayer().getInventory().add(hand2);
		}
		hand1=null;		
		hand2=null;
	}
	public void unequipBody() {
		if(body!=null) {
			body.demod(hero);
			hero.getPlayer().getInventory().add(body);
		}		
		body=null;
	}
	public void unequipHead() {
		if(head!=null) {
			head.demod(hero);
			hero.getPlayer().getInventory().add(head);
		}		
		head=null;
	}
	public void unequipRing1() {
		if(ring1!=null) {
			ring1.demod(hero);
			hero.getPlayer().getInventory().add(ring1);			
		}	
		ring1=null;
	}
	public void unequipRing2() {
		if(ring2!=null) {
			ring2.demod(hero);
			hero.getPlayer().getInventory().add(ring2);			
		}	
		ring2=null;
	}
	//
	public Warrior getHero() {
		return hero;
	}
	public void setHero(Warrior hero) {
		this.hero = hero;
	}
	public Item getHand1() {
		return hand1;
	}
	public void setHand1(Item hand1) {
		this.hand1 = hand1;
	}
	public Item getHand2() {
		return hand2;
	}
	public void setHand2(Item hand2) {
		this.hand2 = hand2;
	}
	public Item getBody() {
		return body;
	}
	public void setBody(Item body) {
		this.body = body;
	}
	public Item getHead() {
		return head;
	}
	public void setHead(Item head) {
		this.head = head;
	}
	public Item getRing1() {
		return ring1;
	}
	public void setRing1(Item ring1) {
		this.ring1 = ring1;
	}
	public Item getRing2() {
		return ring2;
	}
	public void setRing2(Item ring2) {
		this.ring2 = ring2;
	}
	public Item getPotion() {
		return potion;
	}
	public void setPotion(Item potion) {
		this.potion = potion;
	}
	
}
