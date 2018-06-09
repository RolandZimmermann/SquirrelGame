package de.hsa.game.SquirrelGame.core.board;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.games.fatsquirrel.util.XY;

public class BoardConfig {

	private static Logger logger = Logger.getLogger(BoardConfig.class.getName());
	public static int WIDTH_SIZE = 80;
	public static int HEIGHT_SIZE = 80;
	public static int COUNT_WALL = 20;
	public static int COUNT_GOODBEAST = 80;
	public static int COUNT_BADBEAST = 8;
	public static int COUNT_GOODPLANT = 80;
	public static int COUNT_BADPLANT = 8;
	public static int COUNT_HANDOPERATED_MASTERSQUIRREL = 0;
	public static int COUNT_MASTERSQUIRREL = 1;
    public static String[] COUNT_BOTS = {"HalfRandomBot","RandomBot","HalfRandomBot","HalfRandomBot"};
	//public static String[] COUNT_BOTS = {"testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload"};
	public static int GAME_STEPS = 300;	
	
	public static XY getSize() {
		return new XY(WIDTH_SIZE, HEIGHT_SIZE);
	}
	
	public static void load () {
		
		Properties prop = new Properties();
		InputStream input = null;
		
		
		try {
			input = new FileInputStream("ressource/configs/BoardConfig.properties");
			prop.load(input);
			WIDTH_SIZE = Integer.parseInt( prop.getProperty("WIDTH_SIZE"));
			HEIGHT_SIZE = Integer.parseInt( prop.getProperty("HEIGHT_SIZE"));
			COUNT_WALL = Integer.parseInt( prop.getProperty("COUNT_WALL"));
			COUNT_GOODBEAST= Integer.parseInt( prop.getProperty("COUNT_GOODBEAST"));
			COUNT_BADBEAST= Integer.parseInt( prop.getProperty("COUNT_BADBEAST"));
			COUNT_GOODPLANT= Integer.parseInt( prop.getProperty("COUNT_GOODPLANT"));
			COUNT_BADPLANT= Integer.parseInt( prop.getProperty("COUNT_BADPLANT"));
			COUNT_HANDOPERATED_MASTERSQUIRREL = Integer.parseInt( prop.getProperty("COUNT_HANDOPERATED_MASTERSQUIRREL"));
			COUNT_BOTS = prop.getProperty("COUNT_BOTS").split(",");
			GAME_STEPS = Integer.parseInt( prop.getProperty("GAME_STEPS"));
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			logger.log(Level.SEVERE, e.getMessage(), e);;
		} 
	 finally {
		
		if (input != null) {
			
			try {
				input.close();
			}catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}}
	
	
}

