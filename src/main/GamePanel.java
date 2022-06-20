package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DataTruncation;
import java.util.Currency;

import java.lang.Math;
import javax.swing.JPanel;

import entity.Fighter;
import screen.StateBackground;
import entity.Boss1;
import entity.Entity;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable, GameInterface{

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
	KeyHandler keyHandler;
//	TileManager tileManager = new TileManager(this, 0);
	StateBackground stateBackground;
	Fighter player;
	Boss1 monster;
	public UI ui = new UI(this);
	
//	player default position
	int playerX = TILE_SIZE*4;
	int playerY = TILE_SIZE*4;
	public int worldx = 0;
	public int worldy = 0;
	
	int playerSpeed = 4;
	boolean isPause = false;
	
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
		keyHandler = new KeyHandler(this);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
		stateBackground = new StateBackground(this, 0);
		
		player = new Fighter(this, keyHandler);
		monster = new Boss1(this);
	}

	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameState = playState;
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
			player.update(stateBackground.mapdemo);
			monster.update(stateBackground.mapdemo);
//			
			sensing(player, monster);
			
			if(player.x > screenWidth / 2 && player.y > screenHeight / 2) {
				monster.worldX = monster.x + screenWidth / 2 - player.x; 	monster.worldY = monster.y + screenHeight / 2 - player.y;
				monster.worldX = monster.x + screenWidth / 2 - player.x; 			monster.worldY = monster.y + screenHeight / 2 - player.y;
			}
			else if (player.x > screenWidth / 2 && player.y <= screenHeight / 2) {
				monster.worldX = monster.x + screenWidth / 2 - player.x; 	monster.worldY = monster.y;
				monster.worldX = monster.x + screenWidth / 2 - player.x; 			monster.worldY = monster.y;
			}
			else if (player.x <= screenWidth / 2 && player.y > screenHeight / 2) {
				monster.worldX = monster.x; 	monster.worldY = monster.y + screenHeight / 2 - player.y;
				monster.worldX = monster.x; 			monster.worldY = monster.y + screenHeight / 2 - player.y;
			}
			else {
				monster.worldX = monster.x; 	monster.worldY = monster.y;
				monster.worldX = monster.x; 			monster.worldY = monster.y;
			}
		}
	}
	
	
	private void sensing(Fighter p, Boss1 m) {

		if (d(p.selfArea, m.selfArea) < tileSize*3/2) {
			m.attacking = true;
			if ((p.selfArea[0] + p.selfArea[2])/2.0 < (m.selfArea[0] + m.selfArea[2])/2.0) {
				m.directionAttack = 180;
			}
			else {
				m.directionAttack = 0;
			}
		}
		else {
			m.attacking = false;
		}
		
		if (intersec(p.selfArea, m.damageArea) || among(p.selfArea, m.damageArea) || among(m.damageArea, p.selfArea)) {
			System.out.println(2);
			if (m.attacking)
				p.decreHP(m.attack);
			if (p.attacking)
				m.decreHP(p.attack);
		}
	}


	private boolean intersec(int[] Area1, int[] Area2) {
//		System.out.println(Area1[0] + "," + Area1[1] + " " + Area1[2] + "," + Area1[3] + " / " + Area2[0] + "," + Area2[1] + " " + Area2[2] + "," + Area2[3]);
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

	public float d(int [] Area1, int[] Area2) {
		float distance = 0;
		
		float centerX1 = (float)(Area1[0] + Area1[2]) / 2;
		float centerY1 = (float)(Area1[1] + Area1[3]) / 2;
		
		float centerX2 = (float)(Area2[0] + Area2[2]) / 2;
		float centerY2 = (float)(Area2[1] + Area2[3]) / 2;
		
		distance = (float) Math.sqrt((centerX1 - centerX2)*(centerX1-centerX2) + (centerY1 - centerY2)*(centerY1-centerY2));
		
		return distance;
	}
	
//	draw object in screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics2d = (Graphics2D) g;
		
		if (gameState == playState) {
	//		TILE		
			stateBackground.draw(graphics2d, -worldx, -worldy);
			
	//		PLAYER
			player.draw(graphics2d);
			
	//		MONSTER
			monster.draw(graphics2d);
//			
	//		UI
			ui.draw(graphics2d);		
		}
		graphics2d.dispose();
	}

//
//@Override
//public void actionPerformed(ActionEvent e) {
//	// TODO Auto-generated method stub
//	this.requestFocusInWindow();
//	
//}
	
	

}
