public class Food extends Item{
	private int restoreAmt;
	
	public Food(){
		super(1);
		restoreAmt = 3;
	}
	
	public int getRestoreAmount(){
		return restoreAmt;
	}
	
	public String toString(){
		return super.toString() + "Food";
	}
}