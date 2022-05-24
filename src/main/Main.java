package main;

import javax.swing.JFrame;

/**
 * @author vuhongsonchv1619gmail.com
 * This is the begin of program when project running 
 */


public class Main {

	public static void main(String[] args) {
		
//		Create window screen
		JFrame window = new JFrame("Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);

//		create game panel
		GamePanel gamePanel = new GamePanel();
		
		window.add(gamePanel);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
//		Start game
		gamePanel.startGameThread();
	}

}
