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
import entity.Boss1;
import entity.Entity;
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
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;	//768px
	public final int screenHeight = tileSize * maxScreenRow;	//576px
	
//	Create object
	Thread gameThread;
	KeyHandler keyHandler = new KeyHandler(this);
	TileManager tileManager = new TileManager(this, 1);
	Fighter player;
	Boss1 monster;
	public UI ui = new UI(this);
	
	
//	player default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	
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
		player = new Fighter(this, keyHandler);
		monster = new Boss1(this);
//		
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
//			
			sensing(player, monster);
		}
			
	}
	
	
	private void sensing(Fighter p, Boss1 m) {
//		int d = (p.selfCenterX - m.selfCenterX) * (p.selfCenterX - m.selfCenterX) + (p.selfCenterY - m.selfCenterY) * (p.selfCenterY - m.selfCenterY);
//		if (d < tileSize * tileSize){
//			m.attacking = true;
//			
//			if (m.f_attack == 0)
//				m.f_attack = 1;
//			
//			if (p.selfCenterX < m.selfCenterX) m.directionAttack = 180;
//			else m.directionAttack = 0;
//
//			if (f % 120 == 0)
//				p.hp -= (int)((float)(m.attack * (100 - p.defense) / 100));
//			f++;
//		
//		
//		else {
//			if (m.f_attack >= 120) {
//				m.attacking = false;
//				m.f_attack = 0;
//			}
//				
//		}
//		
		if (intersec(p.selfArea, m.damageArea) || among(p.selfArea, m.damageArea) || among(m.damageArea, p.selfArea)) {
			if (m.attacking)
				p.decreHP(m.attack);
			if (p.attacking)
				m.decreHP(p.attack);
		}
	}


	private boolean intersec(int[] Area1, int[] Area2) {
			if (Area1[0] < Area2[2] && Area2[0] < Area1[0] && Area1[1] > Area2[1] && Area1[1] < Area2[3]) return true;
			if (Area1[2] < Area2[2] && Area2[0] < Area1[2] && Area1[1] > Area2[1] && Area1[1] < Area2[3]) return true;
			if (Area1[0] < Area2[2] && Area2[0] < Area1[0] && Area1[3] > Area2[1] && Area1[3] < Area2[3]) return true;
			if (Area1[2] < Area2[2] && Area2[0] < Area1[2] && Area1[3] > Area2[1] && Area1[3] < Area2[3]) return true;
			return false;
		}
	
	private boolean among(int[] Area1, int[] Area2) {
		float centerX = (float)(Area1[0] + Area1[2]) / 2;
		float centerY = (float)(Area1[1] + Area1[3]) / 2;
		
		if (centerX < Area2[2] && centerX > Area2[0] && centerY < Area2[3] && centerY > Area2[1]) return true;
		return false;
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
			tileManager.draw(graphics2d);
			
	//		PLAYER
			player.draw(graphics2d);
			
	//		MONSTER
			monster.draw(graphics2d);
//			
	//		UI
			ui.draw(graphics2d);		
		}
		
		if (gameState == guideState) {
			ui.draw(graphics2d);
		}
		graphics2d.dispose();
	}
	

}
