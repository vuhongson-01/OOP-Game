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
	Tile[] tiles;
	int level;
//	BufferedImage pointboard;
//	save map data to a text file!!!
	public int[][] mapdemo;
	
	public TileManager(GamePanel gamePanel, int level) {
		this.gp = gamePanel;
		this.level = level;
		tiles = new Tile[10];	
		
		getTilesImage();
		
		mapdemo = new int[gp.maxScreenRow][gp.maxScreenCol];
		
		Map mapDemo = new Map();
		getMap(mapDemo);
	}
	
	private void getMap(Map map) {
		try {
			if (level == 1) {
				mapdemo = map.map0;
				}	
			}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void getTilesImage() {
		setup(0, "water", true);
		setup(1, "grass", false);
		setup(2, "wall", true);	
		
	}
	
	public void setup(int index, String imagePath, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		
		try {
			tiles[index] = new Tile();
			tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
			tiles[index].image = uTool.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
			tiles[index].collision = collision;
			
//			pointboard = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D graphics2d) {
		for (int row = 0; row < gp.maxScreenRow; row++) {
			for (int col = 0; col < gp.maxScreenCol; col++) {
				graphics2d.drawImage(tiles[mapdemo[row][col]].image, col * gp.tileSize, row * gp.tileSize, gp.tileSize, gp.tileSize, null);
			}
		}
		
		
	}

}
