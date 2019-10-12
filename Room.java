import java.util.ArrayList;

public class Room{
	
	private int startX, startY, sizeX, sizeY;
	private ArrayList<Integer> connections;
	public Room(int x, int y, int sizeX, int sizeY){
		startX = x;
		startY = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		connections = new ArrayList<>();
	}
	
	
	public int getX(){
		return startX;
	}
	
	public int getY(){
		return startY;
	}
	
	public int getSizeX(){
		return sizeX;
		}
	
	public int getSizeY(){
		return sizeY;
	}
	
	public ArrayList<Integer> getConnections(){
		return connections;
	}
}