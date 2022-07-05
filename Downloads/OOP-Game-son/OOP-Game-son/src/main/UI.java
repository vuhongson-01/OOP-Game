package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.PixelGrabber;

public class UI implements GameInterface{

	
	Font arial_40;
	GamePanel gp;
	public boolean messageOn = false;
	public String message = "";
	public int commandNum = 0;
	
	Graphics2D g2;
	
	
	public UI(GamePanel gp) {
		// TODO Auto-generated constructor stub
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
//		pixel = new Font("")
	}

	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {

		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		
		if (gp.gameState == gp.playState) {
			//
		}
		
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}	
	}

	public void drawPauseScreen() {
		String text = "PAUSE";
		
		int x, y;
		
		x = getXForCenterMetrics(text);
		y = SCREEN_HEIGHT/2;
		
		g2.drawString(text, x, y);
	}
	
	
	public int getXForCenterMetrics(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = SCREEN_WIDTH/2 - length/2;
		return x;
	}
}
