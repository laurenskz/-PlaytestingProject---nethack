import java.awt.Color;

public class FloorTile extends Tile{
	
	public FloorTile(){
		super(" ", Color.orange); // monster's path
	}
	
	public FloorTile(int x, int y){
		super(" ", Color.orange, x, y); // every tile in map / clears when monsters step on it
	}
	
	public FloorTile(String image, Color color, int x, int y){
		super(image, color, x, y);
	}
}