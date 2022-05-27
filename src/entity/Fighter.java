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

public class Fighter extends Entity{

	KeyHandler keyHandler;
	int tileSize; 
	
	private final int defaultHP = 1000;
	private final int defaultMP = 100;
	BufferedImage pointboard, skill2, skill1, skill3, skillbar, impossible;
	
	private int skill1waiting = 0; //pro-active skill - 10s waiting
	private int skill1EffectiveTime = 3*60; //during 3s * 60FPS effective
	private int skill1MP = 40; //skill1 need 60 MP
	
	private int skill2waiting = 0; //passive skill - 7s waiting
	private int skill2EffectiveTime = 3; //during 3s effective
	private int skill2MP = 40; //skill2 need 40 MP
	
	private int skill3waiting = 0;  //passive skill - 12s waiting
	private int skill3EffectiveTime = 4*60; //during 4s * 60FPS effective
	private int skill3MP = 50; //skill3 need 50 MP
	
	public Fighter(GamePanel gp, KeyHandler keyHandler) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		tileSize = gp.tileSize;

		setDefaultValue();
		getPlayerImage();
	}
	
	
	public void setDefaultValue() {
//		location for draw image
		x = 100;
		y = 100;
		
//		location of self in screen
		selfCenterX = x + gp.tileSize;
		selfCenterY = y + gp.tileSize;
		
		hp = defaultHP;
//		hp = 250;
		mp = defaultMP;
		attack = 15;
		defense = 10;
		speed = 2;
		action = "down";
	}

	
	public void getPlayerImage() {
			up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
			up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
			down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
			down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
			left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
			left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
			right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
			right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
			
			upAttack1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
			upAttack2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
			downAttack1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
			downAttack2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
			leftAttack1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
			leftAttack2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
			rightAttack1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
			rightAttack2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
			
			pointboard =  setup("/player/pointboard", gp.tileSize * 4, gp.tileSize);
			impossible = setup("/player/impossible", gp.tileSize, gp.tileSize);
			skill1 = setup("/player/skill1A", gp.tileSize, gp.tileSize); 
			skill2 = setup("/player/skill2A", gp.tileSize, gp.tileSize); 
			skill3 = setup("/player/skill3A", gp.tileSize, gp.tileSize); 
			skillbar = setup("/player/skillbar", gp.tileSize* 3 , gp.tileSize); 
	}
	

	public void update(int [][] map) { // update player's position
		
		f++;
		if (f % 50 == 0) {
			if (mp < 100)
				mp++;
			
			f = 0;
		}
		
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
		drawPoint(graphics2d);
		drawSkill(graphics2d);
		
		switch (action) {
		case "up": {
			if (attacking == false) {
				
				if (spriteNum == 1) { image = up1;}
				if (spriteNum == 2) { image = up2;}		
			}
			if (attacking == true) {
				if (spriteNum == 1) { image = upAttack1;}
				if (spriteNum == 2) { 
					damageAreaX1 = x + 24;
					damageAreaY1 = y + 24;
					damageAreaX2 = x + 36;
					damageAreaY2 = y + 48;
					image = upAttack2;
				}
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
				if (spriteNum == 2) { 
					damageAreaX1 = x + 18;
					damageAreaY1 = y + 48;
					damageAreaX2 = x + 30;
					damageAreaY2 = y + 24;
					image = downAttack2;
				}
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
				if (spriteNum == 2) { 
					damageAreaX1 = x + 24;
					damageAreaY1 = y + 24;
					damageAreaX2 = x + 54;
					damageAreaY2 = y + 36;
					image = leftAttack2;
				}
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
				if (spriteNum == 2) { 
					damageAreaX1 = x + 42;
					damageAreaY1 = y + 24;
					damageAreaX2 = x + 72;
					damageAreaY2 = y + 42;
					image = rightAttack2;
				}
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
		
		if (skill1EffectiveTime == 3*60 || (skill1EffectiveTime < 0 && skill1waiting != 0)) {
			if (image == leftAttack1 || image == leftAttack2) {
				selfCenterX = x + gp.tileSize*3/2;
				selfCenterY = y + gp.tileSize/2;
				graphics2d.drawImage(image, x - tileSize, y, null);
				}
			else if (image == upAttack1 || image == upAttack2){
				selfCenterX = x + gp.tileSize/2;
				selfCenterY = y + gp.tileSize*3/2;
				graphics2d.drawImage(image, x, y - tileSize, null);
			}
			else {
				selfCenterX = x + gp.tileSize/2;
				selfCenterY = y + gp.tileSize/2;
				graphics2d.drawImage(image, x, y, null);
	//			graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}			
		}
	}


	private void drawSkill(Graphics2D graphics2d) {
		
		graphics2d.setColor(Color.black);
		graphics2d.drawImage(skillbar, 0, gp.screenHeight - gp.tileSize, null);
		
		graphics2d.drawImage(skill1, 0, gp.screenHeight - gp.tileSize, null);
		if (mp < skill1MP || skill1waiting != 0) {
			graphics2d.drawImage(impossible, 0, gp.screenHeight - gp.tileSize, null);
		}
		
		
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
				if (skill1EffectiveTime % 12 < 3) {
					selfCenterX = x + gp.tileSize*3/2;
					selfCenterY = y + gp.tileSize/2;
					
					damageAreaX1 = x + 24;
					damageAreaY1 = y + 24;
					damageAreaX2 = x + 54;
					damageAreaY2 = y + 36;
					graphics2d.drawImage(leftAttack2, x - tileSize, y, null);
				}
				else if (skill1EffectiveTime % 12 < 6){
					selfCenterX = x + gp.tileSize/2;
					selfCenterY = y + gp.tileSize*3/2;
					
					damageAreaX1 = x + 24;
					damageAreaY1 = y + 24;
					damageAreaX2 = x + 36;
					damageAreaY2 = y + 48;
					graphics2d.drawImage(upAttack2, x, y - tileSize, null);
				}
				else if (skill1EffectiveTime % 12 < 9) {
					selfCenterX = x + gp.tileSize/2;
					selfCenterY = y + gp.tileSize/2;
					
					damageAreaX1 = x + 42;
					damageAreaY1 = y + 24;
					damageAreaX2 = x + 72;
					damageAreaY2 = y + 42;
					graphics2d.drawImage(rightAttack2, x, y, null);
				}
				else{
					selfCenterX = x + gp.tileSize/2;
					selfCenterY = y + gp.tileSize/2;
					
					damageAreaX1 = x + 18;
					damageAreaY1 = y + 48;
					damageAreaX2 = x + 30;
					damageAreaY2 = y + 24;
					graphics2d.drawImage(downAttack2, x, y, null);
				}
			}
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill1waiting/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill1waiting/60), gp.tileSize/2 - length/2, gp.screenHeight - gp.tileSize/2);
		}
		
		if (skill1waiting == 0) {
			skill1EffectiveTime = 3*60;
		}
		
		graphics2d.drawImage(skill2, gp.tileSize, gp.screenHeight - gp.tileSize, null);
		if (mp < skill2MP || skill2waiting != 0) {
			graphics2d.drawImage(impossible, gp.tileSize, gp.screenHeight - gp.tileSize, null);
		}
		
		
//		skill2
		if (mp > skill2MP && keyHandler.Askill2IsActive && skill2waiting == 0) {
			keyHandler.Askill2IsActive = false;
			skill2waiting = 7*60;
			defense = 15;
			mp -= skill2MP;
		}

		if (skill2waiting != 0) {
			
			skill2EffectiveTime--;
			skill2waiting--;
			
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill2waiting/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill2waiting/60), gp.tileSize + gp.tileSize/2 - length/2, gp.screenHeight - gp.tileSize/2);
		}
		
		if (skill2waiting == 0 || skill2EffectiveTime < 0) {
			defense = 10;
		}
		
		if (skill2waiting == 0) {
			skill2EffectiveTime = 3 * 60;
		}
		
		
//		skill3		
		graphics2d.drawImage(skill3, gp.tileSize*2, gp.screenHeight - gp.tileSize, null);
		if (mp < skill3MP || skill3waiting != 0) {
			graphics2d.drawImage(impossible, gp.tileSize*2, gp.screenHeight - gp.tileSize, null);
		}
		
		if (mp > skill3MP && keyHandler.Askill3IsActive && skill3waiting == 0) {
			keyHandler.Askill3IsActive = false;
			skill3waiting = 20 * 60;
			mp -= skill3MP;
		}
		
		if (skill3waiting != 0) {
			
			if (skill3EffectiveTime > 0 && skill3EffectiveTime % 60 == 0) {
				if (hp + 70 < defaultHP) hp += 70;
				else {
					hp = defaultHP;
				}
			}
			skill3EffectiveTime--;
			skill3waiting--;

			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill3waiting/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill3waiting/60), gp.tileSize*2 + gp.tileSize/2 - length/2, gp.screenHeight - gp.tileSize/2);
		}
		
		if (skill3waiting == 0) {
			skill3EffectiveTime = 4*60;
		}
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
}
