package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Monster2 extends Entity{

	private final int defaultHP = 100;
	private final int defaultMP = 100;
	BufferedImage monster1, monster2, shoot, attacked;

	public Monster2(GamePanel gp) {
		this.gp = gp;
		
		setDefaultValue();
		getMonsterImage();
	}
	
	public void setDefaultValue() {
//		location for draw image
		x = 500;
		y = 100;
		
//		location of self in screen
//		selfCenterX = x + gp.tileSize;
//		selfCenterY = y + gp.tileSize;
//		
		hp = defaultHP;
//		hp = 250;
		mp = defaultMP;
		attack = 15;
		defense = 10;
		speed = 1;
//		action = "down";
	}
	
	public void getMonsterImage() {
		monster1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		monster2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		shoot = setup("/monster/monsterShoot", gp.tileSize, gp.tileSize);
		attacked = setup("/monster/monster_attacked", gp.tileSize, gp.tileSize);
		
	}
	
	public void update(int [][] map) { // update player's position
		
		f++;
		
		if (f % 180 == 0 || isBlocked(map, x, y)) {
			speed = -speed;
			f = 0;
		}
		
		x += speed;
		
//		selfAreaX1 = x + 3;
//		selfAreaY1 = y + 18;
//		selfAreaX2 = x + 45;
//		selfAreaY2 = y + 48;
	}

	private boolean isBlocked(int[][] map, int x, int y) {
		
		if (speed < 0) {
			if (map[(y + gp.tileSize/2) / gp.tileSize][(x + speed) / gp.tileSize] != 1 &&
				map[(y + gp.tileSize) / gp.tileSize][(x + speed) / gp.tileSize] != 1) return true;
		}
		else {
			if (map[(y + gp.tileSize/2) / gp.tileSize][(x + gp.tileSize + speed) / gp.tileSize] != 1 &&
				map[(y + gp.tileSize) / gp.tileSize][(x + gp.tileSize + speed) / gp.tileSize] != 1) return true;
		}
		return false;
	}
	

	public void draw(Graphics2D graphics2d) {
//		selfCenterX = x + gp.tileSize;
//		selfCenterY = y + gp.tileSize;
		if (f % 15 < 10) graphics2d.drawImage(monster1, x, y, null);
		else {
			graphics2d.drawImage(monster2, x, y, null);
		}
	}


}
