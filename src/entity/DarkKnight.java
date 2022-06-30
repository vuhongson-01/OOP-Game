package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class DarkKnight extends Entity{

	
	GamePanel gp;
	KeyHandler keyHandler;
	int tileSize; 
	public int hp, mp, attack, defense;
	private final int defaultHP = 1000;
	private final int defaultMP = 100;
	
	public BufferedImage up1, up2, up3, up4, up5;
	public BufferedImage right1, right2, right3, right4, right5;
	public BufferedImage left1, left2, left3, left4, left5;
	public BufferedImage down1, down2, down3, down4, down5;
	public BufferedImage atk_left, atk_right, atk_up, atk_down_1, atk_down_2, atk_down_3, atk_down_4, atk_down_5, atk_down_6;
	public BufferedImage bmr_right, bmr_left, bmr_up, bmr_down;
	public BufferedImage image1 = null;
	public BufferedImage image2 = null;
	
	public BufferedImage pointboard, skill2, skill1, skill3, skillbar, impossible;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public int f = 0;
	public int f_attack = 0;
	
	private int skill1waiting = 0; //pro-active skill - 10s waiting
	private int skill1EffectiveTime = 5*60; //during 5s * 60FPS effective
	private int skill1MP = 40; //skill1 need 60 MP
	
	public DarkKnight(GamePanel gp, KeyHandler keyHandler) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		tileSize = gp.tileSize;
		
//		attackArea.width = 36;
//		attackArea.height = 36;
		
		setDefaultValue();
		getPlayerImage();
	}
	
	
	public void setDefaultValue() {
//		location for draw image
		worldX = 200;
		worldY = 200;
		
//		location of self in screen
		selfCenterX = screenX + gp.tileSize / 2;
		selfCenterY = screenY + gp.tileSize / 2;
		
		hp = defaultHP;
//		hp = 250;
		mp = defaultMP;
		attack = 15;
		defense = 10;
		speed = 5;
		action = "down";
	}

	
	public void getPlayerImage() {
		
		up1 = setup("/darkknight/up1", gp.tileSize, gp.tileSize);
		up2 = setup("/darkknight/up2", gp.tileSize, gp.tileSize);
		up3 = setup("/darkknight/up3", gp.tileSize, gp.tileSize);
		up4 = setup("/darkknight/up4", gp.tileSize, gp.tileSize);
		up5 = setup("/darkknight/up5", gp.tileSize, gp.tileSize);
		
		down1 = setup("/darkknight/down1", gp.tileSize, gp.tileSize);
		down2 = setup("/darkknight/down2", gp.tileSize, gp.tileSize);
		down3 = setup("/darkknight/down3", gp.tileSize, gp.tileSize);
		down4 = setup("/darkknight/down4", gp.tileSize, gp.tileSize);
		down5 = setup("/darkknight/down5", gp.tileSize, gp.tileSize);
		
		right1 = setup("/darkknight/right1", gp.tileSize, gp.tileSize);
		right2 = setup("/darkknight/right2", gp.tileSize, gp.tileSize);
		right3 = setup("/darkknight/right3", gp.tileSize, gp.tileSize);
		right4 = setup("/darkknight/right4", gp.tileSize, gp.tileSize);
		right5 = setup("/darkknight/right5", gp.tileSize, gp.tileSize);
		
		left1 = setup("/darkknight/left1",  gp.tileSize, gp.tileSize);
		left2 = setup("/darkknight/left2",  gp.tileSize, gp.tileSize);
		left3 = setup("/darkknight/left3",  gp.tileSize, gp.tileSize);
		left4 = setup("/darkknight/left4",  gp.tileSize, gp.tileSize);
		left5 = setup("/darkknight/left5",  gp.tileSize, gp.tileSize);
		
		atk_left = setup("/darkknight/atk_left",  gp.tileSize, gp.tileSize);
		atk_right = setup("/darkknight/atk_right",  gp.tileSize, gp.tileSize);
		atk_up = setup("/darkknight/atk_up",  gp.tileSize, gp.tileSize);
		atk_down_1 = setup("/darkknight/atk_down1",  gp.tileSize, gp.tileSize);
		atk_down_2 = setup("/darkknight/atk_down2",  gp.tileSize, gp.tileSize);
		atk_down_3 = setup("/darkknight/atk_down3",  gp.tileSize, gp.tileSize);
		atk_down_4 = setup("/darkknight/atk_down4",  gp.tileSize, gp.tileSize);
		atk_down_5 = setup("/darkknight/atk_down5", gp.tileSize, gp.tileSize);
		atk_down_6 = setup("/darkknight/atk_down6",  gp.tileSize, gp.tileSize);
		
		bmr_left = setup("/darkknight/boomerang_left", gp.tileSize / 2, gp.tileSize);
		bmr_right = setup("/darkknight/boomerang_right", gp.tileSize / 2, gp.tileSize);
		bmr_up = setup("/darkknight/boomerang_up", gp.tileSize, gp.tileSize / 2);
		bmr_down = setup("/darkknight/boomerang_down", gp.tileSize, gp.tileSize / 2);
			
		pointboard =  setup("/darkknight/pointboard", gp.tileSize * 4, gp.tileSize);
		skill1 = setup("/darkknight/skill1", gp.tileSize, gp.tileSize);
			
	}
	

	public void update(int [][] map) { // update player's position
		
		f++;
		
		selfCenterX = worldX + gp.tileSize / 2;
		selfCenterY = worldY + gp.tileSize / 2;
		
		if (f % 50 == 0) {
			if (mp < 100)
				mp++;		
				f = 0;
			}
		
		if (attacking == true) {
			attacking();
		}
		else {
			damageAreaX1 = -1;
			damageAreaY1 = -1;
			damageAreaX2 = -1;
			damageAreaY2 = -1;
			if (keyHandler.attack== true) {
				attacking = true;	
			}			
			if (keyHandler.upPressed == true) {
				action = "up";
				spriteCounter++;
				preAction = action;
//				*notice: 4 vertexes
				if (map[(worldY + tileSize/3 - speed) / tileSize][worldX / tileSize] == 1 
					&& map[(worldY + tileSize/3 - speed) / tileSize][(worldX + tileSize) / tileSize] == 1)
					worldY -= speed;
			}
			if (keyHandler.downPressed == true) {
				action = "down";
				preAction = action;
				spriteCounter++;
//				*notice: 4 vertexes
				if (map[(worldY + speed + tileSize) / tileSize][worldX / tileSize] == 1 
					&& map[(worldY + speed + tileSize) / tileSize][(worldX + tileSize) / tileSize] == 1)
					worldY += speed;
			}
			if (keyHandler.leftPressed == true) {
				action = "left";
				preAction = action;
				spriteCounter++;
//				*notice: 4 vertexes
				if (map[(worldY + tileSize/3) / tileSize][(worldX - speed) / tileSize] == 1 
					&& map[(worldY + tileSize)/tileSize][(worldX - speed) / tileSize] == 1)
					worldX -= speed;
			}
			if (keyHandler.rightPressed == true) {
				action = "right";
				preAction = action;
				spriteCounter++;
//				*notice: 4 vertexes
				if (map[(worldY + tileSize/3) / tileSize][(worldX + speed + tileSize) / tileSize] == 1 
					&& map[(worldY + tileSize) / tileSize][(worldX + speed + tileSize) / tileSize] == 1)
					worldX += speed;
			}
		}

		if (spriteCounter > 15) {   //15 or any number if liking
			if (spriteNum == 5) {
				spriteNum = 1;
			}
			else {
				spriteNum++;
			}
			spriteCounter = 0;
		}		
	}
	
	public void stopAttack() {
		attacking = false;
		f_attack = 0;
		spriteCounter = 0;
		spriteNum = 1;
		damageAreaX1 = -1;
		damageAreaY1 = -1;
		damageAreaX2 = -1;
		damageAreaY2 = -1;
		image2 = null;
	}
	
	public void attacking() {
		
		f_attack++;
		if(f_attack % 60 < 10) spriteNum = 1;
		else if(f_attack % 60 < 20) spriteNum = 2;
		else if(f_attack % 60 < 30) spriteNum = 3;
		else if(f_attack % 60 < 40) spriteNum = 4;
		else if(f_attack % 60 < 50) spriteNum = 5;
		else stopAttack();
	}

	public void draw(Graphics2D graphics2d) {	
		drawPoint(graphics2d);
		drawSkill(graphics2d);
		
		switch (action) {
		case "up": {
			if (attacking == false) {
				image2 = null;
				if (spriteNum == 1) { image1 = up1;}
				if (spriteNum == 2) { image1 = up2;}		
				if (spriteNum == 3) { image1 = up3;}		
				if (spriteNum == 4) { image1 = up4;}		
				if (spriteNum == 5) { image1 = up5;}		
			}
			if (attacking == true) {
				image2 = bmr_up;
				graphics2d.drawImage(image2, screenX, screenY - spriteNum * gp.tileSize, null);
				damageAreaX1 = screenX;
				damageAreaY1 = screenY - spriteNum * gp.tileSize;
				damageAreaX2 = screenX + gp.tileSize;
				damageAreaY2 = screenY - spriteNum * gp.tileSize + gp.tileSize;
				if (spriteNum == 1) { image1 = atk_up;}
				if (spriteNum == 2) { image1 = atk_up;}
				if (spriteNum == 3) { image1 = atk_up;}
				if (spriteNum == 4) { image1 = atk_up;}
				if (spriteNum == 5) { image1 = atk_up;}
			}
			break;
		}
		case "down": {
			if (attacking == false) {
				image2 = null;
				if (spriteNum == 1) { image1 = down1;}
				if (spriteNum == 2) { image1 = down2;}		
				if (spriteNum == 3) { image1 = down3;}		
				if (spriteNum == 4) { image1 = down4;}		
				if (spriteNum == 5) { image1 = down5;}
			}
			if (attacking == true) {
				image2 = bmr_down;
				graphics2d.drawImage(image2, screenX, screenY + spriteNum * gp.tileSize, null);
				damageAreaX1 = screenX;
				damageAreaY1 = screenY + spriteNum * gp.tileSize;
				damageAreaX2 = screenX + gp.tileSize;
				damageAreaY2 = screenY + spriteNum * gp.tileSize + gp.tileSize;
				if (spriteNum == 1) { image1 = atk_down_1;}
				if (spriteNum == 2) { image1 = atk_down_2;}		
				if (spriteNum == 3) { image1 = atk_down_3;}		
				if (spriteNum == 4) { image1 = atk_down_4;}		
				if (spriteNum == 5) { image1 = atk_down_5;}
			}
			break;
		}
		case "left": {
			if (attacking == false) {
				image2 = null;
				if (spriteNum == 1) { image1 = left1;}
				if (spriteNum == 2) { image1 = left2;}		
				if (spriteNum == 3) { image1 = left3;}		
				if (spriteNum == 4) { image1 = left4;}		
				if (spriteNum == 5) { image1 = left5;}
			}
			if (attacking == true) {
				image2 = bmr_left;
				graphics2d.drawImage(image2, screenX - spriteNum * gp.tileSize, screenY, null);
				damageAreaX1 = screenX - spriteNum * gp.tileSize;
				damageAreaY1 = screenY;
				damageAreaX2 = screenX - spriteNum * gp.tileSize + gp.tileSize;
				damageAreaY2 = screenY + gp.tileSize;
				if (spriteNum == 1) { image1 = atk_left;}
				if (spriteNum == 2) { image1 = atk_left;}		
				if (spriteNum == 3) { image1 = atk_left;}		
				if (spriteNum == 4) { image1 = atk_left;}		
				if (spriteNum == 5) { image1 = atk_left;}
			}
			break;
		}
		case "right": {
			if (attacking == false) {
				image2 = null;
				if (spriteNum == 1) { image1 = right1;}
				if (spriteNum == 2) { image1 = right2;}		
				if (spriteNum == 3) { image1 = right3;}		
				if (spriteNum == 4) { image1 = right4;}		
				if (spriteNum == 5) { image1 = right5;}
			}
			if (attacking == true) {
				image2 = bmr_right;
				graphics2d.drawImage(image2, screenX + spriteNum * gp.tileSize, screenY, null);
				damageAreaX1 = screenX + spriteNum * gp.tileSize;
				damageAreaY1 = screenY;
				damageAreaX2 = screenX + spriteNum * gp.tileSize + gp.tileSize;
				damageAreaY2 = screenY + gp.tileSize;
				if (spriteNum == 1) { image1 = atk_right;}
				if (spriteNum == 2) { image1 = atk_right;}		
				if (spriteNum == 3) { image1 = atk_right;}		
				if (spriteNum == 4) { image1 = atk_right;}		
				if (spriteNum == 5) { image1 = atk_right;}
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
		
		selfCenterX = worldX + gp.tileSize/2;
		selfCenterY = worldY + gp.tileSize/2;
		graphics2d.drawImage(image1, screenX, screenY , null);
	
	//	graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));	
	}
	
	public void drawPoint(Graphics2D graphics2d) {
		graphics2d.drawImage(pointboard, 0, 0, null);
		String text = "HP";
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
		graphics2d.setColor(Color.white);
		graphics2d.drawString(text, 10, 20);
		
		text = "MP";
		graphics2d.drawString(text, 10, 40);
		
		graphics2d.setColor(Color.gray);
		graphics2d.fillRoundRect(gp.tileSize-2, 6, 120 + 4, 16, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(gp.tileSize, 8, (int)(hp/1000.0 * 120), 12, 4, 4);
		
		graphics2d.setColor(Color.gray);
		graphics2d.fillRoundRect(gp.tileSize- 2, 26, 100 + 4, 16, 4, 4);
		graphics2d.setColor(Color.blue);
		graphics2d.fillRoundRect(gp.tileSize, 28, (int)(mp/100.0 * 100), 12, 4, 4);
		
		
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 10F));
		graphics2d.setColor(Color.white);
		text = Integer.toString(hp);
		graphics2d.drawString(text, 60, 18);
		text = Integer.toString(mp);
		graphics2d.drawString(text, 60, 38);
	}
	
private void drawSkill(Graphics2D graphics2d) {
		
		graphics2d.setColor(Color.black);
		graphics2d.drawImage(skillbar, 0, gp.screenHeight - gp.tileSize, null);
		
		graphics2d.drawImage(skill1, 0, gp.screenHeight - gp.tileSize, null);
//		if (mp < skill1MP || skill2waiting != 0) {
//			graphics2d.drawImage(impossible, 0, gp.screenHeight - gp.tileSize, null);
//		}
		
		
//		skill1
		if (mp > skill1MP && keyHandler.Askill1IsActive && skill1waiting == 0) {
			keyHandler.Askill1IsActive = false;
			mp -= skill1MP;
			skill1waiting = 10 * 60;
		}
		
		if (skill1waiting != 0) {
			
			skill1EffectiveTime--;
			skill1waiting--;
			
			if (skill1EffectiveTime >= 0) {
				graphics2d.drawImage(skill1, screenX,  screenY,  null);
			}
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill1waiting/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill1waiting/60), gp.tileSize/2 - length/2, gp.screenHeight - gp.tileSize/2);
		}
		
		if (skill1waiting == 0) {
			skill1EffectiveTime = 3*60;
		}
	}
}

