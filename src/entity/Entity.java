package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	public int x,y;
	
	public int screenX, screenY;
	public int speed;
		
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage leftAttack1, leftAttack2, rightAttack1, rightAttack2, upAttack1, upAttack2, downAttack1, downAttack2, skillAttack;
	public String action, preAction;
	
	int f = 0;
	
	public int hp, mp, attack, defense, attackRange;
	public int attackSpeed;
	
//	center of entity (for calculate distance between 2 entities
	public int selfCenterX;
	public int selfCenterY;
	
//	~ f
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	
	
//	area that can give damage, example: boundary of sword; -----------
	public int damageAreaX1 = -1;						   
	public int damageAreaY1 = -1;						   
	public int damageAreaX2 = -1;
	public int damageAreaY2 = -1;

//	area that can take damage
	public int selfAreaX1;
	public int selfAreaY1;
	public int selfAreaX2;
	public int selfAreaY2;
	
	public int selfArea[];          // instead of 4 variables ^
	public int damageArea[];		// instead of 4 variables ^
	
	public boolean attacking = false;
	
	GamePanel gp;
	public int directionAttack;
	
	public Entity() {
		
	}
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public BufferedImage setup(String imageName, int width, int height) {
		System.out.println(1);
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
	
	public void decreHP(int attack) {
		// TODO Auto-generated method stub
		hp -= attack;
	}
	
}
