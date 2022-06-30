package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DataTruncation;
import java.util.Currency;

import java.lang.Math;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.plaf.multi.MultiTextUI;

//import entity.Fighter;
import screen.StateBackground;
import entity.Boss1;
import entity.Boss5;
import entity.BossH;
import entity.DarkKnight;
import entity.Entity;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable, GameInterface{

//	Set up size of tile, scale, screen,..
	final int originalTileSize = 16; //16x16
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; //48x48
	
//	Create object
	Thread gameThread;
	KeyHandler keyHandler;
//	TileManager tileManager = new TileManager(this, 0);
	StateBackground stateBackground;
	DarkKnight player;
	Boss5 monster;
	public UI ui = new UI(this);
	
//	player default position
	public int worldx = 0;
	public int worldy = 0;
	
	int playerSpeed = 4;
	boolean isPause = false;
	private int level = -1;
	
//	setup FPS
	int FPS = 60;
	int f = 0;
	
//	GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	int sound = 1;
	
	public GamePanel() {
//	setup game panel
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		JButton soundBtn = new JButton();
		soundBtn.setFocusable(true);
		setupButton(soundBtn, "res/button/soundBtnOpen.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH - TILE_SIZE, TILE_SIZE*2);
		
		this.add(soundBtn);

		keyHandler = new KeyHandler(this);
		
		
		this.setFocusable(true);
		stateBackground = new StateBackground(this);
		stateBackground.setup(0, 48, 48);
		player = new DarkKnight(this, keyHandler, stateBackground.mapDemo, 4*TILE_SIZE, 4*TILE_SIZE);
		nextGameState();
		
		this.addKeyListener(keyHandler);
		
		soundBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				sound = 1-sound;
				if (sound == 0) {
					muteSound();
					setupButton(soundBtn, "res/button/soundBtnClose.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH - TILE_SIZE, 0);
				}
				else {
					openSound();
					setupButton(soundBtn, "res/button/soundBtnOpen.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH - TILE_SIZE, 0);
				}
				
			}

			private void openSound() {
				// TODO Auto-generated method stub
				System.out.println("open sound");
			}

			private void muteSound() {
				// TODO Auto-generated method stub
				System.out.println("close sound");
			}
		});
	}
	public void nextGameState() {
		level++;
		if (level == 0) {
			monster = new Boss5(this);
			worldx = 0;
			worldy = 0;
		}
		if (level == 1) {
			stateBackground.setup(level, 32, 32);
			player.setDefaultValue(TILE_SIZE, TILE_SIZE*10);
			worldx = 0;
			worldy = 0;
		}
	}
	
	void setupButton(JButton button, String iconPath, int width, int height, int x, int y) {
    	ImageIcon icon = new ImageIcon(iconPath);
		button.setIcon(resizeIcon(icon, width, height));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setBounds(x, y, width, height);
    }
	
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        return new ImageIcon(resizedImage);
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
			player.update(stateBackground.mapDemo);
			monster.update(stateBackground.mapDemo);

			sensing(player, monster);
		}
	}
	
	
	private void sensing(DarkKnight p, Boss5 m) {
		if (d(p.selfArea, m.selfArea) < TILE_SIZE*3/2) {
			m.attacking = true;
			if (m.f_attack == 0) {
			if ((p.selfArea[0] + p.selfArea[2])/2.0 < (m.selfArea[0] + m.selfArea[2])/2.0) {
				m.directionAttack = 180;
			}
			else {
				m.directionAttack = 0;
			}}
		}
		else {
			m.attacking = false;
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
			stateBackground.draw(graphics2d,level, -worldx, -worldy);
			
	//		PLAYER
			player.draw(graphics2d);
			
	//		MONSTER
			monster.draw(graphics2d);
		}
		else if (gameState == pauseState) {
			String text = "PAUSE";
			
			int x, y;
			graphics2d.setColor(Color.white);
			graphics2d.setFont(new Font("courier", Font.BOLD, 30));
			x = getXForCenterMetrics(graphics2d, text);
			y = SCREEN_HEIGHT/2;
			
			g.drawString(text, x, y);
		}

		graphics2d.dispose();
	}
	public int getXForCenterMetrics(Graphics2D graphics2d, String text) {
		int length = (int)graphics2d.getFontMetrics().getStringBounds(text, graphics2d).getWidth();
		int x = SCREEN_WIDTH/2 - length/2;
		return x;
	}
	
	public void playerTakeMeleeAttack(int attack) {
		if (intersec(player.damageArea, monster.selfArea) || among(player.damageArea, monster.selfArea) || among(monster.selfArea, player.damageArea)) {
			System.out.println("attack " + attack );
			monster.decreHP(attack);
		}		
	}
	public boolean playerTakeRangeAttack(int attack) {
		if (intersec(player.damageArea, monster.selfArea) || among(player.damageArea, monster.selfArea) || among(monster.selfArea, player.damageArea)) {
			System.out.println("attack " + attack );
			monster.decreHP(attack);
			return true;
		}		
		
		return false;
	}
	
	public void monsterTakeAttack(int attack) {
		if (intersec(monster.damageArea, player.selfArea) || among(monster.damageArea, player.selfArea) || among(player.selfArea, monster.damageArea)) {
			System.out.println("attack " + attack );
			player.decreHP(attack);
		}
	}
}
