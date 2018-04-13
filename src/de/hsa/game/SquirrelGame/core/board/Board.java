package de.hsa.game.SquirrelGame.core.board;

import java.util.Random;

import de.hsa.game.SquirrelGame.core.entity.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.EntitySet;
import de.hsa.game.SquirrelGame.core.entity.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.HandOperatedMasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.Wall;
import de.hsa.game.SquirrelGame.gamestats.XY;
import de.hsa.game.SquirrelGames.squirrelgame.entity.*;

public class Board {

	private final int BOARD_WIDTH;
	private final int BOARD_HEIGHT;

	private char[][] boardEnvironment;
	private int id;

	private EntitySet entitySet = new EntitySet();

	Board(int boardWidth, int boardHeight, int countBadPlant, int countGoodPlant, int countBadBeast, int countGoodBeast,
			int countHandOperatedMastersquirrel, int countMastersquirrel, int countWall) {

		this.BOARD_HEIGHT = boardHeight;
		this.BOARD_WIDTH = boardWidth;

		this.boardEnvironment = new char[boardHeight][boardWidth];
		initialize(countBadPlant, countGoodPlant, countBadBeast, countGoodBeast, countHandOperatedMastersquirrel,
				countMastersquirrel, countWall);

	}

	private void initialize(int countBadPlant, int countGoodPlant, int countBadBeast, int countGoodBeast,
			int countHandOperatedMastersquirrel, int countMastersquirrel, int countWall) {

		Random a = new Random();

		for (int i = 0; i < countBadPlant; i++) {
			entitySet.insert(
					new BadPlant(id++, new XY(a.nextInt(BOARD_WIDTH - 1) + 1, a.nextInt(BOARD_HEIGHT - 1) + 1)));
		}

		for (int i = 0; i < countGoodPlant; i++) {
			entitySet.insert(
					new GoodPlant(id++, new XY(a.nextInt(BOARD_WIDTH - 1) + 1, a.nextInt(BOARD_HEIGHT - 1) + 1)));
		}
		for (int i = 0; i < countBadBeast; i++) {
			entitySet.insert(
					new BadBeast(id++, new XY(a.nextInt(BOARD_WIDTH - 1) + 1, a.nextInt(BOARD_HEIGHT - 1) + 1)));
		}
		for (int i = 0; i < countGoodBeast; i++) {
			entitySet.insert(
					new GoodBeast(id++, new XY(a.nextInt(BOARD_WIDTH - 1) + 1, a.nextInt(BOARD_HEIGHT - 1) + 1)));
		}
		for (int i = 0; i < countWall; i++) {
			entitySet.insert(new Wall(id++, new XY(a.nextInt(BOARD_WIDTH - 1) + 1, a.nextInt(BOARD_HEIGHT - 1) + 1)));
		}

		for (int i = 0; i < countHandOperatedMastersquirrel; i++) {
			entitySet.insert(new HandOperatedMasterSquirrel(id++,
					new XY(a.nextInt(BOARD_WIDTH - 1) + 1, a.nextInt(BOARD_HEIGHT - 1) + 1)));
		}
		for (int i = 0; i < countMastersquirrel; i++) {
			entitySet.insert(
					new MasterSquirrel(id++, new XY(a.nextInt(BOARD_WIDTH - 1) + 1, a.nextInt(BOARD_HEIGHT - 1) + 1)));
		}

		for (int y = 0; y < boardEnvironment.length; y++) {
			for (int x = 0; x < boardEnvironment[0].length; x++) {
				if (y == 0 || x == 0 || x == BOARD_WIDTH - 1 || y == BOARD_HEIGHT - 1) {
					boardEnvironment[y][x] = '#';

				}

			}

		}
	}

	public String toString() {
		String s = "";
		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				s += boardEnvironment[y][x];

			}
			s += "\n";
		}

		return s;
	}

}
