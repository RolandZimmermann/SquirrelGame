package de.hsa.game.SquirrelGame.core.board;

import de.hsa.games.fatsquirrel.util.XY;

public class BoardConfig {

	
	public static int WIDTH_SIZE = 30;
	public static int HEIGHT_SIZE = 30;
	public static int COUNT_WALL = 20;
	public static int COUNT_GOODBEAST = 15;
	public static int COUNT_BADBEAST = 13;
	public static int COUNT_GOODPLANT = 10;
	public static int COUNT_BADPLANT = 30;
	public static int COUNT_HANDOPERATED_MASTERSQUIRREL = 0;
	public static int COUNT_MASTERSQUIRREL = 1;
	
	
	public static XY getSize() {
		return new XY(WIDTH_SIZE, HEIGHT_SIZE);
	}
}

