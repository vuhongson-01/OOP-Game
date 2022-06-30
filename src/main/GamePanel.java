package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.DataTruncation;
import java.util.Currency;

import java.lang.Math;
import javax.swing.JPanel;

import entity.Fighter;
import entity.Monster;
import entity.BossH;
import entity.DarkKnight;
import tile.TileManager;

/**
 * @author vuhongsonchv1619gmail.com
 * This class operate the game play
 */

public class GamePanel extends JPanel implements Runnable{

//	Set up size of tile, scale, screen,..
	final int originalTileSize = 16; //16x16
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; //48x48
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;	//768px
	public final int screenHeight = tileSize * maxScreenRow;	//576px
	public int deltaX, deltaY;
	
//	Create object
	Thread gameThread;
	KeyHandler keyHandler = new KeyHandler(this);
	TileManager tileManager = new TileManager(this, 1, 48, 48);
//	Fighter player;
	DarkKnight player;
	Monster monster;
	BossH boss;
	public UI ui = new UI(this);
	
	
//	player default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
// set up map
	
	
//	setup FPS
	int FPS = 60;
	int f = 0;
	
//	GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int guideState = 3;
	
	
	public GamePanel() {
//	setup game panel
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);	
//	create player
//		player = new Fighter(this, keyHandler);
		player = new DarkKnight(this, keyHandler);
		monster = new Monster(this);
		boss = new BossH(this);
		
	}

	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameState = titleState;
		gameThread.start();
	}
	
	
	public void run() { 
// 		Setup FPS = 60
		
		double drawInterval = 1e9/FPS;
		double delta = 0;
		double lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
 		while (gameThread != null) {
 			// create FPS for game 		
 			currentTime = System.nanoTime();
 			delta += (currentTime - lastTime) / drawInterval;
 			timer += (currentTime - lastTime);
 			lastTime = currentTime;
 			
 			if (delta >= 1) {
	//			1 UPDATE: update information such as character position
				update();
	//			2 DRAW: draw screen with the updated information
				repaint();
				delta--;
				drawCount++;
			}
 			
 			if (timer >= 1e9) {
 				drawCount = 0;
 				timer = 0;
 			}
		}
	}
	
	
	public void update() {
		if (gameState == playState) {
			player.update(tileManager.mapdemo);
			monster.update(tileManager.mapdemo);
			boss.update(tileManager.mapdemo);
			sensing(player, boss);
			sensing(player, monster);
			fight(player, monster);
			fight(player, boss);

			// Determine the coordinates on the screen
			if((player.worldX > screenWidth / 2) && (screenWidth / 2 < tileManager.worldWidth - player.worldX)) deltaX = player.worldX - screenWidth / 2;
			else if (player.worldX <= screenWidth / 2) deltaX = 0;
			else if (screenWidth / 2 >= tileManager.worldWidth - player.worldX) deltaX = tileManager.worldWidth - screenWidth;
				
			if((player.worldY > screenHeight / 2) && (screenHeight / 2 < tileManager.worldHeight - player.worldY)) deltaY = player.worldY - screenHeight / 2;
			else if (player.worldY <= screenHeight / 2) deltaY = 0;
			else if (screenHeight / 2 >= tileManager.worldHeight - player.worldY) deltaY = tileManager.worldHeight - screenHeight;
			
			player.screenX = player.worldX - deltaX;
			player.screenY = player.worldY - deltaY;
			
			monster.screenX = monster.worldX - deltaX;	
			monster.screenY = monster.worldY - deltaY;
			
			boss.screenX = boss.worldX - deltaX;
			boss.screenY = boss.worldY - deltaY;	
		}	
	}
	
	
	private void sensing(DarkKnight p, BossH b) {
		b.enemyScreenX = p.screenX;
		b.enemyScreenY = p.screenY;
		int distance = (p.selfCenterX - b.selfCenterX) * (p.selfCenterX - b.selfCenterX) + (p.selfCenterY - b.selfCenterY) * (p.selfCenterY - b.selfCenterY);
		if (distance < 9 * tileSize * tileSize) {
			b.attacking = true;
			if(b.f_attack == 25) p.hp -= b.attack;
			if(b.f_attack == 60) b.f_attack = 0;
			if(p.selfCenterX < b.selfCenterX) {
				BossH.direction = 180;
			}
			else {
				BossH.direction = 0;
			}
		

		}else {
			b.attacking = false;
			b.f_attack = 0;
		}
		if(p.worldX > 20 * tileSize && p.worldY < 13 * tileSize) b.enemyEntered = true;
		else b.enemyEntered = false;
	}
	
	private void sensing(DarkKnight p, Monster m) {
		m.enemyScreenX = p.screenX;
		m.enemyScreenY = p.screenY;
		int distance = (p.selfCenterX - m.selfCenterX) * (p.selfCenterX - m.selfCenterX) + (p.selfCenterY - m.selfCenterY) * (p.selfCenterY - m.selfCenterY);
		if (distance < 9 * tileSize * tileSize && distance > tileSize * tileSize / 4) {
			m.provoked = true;
			if(p.worldX < m.worldX) m.direction = 180;
			else m.direction = 0;
		}else {
			m.provoked = false;
			if(distance < tileSize * tileSize / 4 && distance > 0) {
				if(p.worldX < m.worldX) m.directionAttack = 180;
				else m.directionAttack = 0;
				m.attacking = true;
				if(m.f_attack == 65) p.hp -= m.attack;
			}
		}
	}
	
	private void fight(DarkKnight p, Monster m) {
		if((p.damageAreaX2 > m.selfAreaX1 &&  p.damageAreaX2 < m.selfAreaX2) && ((p.damageAreaY1 > m.selfAreaY1 && p.damageAreaY1 < m.selfAreaY2) || (p.damageAreaY2 > m.selfAreaY1 && p.damageAreaY2 < m.selfAreaY2))) {
			m.hp -= p.attack;
			p.stopAttack();
		}
		if((p.damageAreaX1 > m.selfAreaX1 &&  p.damageAreaX1 < m.selfAreaX2) && ((p.damageAreaY1 > m.selfAreaY1 && p.damageAreaY1 < m.selfAreaY2) || (p.damageAreaY2 > m.selfAreaY1 && p.damageAreaY2 < m.selfAreaY2))) {
			m.hp -= p.attack;
			p.stopAttack();
		}
	}
	
	private void fight(DarkKnight p, BossH m) {
		if((m.selfAreaX1 < p.damageAreaX1 && p.damageAreaX1 < m.selfAreaX2) && (m.selfAreaY1 < p.damageAreaY1 && p.damageAreaY1 < m.selfAreaY2)) {
			m.hp -= p.attack;
			p.stopAttack();
		}
	}


//	draw object in screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics2d = (Graphics2D) g;

//		TITLE STATE
		if (gameState == titleState) {
			ui.draw(graphics2d);
		}
		
		if (gameState == playState) {
	//		TILE		
			tileManager.draw(graphics2d, this);
			
	//		PLAYER
			player.draw(graphics2d);
			
			
	//		MONSTER
			monster.draw(graphics2d);
	//		BOSS
			boss.draw(graphics2d);
			
	//		UI
			ui.draw(graphics2d);		
		}
		
		if (gameState == guideState) {
			ui.draw(graphics2d);
		}
		graphics2d.dispose();
	}
}
