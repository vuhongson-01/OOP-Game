package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Struct;

import main.GamePanel;
import main.KeyHandler;

public class Boss1 extends Entity{

	private final int defaultHP = 100;
	private final int defaultMP = 100;
	BufferedImage left1, left2, left3, left4, right1, right2, right3, right4;
	BufferedImage up1, up2, up3, up4, down1, down2, down3, down4;
	BufferedImage attack_left1, attack_left2, attack_left3, attack_left4, attack_left5;
	BufferedImage attack_right1, attack_right2, attack_right3, attack_right4, attack_right5;
	int direction = 0;
	
	public int f_attack = 0;
	
	String name;
	
	
	public Boss1(GamePanel gp) {
		this.gp = gp;
		
		selfArea = new int[4];
		damageArea = new int[4];
		
		selfArea[0] = x + 16;
		selfArea[1] = y + 10;
		selfArea[2] = x + 64;
		selfArea[3] = y + 76;
		name = "B.Son";
		setDefaultValue();
		getboss1Image();
	}
	
	public void setDefaultValue() {
//		location for draw image
		x = 450;
		y = 300;
		
//		location of self in screen
		selfCenterX = x + gp.tileSize;
		selfCenterY = y + gp.tileSize;
		
		hp = defaultHP;
//		hp = 250;
		mp = defaultMP;
		attack = 15;
		defense = 10;
		speed = 1;
//		action = "down";
	}
	
	public void getboss1Image() { 
		// scale x2 tileSize
		right1 = setup("/boss/boss1_right_1", gp.tileSize * 2, gp.tileSize * 2);
		right2 = setup("/boss/boss1_right_2", gp.tileSize * 2, gp.tileSize * 2);
		right3 = setup("/boss/boss1_right_3", gp.tileSize * 2, gp.tileSize * 2);
		right4 = setup("/boss/boss1_right_4", gp.tileSize * 2, gp.tileSize * 2);
		
		left1 = setup("/boss/boss1_left_1", gp.tileSize * 2, gp.tileSize * 2);
		left2 = setup("/boss/boss1_left_2", gp.tileSize * 2, gp.tileSize * 2);
		left3 = setup("/boss/boss1_left_3", gp.tileSize * 2, gp.tileSize * 2);
		left4 = setup("/boss/boss1_left_4", gp.tileSize * 2, gp.tileSize * 2);
		
		up1 = setup("/boss/boss1_up_1", gp.tileSize * 2, gp.tileSize * 2);
		up2 = setup("/boss/boss1_up_2", gp.tileSize * 2, gp.tileSize * 2);
		up3 = setup("/boss/boss1_up_3", gp.tileSize * 2, gp.tileSize * 2);
		up4 = setup("/boss/boss1_up_4", gp.tileSize * 2, gp.tileSize * 2);
		
		down1 = setup("/boss/boss1_down_1", gp.tileSize * 2, gp.tileSize * 2);
		down2 = setup("/boss/boss1_down_2", gp.tileSize * 2, gp.tileSize * 2);
		down3 = setup("/boss/boss1_down_3", gp.tileSize * 2, gp.tileSize * 2);
		down4 = setup("/boss/boss1_down_4", gp.tileSize * 2, gp.tileSize * 2);
		
		attack_right1 = setup("/boss/boss1_right_attack_1", gp.tileSize * 4, gp.tileSize * 2);
		attack_right2 = setup("/boss/boss1_right_attack_2", gp.tileSize * 4, gp.tileSize * 2);
		attack_right3 = setup("/boss/boss1_right_attack_3", gp.tileSize * 4, gp.tileSize * 2);
		attack_right4 = setup("/boss/boss1_right_attack_4", gp.tileSize * 4, gp.tileSize * 2);
		attack_right5 = setup("/boss/boss1_right_attack_5", gp.tileSize * 4, gp.tileSize * 2);
		
		attack_left1 = setup("/boss/boss1_left_attack_1", gp.tileSize * 4, gp.tileSize * 2);
		attack_left2 = setup("/boss/boss1_left_attack_2", gp.tileSize * 4, gp.tileSize * 2);
		attack_left3 = setup("/boss/boss1_left_attack_3", gp.tileSize * 4, gp.tileSize * 2);
		attack_left4 = setup("/boss/boss1_left_attack_4", gp.tileSize * 4, gp.tileSize * 2);
		attack_left5 = setup("/boss/boss1_left_attack_5", gp.tileSize * 4, gp.tileSize * 2);
		
	}
	
	public void update(int [][] map) {
		
		if (!attacking) {
			f++;
			if (f % 120 == 0 || isBlocked(map, x, y)) { // go around square after every 2s or blocked by topographic
//				System.out.println(f); 
				direction = (direction + 90) % 360;
				f = 0;
			}
			
			if (direction == 0) {
				x += speed;	
			}
			else if (direction == 90)
				y -= speed;
			else if (direction == 180)
				x -= speed;
			else y += speed;
			
//			update self area
			selfAreaX1 = x + 16;
			selfAreaY1 = y + 10;
			selfAreaX2 = x + 64;
			selfAreaY2 = y + 76;			
		}
		else {
			f_attack++;
//			System.out.println(f_attack);
		}

	}

