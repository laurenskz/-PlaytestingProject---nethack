package A.B;

import java.awt.Color;

public abstract class Tile{
	
	public String image = "E";
	public String ID ;
	private Color color = Color.green;
	
	static final int BLOCK_SIZE = 10;
	public int x, y;
	
	boolean marked = false;
	public boolean getMark(){return marked;}
	public void setMark(boolean state){marked = state;}
	
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
		ID = "" + FreshIDGenerator.instance.getNextId() ;
	}
	
	
	public Tile(String image, Color color){
		this.image = image;
		this.color = color;
	}
	
	public Tile(String image, Color color, int x, int y){
		this.image = image;
		this.color = color;
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	public String getImage(){
		return image;
	}
	
	public Color getColor(){
		return color;
	}
	
	
	public void setImage(String image){
		this.image = image;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
}