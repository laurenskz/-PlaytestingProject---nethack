public class Bow extends Weapon{
	
	public Bow(String name, int dmg){
		super(name, dmg);
	}

	
	public String toString(){
		return super.toString() + "Bow of " + getName();
	}
}