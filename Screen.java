import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Screen extends JPanel implements KeyListener{
	
	private Tile[][] tiles;
	private Font tileFont, testFont, invFont, consoleFont, menuFont, controlFont;
	private ArrayList<Room> roomArr;
	private Player p1;
	private PlayerStatus ps;
	private int stairX, stairY; //Feel like this should be in the stair class?
	private int p1SpawnX, p1SpawnY;
	private Color outlineGray, gameAreaOutline;
	private Color healthRed, manaBlue;
	private Color invScreenBG, invScreenHighlight, invScreenEquipped;
	private Color consoleGreen;
	private Color bgColor;
	private boolean inventoryScreen, mainMenu, revealControls;
	private ArrayList<ItemTile> items;
	private WeaponDictonary weapDict;
	private ArrayList<Monster> mobs;
	private int invHighlighted, menuHighlighted;
	private int level;
	private boolean playerTurn, aimingBow;
	private BottomBar bb;
	private MusicPlayer mp;
	
	private int bgRed, bgGreen, bgBlue;
	private int goalRed, goalGreen, goalBlue;
	
	
	public Screen(){
		tiles = new Tile[90][50];
		
		roomArr = new ArrayList<Room>();
		ps = new PlayerStatus();
		items = new ArrayList<ItemTile>();
		weapDict = new WeaponDictonary();
		mobs = new ArrayList<Monster>();
		bb = new BottomBar();
		
		mp = new MusicPlayer();
		//mp.start(); //Need to change the music as i dont like stealing :/
		
		level = 0;
		
		menuHighlighted = 0;
		mainMenu = true;
		
		this.mapSetUp();
		
		//YOU CAN USE ASCII CHARACTERS TOOOO!!! (kinda)
		
		//outlineGray = new Color(152, 152, 152);
		gameAreaOutline = new Color(255, 255, 255);
		healthRed = new Color(249, 45, 14);
		manaBlue = new Color(22, 51, 245);
		invScreenBG = new Color(0.30196f, 0.30196f, 0.30196f, 0.8f);
		invScreenHighlight = new Color(0.69803f, 0.69803f, 0.69803f, 0.9f);
		invScreenEquipped = new Color(1.0f, 1.0f, 0.0f, 0.9f);
		consoleGreen = new Color(0, 255, 26);
		
		

		bgRed = ((int)(Math.random() * 255));
		bgGreen = ((int)(Math.random() * 255));
		bgBlue = ((int)(Math.random() * 255));
		
		goalRed = ((int)(Math.random() * 255));
		goalGreen = ((int)(Math.random() * 255));
		goalBlue = ((int)(Math.random() * 255));

		
		tileFont = new Font("Arial", Font.PLAIN, 10);
		testFont = new Font("Arial", Font.BOLD, 15);
		invFont = new Font("Times New Roman", Font.BOLD, 20);//Should mess around with font type
		consoleFont = new Font("Courier New", Font.BOLD, 15);
		menuFont = new Font("Arial", Font.BOLD, 50);
		controlFont = new Font("Arial", Font.BOLD, 20);
		
		playerTurn = true;
		
		addKeyListener(this);
		
	}
	
	public void addNotify(){
		super.addNotify();
		requestFocus();
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(1000, 600);
	}
	
	public void paintComponent(Graphics gr){
		super.paintComponent(gr);
		
		gr.setColor(Color.black);
		gr.fillRect(0, 0, 1000, 600);
		
		//############### Inventory Screen #################################
		if(mainMenu){
			
			
			//Drawing the everchanging background gradiant
			
			if(bgRed == goalRed && bgGreen == goalGreen && bgBlue == goalBlue){
				goalRed = ((int)(Math.random() * 255));
				goalGreen = ((int)(Math.random() * 255));
				goalBlue = ((int)(Math.random() * 255));
			}
			
			gr.setColor(new Color(bgRed, bgGreen, bgBlue));
			gr.fillRect(0, 0, 1000, 600);
			
			if(bgRed < goalRed)
				bgRed++;
			else if(bgRed > goalRed)
				bgRed--;
			
			if(bgGreen < goalGreen)
				bgGreen++;
			else if(bgGreen > goalGreen)
				bgGreen--;
			
			if(bgBlue < goalBlue)
				bgBlue++;
			else if(bgBlue > goalBlue)
				bgBlue--;
			
			gr.setColor(invScreenHighlight);
			gr.fillRect(50, 50+(100 * menuHighlighted), 300, 75);
			
			gr.setColor(Color.white);
			gr.setFont(menuFont);
			gr.drawString("New Game", 50, 100);
			gr.drawString("Controls", 50, 200);
			gr.drawString("Credits", 50, 300);
			
			
			gr.setFont(controlFont);
			gr.setColor(Color.white);
			
			
			
			if(revealControls){

				
				gr.drawString("Arrow keys to move or select items.", 550, 400);
				gr.drawString("i to open up your inventory.", 550, 420);
				gr.drawString("enter to pick up items you are standing on.", 550, 440);
				gr.drawString("w to wait a turn.", 550, 460);
				gr.drawString("shift to ready/lower your bow.", 550, 480);
				gr.drawString("backspace to delete the selected item.", 550, 500);
			} else 
				gr.drawString("Use the up and down keys and select with enter!", 50, 590);
			
			

			
		} else { //############### Gameplay Painting #################################
				
			for(int i =0; i < items.size(); i++){
				tiles[items.get(i).getX()][items.get(i).getY()] = items.get(i);
			}
			
			if(stairX != -1 && stairY != -1)
				tiles[stairX][stairY] = new StairTile(stairX, stairY);			
			
			for(int i=0; i < mobs.size(); i++){
				if(mobs.get(i).getAlive())
					tiles[mobs.get(i).getX()][mobs.get(i).getY()] = mobs.get(i);
			}
			
			tiles[p1.getX()][p1.getY()] = p1;
			
			for(int i =0; i < tiles.length; i++){
				for(int j =0; j < tiles[i].length; j++){
					gr.setColor(tiles[i][j].getColor());
					gr.drawString(tiles[i][j].getImage(), i*10+50, j *10+10);
				}
			}
			
			int grayR=150, grayG=150, grayB=150;
			// Drawing the thick bar around chat/msg area (w/t gradiant)
			for(int i = 0; i < 10; i++){ 
				gr.setColor(new Color(grayR, grayG, grayB));
				gr.drawRect(0+i, 500+i, 1000-i*2, 100-i*2);
				grayR -= 5;
				grayG -= 5;
				grayB -= 5;
			}
			
			//Drawing the messgaes o the bottom bar
			gr.setColor(consoleGreen);
			gr.setFont(consoleFont);
			for(int i =0; i < 4; i++){
				gr.drawString(bb.getMessage(i), 15, 525+(20*i));
			}
			

			//############### Inventory Screen #################################
			if(inventoryScreen){
				//Drawing the main frame of the invScreen (BG)
				gr.setColor(invScreenBG);
				gr.fillRect(60, 10, 880, 480);
				

				//Highlighting equipped weapon
				gr.setColor(invScreenEquipped);
				gr.fillRect(65, (25*ps.getEquippedIndex()) + 20, 300, 20);
				
				//Highlighting the currently selected item
				gr.setColor(invScreenHighlight);
				gr.fillRect(65, (25*invHighlighted) + 20, 300, 20);
				
				gr.setColor(Color.white);
				gr.setFont(invFont);
				
				int yOffset = 0;
				String toDraw = "";
				for(int i =0; i < ps.getInvSize(); i++){
					//Is there a better way to cast thing back down to their org object type?
					Item item = ps.getInvItem(i);			
					gr.drawString(item.toString() + item.getAttackDmgString(), 70, 35+yOffset);
					
					yOffset += 25;
				}
				
			}
			
			//Drawing outlines of the rooms
			/*
			gr.setColor(Color.white);
			for(int i =0; i < roomArr.size(); i++){
				gr.drawRect((roomArr.get(i).getX()*10+50), (roomArr.get(i).getY()*10), (roomArr.get(i).getSizeX()*10), (roomArr.get(i).getSizeY()*10));
			}
			*/
			
			//Seperating the bottom bar into halves
			gr.setColor(Color.green);
			gr.fillRect(510, 510, 481, 81);
			
			gr.setColor(Color.black);
			gr.drawString(level + "", 520, 520);

			
			
			//Drawing p1's health bar (left side)
			gr.setColor(healthRed);
			gr.fillRect(0, 0, 48, 500);//Left Side
			
			gr.setColor(Color.black);
			gr.fillRect(0, 0, 48, (500/ps.getMaxHealth()) * (ps.getMaxHealth() - ps.getHealth()));
			
			
			//Drawing p1's mana bar (right side) -- color should change depending on player class
			gr.setColor(manaBlue);
			gr.fillRect(950, 0, 50, 500); //Right Side
			
			gr.setColor(Color.black);
			gr.fillRect(950, 0, 50, (500/ps.getMaxMana()) * (ps.getMaxMana() - ps.getMana()));
			
			
			
			//Drawing the outline of the game area
			gr.setColor(gameAreaOutline);
			gr.drawRect(48, 0, 902, 499);
		}
		
	}
	
	private void mapSetUp(){
		
		level++;
		
		//Clearing the tiles array
		for(int i=0; i < tiles.length; i++){
			for(int j =0; j  < tiles[i].length; j++){
				tiles[i][j] = new Wall();
			}
		}
		
		//Setting the tile's x & y
		for(int i=0; i < tiles.length; i++){
			for(int j =0; j  < tiles[i].length; j++){
				tiles[i][j].setX(i);
				tiles[i][j].setY(j);
			}
		}
		
		
		//Clearing roomArr & mobs lists & item lists
		roomArr.clear();
		mobs.clear();
		items.clear();
			
		 
		 if(level%5 == 0){
			 
			 
			 p1 = new Player(20, 20);
			 roomArr.add(new Room(10, 10, 30, 30));
			 
			 for(int i =10; i < 40; i++){
				 for(int j = 10; j < 40; j++){
				 	 tiles[i][j]=new FloorTile(i, j);
				 }
			 }
			 
			 
			 int randX =0, randY = 0;
			do{
				do{
					randX = (int) (Math.random() * 90);
					randY = (int) (Math.random() * 50);
				} while(!(tiles[randX][randY] instanceof FloorTile && !(tiles[randX][randY] instanceof Player)));
			} while(!isInRoom(randX, randY));
			 
			 mobs.add(new Boss(randX, randY));
			 
			 stairX = -1;
			 stairY = -1;
			 
		 } else {
			
			
			//Genning rooms
			for(int i =0; i < 15; i++){
				this.roomGen();
			}
			
			//Connecting Rooms with rand hallways
			this.hallwaySetUp();
			
			/*
			//Placing Wall tiles around all the FloorTile 
			for(int i =1; i < tiles.length-1; i++){
				for(int j =1; j < tiles[i].length-1; j++){
					if(tiles[i][j] instanceof BlankTile){
						
						if(tiles[i-1][j] instanceof FloorTile)
							tiles[i][j] = new Wall(i, j);
						else if(tiles[i+1][j] instanceof FloorTile)
							tiles[i][j] = new Wall(i, j);
						else if(tiles[i][j-1] instanceof FloorTile)
							tiles[i][j] = new Wall(i, j);
						else if(tiles[i][j+1] instanceof FloorTile)
							tiles[i][j] = new Wall(i, j);
					}
				}
			}
			*/
			
			
			//Placing Wall tiles around the FloorTiles
			for(int i =0; i < tiles.length; i++){
				for(int j =0; j < tiles[i].length; j++){
					if(i == 0){
						if(tiles[i+1][j] instanceof FloorTile)
							tiles[i][j] = new Wall(i, j);
					} else if(i == 89){
						if(tiles[i-1][j] instanceof FloorTile)
							tiles[i][j] = new Wall(i, j);
					} else if( j ==0){
						if(tiles[i][j+1] instanceof FloorTile)
							tiles[i][j] = new Wall(i, j);
					} else if(j == 49){
						if(tiles[i][j-1] instanceof FloorTile)
							tiles[i][j] = new Wall(i, j);
					}	
				}
			}
			
			
			
			//Placing the stairs to the next level
			int randX, randY;
			
			do{
				do{
					randX = (int) (Math.random() * 90);
					randY = (int) (Math.random() * 50);
				} while(!(tiles[randX][randY] instanceof FloorTile));
			} while(!isInRoom(randX, randY));
			
			tiles[randX][randY] = new StairTile(randX, randY);
			stairX = randX;
			stairY = randY;
			
			//Placing the player in a room too
			do{
				do{
					randX = (int) (Math.random() * 90);
					randY = (int) (Math.random() * 50);
				} while(!(tiles[randX][randY] instanceof FloorTile));
			} while(!isInRoom(randX, randY));
			
			p1 = new Player(randX, randY);
			
			//Gen & placing random items
			for(int i =0; i < ((int)(Math.random() * 140 + 2)); i++){
				//System.out.println("" + i);
				do{
					do{
						randX = (int) (Math.random() * 90);
						randY = (int) (Math.random() * 50);
					} while(!(tiles[randX][randY] instanceof FloorTile));
				} while(!isInRoom(randX, randY));
				
				Item item = this.genItem();
				//System.out.println(item + "1");
				tiles[randX][randY] = new ItemTile(item, randX, randY);
				items.add(new ItemTile(item, randX, randY));
			}
			
			//Gen & placing random mobs
			for(int i =0; i < ((int)(Math.random() * 14 + 2)); i++){ 
				
				do{
					
					do{
						randX = (int) (Math.random() * 90);
						randY = (int) (Math.random() * 50);
					} while(!(tiles[randX][randY] instanceof FloorTile));
				} while(!isInRoom(randX, randY));
				

				
				mobs.add(new Monster(randX, randY));
			}
			
			for(int i =0; i < ((int)(Math.random() * 6)); i++){ 
					
				do{
					
					do{
						randX = (int) (Math.random() * 90);
						randY = (int) (Math.random() * 50);
					} while(!(tiles[randX][randY] instanceof FloorTile));
				} while(isInRoom(randX, randY));
				

				
				mobs.add(new Monster(randX, randY));
			}
			
		 }
	}
	
	private Item genItem(){
		int rand = (int) (Math.random() * 3 +1); //Come back and update this prob once i add more item
		
		
		Item toReturn = new Sword("ERROR", -1);
		
		switch(rand){
			case 1: //Gold
				int amount = (int)(Math.random() * 451 + 50);
				while(amount % 5 != 0)
					amount--;
				
				toReturn = new Gold(amount);
				break;
			
			case 2://Sword || Bow
				if(((int)(Math.random() * 101)) < 50)
					toReturn = new Sword(weapDict.getRandName(), ((int)(Math.random() * 11)));
				else
					toReturn = new Bow(weapDict.getRandName(), ((int)(Math.random() * 4 + 4)));
					
				break;
			
			case 3://HealthPot?
					toReturn = new HealthPotion();
				break;
				
			default:
				System.out.println("4");
				toReturn = new Item(1);
				break;
		}
		
		return toReturn;
	}
	
	private void hallwaySetUp(){
		/*
		//Plan of Action:
		//Start at roomArr.get(0), detect the closest room (use just the startX and startY of the rooms)
		//Detect the closest facing walls of each other
		//Place floot tiles connecting them (tunneling using a simple algorithm)
		*/
		
		
		
		
		//Connecting closest rooms together
		for(int i =0; i < roomArr.size(); i++){
			Room r1 = roomArr.get(i);
			Room r2 = roomArr.get(this.getClosest(r1, i));
			
			roomConnector(r1, r2);
		}
		
		
		//Creating Random walkways between random rooms
		
		/*
		//Connecting Random Room to Random Room
		for(int i =0; i < roomArr.size(); i++){
			Room r1 = roomArr.get(((int)(Math.random() * roomArr.size())));
			Room r2 = roomArr.get(((int)(Math.random() * roomArr.size())));
			
			roomConnector(r1, r2);
		}
		*/
		
		//Connecting every room to a random room
		for(int i =0; i < roomArr.size(); i++){
			Room r1 = roomArr.get(i);
			Room r2 = roomArr.get((int)(Math.random() * roomArr.size()));
			
			roomConnector(r1, r2);
		}
		
		
		
	}
	
	private void buildWalkway(int x1, int y1, int x2, int y2){
		tiles[x1][y1] = new TestTile(x1, y1);
		tiles[x2][y2] = new TestTile(x2, y2, true);
		
		boolean working = true;
		int currX = x1, currY = y1;
		
		
		do{
			tiles[currX][currY] = new FloorTile(currX, currY);
			
			if(currY > y2)
				currY--;
			else if(currY < y2)
				currY++;
			else if(currX > x2)
				currX--;
			else if(currX < x2)
				currX++;
			else if(currX == x2 && currY == y2)
				working = false;
			else{
				System.out.println("ERROR");
				//System.exit(-1);
			}
		} while(working);
	}
	
	private void roomConnector(Room r1, Room r2){
			//TODO : Loop this part until it gets connects to a room with no connections (arraylist within the room class), or until every room has a connection (where you then just do a final connection?)
			
			
			int room1X = r1.getX();
			int room2X = r2.getX();
			
			int room1Y = r1.getY();
			int room2Y = r2.getY();
			
			int room1Range, room2Range;
			
			//Connecting the two rooms with a path of FloorTile
			
			//Find the the direction of r1 to r2
			
			
			int direction;
			
			int deltaX = room1X - room2X;
			int deltaY = room1Y - room2Y;
			
			
			int x1, y1, x2, y2;
			
			if(deltaX < 0){  //room1X is less than room2X -- r2 is to the right of r1
				
				if(deltaY < 0){//room1Y is less than room2Y -- r2 is below r1
					if(deltaX > deltaY){ //the change in x is greater than the change in y -- r2 is more side-to-side of r1
						
						
						
						x1 = room1X + r1.getSizeX()-2;
						x2 = room2X;
						
						y1 = ((int)(Math.random() * r1.getSizeY() + room1Y));
						y2 = ((int)(Math.random() * r2.getSizeY() + room2Y));
						
					} else{
						
						
						y1 = room1Y+r1.getSizeY()-2;
						y2 = room2Y;
						
						x1 = ( (int)(Math.random() * r1.getSizeX() + room1X));
						x2 = ( (int)(Math.random() * r2.getSizeX() + room2X));
						
						
						
					}	
				} else{
					
					if(deltaX > deltaY){
						
						
						x1 = room1X + r1.getSizeX()-2;
						x2 = room2X;
						
						y1 = ((int)(Math.random() * r1.getSizeY() + room1Y));
						y2 = ((int)(Math.random() * r2.getSizeY() + room2Y));
					} else {
						
						
						y1 = room1Y;
						y2 = room2Y + r2.getSizeY()-2;
						
						x1 = ( (int)(Math.random() * (r1.getSizeX()) + room1X));
						x2 = ( (int)(Math.random() * (r2.getSizeX()) + room2X));
					}
					
					
				}
			} else{ //room1X is greater than room2X -- r2 is to the left of r1
				if(deltaY < 0){
					
					if(deltaX > deltaY){
						
						x1 = room1X;
						x2 = room2X + r2.getSizeX()-2;
						
						y1 = ((int)(Math.random() * (r1.getSizeY()-1) + room1Y+1));
						y2 = ((int)(Math.random() * (r2.getSizeY()-1) + room2Y+1));
						
					} else {
						
						
						y1 = room1Y+r1.getSizeY()-2;
						y2 = room2Y;
						
						x1 = ( (int)(Math.random() * r1.getSizeX() + room1X));
						x2 = ( (int)(Math.random() * r2.getSizeX() + room2X));
						
						
					}
					
					
				} else{
					
					if(deltaX > deltaY){
						
						x1 = room1X;
						x2 = room2X + r2.getSizeX()-2;
						
						y1 = ((int)(Math.random() * r1.getSizeY() + room1Y));
						y2 = ((int)(Math.random() * r2.getSizeY() + room2Y));
						
						
					} else {
						
						y1 = room1Y;
						y2 = room2Y + r2.getSizeY()-2;
						
						x1 = ( (int)(Math.random() * r1.getSizeX() + room1X));
						x2 = ( (int)(Math.random() * r2.getSizeX() + room2X));
					}
					
				}
			}
			
	
			
			this.buildWalkway(x1, y1, x2, y2);
	}
	
	private int getClosest(Room room, int pos){
		double minDistance = 100;
		int minPos = -1;
		
		
		int roomX = room.getX();
		int roomY = room.getY();
		Room compRoom;
		int compRoomX, compRoomY;
		double distance=0;
		
		for(int i =0; i < roomArr.size(); i++){
			if(pos != i){
			compRoom = roomArr.get(i);
			
			compRoomX = compRoom.getX();
			compRoomY = compRoom.getY();
			distance = Math.sqrt((Math.pow(Math.abs(compRoomX-roomX), 2) + Math.pow(Math.abs(compRoomY-roomY), 2)));
			
			
			if(distance < minDistance){
				minDistance = distance;
				minPos = i;
			}
			}
		}
		//System.out.println("R2: " + minPos);
		//System.out.println("Distance: " + distance);
		//System.out.println();
		
		return minPos;
		
		
	}
	
	private boolean roomCollision(Room temp){ //Return true if the room overlaps another
		int x = temp.getX();
		int y = temp.getY();
		int sizeX = temp.getSizeX();
		int sizeY = temp.getSizeY();
		
		for(int i =0; i < roomArr.size(); i++){
			Room room = roomArr.get(i);
			if(x < room.getX() + room.getSizeX() && x+sizeX > room.getX()){
				if(y < room.getY() + room.getSizeY() && y+sizeY > room.getY())
				{
					//System.out.println("true");
					return true;
				}
			}
			
			
		}
		//System.out.println("false");
		return false;
	}
	
	private void roomGen(){
		
		Room temp;
		int sizeX, sizeY, startX, startY;
		do{
		
		
		sizeX = (int)(Math.random() * 16 + 3);
		sizeY = (int)(Math.random() * 16 + 3);

		startX = (int)(Math.random() * (90-sizeX));
		startY = (int)(Math.random() * (50-sizeY));
		
		temp = new Room(startX, startY, sizeX, sizeY);
		
		} while(this.roomCollision(temp));
		
		roomArr.add(temp);
		
		for(int i =startX; i < startX+sizeX; i++){
			for(int j = startY; j < startY+sizeY; j++){
				tiles[i][j]=new FloorTile(i, j);
			}
		}
		
		
		

	}
	
	private boolean isInRoom(int x, int y){
		for(int i =0; i < roomArr.size(); i++){
			if(x > roomArr.get(i).getX() && x < roomArr.get(i).getX() + roomArr.get(i).getSizeX()){
				if(y > roomArr.get(i).getY() && y < roomArr.get(i).getY() + roomArr.get(i).getSizeY()){
					return true;
				}
			}
		}
	
		return false;
	}
	
	private void attackMonster(int x, int y){
		
		int index =0;
		boolean wasAlive = false;
		for(int i = 0; i < mobs.size(); i++){
			if(mobs.get(i).getX() == x && mobs.get(i).getY() == y){
				wasAlive = mobs.get(i).getAlive();
				index = i;
				break;
			}
		}
		
		mobs.get(index).takeDmg(ps.getEquippedDmg());
		bb.addMessage("You attack the monster for " + ps.getEquippedDmg() + " damage.");
		if(mobs.get(index).getAlive() == false && wasAlive == true){
			bb.addMessage("You slay the monster.");
			mobs.get(index).setImage("");
			//random chance for the monster to drop something
			if(((int)(Math.random() * 100) < 35))
				items.add(new ItemTile(this.genItem(), mobs.get(index).getX(), mobs.get(index).getY()));
			else
				tiles[x][y] = new FloorTile();
		}
			
		
	}
	
	private boolean inSameRoom(Monster mob, Player player){
		int mobX = mob.getX(), mobY = mob.getY();
		int playerX = player.getX(), playerY = player.getY();
		int playerRoom =-1, mobRoom=-2;
		
		if(!(isInRoom(playerX, playerY) && isInRoom(mobX, mobY)))
			return false;
		
		for(int i =0; i < roomArr.size(); i++){
			if(mobX > roomArr.get(i).getX() && mobX < roomArr.get(i).getX() + roomArr.get(i).getSizeX()){
				if(mobY > roomArr.get(i).getY() && mobY < roomArr.get(i).getY() + roomArr.get(i).getSizeY()){
					mobRoom = i;
				}
			}
		}
		
		
		for(int i =0; i < roomArr.size(); i++){
			if(playerX > roomArr.get(i).getX() && playerX < roomArr.get(i).getX() + roomArr.get(i).getSizeX()){
				if(playerY > roomArr.get(i).getY() && playerY < roomArr.get(i).getY() + roomArr.get(i).getSizeY()){
					playerRoom = i;
				}
			}
		}
		return playerRoom == mobRoom;
	}
	
	private boolean noMobs(int x, int y){
		for(int i =0; i < mobs.size(); i++){
			if(mobs.get(i).getAlive() && mobs.get(i).getX() == x && mobs.get(i).getY() == y)
				return false;
		}
		
		return true;
	}
	
	private void fireBow(int x, int y, int direction){
		//1 - Up, 2 - Right, 3 -Down, 4 - Left
		do{
			switch(direction){
				
				case 1:
					y--;
					break;
				
				case 2:
					x++;
					break;
					
				case 3:
					y++;
					break;
				
				case 4:
					x--;
					break;
				
				default:
					break;
			}
		} while(tiles[x][y] instanceof FloorTile || tiles[x][y] instanceof ItemTile);
			
		if(tiles[x][y] instanceof Monster){
			bb.addMessage("You shoot hits your mark.");
			this.attackMonster(x, y);
			
		} else if(tiles[x][y] instanceof Wall){
			bb.addMessage("You missed.");
			
		}
	}
	
	public void animate(){
		while(true){
			
			try{ 
				Thread.sleep(10); 
			}catch(InterruptedException ex){ 
				Thread.currentThread().interrupt(); 
			}
			
			if(p1.getX() == stairX && p1.getY() == stairY){
				
				bb.addMessage("You ascend the stairs.");
				tiles[p1.getX()][p1.getY()] = new FloorTile(p1.getX(), p1.getY());
				this.mapSetUp();
				
			} 
			
			//Monster's turn
			if(!playerTurn){
				for(int i =0; i < mobs.size(); i++){
					if(mobs.get(i).getAlive() && mobs.get(i).canAttackPlayer(p1.getX(), p1.getY())){
						bb.addMessage("The monster attacks you for " + mobs.get(i).getAttackDmg() + " health.");
						ps.reduceHealth(mobs.get(i).getAttackDmg());
						if(ps.getHealth() == 0){
							bb.addMessage("You have perished.");
							ps.setAlive(false);
						}
					}
					else if(mobs.get(i).nearPlayer(p1.getX(), p1.getY()))
						continue;
					else if(inSameRoom(mobs.get(i), p1)){
						//Make mob move
						
						mobs.get(i).setSeen(true);
						
						
						tiles[mobs.get(i).getX()][mobs.get(i).getY()] = new FloorTile();
					
						if(((int)(Math.random() * 100 + 1)) < 50){
							if(noMobs(mobs.get(i).getX()-1, mobs.get(i).getY()) && mobs.get(i).getX() > p1.getX())
								mobs.get(i).changeX(-1);
							else if(noMobs(mobs.get(i).getX()+1, mobs.get(i).getY()) && mobs.get(i).getX() < p1.getX())
								mobs.get(i).changeX(1);
							else if(noMobs(mobs.get(i).getX(), mobs.get(i).getY()-1) && mobs.get(i).getY() > p1.getY())
								mobs.get(i).changeY(-1);
							else if(noMobs(mobs.get(i).getX(), mobs.get(i).getY()+1) && mobs.get(i).getY() < p1.getY())
								mobs.get(i).changeY(1); 
						} else {
							
							if(noMobs(mobs.get(i).getX(), mobs.get(i).getY()-1) && mobs.get(i).getY() > p1.getY())
								mobs.get(i).changeY(-1);
							else if(noMobs(mobs.get(i).getX(), mobs.get(i).getY()+1) && mobs.get(i).getY() < p1.getY())
								mobs.get(i).changeY(1); 
							else if(noMobs(mobs.get(i).getX()-1, mobs.get(i).getY()) && mobs.get(i).getX() > p1.getX())
								mobs.get(i).changeX(-1);
							else if(noMobs(mobs.get(i).getX()+1, mobs.get(i).getY()) && mobs.get(i).getX() < p1.getX())
								mobs.get(i).changeX(1);
							

						}
					} else if(mobs.get(i).getSeen()){
						
						//random chance to stop following
						
						//Moves towards player
						int x = mobs.get(i).getX();
						int y = mobs.get(i).getY();
						
						tiles[x][y] = new FloorTile();
						
						if(((int)(Math.random() * 100)) < 50){
							
							if(x > p1.getX() && !(tiles[x-1][y] instanceof Wall || tiles[x-1][y] instanceof Player)){
								x--;
							} else if(x < p1.getX() && !(tiles[x+1][y] instanceof Wall || tiles[x+1][y] instanceof Player)){
								x++;
							} else if(y > p1.getY() && !(tiles[x][y-1] instanceof Wall || tiles[x][y-1] instanceof Player)){
								y--;
							} else if(y < p1.getY() && !(tiles[x][y+1] instanceof Wall || tiles[x][y+1] instanceof Player)){
								y++;
							}
						} else{
							
							if(y > p1.getY() && !(tiles[x][y-1] instanceof Wall || tiles[x][y-1] instanceof Player)){
								y--;
							} else if(y < p1.getY() && !(tiles[x][y+1] instanceof Wall || tiles[x][y+1] instanceof Player)){
								y++;
							}
							else if(x > p1.getX() && !(tiles[x-1][y] instanceof Wall || tiles[x-1][y] instanceof Player)){
								x--;
							} else if(x < p1.getX() && !(tiles[x+1][y] instanceof Wall || tiles[x+1][y] instanceof Player)){
								x++;
							} 
							
						}
						
						mobs.get(i).setX(x);
						mobs.get(i).setY(y);
				
					} else if(mobs.get(i).getSeen() == false){
						
						int x = mobs.get(i).getX();
						int y = mobs.get(i).getY();
						
						for(int j =0; j < 6; j++){
							if(tiles[x+j][y] instanceof Wall)
								break;
							if(tiles[x+j][y] instanceof Player)
								mobs.get(i).setSeen(true);
						}
						for(int j =0; j < 6; j++){
							if(tiles[x-j][y] instanceof Wall)
								break;
							if(tiles[x-j][y] instanceof Player)
								mobs.get(i).setSeen(true);
						}
						for(int j =0; j < 6; j++){
							if(tiles[x][y+j] instanceof Wall)
								break;
							if(tiles[x][y+j] instanceof Player)
								mobs.get(i).setSeen(true);
						}
						for(int j =0; j < 6; j++){
							if(tiles[x][y-j] instanceof Wall)
								break;
							if(tiles[x][y-j] instanceof Player)
								mobs.get(i).setSeen(true);
						}
							
						
						
					}
				}
				
				playerTurn = !playerTurn;
			}
			
			
			for(int i =0; i < mobs.size(); i++){
				if(mobs.get(i).getAlive() == false){
					if(mobs.get(i) instanceof Boss){
						stairX = mobs.get(i).getX();
						stairY = mobs.get(i).getY();
					}
					
					mobs.remove(i);
					i--;
				}
			}

			
			
			
			
			repaint();
		}
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		int x= p1.getX();
		int y = p1.getY();
		
		if(!playerTurn)
			return;
		
		if(mainMenu){
			switch(key){
				case 40://Down... ?
					menuHighlighted++;
					if(menuHighlighted > 2)
						menuHighlighted = 2;
					break;
				 
				case 38: //Up
					menuHighlighted--;
					if(menuHighlighted < 0)
						menuHighlighted = 0;
					break;
				
				case 10://Enter key
					if(menuHighlighted == 1)
						revealControls = true;
					else if(menuHighlighted == 0){
						mainMenu = false;
						//pickCharacter = true;
						
					}
					
					break;
					
				default:
					break;
			}
		
		
		} else if(ps.getAlive() && !inventoryScreen && !aimingBow){
			
			switch(key){
			case 38://Up
				if(tiles[x][y-1] instanceof FloorTile || tiles[x][y-1] instanceof ItemTile){
					tiles[x][y] = new FloorTile(x, y);
					p1.moveUp();
				} else if(tiles[x][y-1] instanceof Monster)
					attackMonster(x, y-1);
				
				playerTurn = !playerTurn;
				if(tiles[x][y-1] instanceof Wall)
					playerTurn = true;
				
				break;
			
			case 40://Down
				if(tiles[x][y+1] instanceof FloorTile || tiles[x][y+1] instanceof ItemTile){
					tiles[x][y] = new FloorTile(x, y);
					p1.moveDown();
				} else if(tiles[x][y+1] instanceof Monster)
					attackMonster(x, y+1);
				
				playerTurn = !playerTurn;
				if(tiles[x][y+1] instanceof Wall)
					playerTurn = true;
				
				break;
			
			case 37://Left
				if(tiles[x-1][y] instanceof FloorTile || tiles[x-1][y] instanceof ItemTile){
					tiles[x][y] = new FloorTile(x, y);
					p1.moveLeft();
				} else if(tiles[x-1][y] instanceof Monster)
					attackMonster(x-1, y);		
				
				playerTurn = !playerTurn;
				if(tiles[x-1][y] instanceof Wall)
					playerTurn = true;
				
				break;
				
			case 39://Right
				if(tiles[x+1][y] instanceof FloorTile || tiles[x+1][y] instanceof ItemTile){
					tiles[x][y] = new FloorTile(x, y);
					p1.moveRight();
				} else if(tiles[x+1][y] instanceof Monster)
					attackMonster(x+1, y);
				
				playerTurn = !playerTurn;
				if(tiles[x+1][y] instanceof Wall)
					playerTurn = true;
				
				break;
			
			case 73: //i key - inv screen toggle
				invHighlighted = 0;
				inventoryScreen = !inventoryScreen;
				break;
			
			case 10://enter key -- checks for item on the ground
				for(int i =0; i < items.size(); i++){
					if(x == items.get(i).getX() && y == items.get(i).getY()){
						ps.addItem(items.get(i).getItem());
						bb.addMessage("You pick up " + items.remove(i) + ".");
						
					}
				}
				break;
				
			case 16://Right Shift key -- Fire bow
				if(!aimingBow &&  ps.getInvItem(ps.getEquippedIndex()) instanceof Bow){
					bb.addMessage("You ready your bow.");
					aimingBow = true;
				} 
				break;
				
			case 87://w key -- wait a turn
				playerTurn = !playerTurn;
				break;
				
			default:
				break;
		}
		
		
		} else if(inventoryScreen){
			
			switch(key){
				
				case 40://Down
					invHighlighted++;
					if(invHighlighted >= ps.getInvSize())
						invHighlighted--;
					break;
				
				case 38://Up
					invHighlighted--;
					if(invHighlighted < 0)
						invHighlighted = 0;
					break;
				
				case 10://Enter
					if(ps.getInvItem(invHighlighted) instanceof Weapon){
						ps.setWeapon((Weapon)ps.getInvItem(invHighlighted));
						ps.setEquipped(invHighlighted);
						bb.addMessage("You equip " + (Weapon)ps.getInvItem(invHighlighted) + ".");
					} else if(ps.getInvItem(invHighlighted) instanceof HealthPotion){
						HealthPotion hp = (HealthPotion) ps.getInvItem(invHighlighted);
						
						bb.addMessage("You restore " + hp.getRestoreAmount() + " health.");
						ps.gainHealth(hp.getRestoreAmount());
						
						ps.decreaseItemAmount(invHighlighted, 1);
						
					}
					break;
					
				case 8://backspace - delete item
					if(ps.getEquippedIndex() != 0 && ps.getInvItem(ps.getEquippedIndex()) instanceof Weapon && invHighlighted != ps.getEquippedIndex()){
						ps.removeItem(invHighlighted);
						invHighlighted--;
					}
						
					break;
				
				case 73: //i key - close inv
					inventoryScreen = !inventoryScreen;
					break;
					
				default:
					break;
			}
			
		} else if(aimingBow){
			
			switch(key){
				case 38://Up
					fireBow(p1.getX(), p1.getY(), 1);
					playerTurn = !playerTurn;
					break;
					
				case 40: //Down
					fireBow(p1.getX(), p1.getY(), 3);
					playerTurn = !playerTurn;
					break;
					
				case 37: //Left
					fireBow(p1.getX(), p1.getY(), 4);
					playerTurn = !playerTurn;
					break;
					
				case 39://Right
					fireBow(p1.getX(), p1.getY(), 2);
					playerTurn = !playerTurn;
					break;
				
				case 16://Shift
					bb.addMessage("You lower your bow.");
					aimingBow = false;
					break;
				
				default:
					break;
			}
			
			aimingBow = false;
			
			
		}
		
		
		/*
			38 - Up
			40 - Down
			37 - Left
			39 - Right
		*/
		repaint();
	}
	
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	
	
	
}