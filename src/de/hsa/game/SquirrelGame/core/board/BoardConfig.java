package de.hsa.game.SquirrelGame.core.board;

import de.hsa.game.SquirrelGame.gamestats.XY;

public class BoardConfig {

	
	public static int WIDTH_SIZE = 50;
	public static int HEIGHT_SIZE = 50;
	public static int COUNT_WALL = 10;
	public static int COUNT_GOODBEAST = 7;
	public static int COUNT_BADBEAST = 3;
	public static int COUNT_GOODPLANT = 8;
	public static int COUNT_BADPLANT = 2;
	public static int COUNT_HANDOPERATED_MASTERSQUIRREL = 0;
	public static int COUNT_MASTERSQUIRREL = 2;
	
	
	public static XY getSize() {
		return new XY(WIDTH_SIZE, HEIGHT_SIZE);
	}
}

