package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Struct;

import main.GameInterface;
import main.GamePanel;

public class Boss3 extends Entity implements GameInterface{

	private final int defaultHP = 100;
	private final int defaultMP = 100;

	int direction = 0;
	
	public int f_attack = 0;
	
	String name;
	
	
	public Boss3(GamePanel gp) {
		this.gp = gp;
		
		selfArea = new int[4];
		damageArea = new int[4];
		name = "BossxHieu";
		setDefaultValue();
		getboss3Image();
	}
	
	public void setDefaultValue() {
//		location for draw image
		x = 600;
		y = 600;
		
		hp = defaultHP;
//		hp = 250;
		mp = defaultMP;
		attack = 15;
		defense = 10;
		speed = 2;
//		action = "down";
	}
	
	public void getboss3Image() { 
		// scale x2 tileSize
		for (int i = 0; i < 4; i++) {
			right[i] = setup("/boss/boss3_right_" + (i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			left[i] = setup("/boss/boss3_left_" + (i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			up[i] = setup("/boss/boss3_up_" + (i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			down[i] = setup("/boss/boss3_down_" + (i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			rightAttack[i] = setup("/boss/boss3_right_attack_" + (i+1), (int) (TILE_SIZE * 5.2), TILE_SIZE * 4);
			leftAttack[i] = setup("/boss/boss3_left_attack_" + (i+1), (int) (TILE_SIZE * 5.2), TILE_SIZE * 4);
		}
		died[0] = setup("/boss/boss3_died_1", (int) (TILE_SIZE * 5.2), TILE_SIZE * 4);
		died[1] = setup("/boss/boss3_died_2", (int) (TILE_SIZE * 5.2), TILE_SIZE * 4);
		died[2] = setup("/boss/boss3_died_3", (int) (TILE_SIZE * 5.2), TILE_SIZE * 4);
	}
	
	public void update(int [][] map) {
		
		if (!attacking) {
			f++;
			if (f % 360 == 0 || isBlocked(map, x, y)) { // go around square after every 6s or blocked by topographic
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
			selfArea[0] = x + 16;
			selfArea[1] = y + 10;
			selfArea[2] = x + 64;
			selfArea[3] = y + 76;			
		}
		else {
			f_attack++;
		}
	}

private boolean isBlocked(int[][] map, int x, int y) {
		
		if (direction == 180) {
			if (map[(y + TILE_SIZE / 2) / TILE_SIZE][(x - speed) / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE * 2) / TILE_SIZE][(x - speed) / TILE_SIZE] != 1) return true;
		}
		if (direction == 0){
			if (map[(y + TILE_SIZE/2) / TILE_SIZE][(x + TILE_SIZE * 2 + speed) / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE * 2) / TILE_SIZE][(x + TILE_SIZE * 2 + speed) / TILE_SIZE] != 1) return true;
		}
		if (direction == 90) {
			if (map[(y + TILE_SIZE / 2 - speed) / TILE_SIZE][x / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE / 2 - speed) / TILE_SIZE][(x + TILE_SIZE * 2) / TILE_SIZE] != 1) return true;
		}
		if (direction == 270) {
			if (map[(y + TILE_SIZE * 2 + speed) / TILE_SIZE][x / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE * 2 + speed) / TILE_SIZE][(x + TILE_SIZE * 2) / TILE_SIZE] != 1) return true;
		}
		return false;
	}
	

public void draw(Graphics2D graphics2d) {
	drawHealthBar(graphics2d);
	
	if (!attacking) {
		if (direction == 180) {
			if (f % 60 < 15) {
				graphics2d.drawImage(left[0], x, y, null);
			}
			else if (f % 60 < 30){
				graphics2d.drawImage(left[1], x, y, null);
			}	
			else if (f % 60 < 45){
				graphics2d.drawImage(left[2], x, y, null);
			}
			else {
				graphics2d.drawImage(left[3], x, y, null);
			}
		}
		else if (direction == 0){
			if (f % 60 < 15) {
				graphics2d.drawImage(right[0], x, y, null);
			}
			else if (f % 60 < 23){
				graphics2d.drawImage(right[1], x, y, null);
			}	
			else if (f % 60 < 45){
				graphics2d.drawImage(right[2], x, y, null);
			}
			else {
				graphics2d.drawImage(right[3], x, y, null);
			}
		}
		else if (direction == 90) {
			if (f % 60 < 15) {
				graphics2d.drawImage(up[0], x, y, null);
			}
			else if (f % 60 < 23){
				graphics2d.drawImage(up[1], x, y, null);
			}	
			else if (f % 60 < 45){
				graphics2d.drawImage(up[2], x, y, null);
			}
			else {
				graphics2d.drawImage(up[3], x, y, null);
			}
		}
		else if (direction == 270) {
			if (f % 60 < 15) {
				graphics2d.drawImage(down[0], x, y, null);
			}
			else if (f % 60 < 23){
				graphics2d.drawImage(down[1], x, y, null);
			}	
			else if (f % 60 < 45){
				graphics2d.drawImage(down[2], x, y, null);
			}
			else {
				graphics2d.drawImage(down[3], x, y, null);
			}
		}			
	}
	else {
		if (directionAttack == 0) {
			if (f_attack % 60 < 15) {
				graphics2d.drawImage(rightAttack[0], x, y, null);
			}
			else if (f_attack % 60 < 30){
				graphics2d.drawImage(rightAttack[1], x, y, null);
			}	
			else if (f_attack % 60 < 45){
				graphics2d.drawImage(rightAttack[2], x, y, null);
			}
			else {
				graphics2d.drawImage(rightAttack[3], x, y, null);
			}
		}
		if (directionAttack == 180) {
			if (f_attack % 60 < 15) {
				graphics2d.drawImage(leftAttack[0], x - TILE_SIZE , y, null);
			}
			else if (f_attack % 60 < 30){
				graphics2d.drawImage(leftAttack[1], x - TILE_SIZE, y, null);
			}	
			else if (f_attack % 60 < 45){
				graphics2d.drawImage(leftAttack[2], x - TILE_SIZE , y, null);
			}
			else {
				graphics2d.drawImage(leftAttack[3], x - TILE_SIZE , y, null);
			}
		}	
	}
	if(hp == 0) {
		if(f % 120 < 30) {
			graphics2d.drawImage(died[0], x , y, null);
		}
		else if(f % 120 < 60 ) {
			graphics2d.drawImage(died[1], x , y, null);
		}
		else {
			graphics2d.drawImage(died[2], x , y, null);
		}
	}
	
}


private void drawHealthBar(Graphics2D graphics2d) {
	graphics2d.setFont(graphics2d.getFont().deriveFont(Font.PLAIN, 12F));
	graphics2d.setColor(Color.white);
	
	graphics2d.drawString(name, x-gp.worldx, y-gp.worldy-10);
	
	graphics2d.fillRoundRect(x-gp.worldx-2, y-gp.worldy-5, TILE_SIZE * 4 + 4, 10, 4, 4);
	graphics2d.setColor(Color.red);
	graphics2d.fillRoundRect(x-gp.worldx, y-gp.worldy-4, (int)(hp*1.0/defaultHP * TILE_SIZE * 4), 8, 4, 4);
	}
}