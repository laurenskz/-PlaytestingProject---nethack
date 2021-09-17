package A.B;


import java.awt.Color;

public class Mob extends Tile{

    public String ID ;
    
	public Mob(int x, int y, String image, Color color){
		super(image, color, x, y);
		ID = ""  + FreshIDGenerator.instance.getNextId() ;
	}
	
}