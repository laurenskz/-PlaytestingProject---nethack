import java.awt.Color;

public class FloorTile extends Tile{
	
	public FloorTile(){
		super(" ", Color.orange);
	}
	
	public FloorTile(int x, int y){
		super(" ", Color.orange, x, y);
	}
	
	public FloorTile(String image, Color color, int x, int y){
		super(image, color, x, y);
	}
}