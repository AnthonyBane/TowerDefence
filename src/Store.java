import java.awt.*;

//Constructor function for the Store class
public class Store {
	public static int shopWidth = 8;
	public static int buttonSize = 52;
	public static int cellSpace = 2;
	public static int displacement = 25;
	public static int iconSize = 20;
	public static int iconSpace = 3;
	public static int iconTextY = 15;
	public static int itemIn = 4;
	public static int heldID = -1;
	
	public boolean holdsItem = false;
	public static int[] buttonID = {0, 0, 0, 0, 0, 0, 0, 1};
	
	public Rectangle[] button = new Rectangle[shopWidth];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;
	
	public Store(){
		define();
	}
	
	public void click(int mouseButton){
		if(mouseButton == 1){
			for(int i=0;i<button.length;i++){
				if(button[i].contains(Screen.mse)){
					if(heldID == Value.airTrashCan){
						holdsItem = false;
					}else{
						heldID = buttonID[i];
						holdsItem = true;
					}
				}
			}
		}
	}
	
	public void define(){
		for(int i=0;i<button.length;i++){
			button[i] = new Rectangle((Screen.myWidth/2) - ((shopWidth*(buttonSize+cellSpace)) /2) + ((buttonSize+cellSpace) * i), (Screen.room.block[Screen.room.worldHeight-1][0].y) + Screen.room.blockSize + displacement, buttonSize, buttonSize);
		}
		
		buttonHealth = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y, iconSize, iconSize);
		buttonCoins = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y + button[0].height - iconSize, iconSize, iconSize);
	}
	
	public void draw (Graphics g){
	
		for(int i=0;i<button.length;i++){
			if(button[i].contains(Screen.mse)){
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
			}
			g.drawImage(Screen.tileset_res[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
			g.drawImage(Screen.tileset_air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width - (itemIn*2), button[i].height - (itemIn*2), null);
		}
		
		g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
		g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);
		g.setFont(new Font("Courier New", Font.BOLD, 14));
		g.setColor(new Color (255, 255, 255));
		g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + iconTextY);
		g.drawString("" + Screen.coins, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + iconTextY);
		
		if(holdsItem){
			g.drawImage(Screen.tileset_air[heldID], Screen.mse.x - ((button[0].width - (itemIn*2))/2) + itemIn, Screen.mse.y - ((button[0].width - (itemIn*2))/2) + itemIn, button[0].width - (itemIn*2), button[0].height - (itemIn*2), null);
		}
	}
}