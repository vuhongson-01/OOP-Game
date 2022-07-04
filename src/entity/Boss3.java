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
	BufferedImage gate[] = new BufferedImage[10];
	BufferedImage die1, die2, die3;

	int direction = 0;
	
	public int f_attack = 0;
	public int f_die = 0;
	public int f_skill = 0;
	
	public boolean enemyEntered = false;
	
	String name;
	
	
	public Boss3(GamePanel gp) {
		this.gp = gp;
		
		selfArea = new int[4];
		damageArea = new int[4];
		name = "BossxPhoenix";
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
			right[i] = setup("/boss/boss3_right_" + (i+1), TILE_SIZE * 3, TILE_SIZE * 3);
			left[i] = setup("/boss/boss3_left_" + (i+1), TILE_SIZE * 3, TILE_SIZE * 3);
			up[i] = setup("/boss/boss3_up_" + (i+1), TILE_SIZE * 3, TILE_SIZE * 3);
			down[i] = setup("/boss/boss3_down_" + (i+1), TILE_SIZE * 3, TILE_SIZE * 3);
			rightAttack[i] = setup("/boss/boss3_right_attack_" + (i+1), (TILE_SIZE * 4), TILE_SIZE * 3);
			leftAttack[i] = setup("/boss/boss3_left_attack_" + (i+1), (TILE_SIZE * 4), TILE_SIZE * 3);
			gate[i] = setup("/boss/gate_" + (i+1), TILE_SIZE * 2, TILE_SIZE * 2);
		}
		die1 = setup("/boss/boss3_died_1", (int) (TILE_SIZE * 3), TILE_SIZE * 3);
		die2 = setup("/boss/boss3_died_2", (int) (TILE_SIZE * 3), TILE_SIZE * 3);
		die3 = setup("/boss/boss3_died_3", (int) (TILE_SIZE * 2), TILE_SIZE * 2);
	}
	
	public void update(int [][] map) {
		f++;
		if(hp > 0) {
		if (!attacking) {
			if (f % 360 == 0 || isBlocked(map, x, y)) {
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
			selfArea[0] = x;
			selfArea[1] = y;
			selfArea[2] = x + 4*TILE_SIZE;
			selfArea[3] = y + 4*TILE_SIZE;	
			
			System.out.println(selfArea[0] + " - -" + selfArea[1] + " - -" + selfArea[2] + " - -" + selfArea[3]);		
		}
		else {
			f_attack++;
		}
	}else {
		f_die++;
		selfArea[0] = -1;
		selfArea[1] = -1;
		selfArea[2] = -1;
		selfArea[3] = -1;	
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
	if(hp > 0) {
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
				damageArea[0] = x + TILE_SIZE * 2 + 10;
				damageArea[1] = y + TILE_SIZE - 10;
				damageArea[2] = x + 30;
				damageArea[3] = y + TILE_SIZE + 30;
				if (f_attack == 50) gp.monsterTakeAttack(attack);
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
				damageArea[0] = x - TILE_SIZE * 2 + 10;
				damageArea[1] = y + TILE_SIZE - 10;
				damageArea[2] = x + 30;
				damageArea[3] = y + TILE_SIZE + 30;
				if (f_attack == 50) gp.monsterTakeAttack(attack);
				graphics2d.drawImage(leftAttack[3], x - TILE_SIZE , y, null);
			}
		}
	}
	}else {		
		if(f_die % 40 < 10) graphics2d.drawImage(gate[0], 3 * TILE_SIZE - gp.worldx, 2 * TILE_SIZE - gp.worldy, null);
		else if(f_die % 40 < 20) graphics2d.drawImage(gate[1], 3 * TILE_SIZE - gp.worldx, 2 * TILE_SIZE - gp.worldy, null);
		else if(f_die % 40 < 30) graphics2d.drawImage(gate[2], 3 * TILE_SIZE - gp.worldx, 2 * TILE_SIZE - gp.worldy, null);
		else graphics2d.drawImage(gate[3], 3 * TILE_SIZE - gp.worldx, 2 * TILE_SIZE - gp.worldy, null);
		
		if(f_die < 10) graphics2d.drawImage(die1, x - gp.worldx, y - gp.worldy, null);
		else if(f_die < 23) graphics2d.drawImage(die2,  x - gp.worldx,  y - gp.worldy,  null);
		else graphics2d.drawImage(die3,  x - gp.worldx,  y - gp.worldy,  null);
	}

}

private void drawHealthBar(Graphics2D graphics2d) {
	graphics2d.setFont(graphics2d.getFont().deriveFont(Font.PLAIN, 12F));
	graphics2d.setColor(Color.white);
	
	graphics2d.drawString(name, x-gp.worldx, y-gp.worldy-10);
	
	graphics2d.fillRoundRect(x-gp.worldx-2, y-gp.worldy-5, TILE_SIZE * 3 + 4, 10, 4, 4);
	graphics2d.setColor(Color.red);
	graphics2d.fillRoundRect(x-gp.worldx, y-gp.worldy-4, (int)(hp*1.0/defaultHP * TILE_SIZE * 3), 8, 4, 4);
	}
}