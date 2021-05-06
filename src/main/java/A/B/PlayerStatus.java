package A.B;


import java.util.ArrayList;

public class PlayerStatus{
	
	public int health, maxHealth;
	private int mana, maxMana;
	public int gold;
	
	public boolean alive;
	
	public ArrayList<Item> inventory;
	public Weapon weap; //This will be the equipped weapon
	private int equippedIndex;
	
	public PlayerStatus(){			
		health = 10; 
		maxHealth = 10;
		
		mana = 10;
		maxMana = 10;
		
		alive = true;
		
		
		inventory = new ArrayList<Item>();
		inventory.add(new BareHand(1));
		
		weap = new BareHand(1);
		inventory.add(new HealthPotion());
		//inventory.add(new HealthPotion());

		inventory.add(new Food());
		inventory.add(new Water());


		//inventory.add(new HealthPotion());
			
		//inventory.add(new Sword("something",2));
//		inventory.add(new Bow("something else",3));
//		inventory.add(new Sword("something more more ",9));
//		inventory.add(new Sword("something more more additional ",4));
//		inventory.add(new Bow("something more more more else",6));
//		inventory.add(new Bow("something (this is the weapon with the highest damage in inventory)",11));
		inventory.add(new Sword("something more",1));


		//inventory.add(new Water());
		equippedIndex = 0;
	}
	
	public int getHealth(){return health;}
	public int getMana(){return mana;}
	public int getMaxMana(){return maxMana;}
	public int getMaxHealth(){return maxHealth;}
	
	public void reduceHealth(int amount){
		health -= amount;
		if(health < 0)
			health = 0;
	}
	public void gainHealth(int amount){
		health += amount;
		if(health > maxHealth)
			health = maxHealth;
	}
	public void increaseMaxHealth(int amount){
		maxHealth += amount;
	}
	
	public void decreaseMaxHealth(int amount){
		maxHealth -= amount;
	}
	
	
	
	public void reduceMana(int amount){
		mana -= amount;
	}
	public void gainLana(int amount){
		mana += amount;
		if(mana > maxMana)
			mana = maxMana;
	}
	public boolean checkManaCost(int cost){//True if it can be casted
		if(mana - cost >= 0)
			return true;
		else
			return false;
	}
	public void increaseMaxMana(int amount){
		maxMana += amount;
	}
	public void decreaseMaxMana(int amount){
		maxMana -= amount;
	}
	
	public int getInvSize(){
		return inventory.size();
	}
	
	public Item getInvItem(int i){
		return inventory.get(i);
	}
	
	public void addItem(Item item){
		inventory.add(item);
		//this.sort();
	}
	
	public void decreaseItemAmount(int itemIndex, int amount){
		inventory.get(itemIndex).decreaseAmount(amount);
		if(inventory.get(itemIndex).getAmount() == 0)
			inventory.remove(itemIndex);
	}

	public void setWeapon(Weapon weap){
		this.weap = weap;
	}
	
	public void setEquipped(int value){
		equippedIndex = value;
	}
	
	public int getEquippedIndex(){
		return equippedIndex;
	}
	
	public int getEquippedDmg(){
		return weap.getAttack();
	}
	
	public boolean getAlive(){
		return alive;
	}
	
	public void setAlive(boolean state){
		alive = state;
	}
	
	public void removeItem(int index){
		inventory.remove(index);
	}
	
//	private void sort(){
//		for(int i=0; i < inventory.size()-1; i++){
//			for(int j=i+1; j < inventory.size(); j++){
//				//THERES GOTTA BE A BETTER WAY TO DO THIS
//				if((inventory.get(i) instanceof Gold && inventory.get(j) instanceof Gold || (inventory.get(i) instanceof HealthPotion && inventory.get(j) instanceof HealthPotion))){
//					
//					inventory.get(i).addAmount(inventory.get(j).getAmount());
//					inventory.remove(j);
//					
//				}
//			}
//		}
//	}
	
	
	
}