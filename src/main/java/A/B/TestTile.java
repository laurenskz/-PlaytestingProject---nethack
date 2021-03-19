package A.B;


import java.awt.Color;

public class TestTile extends Tile{
	
	public TestTile(){
		super("T", Color.green);
	}
	
	public TestTile(int x, int y){
		super("T", Color.green, x, y);
	}
	
	public TestTile(int x, int y, boolean state){
		super("T", Color.red, x ,y)
;	}
}