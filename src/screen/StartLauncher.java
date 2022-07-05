package screen;
import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.PublicKey;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import main.DemoMain;
import main.GameInterface;
import main.UtilityTool;

public class StartLauncher extends JPanel implements GameInterface{
    
    JButton playBtn, moreBtn, exitBtn;
    JLabel title;
    DemoMain main;
    JPanel bgr;
    ImageIcon icon;
    
    public StartLauncher(DemoMain main) {
    	setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(MAIN_COLOR);
		setDoubleBuffered(true);
		setFocusable(true);
		setLayout(null);
	    
		this.main = main;
		
		playBtn = new JButton();
		moreBtn = new JButton();
		exitBtn = new JButton();
		
		bgr = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				Graphics2D graphics2d = (Graphics2D) g;
				UtilityTool uTool = new UtilityTool();
				try {
					BufferedImage bgrImg = ImageIO.read(getClass().getResourceAsStream("/background/screenBgr.png"));
					bgrImg = uTool.scaleImage(bgrImg, SCREEN_WIDTH, SCREEN_HEIGHT);
					graphics2d.drawImage(bgrImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
					
					BufferedImage gameTitle = ImageIO.read(getClass().getResourceAsStream("/background/gametitle.png"));
					bgrImg = uTool.scaleImage(bgrImg, TILE_SIZE*15, TILE_SIZE*6);
					graphics2d.drawImage(gameTitle, TILE_SIZE*5/2, TILE_SIZE, TILE_SIZE*15, TILE_SIZE*6, null);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				graphics2d.dispose();
			}
		};
		
		setup();	
		
		add(playBtn);
		add(moreBtn);
		add(exitBtn);
		add(bgr);	
    }
    
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        return new ImageIcon(resizedImage);
    }
    void setupButton(JButton button, String iconPath, int width, int height, int x, int y) {
    	icon = new ImageIcon(iconPath);
		button.setIcon(resizeIcon(icon, width, height));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setBounds(x, y, width, height);
    }
    
    void setup() {
    	
    	bgr.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    	
    	setupButton(playBtn, "res/button/playBtn.png", TILE_SIZE*4, TILE_SIZE*2, SCREEN_WIDTH/2 - TILE_SIZE*2, TILE_SIZE*7);
    	
    	setupButton(moreBtn, "res/button/moreBtn.png", TILE_SIZE*4, TILE_SIZE*2, SCREEN_WIDTH/2 - TILE_SIZE*2, TILE_SIZE*9);
		
    	setupButton(exitBtn, "res/button/exitBtn.png", TILE_SIZE*4, TILE_SIZE*2, SCREEN_WIDTH/2 - TILE_SIZE*2, TILE_SIZE*11);
		
		playBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				main.gameOn();
				main.pushOptionScreen();
				main.nextScreen();
				System.out.println("PLAY");
			}
		});
		
		moreBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main.pushMoreScreen();
				main.nextScreen();
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	
    }
   
}
