public class Item{
	private int amount;
	
	public Item(int amount){
		this.amount = amount;
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