package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	public int x,y;
//	public int worldX, worldY;
	
	public int screenX, screenY;
	public int speed;
		
	public BufferedImage up[] = new BufferedImage[10];
	public BufferedImage down[] = new BufferedImage[10];
	public BufferedImage left[] = new BufferedImage[10];
	public BufferedImage right[] = new BufferedImage[10];
	
	public BufferedImage leftAttack[] = new BufferedImage[10];
	public BufferedImage rightAttack[] = new BufferedImage[10];
	public BufferedImage upAttack[] = new BufferedImage[10];
	public BufferedImage downAttack[] = new BufferedImage[10];
	
	public BufferedImage skillAttack;
	public String action, preAction;
	
	int f = 0;
	
	public int hp, mp, attack, defense, attackRange;
	public int attackSpeed;
	
//	~ f
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
//	area that can give damage, example: boundary of sword; -----------
	
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
//		System.out.println(1);
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
