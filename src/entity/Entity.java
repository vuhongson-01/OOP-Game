package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	public int x,y;
	public int speed;
		
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage leftAttack1, leftAttack2, rightAttack1, rightAttack2, topAttack1, topAttack2, downAttack1, downAttack2, skillAttack;
	public String action, preAction;
	
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0); 
	
	int screenX, screenY;
	public int spriteCounter = 0;
	public int spriteNum = 1;

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
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
			image = uTool.scaleImage(image, width, height);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
}
