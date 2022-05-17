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
	
	public int [][] map;
	GamePanel gp;
	int level; //level ~ map
	
	public TileManager(GamePanel gamePanel, int level) {
		/*
		 * 
		 */
	}
	
	
	private void getMap(Map map) { //get map from Map and sign to map array [][]	
		/*
		 * 
		 */
	}

	
	public void getTilesImage() {
		setup(0, "water", true); // example
		/*
		 * 
		 */
	}
	
	
	public void setup(int index, String imagePath, boolean collision) {
		/*
		 * read image ~ map[][] value and scale to tileSize
		 */
	}
	
	
	public void draw(Graphics2D graphics2d) {
		// Draw map (convert from array[][])
	}

}
