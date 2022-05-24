package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Monster extends Entity{

	GamePanel gp;
	BufferedImage monster1, monster2;
	Fighter fighter;
	
	public boolean excitement = false;
	
	int tileSize; 
	public int hp, mp, attack, defense;
	private final int defaultHP = 1000;
	public int selfLocX;
	public int selfLocY;
	int f = 0;
	
	private int maxDistance = 180;
	private int areaX, areaY;
	
	public Monster(GamePanel gp) {
		this.gp = gp;
		
		setDefaultValue();
		getMonsterImage();
	}
	
	
	private void getMonsterImage() {
		monster1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		monster2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
	}
	
	
	private void setDefaultValue() {
		x = 500;
		y = 100;
		
//		monster can not move so far from area
		areaX = x;
		areaY = y;
		
//		location of self in screen
		selfLocX = x + gp.tileSize;
		selfLocY = y + gp.tileSize;
		
		hp = defaultHP;
//		hp = 250;
		attack = 15;
		defense = 10;
		speed = 1;
		action = "down";	
	}

	
	public void update(int [][] map) {
		f++;
//		System.out.println(f + "---");
		if (!excitement) { 
			if (f % 180 == 0 || isBlocked(map, x, y)) {
				speed = -speed;
				f = 0;
			}
		}
		else {
			
		}
		
		x += speed;
		selfLocX = x + gp.tileSize/2;
		selfLocY = y + gp.tileSize/2;
	}
	
	int squareDistance (int x, int y) {
		return x*x + y*y;
	}
	
	private boolean isBlocked(int[][] map, int x, int y) {
		if (speed < 0) {
			if (map[(y + gp.tileSize/2) / gp.tileSize][(x + speed) / gp.tileSize] != 1 && 
				map[(y + gp.tileSize) / gp.tileSize][(x + speed) / gp.tileSize] != 1) 
				return true;
		}
		else {
			if (map[(y + gp.tileSize/2) / gp.tileSize][(x + speed + gp.tileSize) / gp.tileSize] != 1 && 
					map[(y + gp.tileSize) / gp.tileSize][(x + speed + gp.tileSize) / gp.tileSize] != 1) 
					return true;
		}
			
		return false;
	}


	public void draw(Graphics2D g2) {
		if (f % 15 < 10) {
			g2.drawImage(monster1, x, y, null);
		}
		else {
			g2.drawImage(monster2, x, y, null);
		}
	}
}
