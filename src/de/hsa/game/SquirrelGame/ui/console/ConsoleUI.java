package de.hsa.game.SquirrelGame.ui.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.*;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.*;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.UI;
import de.hsa.game.SquirrelGame.ui.consoletest.exceptions.ScanException;
import de.hsa.game.SquirrelGame.core.entity.character.*;
import de.hsa.game.SquirrelGame.core.entity.*;

public class ConsoleUI implements UI {
	private PrintStream outputStream = System.out;
	private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	private CommandScanner commandScanner = new CommandScanner(GameCommandType.values(), inputReader, outputStream);

	public MoveCommand getCommand() {
		Command command = null;

		try {
			command = commandScanner.next();
		} catch (NumberFormatException | ScanException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (command == null) {
			return null;
		}
		Object[] params = command.getParams();
		GameCommandType commandType = (GameCommandType) command.getCommandType();
		try {
			Class[] methodparameters;
			if (params != null) {
				methodparameters = new Class[] { PrintStream.class, params[0].getClass(), params[1].getClass() };
				Method method = commandType.getClass().getMethod(commandType.getName(), methodparameters);

				return (MoveCommand) method.invoke(commandType, outputStream, params[0], params[1]);
			} else {
				methodparameters = new Class[] { PrintStream.class };
				Method method = commandType.getClass().getMethod(commandType.getName(), methodparameters);

				return (MoveCommand) method.invoke(commandType, outputStream);
			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void render(BoardView boardView) {
		clearConsole();
		for (int y = 0; y < boardView.getSize().getY(); y++) {
			for (int x = 0; x < boardView.getSize().getX(); x++) {
				Entity toInsert = boardView.getEntityType(x, y);
				if (toInsert == null) {
					outputStream.print(" ");
				} else if (toInsert instanceof Wall) {
					outputStream.print("#");
				} else if (toInsert instanceof MasterSquirrel || toInsert instanceof HandOperatedMasterSquirrel) {
					outputStream.print("@");
				} else if (toInsert instanceof MiniSquirrel) {
					outputStream.print("+");
				} else if (toInsert instanceof GoodBeast) {
					outputStream.print("G");
				} else if (toInsert instanceof BadBeast) {
					outputStream.print("B");
				} else if (toInsert instanceof GoodPlant) {
					outputStream.print("g");
				} else if (toInsert instanceof BadPlant) {
					outputStream.print("b");
				}
			}
			outputStream.println();
		}
	}

	private void clearConsole() {
		for (int i = 0; i < 60; i++)
			outputStream.println();
	}
}
