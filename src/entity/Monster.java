package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import main.GamePanel;


public class Monster extends Entity{

	
	GamePanel gp;

	public int hp, mp, attack, defense;
	String name;
	private final int defaultHP = 100;
	private final int defaultMP = 100;
	BufferedImage monster_left_1, monster_left_2, monster_left_3, monster_left_4;
	BufferedImage monster_right_1, monster_right_2, monster_right_3, monster_right_4;
	BufferedImage monster_attack_left_1, monster_attack_left_2, monster_attack_left_3, monster_attack_left_4, monster_attack_left_5;
	BufferedImage monster_attack_right_1, monster_attack_right_2, monster_attack_right_3, monster_attack_right_4, monster_attack_right_5;
	BufferedImage monster_die_1, monster_die_2, monster_die_3;
	
	public int enemyScreenX, enemyScreenY;
	public boolean provoked = false;
	public int runSpeed;
	public int direction = 0;
	
	public int f = 0;
	public int f_die = 0;
	public int f_attack = 0;
	
	public Monster(GamePanel gp) {
		this.gp = gp;
		
		setDefaultValue();
		getMonsterImage();
	}
	
	public void setDefaultValue() {
//		location for draw image
		worldX = 300;
		worldY = 100;
		
//		location of self in screen
		selfCenterX = screenX + gp.tileSize / 2;
		selfCenterY = screenY + gp.tileSize / 2;
		
		selfAreaX1 = selfCenterX - gp.tileSize / 2;
		selfAreaX2 = selfCenterX + gp.tileSize / 2;
		selfAreaY1 = selfCenterY - gp.tileSize / 2;
		selfAreaY2 = selfCenterY + gp.tileSize / 2;
		

		
		name = "slime LV1";
		hp = defaultHP;
//		hp = 250;
		mp = defaultMP;
		attack = 15;
		defense = 10;
		speed = 1;
		runSpeed = 2;
		
//		action = "down";
	}
	
	public void getMonsterImage() {
		monster_left_1 = setup("/monster/slime_left_1", gp.tileSize, gp.tileSize);
		monster_left_2 = setup("/monster/slime_left_2", gp.tileSize, gp.tileSize);
		monster_left_3 = setup("/monster/slime_left_3", gp.tileSize, gp.tileSize);
		monster_left_4 = setup("/monster/slime_left_4", gp.tileSize, gp.tileSize);
		
		monster_right_1 = setup("/monster/slime_right_1", gp.tileSize, gp.tileSize);
		monster_right_2 = setup("/monster/slime_right_2", gp.tileSize, gp.tileSize);
		monster_right_3 = setup("/monster/slime_right_3", gp.tileSize, gp.tileSize);
		monster_right_4 = setup("/monster/slime_right_4", gp.tileSize, gp.tileSize);
		
		monster_attack_left_1 = setup("/monster/slime_attack_left_1", gp.tileSize, gp.tileSize);		
		monster_attack_left_2 = setup("/monster/slime_attack_left_2", gp.tileSize, gp.tileSize);
		monster_attack_left_3 = setup("/monster/slime_attack_left_3", gp.tileSize, gp.tileSize);
		monster_attack_left_4 = setup("/monster/slime_attack_left_4", gp.tileSize, gp.tileSize);
		monster_attack_left_5 = setup("/monster/slime_attack_left_5", gp.tileSize, gp.tileSize);
		
		monster_attack_right_1 = setup("/monster/slime_attack_right_1", gp.tileSize, gp.tileSize);	
		monster_attack_right_2 = setup("/monster/slime_attack_right_2", gp.tileSize, gp.tileSize);	
		monster_attack_right_3 = setup("/monster/slime_attack_right_3", gp.tileSize, gp.tileSize);	
		monster_attack_right_4 = setup("/monster/slime_attack_right_4", gp.tileSize, gp.tileSize);	
		monster_attack_right_5 = setup("/monster/slime_attack_right_5", gp.tileSize, gp.tileSize);	
		
		monster_die_1 = setup("/monster/die_1", gp.tileSize, gp.tileSize);
		monster_die_2 = setup("/monster/die_2", gp.tileSize, gp.tileSize);
		monster_die_3 = setup("/monster/die_3", gp.tileSize, gp.tileSize);
		
	}
	
	public void update(int [][] map) { // update player's position
		
		if(hp > 0) {
			f++;
			
			if(!attacking) {
				if(!provoked) {
					if (f % 180 == 0 || isBlocked(map, worldX, worldY)) {
						direction = (direction + 180) % 360;
						f = 0;
					}
				
					if(direction == 0) worldX += speed;
					else worldX -= speed;
					selfAreaX1 = worldX + 6;
					selfAreaY1 = worldY + 18;
					selfAreaX2 = worldX + 45;
					selfAreaY2 = worldY + 48;
				}else {
					int directX = (enemyScreenX + gp.tileSize/2) - selfCenterX;
					int directY = (enemyScreenY + gp.tileSize/2) - selfCenterY;
					double d = Math.sqrt(directX * directX + directY * directY);
					if(d > 0) {
						worldX += runSpeed * directX / d;
						worldY += runSpeed * directY / d;
					}
				}
			}else {
				f_attack++;
				direction = directionAttack;
			}
		}else {
			f_die++;
			if(f_die >= 60) {
				screenX = -1;
				screenY = -1;
				worldX = -1;
				worldY = -1;
				selfAreaX1 = -1;
				selfAreaY1 = -1;
				selfAreaX2 = -1;
				selfAreaY1 = -1;
				
			}
		}
		
	}


