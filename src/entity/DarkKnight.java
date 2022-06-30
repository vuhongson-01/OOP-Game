package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameInterface;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class DarkKnight extends Entity implements GameInterface{

	GamePanel gp;
	KeyHandler keyHandler;
	private final int defaultHP = 1000;
	private final int defaultMP = 100;
	
	public BufferedImage bmr_right, bmr_left, bmr_up, bmr_down;
	public BufferedImage image1 = null;
	public BufferedImage image2 = null;
	
	public BufferedImage pointboard, skill2, skill1, skill3, skillbar, impossible;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public int f = 0;
	public int f_attack = 0;
	
	boolean deleteAttack = false;
	
	private int skill1waiting = 0; //pro-active skill - 10s waiting
	private int skill1EffectiveTime = 5*60; //during 5s * 60FPS effective
	private int skill1MP = 40; //skill1 need 60 MP
	
	public DarkKnight(GamePanel gp, KeyHandler keyHandler, int[][] map, int firstLocationX, int firstLocationY) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		
		setDefaultValue(firstLocationX, firstLocationY);
		getPlayerImage();
	}
	
	
	public void setDefaultValue(int firstLocationX, int firstLocationY) {
//		location for draw image
		x = firstLocationX;
		y = firstLocationY;

		selfArea = new int [4];
		damageArea = new int [4];
		
		selfArea[0] = gp.worldx + x + TILE_SIZE/6;
		selfArea[1] = gp.worldy + y + TILE_SIZE/6;
		selfArea[2] = gp.worldx + x+ TILE_SIZE/6*5;
		selfArea[3] = gp.worldy + y + TILE_SIZE/6*5;
				
		hp = defaultHP;
		mp = defaultMP;
		attack = 15;
		defense = 10;
		speed = 2;
		
		action = "down";
	}
	
	public void getPlayerImage() {
		
		for (int i = 0; i < 5; i++) {
			up[i] = setup("/darkknight/up" + (i+1), TILE_SIZE, TILE_SIZE);
			down[i] = setup("/darkknight/down" + (i+1), TILE_SIZE, TILE_SIZE);
			right[i] = setup("/darkknight/right" + (i+1), TILE_SIZE, TILE_SIZE);
			left[i] = setup("/darkknight/left" + (i+1),  TILE_SIZE, TILE_SIZE);
		}

		leftAttack[0] = setup("/darkknight/atk_left",  TILE_SIZE, TILE_SIZE);
		rightAttack[0] = setup("/darkknight/atk_right",  TILE_SIZE, TILE_SIZE);
		upAttack[0] = setup("/darkknight/atk_up",  TILE_SIZE, TILE_SIZE);
		
		for (int i = 0; i < 6; i++) {
			downAttack[i] = setup("/darkknight/atk_down" + (i+1),  TILE_SIZE, TILE_SIZE);
		}
		
		bmr_left = setup("/darkknight/boomerang_left", TILE_SIZE / 2, TILE_SIZE);
		bmr_right = setup("/darkknight/boomerang_right", TILE_SIZE / 2, TILE_SIZE);
		bmr_up = setup("/darkknight/boomerang_up", TILE_SIZE, TILE_SIZE / 2);
		bmr_down = setup("/darkknight/boomerang_down", TILE_SIZE, TILE_SIZE / 2);
			
		pointboard =  setup("/darkknight/pointboard", TILE_SIZE * 4, TILE_SIZE);
		skill1 = setup("/darkknight/skill1", TILE_SIZE, TILE_SIZE);
		
		impossible = setup("/player/impossible", TILE_SIZE, TILE_SIZE);
		skillbar = setup("/player/skillbar", TILE_SIZE* 3 , TILE_SIZE); 
			
	}
	

	public void update(int [][] map) { // update player's position
		
		f++;
		
//		selfCenterX = x + TILE_SIZE / 2;
//		selfCenterY = y + TILE_SIZE / 2;
		
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
				if (map[(gp.worldy + y + TILE_SIZE/3 - speed) / TILE_SIZE][(x + gp.worldx)/ TILE_SIZE] == 1 
					&& map[(gp.worldy + y + TILE_SIZE/3 - speed) / TILE_SIZE][(gp.worldx + x + TILE_SIZE) / TILE_SIZE] == 1) {
					if (gp.worldy > 0 && y == TILE_SIZE * 4) gp.worldy -= speed;
					else {
						if (gp.worldy == 0) y -= speed;
						else {
							if (y > TILE_SIZE*4) y -= speed;
						}
					}
				}	
			}
			if (keyHandler.downPressed == true) {
				action = "down";
				preAction = action;
				spriteCounter++;
//				*notice: 4 vertexes
				if (map[(gp.worldy + y + speed + TILE_SIZE) / TILE_SIZE][(gp.worldx + x) / TILE_SIZE] == 1 
					&& map[(gp.worldy + y + speed + TILE_SIZE) / TILE_SIZE][(gp.worldx + x + TILE_SIZE) / TILE_SIZE] == 1) {
					
					if (y == SCREEN_HEIGHT - TILE_SIZE*4 && gp.worldy < TILE_SIZE*48 - SCREEN_HEIGHT) gp.worldy += speed;
					else {
						if (gp.worldy == TILE_SIZE*48 - SCREEN_HEIGHT) y += speed;
						else {
							if (y < SCREEN_HEIGHT - TILE_SIZE*4) {
								y += speed;
							}						
						}
					}
				}
			}
			if (keyHandler.leftPressed == true) {
				action = "left";
				preAction = action;
				spriteCounter++;
//				*notice: 4 vertexes
				if (map[(gp.worldy + y + TILE_SIZE/3) / TILE_SIZE][(gp.worldx + x - speed) / TILE_SIZE] == 1 
					&& map[(gp.worldy + y + TILE_SIZE)/TILE_SIZE][(gp.worldx + x - speed) / TILE_SIZE] == 1) {
					
					if(x == TILE_SIZE*4 && gp.worldx > 0) gp.worldx -= speed; 
					else {
						if (gp.worldx == 0) x -= speed;
						else {
							if (x > TILE_SIZE*4) x -= speed; 
						}
					}
				}
			}
			if (keyHandler.rightPressed == true) {
				action = "right";
				preAction = action;
				spriteCounter++;
//				*notice: 4 vertexes
				if (map[(gp.worldy + y + TILE_SIZE/3) / TILE_SIZE][(gp.worldx + x + speed + TILE_SIZE) / TILE_SIZE] == 1 
					&& map[(gp.worldy + y + TILE_SIZE) / TILE_SIZE][(gp.worldx + y + speed + TILE_SIZE) / TILE_SIZE] == 1) {
					
					if (gp.worldx < TILE_SIZE*48 - SCREEN_WIDTH  && x == SCREEN_WIDTH - 4*TILE_SIZE) gp.worldx += speed;
					else {
						if (gp.worldx == TILE_SIZE*48 - SCREEN_WIDTH) x += speed;
						else {
							if (x < SCREEN_WIDTH - 4*TILE_SIZE) x += speed;
						}
					}
				}
				
			}
			selfArea[0] = gp.worldx + x + TILE_SIZE/6;
			selfArea[1] = gp.worldy + y + TILE_SIZE/6;
			selfArea[2] = gp.worldx + x+ TILE_SIZE/6*5;
			selfArea[3] = gp.worldy + y + TILE_SIZE/6*5;
			
			if (spriteCounter > 15) {   //15 or any number if liking
				if (spriteCounter <= 5) {
					spriteNum = 2;
				}
				else if (spriteCounter <= 25){
					spriteNum = 1;
				}
				spriteCounter = 0;
			}	
			
		}
