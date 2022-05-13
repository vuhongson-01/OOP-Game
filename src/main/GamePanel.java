package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Currency;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	final int originalTileSize = 16; //16x16
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; //48x48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;	//768px
	final int screenHeight = tileSize * maxScreenRow;	//576px
	
	Thread gameThread;
	KeyHandler keyHandler = new KeyHandler();
	Player player; 
	
//	player default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	int FPS = 60;
	TileManager tileManager = new TileManager(this, 1);
	
	public GamePanel() {
//	setup game panel
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);	
//	create player
		player = new Player(this, keyHandler);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void run() {
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
// 				System.out.println("FPS: " + drawCount);
 				drawCount = 0;
 				timer = 0;
 			}
		}
	}
	
	public void update() {
//		
//		for (int i = 0; i < 12; i++) {
//			for (int j = 0; j < 16; j++) {
//				System.out.print(tileManager.mapdemo[i][j]+ " ");
//			}
//			System.out.println();
//		}
		player.update(tileManager.mapdemo);
	}
	
//	draw player in screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics2d = (Graphics2D) g;
		
		tileManager.draw(graphics2d);
		player.draw(graphics2d);
		
		graphics2d.dispose();
	}
	

}
