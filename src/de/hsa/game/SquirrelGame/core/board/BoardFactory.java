package de.hsa.game.SquirrelGame.core.board;

import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;

/**
 * A class to create a board
 * 
 * @author Roland
 *
 */
public class BoardFactory {

	/**
	 * 
	 * @return a board with params set in the BoardConfig
	 */
	public static Board createBoard() {

		return new Board(BoardConfig.WIDTH_SIZE, BoardConfig.HEIGHT_SIZE, BoardConfig.COUNT_BADPLANT,
				BoardConfig.COUNT_GOODPLANT, BoardConfig.COUNT_BADBEAST, BoardConfig.COUNT_GOODBEAST,
				BoardConfig.COUNT_WALL, BoardConfig.COUNT_BOTS);
	}
	
	public static Board createPlayerBoard() {
		return new Board(BoardConfig.WIDTH_SIZE, BoardConfig.HEIGHT_SIZE, BoardConfig.COUNT_BADPLANT,
				BoardConfig.COUNT_GOODPLANT, BoardConfig.COUNT_BADBEAST, BoardConfig.COUNT_GOODBEAST,
				BoardConfig.COUNT_HANDOPERATED_MASTERSQUIRREL, BoardConfig.COUNT_WALL,BoardConfig.NAME);
	}

	/**
	 * 
	 * @param bots
	 *            an array of BotControllerFacotrys for training the training of a
	 *            neural net
	 * @return a board with params set in the BoardConfig
	 */
	public static Board createTrainingBoard(BotControllerFactory[] bots) {
		return new Board(BoardConfig.WIDTH_SIZE, BoardConfig.HEIGHT_SIZE, BoardConfig.COUNT_BADPLANT,
				BoardConfig.COUNT_GOODPLANT, BoardConfig.COUNT_BADBEAST, BoardConfig.COUNT_GOODBEAST,
				BoardConfig.COUNT_WALL, bots);
	}

}