	private boolean isBlocked(int[][] map, int x, int y) {
		
		if (direction == 180) {
			if (map[(y + gp.tileSize / 2) / gp.tileSize][(x - speed) / gp.tileSize] != 1 &&
				map[(y + gp.tileSize * 2) / gp.tileSize][(x - speed) / gp.tileSize] != 1) return true;
		}
		if (direction == 0){
			if (map[(y + gp.tileSize/2) / gp.tileSize][(x + gp.tileSize * 2 + speed) / gp.tileSize] != 1 &&
				map[(y + gp.tileSize * 2) / gp.tileSize][(x + gp.tileSize * 2 + speed) / gp.tileSize] != 1) return true;
		}
		if (direction == 90) {
			if (map[(y + gp.tileSize / 2 - speed) / gp.tileSize][x / gp.tileSize] != 1 &&
				map[(y + gp.tileSize / 2 - speed) / gp.tileSize][(x + gp.tileSize * 2) / gp.tileSize] != 1) return true;
		}
		if (direction == 270) {
			if (map[(y + gp.tileSize * 2 + speed) / gp.tileSize][x / gp.tileSize] != 1 &&
				map[(y + gp.tileSize * 2 + speed) / gp.tileSize][(x + gp.tileSize * 2) / gp.tileSize] != 1) return true;
		}
		return false;
	}
	

//	void attack() {
//		
//	}
	
	public void draw(Graphics2D graphics2d) {
		selfCenterX = x + gp.tileSize;
		selfCenterY = y + gp.tileSize;
		
		drawHealthBar(graphics2d);
		
		if (!attacking) {
			if (direction == 180) {
				
				if (f % 60 < 15) {
					graphics2d.drawImage(left1, x, y, null);
				}
				else if (f % 60 < 30){
					graphics2d.drawImage(left2, x, y, null);
				}	
				else if (f % 60 < 45){
					graphics2d.drawImage(left3, x, y, null);
				}
				else {
					graphics2d.drawImage(left4, x, y, null);
				}
			}
			else if (direction == 0){

				if (f % 60 < 15) {
					graphics2d.drawImage(right1, x, y, null);
				}
				else if (f % 60 < 23){
					graphics2d.drawImage(right2, x, y, null);
				}	
				else if (f % 60 < 45){
					graphics2d.drawImage(right3, x, y, null);
				}
				else {
					graphics2d.drawImage(right4, x, y, null);
				}
			}
			else if (direction == 90) {

				if (f % 60 < 15) {
					graphics2d.drawImage(up1, x, y, null);
				}
				else if (f % 60 < 23){
					graphics2d.drawImage(up2, x, y, null);
				}	
				else if (f % 60 < 45){
					graphics2d.drawImage(up3, x, y, null);
				}
				else {
					graphics2d.drawImage(up4, x, y, null);
				}
			}
			else if (direction == 270) {
				if (f % 60 < 15) {
					graphics2d.drawImage(down1, x, y, null);
				}
				else if (f % 60 < 23){
					graphics2d.drawImage(down2, x, y, null);
				}	
				else if (f % 60 < 45){
					graphics2d.drawImage(down3, x, y, null);
				}
				else {
					graphics2d.drawImage(down4, x, y, null);
				}
			}			
		}
		else {
			if (directionAttack == 0) {
				
				selfArea[0] = x + 16;
				selfArea[1] = y + 10;
				selfArea[2] = x + gp.tileSize*2;
				selfArea[3] = y + 76;
				
				if (f_attack % 120 < 20) {
					graphics2d.drawImage(attack_right1, x, y, null);
				}
				else if (f_attack % 120 < 40){
					graphics2d.drawImage(attack_right2, x, y, null);
				}	
				else if (f_attack % 120 < 60){
					graphics2d.drawImage(attack_right3, x, y, null);
				}
				else if (f_attack % 120 < 80){
					damageArea[0] = gp.tileSize * 2 - 10;
					damageArea[1] = gp.tileSize - 10;
					damageArea[2] = gp.tileSize * 2 + 30;
					damageArea[3] = gp.tileSize + 30;
					graphics2d.drawImage(attack_right4, x, y, null);
				}
				else {
					graphics2d.drawImage(attack_right5, x, y, null);
				}		
			}
			if (directionAttack == 180) {
				
				selfArea[0] = x + gp.tileSize * 2;
				selfArea[1] = y + 10;
				selfArea[2] = x + gp.tileSize * 4;
				selfArea[3] = y + 76;
				
				if (f_attack % 120 < 20) {
					graphics2d.drawImage(attack_left1, x - gp.tileSize * 2, y, null);
				}
				else if (f_attack % 120 < 40){
					graphics2d.drawImage(attack_left2, x - gp.tileSize * 2, y, null);
				}	
				else if (f_attack % 120 < 60){
					graphics2d.drawImage(attack_left3, x - gp.tileSize * 2, y, null);
				}
				else if (f_attack % 120 < 80){
					damageArea[0] = gp.tileSize * 2 - 10;
					damageArea[1] = gp.tileSize - 10;
					damageArea[2] = gp.tileSize * 2 + 30;
					damageArea[3] = gp.tileSize + 30;
					graphics2d.drawImage(attack_left4, x - gp.tileSize * 2, y, null);
				}
				else {
					graphics2d.drawImage(attack_left5, x - gp.tileSize * 2, y, null);
				}	
			}
		}
	}

	private void drawHealthBar(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.PLAIN, 12F));
		graphics2d.setColor(Color.white);
		
		graphics2d.drawString(name, x, y-10);
		
		graphics2d.fillRoundRect(x-2, y-5, gp.tileSize * 2 + 4, 10, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(x, y-4, (int)(hp/defaultHP * gp.tileSize * 2), 8, 4, 4);
		
	}
}
