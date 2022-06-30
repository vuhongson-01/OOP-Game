package tile;

import java.awt.Graphics2D;
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
	Tile map;
	GamePanel gp;
	public int level;
	public int worldWidth, worldHeight;
//	save map data to a text file!!!
	public int[][] mapdemo;
	
	public TileManager(GamePanel gamePanel, int level, int worldCol, int worldRow) {
		this.gp = gamePanel;
		this.level = level;
		this.worldWidth = worldCol * gp.tileSize;
		this.worldHeight = worldRow * gp.tileSize;	
		getTilesImage();
		mapdemo = new int[48][48];
		Map mapDemo = new Map();
		getMap(mapDemo);
	}
	
	public void updateTile(int level) {
		this.level = level;
		mapdemo = new int[48][48];
		Map mapDemo = new Map();
		getMap(mapDemo);
	}
	
	private void getMap(Map map) {
		try {
			if (level == 1) {
				mapdemo = map.map1;
				}	
			}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	public void getTilesImage() {
		setup("MapHoang", 1);
	}
	
	public void setup(String imagePath, int index) {
		UtilityTool uTool = new UtilityTool();
		
		try {
			map = new Tile();
			map.image = ImageIO.read(getClass().getResourceAsStream("/tilesH/" + imagePath + ".png"));
			map.image = uTool.scaleImage(map.image, worldWidth, worldHeight);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D graphics2d, GamePanel gp) {
		graphics2d.drawImage(map.image, - gp.deltaX, - gp.deltaY, null); 
	}

}
