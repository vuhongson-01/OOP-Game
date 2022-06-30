//package entity;
//
////import java.awt.AlphaComposite;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics2D;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.lang.invoke.LambdaMetafactory;
//import java.util.GregorianCalendar;
//
//import javax.imageio.ImageIO;
//
//import main.GameInterface;
//import main.GamePanel;
//import main.KeyHandler;
//import main.UtilityTool;
//
//public class Fighter extends Entity implements GameInterface{
//
//	KeyHandler keyHandler;
//	int tileSize; 
//	
//	private final int defaultHP = 1000;
//	private final int defaultMP = 100;
//	BufferedImage pointboard, skill2, skill1, skill3, skillbar, impossible;
//	
//	private int skill1waiting = 0; //pro-active skill - 10s waiting
//	private int skill1EffectiveTime = 3*60; //during 3s * 60FPS effective
//	private int skill1MP = 40; //skill1 need 60 MP
//	
//	private int skill2waiting = 0; //passive skill - 7s waiting
//	private int skill2EffectiveTime = 3*60; //during 3s effective
//	private int skill2MP = 40; //skill2 need 40 MP
//	
//	private int skill3waiting = 0;  //passive skill - 12s waiting
//	private int skill3EffectiveTime = 4*60; //during 4s * 60FPS effective
//	private int skill3MP = 50; //skill3 need 50 MP
//	
//	int [][] map;
//	
//	public Fighter(GamePanel gp, KeyHandler keyHandler, int[][] map, int firstLocationX, int firstLocationY) {
//		this.gp = gp;
//		this.keyHandler = keyHandler;
//		this.map = map;
//		tileSize = gp.tileSize;
//		
//		setDefaultValue(firstLocationX, firstLocationY);
//		getPlayerImage();
//	}
//	
//	public void getAttack(int damage) {
//		hp -= damage;
//	}
//	public void setDefaultValue(int firstLocationX, int firstLocationY) {
////		location for draw image
//		x = firstLocationX;
//		y = firstLocationY;
//
//		selfArea = new int [4];
//		damageArea = new int [4];
//		
//		selfArea[0] = gp.worldx + x + gp.tileSize/6;
//		selfArea[1] = gp.worldy + y + gp.tileSize/6;
//		selfArea[2] = gp.worldx + x+ gp.tileSize/6*5;
//		selfArea[3] = gp.worldy + y + gp.tileSize/6*5;
//				
//		hp = defaultHP;
//		mp = defaultMP;
//		attack = 15;
//		defense = 10;
//		speed = 2;
//		
//		action = "down";
//	}
//
//	
//	public void getPlayerImage() {
//			up[0] = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
//			up[1] = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
//			down[0] = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
//			down[1] = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
//			left[0] = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
//			left[1] = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
//			right[0] = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
//			right[1] = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
//			
//			upAttack1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
//			upAttack2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
//			downAttack1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
//			downAttack2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
//			leftAttack1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
//			leftAttack2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
//			rightAttack1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
//			rightAttack2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
//			
//			pointboard =  setup("/player/pointboard", gp.tileSize * 4, gp.tileSize);
//			impossible = setup("/player/impossible", gp.tileSize, gp.tileSize);
//			skill1 = setup("/player/skill1A", gp.tileSize, gp.tileSize); 
//			skill2 = setup("/player/skill2A", gp.tileSize, gp.tileSize); 
//			skill3 = setup("/player/skill3A", gp.tileSize, gp.tileSize); 
//			skillbar = setup("/player/skillbar", gp.tileSize* 3 , gp.tileSize); 
//	}
//	
//
//	public void update(int [][] map) { // update player's position
//		
//		f++;
//		
//		if (f % 50 == 0) {
//			if (mp < 100)
//				mp++;
//			f = 0;
//		}
//		
//		if (attacking == true) {
//			attacking();
////			spriteCounter++;
////			spriteCounter %= 25;
//		}
//		else {
//			if (keyHandler.attack== true) {
//				attacking = true;
//			}
//		
//			if (keyHandler.upPressed == true) {
//				action = "up";
//				spriteCounter++;
//				preAction = action;
////				*notice: 4 vertexes
//				if (map[(gp.worldy + y + tileSize/3 - speed) / tileSize][(x + gp.worldx)/ tileSize] == 1 
//					&& map[(gp.worldy + y + tileSize/3 - speed) / tileSize][(gp.worldx + x + tileSize) / tileSize] == 1) {
//					if (gp.worldy > 0 && y == TILE_SIZE * 4) gp.worldy -= speed;
//					else {
//						if (gp.worldy == 0) y -= speed;
//						else {
//							if (y > TILE_SIZE*4) y -= speed;
//						}
//					}
//				}	
//			}
//			if (keyHandler.downPressed == true) {
//				action = "down";
//				preAction = action;
//				spriteCounter++;
////				*notice: 4 vertexes
//				if (map[(gp.worldy + y + speed + tileSize) / tileSize][(gp.worldx + x) / tileSize] == 1 
//					&& map[(gp.worldy + y + speed + tileSize) / tileSize][(gp.worldx + x + tileSize) / tileSize] == 1) {
//					
//					if (y == SCREEN_HEIGHT - TILE_SIZE*4 && gp.worldy < TILE_SIZE*48 - SCREEN_HEIGHT) gp.worldy += speed;
//					else {
//						if (gp.worldy == TILE_SIZE*48 - SCREEN_HEIGHT) y += speed;
//						else {
//							if (y < SCREEN_HEIGHT - TILE_SIZE*4) {
//								y += speed;
//							}						
//						}
//					}
//				}
//					
//			}
//			if (keyHandler.leftPressed == true) {
//				action = "left";
//				preAction = action;
//				spriteCounter++;
////				*notice: 4 vertexes
//				if (map[(gp.worldy + y + tileSize/3) / tileSize][(gp.worldx + x - speed) / tileSize] == 1 
//					&& map[(gp.worldy + y + tileSize)/tileSize][(gp.worldx + x - speed) / tileSize] == 1) {
//					
//					if(x == TILE_SIZE*4 && gp.worldx > 0) gp.worldx -= speed; 
//					else {
//						if (gp.worldx == 0) x -= speed;
//						else {
//							if (x > TILE_SIZE*4) x -= speed; 
//						}
//					}
//				}
//					
//			}
//			if (keyHandler.rightPressed == true) {
//				action = "right";
//				preAction = action;
//				spriteCounter++;
////				*notice: 4 vertexes
//				if (map[(gp.worldy + y + tileSize/3) / tileSize][(gp.worldx + x + speed + tileSize) / tileSize] == 1 
//					&& map[(gp.worldy + y + tileSize) / tileSize][(gp.worldx + y + speed + tileSize) / tileSize] == 1) {
//					
//					if (gp.worldx < TILE_SIZE*48 - SCREEN_WIDTH  && x == SCREEN_WIDTH - 4*TILE_SIZE) gp.worldx += speed;
//					else {
//						if (gp.worldx == TILE_SIZE*48 - SCREEN_WIDTH) x += speed;
//						else {
//							if (x < SCREEN_WIDTH - 4*TILE_SIZE) x += speed;
//						}
//					}
//				}
//				
//			}
//
//			selfArea[0] = gp.worldx + x + gp.tileSize/6;
//			selfArea[1] = gp.worldy + y + gp.tileSize/6;
//			selfArea[2] = gp.worldx + x+ gp.tileSize/6*5;
//			selfArea[3] = gp.worldy + y + gp.tileSize/6*5;
//			
//			if (spriteCounter > 15) {   //15 or any number if liking
//				if (spriteCounter <= 5) {
//					spriteNum = 2;
//				}
//				else if (spriteCounter <= 25){
//					spriteNum = 1;
//				}
//				spriteCounter = 0;
//			}	
//			
//		}
//		if ((gp.worldx + x) / TILE_SIZE == 10 && (gp.worldy+y)/TILE_SIZE == 10) {
//			gp.nextGameState();
//		}
//	}
//	
//	public void attacking() {
//		spriteCounter++;
//		
//		if (spriteCounter <= 5) {
//			spriteNum = 1;
//		}
//		else if (spriteCounter <= 25) {
//			spriteNum = 2;
//		}
//		else if (spriteCounter > 25){
//			spriteNum = 1;
//			spriteCounter = 0;
//			attacking = false;
//		}
//	}
//
//	public void draw(Graphics2D graphics2d) {
//		BufferedImage image = null;
//		drawPoint(graphics2d);
//		drawSkill(graphics2d);
//		
//		switch (action) {
//		case "up": {
//			if (attacking == false) {
//				
//				if (spriteCounter <= 5) { image = up[0];}
//				else if (spriteCounter <= 25) { image = up2;}		
//			}
//			if (attacking == true) {
//				if (spriteCounter <= 5) { image = upAttack1;}
//				else if (spriteCounter <= 25) { 
//					damageArea[0] = gp.worldx + x + 24;
//					damageArea[1] = gp.worldy + y + 24;
//					damageArea[2] = gp.worldx + x + 24;
//					damageArea[3] = gp.worldy + y + 24;
//					image = upAttack2;
//					if (spriteCounter == 20) gp.playerTakeAttack(attack);
//				}
//			}
//			break;
//		}
//		case "down": {
//			if (attacking == false) {
//				if (spriteCounter <= 5) { image = down1;}
//				else if(spriteCounter <= 25) { image = down2;}	
//			}
//			if (attacking == true) {
//				if (spriteCounter <= 5) { image = downAttack1;}
//				else if (spriteCounter <= 25) { 
//					damageArea[0] = gp.worldx + x + 18;
//					damageArea[1] = gp.worldy + y + 48;
//					damageArea[2] = gp.worldx + x + 30;
//					damageArea[3] = gp.worldy + y + 24;
//					image = downAttack2;
//					if (spriteCounter == 20) gp.playerTakeAttack(attack);
//				}
//			}
//			break;
//		}
//		case "left": {
//			if (attacking == false) {
//				if (spriteCounter <= 5) { image = left1;}
//				else if(spriteCounter <= 25) { image = left2;}	
//			}
//			if (attacking == true) {
//				if (spriteCounter <= 5) { 
//					image = leftAttack1;}
//				else if (spriteCounter <= 25) { 
//					damageArea[0] = gp.worldx + x + 24;
//					damageArea[1] = gp.worldy + y + 24;
//					damageArea[2] = gp.worldx + x + 54;
//					damageArea[3] = gp.worldy + y + 36;
//					image = leftAttack2;
//					if (spriteCounter == 20) gp.playerTakeAttack(attack);
//				}
//			}
//			break;
//		}
//		case "right": {
//			if (attacking == false) {
//				if (spriteCounter <= 5) { image = right1;}
//				else if(spriteCounter <= 25) { image = right2;}	
//			}
//			if (attacking == true) {
//				if (spriteCounter <= 5) { image = rightAttack1;}
//				else if (spriteCounter <= 25) { 
//					damageArea[0] = gp.worldx + x + 42;
//					damageArea[1] = gp.worldy + y + 24;
//					damageArea[2] = gp.worldx + x + 72;
//					damageArea[3] = gp.worldy + y + 42;
//					image = rightAttack2;
//					
//					if (spriteCounter == 20) gp.playerTakeAttack(attack);
//				}
//			}
//			break;
//		}
//		default:
//			throw new IllegalArgumentException("Unexpected value: ");
//		}
//		
//		if (skill1EffectiveTime == 3*60 || (skill1EffectiveTime < 0 && skill1waiting != 0)) {
//			if (image == leftAttack1 || image == leftAttack2) {
//
//				selfArea[0] = gp.worldx + x + gp.tileSize + 10;
//				selfArea[1] = gp.worldy + y + 10;
//				selfArea[2] = gp.worldx + x + gp.tileSize + 40;
//				selfArea[3] = gp.worldy + y + 40;
//				graphics2d.drawImage(image, x - tileSize, y, null);
//				}
//			else if (image == upAttack1 || image == upAttack2){
//				selfArea[0] = gp.worldx + x + 10;
//				selfArea[1] = gp.worldy + gp.tileSize + 10;
//				selfArea[2] = gp.worldx + x + 40;
//				selfArea[3] = gp.worldy + gp.tileSize + 40;
//				graphics2d.drawImage(image, x, y - tileSize, null);
//			}
//			else {
//				selfArea[0] = gp.worldx + x + 10;
//				selfArea[1] = gp.worldy + y + 10;
//				selfArea[2] = gp.worldx + x + 40;
//				selfArea[3] = gp.worldy + y + 40;
//				graphics2d.drawImage(image, x, y, null);
//			}			
//		}
//	}
//
//
//	private void drawSkill(Graphics2D graphics2d) {
//		
//		graphics2d.setColor(Color.black);
//		graphics2d.drawImage(skillbar, 0, SCREEN_HEIGHT-TILE_SIZE, null);
//		
//		graphics2d.drawImage(skill1, 0, SCREEN_HEIGHT-TILE_SIZE, null);
//		if (mp < skill1MP || skill1waiting != 0) {
//			graphics2d.drawImage(impossible, 0, SCREEN_HEIGHT-TILE_SIZE, null);
//		}
//		
////		skill1
//		if (mp > skill1MP && keyHandler.Askill1IsActive && skill1waiting == 0) {
//			keyHandler.Askill1IsActive = false;
//			mp -= skill1MP;
//			skill1waiting = 10 * 60;
//		}
//		
//		if (skill1waiting != 0) {
//			
//			skill1EffectiveTime--;
//			skill1waiting--;
//			
//			if (skill1EffectiveTime >= 0) {
//				if (skill1EffectiveTime % 12 < 3) {
//					damageArea[0] = gp.worldx + x + 24;
//					damageArea[1] = gp.worldy + y + 24;
//					damageArea[2] = gp.worldx + x + 54;
//					damageArea[3] = gp.worldy + y + 36;
//					if (skill1EffectiveTime % 12 == 2) gp.playerTakeAttack(attack/5);
//					graphics2d.drawImage(leftAttack2, x - tileSize, y, null);
//				}
//				else if (skill1EffectiveTime % 12 == 5){			
//					damageArea[0] = gp.worldx + x  + 24;
//					damageArea[1] = gp.worldy + y + 24;
//					damageArea[2] = gp.worldx + x  + 36;
//					damageArea[3] = gp.worldy + y + 48;
//					if (skill1EffectiveTime % 12 == 2) gp.playerTakeAttack(attack/5);
//					graphics2d.drawImage(upAttack2, x, y - tileSize, null);
//				}
//				else if (skill1EffectiveTime % 12 == 8) {
//					damageArea[0] = gp.worldx + x  + 42;
//					damageArea[1] = gp.worldy + y + 24;
//					damageArea[2] = gp.worldx + x  + 72;
//					damageArea[3] = gp.worldy + y + 42;
//					if (skill1EffectiveTime % 12 == 2) gp.playerTakeAttack(attack/5);
//					graphics2d.drawImage(rightAttack2, x, y, null);
//				}
//				else{
//					damageArea[0] = gp.worldx + x + 18;
//					damageArea[1] = gp.worldy + y + 48;
//					damageArea[2] = gp.worldx + x + 30;
//					damageArea[3] = gp.worldy + y + 24;
//					if (skill1EffectiveTime % 12 == 11) gp.playerTakeAttack(attack/5);
//					graphics2d.drawImage(downAttack2, x, y, null);
//				}
//			}
//			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
//			graphics2d.setColor(Color.white);
//			
//			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill1waiting/60), graphics2d).getWidth();
//			graphics2d.drawString(Integer.toString(skill1waiting/60), gp.tileSize/2 - length/2, SCREEN_HEIGHT-TILE_SIZE/2);
//		}
//		
//		if (skill1waiting == 0) {
//			skill1EffectiveTime = 3*60;
//		}
//		
//		graphics2d.drawImage(skill2, gp.tileSize, SCREEN_HEIGHT-TILE_SIZE, null);
//		if (mp < skill2MP || skill2waiting != 0) {
//			graphics2d.drawImage(impossible, gp.tileSize, SCREEN_HEIGHT-TILE_SIZE, null);
//		}
//		
////		skill2
//		if (mp > skill2MP && keyHandler.Askill2IsActive && skill2waiting == 0) {
//			keyHandler.Askill2IsActive = false;
//			skill2waiting = 7*60;
//			defense = 15;
//			mp -= skill2MP;
//		}
//
//		if (skill2waiting != 0) {
//			
//			skill2EffectiveTime--;
//			skill2waiting--;
//			
//			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
//			graphics2d.setColor(Color.white);
//			
//			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill2waiting/60), graphics2d).getWidth();
//			graphics2d.drawString(Integer.toString(skill2waiting/60), gp.tileSize + gp.tileSize/2 - length/2, SCREEN_HEIGHT-TILE_SIZE/2);
//		}
//		
//		if (skill2waiting == 0 || skill2EffectiveTime < 0) {
//			defense = 10;
//		}
//		
//		if (skill2waiting == 0) {
//			skill2EffectiveTime = 3 * 60;
//		}
//		
//		
////		skill3		
//		graphics2d.drawImage(skill3, gp.tileSize*2, SCREEN_HEIGHT-TILE_SIZE, null);
//		if (mp < skill3MP || skill3waiting != 0) {
//			graphics2d.drawImage(impossible, gp.tileSize*2, SCREEN_HEIGHT-TILE_SIZE, null);
//		}
//		
//		if (mp > skill3MP && keyHandler.Askill3IsActive && skill3waiting == 0) {
//			keyHandler.Askill3IsActive = false;
//			skill3waiting = 20 * 60;
//			mp -= skill3MP;
//		}
//		
//		if (skill3waiting != 0) {
//			
//			if (skill3EffectiveTime > 0 && skill3EffectiveTime % 60 == 0) {
//				if (hp + 70 < defaultHP) hp += 70;
//				else {
//					hp = defaultHP;
//				}
//			}
//			skill3EffectiveTime--;
//			skill3waiting--;
//
//			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
//			graphics2d.setColor(Color.white);
//			
//			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill3waiting/60), graphics2d).getWidth();
//			graphics2d.drawString(Integer.toString(skill3waiting/60), gp.tileSize*2 + gp.tileSize/2 - length/2, SCREEN_HEIGHT-TILE_SIZE/2);
//		}
//		
//		if (skill3waiting == 0) {
//			skill3EffectiveTime = 4*60;
//		}
//	}
//
//
//	public void drawPoint(Graphics2D graphics2d) {
//		graphics2d.drawImage(pointboard, 0, 0, null);
//		String text = "HP";
//		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
//		graphics2d.setColor(Color.white);
//		graphics2d.drawString(text, 10, 20);
//		
//		text = "MP";
//		graphics2d.drawString(text, 10, 40);
//		
//		graphics2d.setColor(Color.gray);
//		graphics2d.fillRoundRect(gp.tileSize-2, 6, 120 + 4, 16, 4, 4);
//		graphics2d.setColor(Color.red);
//		graphics2d.fillRoundRect(gp.tileSize, 8, (int)(hp/1000.0 * 120), 12, 4, 4);
//		
//		graphics2d.setColor(Color.gray);
//		graphics2d.fillRoundRect(gp.tileSize- 2, 26, 100 + 4, 16, 4, 4);
//		graphics2d.setColor(Color.blue);
//		graphics2d.fillRoundRect(gp.tileSize, 28, (int)(mp/100.0 * 100), 12, 4, 4);
//		
//		
//		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 10F));
//		graphics2d.setColor(Color.white);
//		text = Integer.toString(hp);
//		graphics2d.drawString(text, 60, 18);
//		text = Integer.toString(mp);
//		graphics2d.drawString(text, 60, 38);
//	}
//
//	private void updateSelfArea(int tlx, int tly, int brx, int bry) {
//		selfArea[0] = tlx;
//		selfArea[1] = tly;
//		selfArea[2] = brx;
//		selfArea[3] = bry;
//	}
//
//}
