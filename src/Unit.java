import java.awt.*;

//Constructor function for the Unit class
public class Unit extends Rectangle {
	
	public int xCord, yCord;
	public int unitSize = 52;
	public int mobID = Value.mobAir;
	public boolean inGame = false;
	public int up = 0, down = 1, right = 2, left = 3;
	public int mobWalk = 0;
	public int direction = right;
	public boolean hasUp, hasDown, hasLeft, hasRight = false;
	
	public Unit() {
		
	}
	
	public void spawnUnit(int mobID) {
		for (int y=0; y<Screen.room.block.length;y++){
			if(Screen.room.block[y][0].groundID == Value.groundRoad){
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, unitSize, unitSize);
				xCord = 0;
				yCord = y;
			}
		}
		
		this.mobID = mobID;
		inGame = true;
	}
	
	public void deleteUnit() {
		inGame = false;
	}
	
	public void loseHealth(){
		Screen.health -= 1;
	}
	
	public int walkFrame = 0, walkSpeed = 40;
	public void physic(){
		if(walkFrame >= walkSpeed){
			if(direction == right){
				x += 1;
				hasRight = true;
			} else if(direction == up){
				y -= 1;
			} else if(direction == down){
				y += 1;
			} else if(direction == left){
				x -= 1;
			}
			
			mobWalk += 1;
			
			if(mobWalk == Screen.room.blockSize){
				if(direction == right){
					xCord += 1;
					hasRight = true;
				} else if(direction == up){
					yCord -= 1;
					hasUp = true;
				} else if(direction == down){
					yCord += 1;
					hasDown = true;
				} else if (direction == left){
					xCord -= 1;
					hasLeft = true;
				}
				
				if(!hasUp){
					try {
						if(Screen.room.block[yCord+1][xCord].groundID == Value.groundRoad){
							direction = down;
						}
					} catch(Exception e){}
				}
				
				if(!hasDown){
					try {
						if(Screen.room.block[yCord-1][xCord].groundID == Value.groundRoad){
							direction = up;
						}	
					} catch(Exception e){}
				}

				if(!hasLeft){
					try {
						if(Screen.room.block[yCord][xCord+1].groundID == Value.groundRoad){
							direction = right;
						}
					} catch(Exception e){}
				}
				
				if(!hasRight){
					try {
						if(Screen.room.block[yCord][xCord-1].groundID == Value.groundRoad){
							direction = left;
						}
					} catch(Exception e){}
				}
				
				if(Screen.room.block[yCord][xCord].airID == Value.airFinish){
					deleteUnit();
					loseHealth();
					
				}
				
				hasUp = false;
				hasDown = false;
				hasLeft = false;
				hasRight = false;
				mobWalk = 0;
			}
			walkFrame = 0;
		} else{
			walkFrame += 1;
		}
	}
	
	public void draw (Graphics g){
			g.drawImage(Screen.tileset_mob[mobID],  x,  y,  width,  height,  null);
	}
}
