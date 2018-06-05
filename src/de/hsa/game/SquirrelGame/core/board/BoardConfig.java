package de.hsa.game.SquirrelGame.core.board;

import de.hsa.games.fatsquirrel.util.XY;

public class BoardConfig {

	
	public static int WIDTH_SIZE = 40;
	public static int HEIGHT_SIZE = 40;
	public static int COUNT_WALL = 20;
	public static int COUNT_GOODBEAST = 10;
	public static int COUNT_BADBEAST = 10;
	public static int COUNT_GOODPLANT = 15;
	public static int COUNT_BADPLANT = 15;
	public static int COUNT_HANDOPERATED_MASTERSQUIRREL = 0;
	public static int COUNT_MASTERSQUIRREL = 1;
    public static String[] COUNT_BOTS = {"HalfRandomBot","RandomBot","HalfRandomBot","HalfRandomBot","MaToRoKi"};
	//public static String[] COUNT_BOTS = {"testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload","testImpload"};
	public static int GAME_STEPS = 150;	
	
	public static XY getSize() {
		return new XY(WIDTH_SIZE, HEIGHT_SIZE);
	}
}

