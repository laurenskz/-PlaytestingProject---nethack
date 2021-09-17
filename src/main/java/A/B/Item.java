package A.B;


public class Item{
	public int amount;
	public String ID ;
	
	public Item(int amount){
		this.amount = amount;
		ID = "" + FreshIDGenerator.instance.getNextId() ;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void addAmount(int num){
		amount += num;
	}
	
	public void decreaseAmount(int num){
		amount -= num;
	}
	
	public String getAttackDmgString(){
		return "";
	}
	
	public String toString(){
		return amount + "x ";
	}
}