	private boolean isBlocked(int[][] map, int worldX, int worldY) {
		
		if (speed < 0) {
			if (map[(worldY + gp.tileSize/2) / gp.tileSize][(worldX + speed) / gp.tileSize] != 1 &&
				map[(worldY + gp.tileSize) / gp.tileSize][(worldX + speed) / gp.tileSize] != 1) return true;
		}
		else {
			if (map[(worldY + gp.tileSize/2) / gp.tileSize][(worldX + gp.tileSize + speed) / gp.tileSize] != 1 &&
				map[(worldY + gp.tileSize) / gp.tileSize][(worldX + gp.tileSize + speed) / gp.tileSize] != 1) return true;
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2d) {
		selfCenterX = worldX + gp.tileSize / 2;
		selfCenterY = worldY + gp.tileSize / 2;
		if(hp > 0) {
			drawHealthBar(graphics2d);
			if(provoked) {
				if(direction == 0) {
					if(f % 60 < 15) graphics2d.drawImage(monster_right_1, screenX, screenY, null);
					else if (f % 60 < 30) graphics2d.drawImage(monster_right_2, screenX, screenY, null);
					else if (f % 60 < 45) graphics2d.drawImage(monster_right_3, screenX, screenY, null);
					else graphics2d.drawImage(monster_right_4, screenX, screenY, null);
				}
				else if(direction == 180) {
					if (f % 60 < 15) graphics2d.drawImage(monster_left_1, screenX, screenY, null);
					else if (f % 60 < 30) graphics2d.drawImage(monster_left_2, screenX, screenY, null);
					else if (f % 60 < 45) graphics2d.drawImage(monster_left_3, screenX, screenY, null);
					else graphics2d.drawImage(monster_left_4, screenX, screenY, null);
				}
			}
			
			if(!attacking) {
				if(direction == 180) {
					if (f % 60 < 15) graphics2d.drawImage(monster_left_1, screenX, screenY, null);
					else if (f % 60 < 30) graphics2d.drawImage(monster_left_2, screenX, screenY, null);
					else if (f % 60 < 45) graphics2d.drawImage(monster_left_3,screenX, screenY, null);
					else graphics2d.drawImage(monster_left_4, screenX, screenY, null);
				}
				else if(direction == 0) {
					if(f % 60 < 15) graphics2d.drawImage(monster_right_1, screenX, screenY, null);
					else if (f % 60 < 30) graphics2d.drawImage(monster_right_2, screenX, screenY, null);
					else if (f % 60 < 45) graphics2d.drawImage(monster_right_3, screenX, screenY, null);
					else graphics2d.drawImage(monster_right_4, screenX, screenY, null);
				}
			}else {
				if (directionAttack == 180) {
					if (f_attack % 120 < 10) graphics2d.drawImage(monster_attack_left_1, screenX, screenY, null);
					else if (f_attack % 120 < 20) graphics2d.drawImage(monster_attack_left_2, screenX, screenY, null);
					else if (f_attack % 120 < 50) graphics2d.drawImage(monster_attack_left_3, screenX, screenY, null);
					else if (f_attack % 120 < 55) graphics2d.drawImage(monster_attack_left_4, screenX, screenY, null);
					else if (f_attack % 120 < 80) graphics2d.drawImage(monster_attack_left_5, screenX, screenY, null);
					else if (f_attack % 120 < 100)graphics2d.drawImage(monster_left_1,  screenX, screenY,  null);
					else {
						attacking = false;
						f_attack = 0;
					}
				
				}
				else if (directionAttack == 0) {
					if (f_attack % 120 < 10) graphics2d.drawImage(monster_attack_right_1, screenX, screenY, null);
					else if (f_attack % 120 < 20) graphics2d.drawImage(monster_attack_right_2, screenX, screenY, null);
					else if (f_attack % 120 < 50) graphics2d.drawImage(monster_attack_right_3, screenX, screenY, null);
					else if (f_attack % 120 < 55) graphics2d.drawImage(monster_attack_right_4, screenX, screenY, null);
					else if (f_attack % 120 < 60) graphics2d.drawImage(monster_attack_right_5, screenX, screenY, null);
					else {
						attacking = false;
						f_attack = 0;
					}
				}
			}
		
		}else {
			if(f_die < 20) graphics2d.drawImage(monster_die_1,  screenX,  screenY,  null);
			else if(f_die < 40) graphics2d.drawImage(monster_die_2,  screenX,  screenY,  null);
			else if(f_die < 60) graphics2d.drawImage(monster_die_3,  screenX,  screenY,  null);
			else {

			}
		}
	}
	
	
	private void drawHealthBar(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.PLAIN, 12F));
		graphics2d.setColor(Color.black);
		
		graphics2d.drawString(name, screenX, screenY -10);
		
		graphics2d.fillRoundRect(screenX -2, screenY-5, 100, 10, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(screenX, screenY-4, hp, 8, 4, 4);
		
	}


}
