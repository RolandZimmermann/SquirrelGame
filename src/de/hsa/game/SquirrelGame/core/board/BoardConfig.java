package de.hsa.game.SquirrelGame.core.board;

import de.hsa.game.SquirrelGame.gamestats.XY;

public class BoardConfig {

	
	public static int WIDTH_SIZE = 30;
	public static int HEIGHT_SIZE = 30;
	public static int COUNT_WALL = 10;
	public static int COUNT_GOODBEAST = 6;
	public static int COUNT_BADBEAST = 6;
	public static int COUNT_GOODPLANT = 7;
	public static int COUNT_BADPLANT = 7;
	public static int COUNT_HANDOPERATED_MASTERSQUIRREL = 1;
	public static int COUNT_MASTERSQUIRREL = 0;
	
	
	public static XY getSize() {
		return new XY(WIDTH_SIZE, HEIGHT_SIZE);
	}
}
