package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Fighter extends Entity{

	
	GamePanel gp;
	KeyHandler keyHandler;
	int tileSize;
	
	public Fighter(GamePanel gp, KeyHandler keyHandler) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		tileSize = gp.tileSize;
		
		setDefaultValue();
		getPlayerImage();
	}
	
	
	public void setDefaultValue() {
		x = 100;
		y = 100;
		speed = 4;
		action = "down";
	}

	
	public void getPlayerImage() {
			up1 = setup("boy_up_1", gp.tileSize, gp.tileSize);
			up2 = setup("boy_up_2", gp.tileSize, gp.tileSize);
			down1 = setup("boy_down_1", gp.tileSize, gp.tileSize);
			down2 = setup("boy_down_2", gp.tileSize, gp.tileSize);
			left1 = setup("boy_left_1", gp.tileSize, gp.tileSize);
			left2 = setup("boy_left_2", gp.tileSize, gp.tileSize);
			right1 = setup("boy_right_1", gp.tileSize, gp.tileSize);
			right2 = setup("boy_right_2", gp.tileSize, gp.tileSize);
			
			topAttack1 = setup("boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
			topAttack2 = setup("boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
			downAttack1 = setup("boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
			downAttack2 = setup("boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
			leftAttack1 = setup("boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
			leftAttack2 = setup("boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
			rightAttack1 = setup("boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
			rightAttack2 = setup("boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
	}
	

	public void update(int [][] map) { // update player's position
		
		if (attacking == true) {
			attacking();
		}
		else {
			if (keyHandler.attack== true) {
				attacking = true;
			}
			
			if (keyHandler.upPressed == true) {
				action = "up";
				spriteCounter++;
				preAction = action;
//				*notice: 4 vertexes
				if (map[(y + tileSize/3 - speed) / tileSize][x / tileSize] == 1 
					&& map[(y + tileSize/3 - speed) / tileSize][(x + tileSize) / tileSize] == 1)
					y -= speed;
			}
			if (keyHandler.downPressed == true) {
				action = "down";
				preAction = action;
				spriteCounter++;
//				*notice: 4 vertexes
				if (map[(y + speed + tileSize) / tileSize][x / tileSize] == 1 
					&& map[(y + speed + tileSize) / tileSize][(x + tileSize) / tileSize] == 1)
					y += speed;
			}
			if (keyHandler.leftPressed == true) {
				action = "left";
				preAction = action;
				spriteCounter++;
//				*notice: 4 vertexes
				if (map[(y + tileSize/3) / tileSize][(x - speed) / tileSize] == 1 
					&& map[(y + tileSize)/tileSize][(x - speed) / tileSize] == 1)
					x -= speed;
			}
			if (keyHandler.rightPressed == true) {
				action = "right";
				preAction = action;
				spriteCounter++;
//				*notice: 4 vertexes
				if (map[(y + tileSize/3) / tileSize][(x + speed + tileSize) / tileSize] == 1 
					&& map[(y + tileSize) / tileSize][(x + speed + tileSize) / tileSize] == 1)
					x += speed;
			}

			if (spriteCounter > 15) {   //15 or any number if liking
				if (spriteNum == 1) {
					spriteNum = 2;
				}
				else if (spriteNum == 2){
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
				
		}
	}
	
	public void attacking() {
		spriteCounter++;
		
		if (spriteCounter <= 5) {
			spriteNum = 1;
		}
		else if (spriteCounter <= 25) {
			spriteNum = 2;
		}
		else if (spriteCounter > 25){
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}

	public void draw(Graphics2D graphics2d) {
		BufferedImage image = null;
		
		switch (action) {
		case "up": {
			
			if (attacking == false) {
				if (spriteNum == 1) { image = up1;}
				if (spriteNum == 2) { image = up2;}				
			}
			if (attacking == true) {
				if (spriteNum == 1) { image = topAttack1;}
				if (spriteNum == 2) { image = topAttack2;}
			}
			break;
		}
		case "down": {
			if (attacking == false) {
				if (spriteNum == 1) { image = down1;}
				if(spriteNum == 2) { image = down2;}				
			}
			if (attacking == true) {
				if (spriteNum == 1) { image = downAttack1;}
				if (spriteNum == 2) { image = downAttack2;}
			}
			break;
		}
		case "left": {
			if (attacking == false) {
				if (spriteNum == 1) { image = left1;}
				if(spriteNum == 2) { image = left2;}				
			}
			if (attacking == true) {
				if (spriteNum == 1) { image = leftAttack1;}
				if (spriteNum == 2) { image = leftAttack2;}
			}
			break;
		}
		case "right": {
			if (attacking == false) {
				if (spriteNum == 1) { image = right1;}
				if(spriteNum == 2) { image = right2;}	
			}
			if (attacking == true) {
				if (spriteNum == 1) { image = rightAttack1;}
				if (spriteNum == 2) { image = rightAttack2;}
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
		
		if (image == leftAttack1 || image == leftAttack2) 
			graphics2d.drawImage(image, x - tileSize, y, null);
		else if (image == topAttack1 || image == topAttack2){
			graphics2d.drawImage(image, x, y - tileSize, null);
		}
		else {
			graphics2d.drawImage(image, x, y, null);
//			graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}
}
