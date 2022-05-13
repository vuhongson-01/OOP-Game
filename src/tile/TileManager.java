package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	Tile[] tiles;
//	save map data to a text file!!!
	int[][] mapdemo;
	
	public TileManager(GamePanel gamePanel, int level) {
		this.gp = gamePanel;
		tiles = new Tile[10];	
		
		getTilesImage();
		
		mapdemo = new int[gp.maxScreenRow][gp.maxScreenCol];
		getMapFromTextFile(1);
	}
	
	private void getMapFromTextFile(int level) {
		try {
			if (level == 1) {
				InputStream iStream = getClass().getResourceAsStream("map/mapdemo.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
				
				int col = 0;
				int row = 0;
				
				while (row < gp.maxScreenRow) {
					String lineString = br.readLine();
					while (col < gp.maxScreenCol) {
						String mapElementCodesString[] = lineString.split(" ");
						
						int mapElementCode = Integer.parseInt(mapElementCodesString[col]); 
						mapdemo[row][col] = mapElementCode;
						col++;
						System.out.print(mapElementCode + " ");
					}
					System.out.println();
					row++;
					col = 0;
				}
				br.close();
			}
		} catch (Exception e) {
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
