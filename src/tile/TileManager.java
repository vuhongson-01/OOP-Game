package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.CollationElementIterator;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import map.Map;

public class TileManager {

	GamePanel gp;
	int level;
	public int[][] mapdemo;
	BufferedImage [] backgroundMap = new BufferedImage [5];
	
	public TileManager(GamePanel gamePanel, int level) {
		this.gp = gamePanel;
		this.level = level;
		
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

	public void getTilesImage() {
		setup(0, "water", true);
		setup(1, "grass", false);
		setup(2, "wall", true);			
	}
	
	public void setup(int index, String imagePath, boolean collision) {
		
	}
	
	public void draw(Graphics2D graphics2d) {
		graphics2d.drawImage(backgroundMap[0], 0, 0, gp.tileSize * 30, gp.tileSize * 30, null);
	}

}
