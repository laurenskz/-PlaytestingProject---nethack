public class HealthPotion extends Item{
	private int restoreAmt;
	
	public HealthPotion(){
		super(1);
		restoreAmt = 5;
	}
	
	public int getRestoreAmount(){
		return restoreAmt;
	}
	
	public String toString(){
		return super.toString() + "Health Potion";
	}
}