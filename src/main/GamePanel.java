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
	Monster monster;
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
		monster = new Monster(this);
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
			touching(player, monster);
		}
			
	}
	
	
	private void touching(Fighter p, Monster m) {
		int d = (p.selfLocX - m.selfLocX) * (p.selfLocX - m.selfLocX) + (p.selfLocY - m.selfLocY) * (p.selfLocY - m.selfLocY);
		if (d < tileSize * tileSize)
			{
			if (f % 60 == 0)
				p.hp -= (int)((float)(m.attack * (100 - p.defense) / 100));
			f++;
			}
		else {
			f = 0;
//			if ()
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
			tileManager.draw(graphics2d);
			
	//		PLAYER
			player.draw(graphics2d);
			
	//		MONSTER
			monster.draw(graphics2d);
			
	//		UI
			ui.draw(graphics2d);		
		}
		
		if (gameState == guideState) {
			ui.draw(graphics2d);
		}
		graphics2d.dispose();
	}
	

}
