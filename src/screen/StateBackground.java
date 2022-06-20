package screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import map.Map;

public class StateBackground {

	GamePanel gp;
	int level;
	public int[][] mapdemo;
	BufferedImage [] backgroundMap = new BufferedImage [5];
	
	public StateBackground(GamePanel gamePanel, int level) {
		this.gp = gamePanel;
		this.level = level;
		setup(level);
	}
	
	
	public void setup(int index) {
		Map map = new Map();
		mapdemo = map.world0;	
		UtilityTool uTool = new UtilityTool();
		try {
			backgroundMap[0] = ImageIO.read(getClass().getResourceAsStream("/background/state1.png"));
			backgroundMap[0] = uTool.scaleImage(backgroundMap[0], gp.tileSize*48, gp.tileSize*48);
//			backgroundMap[1] = ImageIO.read(getClass().getResourceAsStream("/background/gametitle.png"));
//			backgroundMap[2] = ImageIO.read(getClass().getResourceAsStream("/background/gametitle.png"));
//			backgroundMap[3] = ImageIO.read(getClass().getResourceAsStream("/background/gametitle.png"));
//			backgroundMap[4] = ImageIO.read(getClass().getResourceAsStream("/background/gametitle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D graphics2d, int x, int y) {
		graphics2d.drawImage(backgroundMap[level], x, y, gp.tileSize * 48, gp.tileSize * 48, null);
	}

}
