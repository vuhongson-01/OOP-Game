package screen;
import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import main.DemoMain;
import main.GameInterface;

public class PlayerOptionScreen extends JPanel implements GameInterface{
    
    JButton playBtn, moreBtn, exitBtn;
    JLabel title;
    DemoMain main;
//    public static String screenName = "StartLauncher";
    
    public PlayerOptionScreen(DemoMain main) {
    	setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(MAIN_COLOR);
		setDoubleBuffered(true);
		setFocusable(true);
		setLayout(null);
		
		this.main = main;
		playBtn = new JButton("PLAY");
		moreBtn = new JButton("MORE");
		exitBtn = new JButton("EXIT");
		title = new JLabel(TITLE);
	
		setup();
		
		add(title);
		add(playBtn);
		add(moreBtn);
		add(exitBtn);
    }
    
    void setup() {
    	
    	title.setBounds(SCREEN_WIDTH/2 - TILE_SIZE, 
				TILE_SIZE*5, 
				TILE_SIZE*2, 
		  		TILE_SIZE);
		playBtn.setBounds(SCREEN_WIDTH/2 - TILE_SIZE, 
						  TILE_SIZE*8, 
						  TILE_SIZE*2, 
						  TILE_SIZE);
		moreBtn.setBounds(SCREEN_WIDTH/2 - TILE_SIZE, 
						  TILE_SIZE*10, 
						  TILE_SIZE*2, 
						  TILE_SIZE);
		exitBtn.setBounds(SCREEN_WIDTH/2 - TILE_SIZE, 
						  TILE_SIZE*12, 
						  TILE_SIZE*2, 
						  TILE_SIZE);
		
		playBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				main.gameOn();
				System.out.println("PLAY");
				
			}
		});
		
		moreBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("MORE");
				
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
