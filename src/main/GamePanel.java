package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.DataTruncation;
import java.util.Currency;

import java.lang.Math;
import java.security.PublicKey;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.plaf.multi.MultiTextUI;
import javax.xml.crypto.dsig.keyinfo.PGPData;

//import entity.Fighter;
import screen.StateBackground;
import entity.Boss1;
import entity.Boss3;
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
	JPanel confirmBoard;
	JPanel endGameBoard;
	JPanel victoryBoard;
//	TileManager tileManager = new TileManager(this, 0);
	DemoMain main;
	StateBackground stateBackground;
	DarkKnight player;
	Boss3 monster;
	public UI ui = new UI(this);
	
//	player default position
	public int worldx = 0;
	public int worldy = 0;
	
	int playerSpeed = 4;
	public boolean isPause = false;
	private int level = -1;
	
//	setup FPS
	int FPS = 60;
	int f = 0;
	
//	GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	int sound = 1;
	boolean visibleBoard = false;
	
	public GamePanel(DemoMain main) {
		//	setup game panel
    	setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(MAIN_COLOR);
		setDoubleBuffered(true);
		setFocusable(true);
		setLayout(null);
		nextGameState();
		keyHandler = new KeyHandler(this);
		stateBackground = new StateBackground(this);
		stateBackground.setup(0, 48, 48);
		player = new DarkKnight(this, keyHandler, stateBackground.mapDemo, 4*TILE_SIZE, 4*TILE_SIZE);
		this.main = main;
		
		JPanel state = new JPanel() {
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D graphics2d = (Graphics2D) g;
				
				//		TILE		
						stateBackground.draw(graphics2d,level, -worldx, -worldy);
						
				//		PLAYER
						player.draw(graphics2d);
						
				//		MONSTER
						monster.draw(graphics2d);

					if (isPause) {
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
		};
		state.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		JButton soundBtn = new JButton();
		setupButton(soundBtn, "res/button/soundBtnOpen.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH - 2*TILE_SIZE, 0);
		soundBtn.setFocusable(false);
		soundBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sound = 1-sound;
				if (sound == 0) {
					muteSound();
					setupButton(soundBtn, "res/button/soundBtnMute.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH - 2*TILE_SIZE, 0);
				}
				else {
					openSound();
					setupButton(soundBtn, "res/button/soundBtnOpen.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH - 2*TILE_SIZE, 0);
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
		
		
		confirmBoard = new JPanel();
		createConfirmBoard(confirmBoard);
		
		endGameBoard = new JPanel();
		createEndGameBoard(endGameBoard);
		
		victoryBoard = new JPanel();
		createVictoryBoard(victoryBoard);
		
		JButton quitBtn = new JButton();
		setupButton(quitBtn, "res/button/quit.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH - TILE_SIZE, 0);
		quitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmBoard.setVisible(true);
				isPause = true;
			}
		});
		

		this.add(soundBtn);
		this.add(confirmBoard);
		this.add(endGameBoard);
		this.add(victoryBoard);
		this.add(quitBtn);
		this.add(state);
		this.addKeyListener(keyHandler);
	}
	
	
	private void createEndGameBoard(JPanel endGameBoard) {
		endGameBoard.setPreferredSize(new Dimension(TILE_SIZE*10, TILE_SIZE*8));
		endGameBoard.setDoubleBuffered(true);
		endGameBoard.setFocusable(true);
		endGameBoard.setLayout(null);
		endGameBoard.setBounds(SCREEN_WIDTH/2-TILE_SIZE*5 , SCREEN_HEIGHT/2-TILE_SIZE*4, TILE_SIZE*10, TILE_SIZE*8);
		endGameBoard.setOpaque(false);
    	
    	
		JPanel board = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				Graphics2D graphics2d = (Graphics2D) g;
				UtilityTool uTool = new UtilityTool();
				try {
					BufferedImage bgrImg = ImageIO.read(getClass().getResourceAsStream("/background/smallBoard.png"));
					bgrImg = uTool.scaleImage(bgrImg, TILE_SIZE*10, TILE_SIZE*8);
					graphics2d.drawImage(bgrImg, 0, 0, TILE_SIZE*10, TILE_SIZE*8, null);
					graphics2d.setColor(Color.white);
					graphics2d.setFont(new Font("courier", Font.BOLD, 18));
					graphics2d.drawString("You are Defeat", TILE_SIZE, 3*TILE_SIZE);
					graphics2d.drawString("Press the button to quit the game!", TILE_SIZE, 7*TILE_SIZE/2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				graphics2d.dispose();
			}
		};
		board.setOpaque(false);
		board.setBounds(0, 0, TILE_SIZE*10, TILE_SIZE*8);
		
		
		JButton xbt = new JButton();
		setupButton(xbt, "res/button/quit.png", TILE_SIZE*2, TILE_SIZE*2, 4*TILE_SIZE, 4*TILE_SIZE);
		xbt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				main.backScreen();
				main.backScreen();
			}
		});
		
		endGameBoard.add(xbt);
		endGameBoard.add(board);
		endGameBoard.setVisible(false);
	}
	
	private void createVictoryBoard(JPanel endGameBoard) {
		endGameBoard.setPreferredSize(new Dimension(TILE_SIZE*12, TILE_SIZE*10));
		endGameBoard.setDoubleBuffered(true);
		endGameBoard.setFocusable(true);
		endGameBoard.setLayout(null);
		endGameBoard.setBounds(SCREEN_WIDTH/2-TILE_SIZE*6 , SCREEN_HEIGHT/2-TILE_SIZE*5, TILE_SIZE*12, TILE_SIZE*10);
		endGameBoard.setOpaque(false);
    	
    	
		JPanel board = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				Graphics2D graphics2d = (Graphics2D) g;
				UtilityTool uTool = new UtilityTool();
				try {
					BufferedImage bgrImg = ImageIO.read(getClass().getResourceAsStream("/background/board.png"));
					bgrImg = uTool.scaleImage(bgrImg, TILE_SIZE*12, TILE_SIZE*10);
					graphics2d.drawImage(bgrImg, 0, 0, TILE_SIZE*12, TILE_SIZE*10, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
				graphics2d.dispose();
			}
		};
		board.setOpaque(false);
		board.setBounds(0, 0, TILE_SIZE*12, TILE_SIZE*10);
		
		
		JButton xbt = new JButton();
		setupButton(xbt, "res/button/quit.png", TILE_SIZE*2, TILE_SIZE*2, 5*TILE_SIZE, 6*TILE_SIZE);
		xbt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				main.backScreen();
				main.backScreen();
			}
		});
		
		endGameBoard.add(xbt);
		endGameBoard.add(board);
		endGameBoard.setVisible(true);
	}

	void createConfirmBoard(JPanel settingBoard) {
    	settingBoard.setPreferredSize(new Dimension(TILE_SIZE*10, TILE_SIZE*8));
    	settingBoard.setDoubleBuffered(true);
    	settingBoard.setFocusable(true);
    	settingBoard.setLayout(null);
    	settingBoard.setBounds(SCREEN_WIDTH/2-TILE_SIZE*5 , SCREEN_HEIGHT/2-TILE_SIZE*4, TILE_SIZE*10, TILE_SIZE*8);
    	settingBoard.setOpaque(false);
    	 	
		JPanel board = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				Graphics2D graphics2d = (Graphics2D) g;
				UtilityTool uTool = new UtilityTool();
				try {
					BufferedImage bgrImg = ImageIO.read(getClass().getResourceAsStream("/background/smallBoard.png"));
					bgrImg = uTool.scaleImage(bgrImg, TILE_SIZE*10, TILE_SIZE*8);
					graphics2d.drawImage(bgrImg, 0, 0, TILE_SIZE*10, TILE_SIZE*8, null);
					graphics2d.setColor(Color.white);
					graphics2d.setFont(new Font("courier", Font.BOLD, 18));
					graphics2d.drawString("Are you sure want to quit?", 2*TILE_SIZE, 7*TILE_SIZE/2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				graphics2d.dispose();
			}
		};
		board.setOpaque(false);
		board.setBounds(0, 0, TILE_SIZE*10, TILE_SIZE*8);
		
		
		JButton xbt = new JButton();
		setupButton(xbt, "res/button/xButton.png", TILE_SIZE, TILE_SIZE, 6*TILE_SIZE, 4*TILE_SIZE);
		xbt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				settingBoard.setVisible(false);
				isPause = false;
			}
		});
		
		JButton vbt = new JButton();
		setupButton(vbt, "res/button/vButton.png", TILE_SIZE, TILE_SIZE, 3*TILE_SIZE, 4*TILE_SIZE);
		vbt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				main.backScreen();
				main.backScreen();
			}
		});
		
		
		settingBoard.add(xbt);
		settingBoard.add(vbt);
		settingBoard.add(board);
		settingBoard.setVisible(false);
	}
	
	public void nextGameState() {
		level++;
		if (level == 0) {
			monster = new Boss3(this);
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
		button.setFocusable(false);
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
		if (!isPause) {
			player.update(stateBackground.mapDemo);
			monster.update(stateBackground.mapDemo);

			sensing(player, monster);
		}
	}
	
	
	private void sensing(DarkKnight p, Boss3 m) {
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


	public void endGame() {
		endGameBoard.setVisible(true);
	}
}
