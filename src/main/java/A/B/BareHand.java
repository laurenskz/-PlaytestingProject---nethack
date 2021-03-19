package A.B;


public class BareHand extends Weapon{
	
	public BareHand(int dmg){
		super("Hands", dmg);
	}
	
	public String toString(){
		return super.toString() + "Bare Hands";
	}
	
}