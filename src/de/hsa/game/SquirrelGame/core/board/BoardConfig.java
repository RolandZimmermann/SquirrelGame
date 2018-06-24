package de.hsa.game.SquirrelGame.core.board;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.jar.Attributes.Name;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.gamemode.GameMode;
import de.hsa.games.fatsquirrel.util.XY;

/**
 * Config class for the board
 * 
 * @author Roland
 *
 */
public class BoardConfig {

	private static Logger logger = Logger.getLogger(BoardConfig.class.getName());

	/**
	 * the length of the walls
	 */
	public static int WALL_LENGTH = 10;
	/**
	 * the width of the game board
	 */
	public static int WIDTH_SIZE = 80;
	/**
	 * the size height of the game board
	 */
	public static int HEIGHT_SIZE = 80;
	/**
	 * the amount of walls
	 */
	public static int COUNT_WALL = 20;
	/**
	 * the amount of good beasts
	 */
	public static int COUNT_GOODBEAST = 80;
	/**
	 * the amount of bad beast
	 */
	public static int COUNT_BADBEAST = 8;
	/**
	 * the amount of good plants
	 */
	public static int COUNT_GOODPLANT = 80;
	/**
	 * the amount of bad plants
	 */
	public static int COUNT_BADPLANT = 8;
	/**
	 * the amount of master Squirrels controlled by the player
	 */
	public static int COUNT_HANDOPERATED_MASTERSQUIRREL = 1;
	/**
	 * the amount of mastersquirrels **only in use for test classes**
	 */
	public static int COUNT_MASTERSQUIRREL = 1;
	/**
	 * an array of bot names to be loaded into the game
	 */
	public static String[] COUNT_BOTS = { "HalfRandomBot", "RandomBot", "HalfRandomBot", "HalfRandomBot" };
	public static String COUNT_BOTS_STRING = "HalfRandomBot2";
	
	// public static String[] COUNT_BOTS =
	// {"testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload"};
	/**
	 * the amount of game rounds
	 */
	public static int GAME_STEPS = 300;

	public static boolean TRAINING = false;

	public static boolean OLD_AI = false;

	public static boolean MULTI_THREAD = true;

	public static int FPS = 10;

	public static int CELL_SIZE = 16;

	public static boolean WITH_BOTS = true;

	public static String[] NAME = { "PLAYER" };

	public static XY getSize() {
		return new XY(WIDTH_SIZE, HEIGHT_SIZE);
	}

	public static String GAME_MODE = "JFX";

	public static GameMode gameMode = GameMode.JFX;

	public static int AI_POPULATION = 30;
	
	public static int PORT = 54321;

	/**
	 * Loads a config file to change default settings
	 */
	public static void load() {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("ressource/configs/BoardConfig.properties");
			prop.load(input);
			WIDTH_SIZE = Integer.parseInt(prop.getProperty("WIDTH_SIZE"));
			HEIGHT_SIZE = Integer.parseInt(prop.getProperty("HEIGHT_SIZE"));
			COUNT_WALL = Integer.parseInt(prop.getProperty("COUNT_WALL"));
			COUNT_GOODBEAST = Integer.parseInt(prop.getProperty("COUNT_GOODBEAST"));
			COUNT_BADBEAST = Integer.parseInt(prop.getProperty("COUNT_BADBEAST"));
			COUNT_GOODPLANT = Integer.parseInt(prop.getProperty("COUNT_GOODPLANT"));
			COUNT_BADPLANT = Integer.parseInt(prop.getProperty("COUNT_BADPLANT"));
			COUNT_BOTS_STRING = prop.getProperty("COUNT_BOTS");
			COUNT_BOTS = prop.getProperty("COUNT_BOTS").split(",");
			GAME_STEPS = Integer.parseInt(prop.getProperty("GAME_STEPS"));
			WALL_LENGTH = Integer.parseInt(prop.getProperty("WALL_LENGTH"));
			int training = Integer.parseInt(prop.getProperty("TRAINING"));
			int oldAI = Integer.parseInt(prop.getProperty("OLD_AI"));
			TRAINING = training < 1 ? false : true;
			OLD_AI = oldAI < 1 ? false : true;
			FPS = Integer.parseInt(prop.getProperty("FPS"));
			if (FPS <= 0) {
				FPS = 1;
			}
			int multithread = Integer.parseInt(prop.getProperty("MULTI_THREAD"));
			MULTI_THREAD = multithread < 1 ? false : true;
			CELL_SIZE = Integer.parseInt(prop.getProperty("CELL_SIZE"));
			int withBots = Integer.parseInt(prop.getProperty("WITH_BOTS"));
			WITH_BOTS = withBots < 1 ? false : true;
			try {
				int getAIPop = Integer.parseInt(prop.getProperty("AI_POPULATION"));
				AI_POPULATION = getAIPop;
			} catch (NumberFormatException k) {
			}
			GAME_MODE = prop.getProperty("GAME_MODE");
			try {
				gameMode = GameMode.valueOf(GAME_MODE);
			} catch (Exception u) {
				gameMode = GameMode.JFX;
			}
			try {
				PORT = Integer.parseInt(prop.getProperty("PORT"));
			} catch (NumberFormatException j) {
			}

		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {

			if (input != null) {

				try {
					input.close();
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
	}

	public static void save() {
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream("ressource/configs/BoardConfig.properties");

			prop.setProperty("WIDTH_SIZE", Integer.toString(WIDTH_SIZE));
			prop.setProperty("HEIGHT_SIZE", Integer.toString(HEIGHT_SIZE));
			prop.setProperty("COUNT_WALL", Integer.toString(COUNT_WALL));
			prop.setProperty("COUNT_GOODBEAST", Integer.toString(COUNT_GOODBEAST));
			prop.setProperty("COUNT_BADBEAST", Integer.toString(COUNT_BADBEAST));
			prop.setProperty("COUNT_GOODPLANT", Integer.toString(COUNT_GOODPLANT));
			prop.setProperty("COUNT_BADPLANT", Integer.toString(COUNT_BADPLANT));
			String bots = "";
			for (int i = 0; i < COUNT_BOTS.length; i++) {
				bots += COUNT_BOTS[i] + ",";
				
			}
			prop.setProperty("COUNT_BOTS", bots);
			prop.setProperty("GAME_STEPS", Integer.toString(GAME_STEPS));
			prop.setProperty("WALL_LENGTH", Integer.toString(WALL_LENGTH));
			prop.setProperty("TRAINING", TRAINING ? "1" : "0");
			prop.setProperty("OLD_AI", OLD_AI ? "1" : "0");
			prop.setProperty("FPS", Integer.toString(FPS));
			prop.setProperty("MULTI_THREAD", MULTI_THREAD ? "1" : "0");
			prop.setProperty("CELL_SIZE", Integer.toString(CELL_SIZE));
			prop.setProperty("WITH_BOTS", WITH_BOTS ? "1" : "0");
			prop.setProperty("AI_POPULATION", Integer.toString(AI_POPULATION));
			prop.setProperty("GAME_MODE", gameMode.name());
			prop.setProperty("PORT", Integer.toString(PORT));
			prop.setProperty("NAME", NAME[0]);
			prop.setProperty("COUNT_HANDOPERATED_MASTERSQUIRREL", Integer.toString(COUNT_HANDOPERATED_MASTERSQUIRREL));
			prop.store(output, null);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}

	}

}
