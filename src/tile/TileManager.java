package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import map.MapDemo;

public class TileManager {

	GamePanel gp;
	Tile[] tiles;
	int level;
//	save map data to a text file!!!
	public int[][] mapdemo;
	
	public TileManager(GamePanel gamePanel, int level) {
		this.gp = gamePanel;
		this.level = level;
		tiles = new Tile[10];	
		
		getTilesImage();
		
		mapdemo = new int[gp.maxScreenRow][gp.maxScreenCol];
		
		MapDemo mapDemo = new MapDemo();
		getMap(mapDemo);
	}
	
	private void getMap(MapDemo map) {
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
		 try {
			tiles[1] = new Tile();
			tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tiles[2] = new Tile();
			tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tiles[0]= new Tile();
			tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
		} catch (Exception e) {
			// TODO: handle exception
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
