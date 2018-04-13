package de.hsa.game.SquirrelGame.core.board;

public class BoardFactory {

	public static Board createBoard() {

		return new Board(BoardConfig.WIDTH_SIZE, BoardConfig.HEIGHT_SIZE, BoardConfig.COUNT_BADPLANT,
				BoardConfig.COUNT_GOODPLANT, BoardConfig.COUNT_BADBEAST, BoardConfig.COUNT_GOODBEAST,
				BoardConfig.COUNT_HANDOPERATED_MASTERSQUIRREL, BoardConfig.COUNT_MASTERSQUIRREL, BoardConfig.COUNT_WALL

		);
	}

}
