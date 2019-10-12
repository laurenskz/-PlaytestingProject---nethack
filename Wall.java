import java.awt.Color;

public class Wall extends Tile{
	
	public Wall(){
		super("#", Color.white);
		
	}
	
	public Wall(int x, int y){
		super("#", Color.white, x, y);
	}
	
}