package de.hsa.game.SquirrelGame.ui.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.*;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.*;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.log.GameLogger;
import de.hsa.game.SquirrelGame.ui.UI;
import de.hsa.game.SquirrelGame.ui.exceptions.ScanException;
import de.hsa.game.SquirrelGame.core.entity.character.*;
import de.hsa.game.SquirrelGame.core.entity.*;

public class ConsoleUI implements UI {
	private static Logger logger = Logger.getLogger(GameLogger.class.getName());
	static {
		new GameLogger();
	}
	
	private PrintStream outputStream = System.out;
	private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	private CommandScanner commandScanner = new CommandScanner(GameCommandType.values(), inputReader, outputStream);

	private boolean outputMasterEnergy = false;
	private boolean outputHelp = false;
	private boolean outputall = false;

	public MoveCommand getCommand() {
		Command command = null;

		try {
			command = commandScanner.next();
		} catch (NumberFormatException | ScanException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}

		if (command == null) {
			return null;
		}
		Object[] params = command.getParams();
		Executeable commandType = (GameCommandType) command.getCommandType();
		try {
			MoveCommand moveCommand = null;
			if (params != null) {
				moveCommand = commandType.execute(params[0], params[1]);
			} else {
				moveCommand = commandType.execute();
			}

			if (moveCommand == MoveCommand.HELP) {
				outputHelp = true;
			}
			else if (moveCommand == MoveCommand.MASTER) {
				outputMasterEnergy = true;
			} else if(moveCommand == MoveCommand.ALL) {
				outputall = true;
			}
			
			return moveCommand;
		} catch (IllegalArgumentException | SecurityException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}
		logger.finer("returned Command");
		return null;
	}

	public void render(BoardView boardView) {
		ArrayList<Entity> entitysToOutput = new ArrayList<>();
		clearConsole();
		for (int y = 0; y < boardView.getSize().getY(); y++) {
			for (int x = 0; x < boardView.getSize().getX(); x++) {
				Entity toInsert = boardView.getEntityType(x, y);
				if (toInsert == null) {
					outputStream.print(" ");
				} else if (toInsert instanceof Wall) {
					outputStream.print("#");
					if(outputall) {
						entitysToOutput.add(toInsert);
					}
				} else if (toInsert instanceof MasterSquirrel || toInsert instanceof HandOperatedMasterSquirrel) {
					outputStream.print("@");
					if (outputMasterEnergy || outputall) {
						entitysToOutput.add(toInsert);
					}
				} else if (toInsert instanceof MiniSquirrel) {
					outputStream.print("+");
					if(outputall) {
						entitysToOutput.add(toInsert);
					}
				} else if (toInsert instanceof GoodBeast) {
					outputStream.print("G");
					if(outputall) {
						entitysToOutput.add(toInsert);
					}
				} else if (toInsert instanceof BadBeast) {
					outputStream.print("B");
					if(outputall) {
						entitysToOutput.add(toInsert);
					}
				} else if (toInsert instanceof GoodPlant) {
					outputStream.print("g");
					if(outputall) {
						entitysToOutput.add(toInsert);
					}
				} else if (toInsert instanceof BadPlant) {
					outputStream.print("b");
					if(outputall) {
						entitysToOutput.add(toInsert);
					}
				}
				
			}
			outputStream.println();
		}
		if (outputMasterEnergy) {
			for (int i = 0; i < entitysToOutput.size(); i++) {
				System.out.println(
						"ID: " + entitysToOutput.get(i).getId() + " | Energy:" + entitysToOutput.get(i).getEnergy());
			}
			outputMasterEnergy = false;
		}
		else if (outputHelp) {
			for (GameCommandType commandType : GameCommandType.values()) {
				outputStream.println(commandType.getName() + ": " + commandType.getHelpText());
			}
			outputHelp = false;
		} else if(outputall) {
			for (int i = 0; i < entitysToOutput.size(); i++) {
				System.out.println(entitysToOutput.get(i).toString());
			}
			outputall = false;
		}
		
		logger.finest("Updated UI");
	}

	private void clearConsole() {
		for (int i = 0; i < 100; i++)
			outputStream.println();
	}

	@Override
	public void message(String msg) {
		// TODO Auto-generated method stub
		
	}
}
