package A.B;


import java.awt.Color;

public class Monster extends Mob{
	
	private int health, attackDmg;
	private boolean alive, waitTurn, seenPlayer;
	
	public Monster(int x, int y){
		super(x, y, "a", Color.red);
		
		alive = true;
		health = 2;//To be rand... ?
		attackDmg = 2;//To be rand... ? 
		waitTurn = true;
		seenPlayer = false;
		
		
	}
	
	public Monster(int x, int y, Color color){
		super(x, y, "B", color);
		
		alive = true;
		health = 10; //To be rand... ?
		attackDmg = 5;//To be rand... ? 
		waitTurn = false;
		seenPlayer = true;
	}
	
	public void takeDmg(int amount){
		health -= amount;
		if(health <= 0){
			health = 0;
			alive = false;
		}
	}
	
	public int getAttackDmg(){
		return attackDmg;
	}
	
	public boolean getAlive(){
		return alive;
	}
	
	public void setSeen(boolean state){
		seenPlayer = state;
	}
	
	public boolean getSeen(){
		return seenPlayer;
	}
	
	public boolean canAttackPlayer(int pX, int pY){
		int x = getX();
		int y = getY();
		
		if(x == pX && y == pY-1)
			return shouldAttackPlayer();
		else if(x == pX && y == pY+1)
			return shouldAttackPlayer();
		else if(x == pX+1 && y == pY)
			return shouldAttackPlayer();
		else if(x == pX-1 && y == pY)
			return shouldAttackPlayer();
		else{
			waitTurn = true;
			return false;
		}
	
	}
	
	private boolean shouldAttackPlayer(){
		return true;
		//Do i want the monster to wait before attacking the player? (once they get into range)
		/*
				if(waitTurn){
			waitTurn = false;
			return false;
		} else{
			waitTurn = true;
			return true;
		}
		*/
	}
	
	public boolean nearPlayer(int pX, int pY){
		
		int x = getX();
		int y = getY();
		
		if(x == pX && y == pY-1)
			return true;
		else if(x == pX && y == pY+1)
			return true;
		else if(x == pX+1 && y == pY)
			return true;
		else if(x == pX-1 && y == pY)
			return true;
		else
			return false;
		
	}
	
	public void changeX(int amount){
		x += amount;
	}
	
	public void changeY(int amount){
		y += amount;
	}
	
	
	
	
	
}