public class Sword extends Weapon{
	
	public Sword(String name, int dmg){
		super(name, dmg);
	}
	
	public String toString(){
		return super.toString() + "Sword of " + getName();
	}
	
}