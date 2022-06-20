package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.PixelGrabber;

public class UI {

	
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
		
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		if (gp.gameState == gp.playState) {
			//
		}
		
		if (gp.gameState == gp.pauseState && gp.isPause) {
			drawPauseScreen();
		}	
		
		if (gp.gameState == gp.guideState) {
			drawGuideScreen();
		}
		
	}
		
	public void drawGuideScreen() {
		// TODO Auto-generated method stub
		g2.setColor(new Color(80, 120, 80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		
//		BACK Button
		String text = "< Back";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		
//		Content
		text = "Team 17";
		x = gp.tileSize * 2;
		y = gp.tileSize * 3;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		text = "1.";
		y += gp.tileSize/2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		text = "2.";
		y += gp.tileSize/2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		text = "3.";
		y += gp.tileSize/2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		text = "4.";
		y += gp.tileSize/2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		text = "5.";
		y += gp.tileSize/2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
	}


	public void drawTitleScreen() {
		// TODO Auto-generated method stub
//		draw background
		g2.setColor(new Color(70, 120, 80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		
//		draw title
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
		String text = "OOP Game Project";
		
		int x = getXForCenterMetrics(text);
		int y = gp.tileSize * 3;
		
		g2.setColor(Color.black);
		g2.drawString(text, x+3, y+3);
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		
//		Blue boy image
		x = gp.screenWidth / 2 - gp.tileSize / 2;
		y += gp.tileSize * 1.5;
		
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		
//		BUTTON
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = getXForCenterMetrics(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x ,y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize ,y);
		}
		
		text = "GUIDE";
		x = getXForCenterMetrics(text);
		y += gp.tileSize;
		g2.drawString(text, x ,y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize ,y);
		}
		
		text = "QUIT";
		x = getXForCenterMetrics(text);
		y += gp.tileSize;
		g2.drawString(text, x ,y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize ,y);
		}
		
//		GUIDE
		
	}


	public void drawPauseScreen() {
		String text = "PAUSE";
		
		int x, y;
		
		x = getXForCenterMetrics(text);
		y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	
	public int getXForCenterMetrics(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
