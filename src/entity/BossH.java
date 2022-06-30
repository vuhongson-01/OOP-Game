package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Struct;

import main.GameInterface;
import main.GamePanel;


public class BossH extends Entity implements GameInterface{

	private final int defaultHP = 1000;
	private final int defaultMP = 100;
	BufferedImage attack1, skill1, skill2, skill3;
	BufferedImage gate[] = new BufferedImage[10];
	BufferedImage die1, die2;

	public static int direction = 0;
	
	public int f_attack = 0;
	public int f_skill = 0;
	public int f_die = 0;
	public int enemyScreenX, enemyScreenY;
	int locateX, locateY;
	
	public boolean enemyEntered = false;
	
	
	String name;
	
	
	public BossH(GamePanel gp) {
		this.gp = gp;
		
		name = "DRAKE - LV100";
		selfArea = new int[4];
		damageArea = new int[4];
		setDefaultValue();
		getbossImage();
	}
	
	public void setDefaultValue() {
//		location for draw image
		x = 200;
		y = 300;
		
		hp = defaultHP;
		mp = defaultMP;
		attack = 15;
		defense = 10;
		attackSpeed = 60;
		speed = 1;
	}
	
	public void getbossImage() { 
		// scale x2 tileSize
		for (int i = 0; i < 3; i++) {
			right[i] = setup("/boss/boss_right_" + (i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			left[i] = setup("/boss/boss_left_" + (i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			up[i] = setup("/boss/boss_up_" + (i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			down[i] = setup("/boss/boss_down_" + (i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			gate[i] = setup("/boss/gate_" + (i+1), TILE_SIZE * 2, TILE_SIZE * 2);
		}

		attack1 = setup("/boss/boss_lighting", TILE_SIZE, TILE_SIZE*2);
		die1 = setup("/boss/boss_die_1", TILE_SIZE * 4, TILE_SIZE * 4);
		die2 = setup("/boss/boss_die_2", TILE_SIZE * 4, TILE_SIZE * 4);

	}
	
	public void update(int [][] map) {
		f++;
		if(hp > 0) {
			if (!attacking) {
				if (f % 180 == 0) { 
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
				
//				update self area
				selfArea[0] = x;
				selfArea[1] = y;
				selfArea[2] = x + 4*TILE_SIZE;
				selfArea[3] = y + 4*TILE_SIZE;	
				
				System.out.println(selfArea[0] + " - -" + selfArea[1] + " - -" + selfArea[2] + " - -" + selfArea[3]);
			}
			else {
				f_attack++;
			}
			
			if(enemyEntered) {
				f_skill++;
				if(f_skill % 180 < 10) {
					locateX = enemyScreenX + gp.worldx;
					locateY = enemyScreenY + gp.worldy;
				}
			}
		}else {
			f_die++;
			selfArea[0] = -1;
			selfArea[1] = -1;
			selfArea[2] = -1;
			selfArea[3] = -1;	
		}
		
		
	}

	public void draw(Graphics2D graphics2d) {
		if(hp > 0) {
			drawHealthBar(graphics2d);
			if (!attacking) {
				if (direction == 180) {
					if (f % 60 < 25) {
						graphics2d.drawImage(left[0], x - gp.worldx, y - gp.worldy, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(left[1], x - gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 55) {
						graphics2d.drawImage(left[2], x - gp.worldx, y - gp.worldy, null);
					}else graphics2d.drawImage(left[1], x - gp.worldx, y - gp.worldy, null);
				}
				else if (direction == 0){
					if (f % 60 < 25) {
						graphics2d.drawImage(right[0], x - gp.worldx, y - gp.worldy, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(right[1], x - gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 55) {
						graphics2d.drawImage(right[2], x - gp.worldx, y - gp.worldy, null);
					}else graphics2d.drawImage(right[1], x - gp.worldx, y - gp.worldy, null);
				}
				else if (direction == 90) {
					if (f % 60 < 25) {
						graphics2d.drawImage(up[0], x - gp.worldx, y - gp.worldy, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(up[1], x - gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 55) {
						graphics2d.drawImage(up[2], x - gp.worldx, y - gp.worldy, null);
					}else graphics2d.drawImage(up[1], x - gp.worldx, y - gp.worldy, null);
				}
				else if (direction == 270) {
					if (f % 60 < 25) {
						graphics2d.drawImage(down[0], x - gp.worldx, y - gp.worldy, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(down[1], x - gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 55) {
						graphics2d.drawImage(down[2], x - gp.worldx, y - gp.worldy, null);
					}else graphics2d.drawImage(down[1], x - gp.worldx, y - gp.worldy, null);
				}
			}else {
				if (direction == 180) {
					if (f_attack % 60 < 20) {
						graphics2d.drawImage(left[0], x - gp.worldx, y - gp.worldy, null);
					}
					else if (f_attack % 60 < 30){
						graphics2d.drawImage(left[1], x - gp.worldx, y - gp.worldy, null);
						graphics2d.drawImage(attack1,  enemyScreenX + TILE_SIZE / 2,  enemyScreenY - TILE_SIZE,  null);
					}else if (f_attack % 60 < 55) {
						graphics2d.drawImage(left[2], x - gp.worldx, y - gp.worldy, null);				
					}else {
						graphics2d.drawImage(left[1], x - gp.worldx, y - gp.worldy, null);	
					}					
				}
				else if (direction == 0){
					if (f_attack % 60 < 20) {
						graphics2d.drawImage(right[0], x - gp.worldx, y - gp.worldy, null);					
					}
					else if (f_attack % 60 < 30){
						graphics2d.drawImage(right[1], x - gp.worldx, y - gp.worldy, null);					
						graphics2d.drawImage(attack1,  enemyScreenX + TILE_SIZE / 2,  enemyScreenY - TILE_SIZE,  null);
					}else if (f_attack % 60 < 55) {
						graphics2d.drawImage(right[2],x - gp.worldx, y - gp.worldy, null);					
					}else {
						graphics2d.drawImage(right[1], x - gp.worldx, y - gp.worldy, null);				
					}					
				}
			}
			if(enemyEntered) {
				if(f_skill % 180 < 10) {
				
				}else if(f_skill % 180 < 20) graphics2d.drawImage(skill1, locateX - gp.worldx, locateY - TILE_SIZE * 2 - gp.worldy, null);
				else if(f_skill % 180 < 25) graphics2d.drawImage(skill1, locateX - gp.worldx, locateY - TILE_SIZE - gp.worldy , null);
				else if(f_skill % 180 < 30) graphics2d.drawImage(skill2, locateX - gp.worldx, locateY - TILE_SIZE - gp.worldy, null);
				else if(f_skill % 180 < 40) graphics2d.drawImage(skill3, locateX - gp.worldx, locateY - TILE_SIZE - gp.worldy, null);
				else {
					        
				}
			}
		
		}else {		
			if(f_die % 40 < 10) graphics2d.drawImage(gate[0], 30 * TILE_SIZE - gp.worldx, 2 * TILE_SIZE - gp.worldy, null);
			else if(f_die % 40 < 20) graphics2d.drawImage(gate[1], 30 * TILE_SIZE - gp.worldx, 2 * TILE_SIZE - gp.worldy, null);
			else if(f_die % 40 < 30) graphics2d.drawImage(gate[2], 30 * TILE_SIZE - gp.worldx, 2 * TILE_SIZE - gp.worldy, null);
			else graphics2d.drawImage(gate[3], 30 * TILE_SIZE - gp.worldx, 2 * TILE_SIZE - gp.worldy, null);
			
			if(f_die < 20) graphics2d.drawImage(die1, x - gp.worldx, y - gp.worldy, null);
			else if(f_die < 40) graphics2d.drawImage(die2,  x - gp.worldx,  y - gp.worldy,  null);
		}
			
		
	}

	private void drawHealthBar(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.PLAIN, 12F));
		graphics2d.setColor(Color.white);
		
		graphics2d.drawString(name, x-gp.worldx, y-10-gp.worldy);
		
		graphics2d.fillRoundRect(x-2-gp.worldx, y-5-gp.worldy, gp.tileSize * 2 + 4, 10, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(x-gp.worldx, y-4-gp.worldy, (int)(hp*1.0/defaultHP * gp.tileSize * 2), 8, 4, 4);
		
	}
}