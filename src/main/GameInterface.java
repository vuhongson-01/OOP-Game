
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Entity;

public interface GameInterface {
		public static int TILE_SIZE = 48;
		public static int SCREEN_COL = 20;
		public static int SCREEN_ROW = 15;
		public static int SCREEN_WIDTH = TILE_SIZE * SCREEN_COL;
		public static int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROW;
		
		public static Color MAIN_COLOR = new Color(70, 120, 80);
		public static String TITLE = "GROUP 4";
		public static int FPS = 60;
		public static String color = "B16D28";
}
