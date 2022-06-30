package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Struct;

import main.GamePanel;


public class BossH extends Entity{

	private final int defaultHP = 100;
	private final int defaultMP = 100;
	BufferedImage left1, left2, left3, left4, right1, right2, right3, right4;
	BufferedImage up1, up2, up3, up4, down1, down2, down3, down4;
	BufferedImage attack1, skill1, skill2, skill3, gate1, gate2, gate3, gate4;
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
		setDefaultValue();
		getbossImage();
	}
	
	public void setDefaultValue() {
//		location for draw image
		worldX = 1000;
		worldY = 300;
		
//		location of self in screen
		selfCenterX = screenX + gp.tileSize * 2;
		selfCenterY = screenY + gp.tileSize * 2;
		
		hp = defaultHP;
		mp = defaultMP;
		attack = 15;
		defense = 10;
		atkSpeed = 60;
		speed = 1;
		

	}
	
	public void getbossImage() { 
		// scale x2 tileSize
		right1 = setup("/boss/boss_right_1", gp.tileSize * 4, gp.tileSize * 4);
		right2 = setup("/boss/boss_right_2", gp.tileSize * 4, gp.tileSize * 4);
		right3 = setup("/boss/boss_right_3", gp.tileSize * 4, gp.tileSize * 4);
		right4 = setup("/boss/boss_right_2", gp.tileSize * 4, gp.tileSize * 4);

		
		left1 = setup("/boss/boss_left_1", gp.tileSize * 4, gp.tileSize * 4);
		left2 = setup("/boss/boss_left_2", gp.tileSize * 4, gp.tileSize * 4);
		left3 = setup("/boss/boss_left_3", gp.tileSize * 4, gp.tileSize * 4);
		left4 = setup("/boss/boss_left_2", gp.tileSize * 4, gp.tileSize * 4);

		up1 = setup("/boss/boss_up_1", gp.tileSize * 4, gp.tileSize * 4);
		up2 = setup("/boss/boss_up_2", gp.tileSize * 4, gp.tileSize * 4);
		up3 = setup("/boss/boss_up_3", gp.tileSize * 4, gp.tileSize * 4);
		up4 = setup("/boss/boss_up_2", gp.tileSize * 4, gp.tileSize * 4);

		
		down1 = setup("/boss/boss_down_1", gp.tileSize * 4, gp.tileSize * 4);
		down2 = setup("/boss/boss_down_2", gp.tileSize * 4, gp.tileSize * 4);
		down3 = setup("/boss/boss_down_3", gp.tileSize * 4, gp.tileSize * 4);
		down4 = setup("/boss/boss_down_2", gp.tileSize * 4, gp.tileSize * 4);
	
		attack1 = setup("/boss/boss_lighting", gp.tileSize, gp.tileSize * 2);
		skill1 = setup("/boss/ice_drop_1", gp.tileSize * 2, gp.tileSize * 2);
		skill2 = setup("/boss/ice_drop_2", gp.tileSize * 2, gp.tileSize * 2);
		skill3 = setup("/boss/ice_drop_3", gp.tileSize * 2, gp.tileSize * 2);
		
		gate1 = setup("/boss/gate_1", gp.tileSize * 2, gp.tileSize * 2);
		gate2 = setup("/boss/gate_2", gp.tileSize * 2, gp.tileSize * 2);
		gate3 = setup("/boss/gate_3", gp.tileSize * 2, gp.tileSize * 2);
		gate4 = setup("/boss/gate_4", gp.tileSize * 2, gp.tileSize * 2);
		
		die1 = setup("/boss/boss_die_1", gp.tileSize * 4, gp.tileSize * 4);
		die2 = setup("/boss/boss_die_2", gp.tileSize * 4, gp.tileSize * 4);

	}
	
	public void update(int [][] map) {
		f++;
		if(hp > 0) {
			selfCenterX = screenX + gp.tileSize * 2;
			selfCenterY = screenY + gp.tileSize * 2;
			
			if (!attacking) {
				if (f % 180 == 0) { 
					direction = (direction + 90) % 360;
					f = 0;
				}
				
				if (direction == 0) {
					worldX += speed;	
				}
				else if (direction == 90)
					worldY -= speed;
				else if (direction == 180)
					worldX -= speed;
				else worldY += speed;
				
//				update self area
				selfAreaX1 = selfCenterX - gp.tileSize*2;
				selfAreaY1 = selfCenterY - gp.tileSize*2;
				selfAreaX2 = selfCenterX + gp.tileSize*2;
				selfAreaY2 = selfCenterY + gp.tileSize*2;			
			}
			else {
				f_attack++;
			}
			if(enemyEntered) {
				f_skill++;
				if(f_skill % 180 < 10) {
					locateX = enemyScreenX + gp.deltaX;
					locateY = enemyScreenY + gp.deltaY;
				}
			}
		}else {
			f_die++;
		}
		
		
	}

	public void draw(Graphics2D graphics2d) {
		if(hp > 0) {
			drawHealthBar(graphics2d);
			if (!attacking) {
				if (direction == 180) {
					if (f % 60 < 25) {
						graphics2d.drawImage(left1, screenX, screenY, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(left2, screenX, screenY, null);
					}else if (f % 60 < 55) {
						graphics2d.drawImage(left3, screenX, screenY, null);
					}else graphics2d.drawImage(left4, screenX, screenY, null);
				}
				else if (direction == 0){
					if (f % 60 < 25) {
						graphics2d.drawImage(right1,screenX, screenY, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(right2, screenX, screenY, null);
					}else if (f % 60 < 55) {
						graphics2d.drawImage(right3, screenX, screenY, null);
					}else graphics2d.drawImage(right4, screenX, screenY, null);
				}
				else if (direction == 90) {
					if (f % 60 < 25) {
						graphics2d.drawImage(up1, screenX, screenY, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(up2, screenX, screenY, null);
					}else if (f % 60 < 55) {
						graphics2d.drawImage(up3, screenX, screenY, null);
					}else graphics2d.drawImage(up4, screenX, screenY, null);
				}
				else if (direction == 270) {
					if (f % 60 < 25) {
						graphics2d.drawImage(down1,screenX, screenY, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(down2, screenX, screenY, null);
					}else if (f % 60 < 55) {
						graphics2d.drawImage(down3, screenX, screenY, null);
					}else graphics2d.drawImage(down4, screenX, screenY, null);
				}
			}else {
				if (direction == 180) {
					if (f_attack % 60 < 20) {
						graphics2d.drawImage(left1, screenX, screenY, null);
					}
					else if (f_attack % 60 < 30){
						graphics2d.drawImage(left2, screenX, screenY, null);
						graphics2d.drawImage(attack1,  enemyScreenX + gp.tileSize / 2,  enemyScreenY - gp.tileSize,  null);
					}else if (f_attack % 60 < 55) {
						graphics2d.drawImage(left3,screenX, screenY, null);				
					}else {
						graphics2d.drawImage(left4, screenX, screenY, null);	
					}					
				}
				else if (direction == 0){
					if (f_attack % 60 < 20) {
						graphics2d.drawImage(right1, screenX, screenY, null);					
					}
					else if (f_attack % 60 < 30){
						graphics2d.drawImage(right2, screenX, screenY, null);					
						graphics2d.drawImage(attack1,  enemyScreenX + gp.tileSize / 2,  enemyScreenY - gp.tileSize,  null);
					}else if (f_attack % 60 < 55) {
						graphics2d.drawImage(right3,screenX, screenY, null);					
					}else {
						graphics2d.drawImage(right4, screenX, screenY, null);				
					}					
				}
			}
			if(enemyEntered) {
				if(f_skill % 180 < 10) {
				
				}else if(f_skill % 180 < 20) graphics2d.drawImage(skill1, locateX - gp.deltaX, locateY - gp.tileSize * 2 - gp.deltaY, null);
				else if(f_skill % 180 < 25) graphics2d.drawImage(skill1, locateX - gp.deltaX, locateY - gp.tileSize - gp.deltaY , null);
				else if(f_skill % 180 < 30) graphics2d.drawImage(skill2, locateX - gp.deltaX, locateY - gp.tileSize - gp.deltaY, null);
				else if(f_skill % 180 < 40) graphics2d.drawImage(skill3, locateX - gp.deltaX, locateY - gp.tileSize - gp.deltaY, null);
				else {
					        
				}
			}
		
		}else {		
			if(f_die % 40 < 10) graphics2d.drawImage(gate1, 30 * gp.tileSize - gp.deltaX, 2 * gp.tileSize - gp.deltaY, null);
			else if(f_die % 40 < 20) graphics2d.drawImage(gate2, 30 * gp.tileSize - gp.deltaX, 2 * gp.tileSize - gp.deltaY, null);
			else if(f_die % 40 < 30) graphics2d.drawImage(gate3, 30 * gp.tileSize - gp.deltaX, 2 * gp.tileSize - gp.deltaY, null);
			else graphics2d.drawImage(gate4, 30 * gp.tileSize - gp.deltaX, 2 * gp.tileSize - gp.deltaY, null);
			
			if(f_die < 20) graphics2d.drawImage(die1, screenX, screenY, null);
			else if(f_die < 40) graphics2d.drawImage(die2,  screenX,  screenY,  null);
		}
			
		
	}

	private void drawHealthBar(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.PLAIN, 12F));
		graphics2d.setColor(Color.black);
		
		graphics2d.drawString(name, screenX, screenY -10);
		
		graphics2d.fillRoundRect(screenX - 2, screenY - 5, defaultHP, 10, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(screenX , screenY - 4, hp, 8, 4, 4);
		
	}
}