import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.swing.*;

public class Screen extends JPanel implements Runnable{
	public Thread thread = new Thread(this);
	public static boolean isFirst = true;
	public static int myWidth, myHeight;
	public static Room room;

	public int spawnTime = 2400, spawnFrame = 0;																										//sets spawn time, decrease for increased difficulty, can be altered through a difficulty setting for higher complexity
	
	public static Unit[] mobs = new Unit[100];
	
	public static Save save;
	public static Point mse = new Point(0, 0);
	public static Store store;
	
	//Defining beginning coins and health. Remodel to adjust difficulty or game length, add functions for cheat codes or monitisation game model.
	public static int coins = 10, health = 100;
	
	//Block defines image tileset's
	public static Image[] tileset_ground = new Image [10];
	public static Image[] tileset_air = new Image [10];
	public static Image[] tileset_res = new Image[100];
	public static Image[] tileset_mob = new Image[100];
	public static Image[] tileset_tower = new Image[100];
	
	//Constructor function for the Screen class
	public Screen(Frame frame){
		frame.addMouseListener(new KeyHandle());
		frame.addMouseMotionListener(new KeyHandle());
		thread.start();
	}
	
	//Function defines the Screen class
	public void define(){
		room = new Room();																																//calls Room constructor
		save = new Save();																																//calls Save constructor
		store = new Store();																															//calls Store constructor
		
		//for loop gets ground images (such as terrain)
		for(int i=0; i<tileset_ground.length;i++){
			tileset_ground[i] = new ImageIcon("Resources/tileset_ground.png").getImage();																//returns the image icon as an image
			tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new  CropImageFilter(0, 26 * i, 26, 26)));			//crops image as a 26*26 pixel image and cropped based on i iterations
		}
		
		//for loops gets air images (such as end gate)
		for(int i=0; i<tileset_air.length;i++){
			tileset_air[i] = new ImageIcon("Resources/tileset_air.png").getImage();																		//returns the image icon as an image
			tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new  CropImageFilter(0, 26 * i, 26, 26)));					//crops image as a 26*26 pixel image and cropped based on i iterations
		}
		
		//Block gets Screen images
		tileset_res[0] = new ImageIcon("Resources/cell.png").getImage();
		tileset_res[1] = new ImageIcon("Resources/heart.png").getImage();
		tileset_res[2] = new ImageIcon("Resources/coin.png").getImage();
		
		//Block gets mobs, rework mob.png to tileset_ground like list and for loop to gain more mob types simply
		tileset_mob[0] = new ImageIcon("Resources/mob.png").getImage();
		
		//Loads save
		save.loadSave(new File("save/mission1.elv"));																									//save is currently set to first level
		
		//for loop populates mob array with new mob class
		for(int i=0; i<mobs.length;i++){
			mobs[i] = new Unit();																														//calls Unit  constructor
		}
	}
	
	//Functions paints the Screen class
	public void paintComponent(Graphics g){
		if(isFirst) {
			myWidth = getWidth();																														//sets Screen width to app width
			myHeight = getHeight();																														//sets Screen height to app height
			define();																																	//define Sceen class
			
			isFirst = false;
		}
		
		g.setColor(new Color(50, 50, 50));																												//sets background colour
		g.fillRect(0, 0, getWidth(), getHeight()); 																										//paint background colour		
		g.setColor(new Color(0, 100, 0));																												//set border line colour
		g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y +room.blockSize + 1);								//draws left line of grid
		g.drawLine(room.block[0][room.worldWidth-1].x + room.blockSize, 0, room.block[0][room.worldWidth-1].x + room.blockSize, room.block[room.worldHeight-1][0].y +room.blockSize + 1);					//draws right line of grid
		g.drawLine(room.block[0][0].x, room.block[room.worldHeight-1][0].y + room.blockSize, room.block[0][room.worldWidth -1].x + room.blockSize , room.block[room.worldHeight-1][0].y + room.blockSize); 	//draws bottom line of grid
		
		room.draw(g); 
		
		//for loop to repaint new position of ingame mobs
		for(int i=0;i<mobs.length;i++){
			if(mobs[i].inGame){
				mobs[i].draw(g);
			}
		}
		
		store.draw(g);																																	//draws the store
	}
	
	//function spawns mobs... kind of self explanatory
	public void mobSpawner(){
		if(spawnFrame >= spawnTime) {																													//when allocated spawn time is reached, unit is spawned
			for(int i=0;i<mobs.length;i++){																												
				if(!mobs[i].inGame){																													//if mob is not in the game
					mobs[i].spawnUnit(Value.mobGround);																									//unit is spawned
					break;																																//breaks for loop
				}
			}
			spawnFrame = 0;																																//resets effective spawn timer
		} else {
			spawnFrame += 1;																															//increases effective spawn counter by 1
		}
	}
	
	public void run(){
		while(true){
			if(!isFirst){																																//if function paintcomponent has not been called, statement cannot be called
				room.Physic();																															
				mobSpawner();																															//calls spawner
				for(int i=0;i<mobs.length;i++){																											//iterates the length of mobs array
					if(mobs[i].inGame){																													//checks to see if mob is in the game
						mobs[i].physic();																												//calls the physics function of the mob
					}
				}
			}
			
			repaint();																																	//repaints the screen
			
			try{
				Thread.sleep(1);																														//sleeps thread
			} catch(Exception e) {}
		}
	}
}