//		if ((gp.worldx + x) / TILE_SIZE == 10 && (gp.worldy+y)/TILE_SIZE == 10) {
//			gp.nextGameState();
//		}
	}
	
	public void stopAttack() {
		attacking = false;
		f_attack = 0;
		spriteCounter = 0;
		spriteNum = 1;
		damageArea[0] = -1;
		damageArea[1] = -1;
		damageArea[2] = -1;
		damageArea[3] = -1;
		image2 = null;
	}
	
	public void attacking() {
		
		
		
		if (f_attack != 0) deleteAttack =  gp.playerTakeRangeAttack(attack);
		
		if (!deleteAttack) {
			f_attack++;
			if(f_attack % 60 < 10) spriteNum = 1;
			else if(f_attack % 60 < 20) spriteNum = 2;
			else if(f_attack % 60 < 30) spriteNum = 3;
			else if(f_attack % 60 < 40) spriteNum = 4;
			else if(f_attack % 60 < 50) spriteNum = 5;
			else stopAttack();
		}
		else {
			stopAttack();
			deleteAttack = false;
		}
	}

	public void draw(Graphics2D graphics2d) {	
		drawPoint(graphics2d);
		drawSkill(graphics2d);
		
		switch (action) {
		case "up": {
			if (attacking == false) {
				image2 = null;
				image1 = up[spriteNum-1];
			}
			if (attacking == true) {
				image2 = bmr_up;
				
				damageArea[0] = gp.worldx + x;
				damageArea[1] = gp.worldy + y - spriteNum * TILE_SIZE;
				damageArea[2] = gp.worldx + x + TILE_SIZE;
				damageArea[3] = gp.worldy + y- spriteNum * TILE_SIZE + TILE_SIZE;
				image1 = upAttack[0];
			}
			break;
		}
		case "down": {
			if (attacking == false) {
				image2 = null;
				image1 = down[spriteNum-1];
			}
			if (attacking == true) {
				image2 = bmr_down;

				damageArea[0] = gp.worldx + x;
				damageArea[1] = gp.worldy + y +spriteNum * TILE_SIZE;
				damageArea[2] = gp.worldx + x + TILE_SIZE;
				damageArea[3] = gp.worldy + y + spriteNum * TILE_SIZE + TILE_SIZE;
				
				image1 = downAttack[spriteNum-1];
			}
			break;
		}
		case "left": {
			if (attacking == false) {
				image2 = null;
				image1 = left[spriteNum-1];
			}
			if (attacking == true) {
				image2 = bmr_left;
			
				damageArea[0] = gp.worldx + x - spriteNum * TILE_SIZE;
				damageArea[1] = gp.worldy + y;
				damageArea[2] = gp.worldx + x - spriteNum * TILE_SIZE + TILE_SIZE;
				damageArea[3] = gp.worldy + y + TILE_SIZE;
				image1 = leftAttack[0];
			}
			break;
		}
		case "right": {
			if (attacking == false) {
				image2 = null;
				image1 = right[spriteNum-1];
			}
			if (attacking == true) {
				image2 = bmr_right;
				
				damageArea[0] = gp.worldx + x + spriteNum * TILE_SIZE;
				damageArea[1] = gp.worldy + y;
				damageArea[2] = gp.worldx + x + spriteNum * TILE_SIZE + TILE_SIZE;
				damageArea[3] = gp.worldy + y + TILE_SIZE;
				image1 = rightAttack[0];
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
		
//		selfCenterX = x + TILE_SIZE/2;
//		selfCenterY = y + TILE_SIZE/2;
	
		graphics2d.drawImage(image2, damageArea[0], damageArea[1], null);
		graphics2d.drawImage(image1, x, y, null);
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
		graphics2d.fillRoundRect(TILE_SIZE-2, 6, 120 + 4, 16, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(TILE_SIZE, 8, (int)(hp/1000.0 * 120), 12, 4, 4);
		
		graphics2d.setColor(Color.gray);
		graphics2d.fillRoundRect(TILE_SIZE- 2, 26, 100 + 4, 16, 4, 4);
		graphics2d.setColor(Color.blue);
		graphics2d.fillRoundRect(TILE_SIZE, 28, (int)(mp/100.0 * 100), 12, 4, 4);
		
		
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 10F));
		graphics2d.setColor(Color.white);
		text = Integer.toString(hp);
		graphics2d.drawString(text, 60, 18);
		text = Integer.toString(mp);
		graphics2d.drawString(text, 60, 38);
	}
	
private void drawSkill(Graphics2D graphics2d) {
		
		graphics2d.setColor(Color.black);
		graphics2d.drawImage(skillbar, 0, SCREEN_HEIGHT - TILE_SIZE, null);
		
		graphics2d.drawImage(skill1, 0, SCREEN_HEIGHT - TILE_SIZE, null);
		
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
				graphics2d.drawImage(skill1, gp.worldx+x, gp.worldy+y,  null);
			}
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			if (mp < skill1MP || skill1waiting != 0) {
				graphics2d.drawImage(impossible, 0, SCREEN_HEIGHT-TILE_SIZE, null);
			}
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill1waiting/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill1waiting/60), TILE_SIZE/2 - length/2, SCREEN_HEIGHT - TILE_SIZE/3);
			
		}
		
		if (skill1waiting == 0) {
			skill1EffectiveTime = 3*60;
		}
	}
}

