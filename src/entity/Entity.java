package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	public int worldX, worldY;			// coordinates on map
	public int screenX, screenY;		// coordinates on screen
	
	public int speed;
		
	
	public String action, preAction;
	
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); 
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0); 
	
	int f;
	public int hp, mp, attack, defense, attackRange, atkSpeed;
	public int directionAttack;
	
	public int selfCenterX;
	public int selfCenterY;
	
	public int damageAreaX1 = -1;
	public int damageAreaY1 = -1;
	public int damageAreaX2 = -1;
	public int damageAreaY2 = -1;
	
	public int selfAreaX1;
	public int selfAreaY1;
	public int selfAreaX2;
	public int selfAreaY2;
	
	
	

	

	public boolean attacking = false;
	
	
	
	GamePanel gp;
	
	public Entity() {
		
	}
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public BufferedImage setup(String imageName, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
			image = uTool.scaleImage(image, width, height);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
}
