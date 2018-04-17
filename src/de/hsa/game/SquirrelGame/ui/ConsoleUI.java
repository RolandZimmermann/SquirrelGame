package de.hsa.game.SquirrelGame.ui;

import java.util.Scanner;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.*;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.*;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.core.entity.character.*;
import de.hsa.game.SquirrelGame.core.entity.*;

public class ConsoleUI implements UI {
	public MoveCommand getCommand() {
		Scanner s = new Scanner(System.in);

		char h = s.next().charAt(0);
		switch (h) {
		case 'w':
		case 'W':
			return MoveCommand.UP;
		case 'a':
		case 'A':
			return MoveCommand.LEFT;
		case 's':
		case 'S':
			return MoveCommand.DOWN;
		case 'd':
		case 'D':
			return MoveCommand.RIGHT;
		case 'q':
		case 'Q':
			return MoveCommand.UPLEFT;
		case 'e':
		case 'E':
			return MoveCommand.UPRIGHT;
		case 'y':
		case 'Y':
			return MoveCommand.DOWNLEFT;
		case 'x':
		case 'X':
			return MoveCommand.DOWNRIGHT;

		default:
			return MoveCommand.NON;
		}
	}

	public void render(BoardView boardView) {
		clearConsole();
		for (int y = 0; y < boardView.getSize().getY(); y++) {
			for (int x = 0; x < boardView.getSize().getX(); x++) {
				Entity toInsert = boardView.getEntityType(x, y);
				if (toInsert == null) {
					System.out.print(" ");
				} else if (toInsert instanceof Wall) {
					System.out.print("#");
				} else if (toInsert instanceof MasterSquirrel || toInsert instanceof HandOperatedMasterSquirrel) {
					System.out.print("@");
				} else if (toInsert instanceof MiniSquirrel) {
					System.out.print("+");
				} else if (toInsert instanceof GoodBeast) {
					System.out.print("G");
				} else if (toInsert instanceof BadBeast) {
					System.out.print("B");
				} else if (toInsert instanceof GoodPlant) {
					System.out.print("g");
				} else if (toInsert instanceof BadPlant) {
					System.out.print("b");
				}
			}
			System.out.println();
		}
	}

	private void clearConsole() {
		for (int i = 0; i < 60; i++)
			System.out.println();
	}
}
