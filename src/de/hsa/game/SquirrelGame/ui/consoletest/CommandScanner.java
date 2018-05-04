package de.hsa.game.SquirrelGame.ui.consoletest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import de.hsa.game.SquirrelGame.ui.consoletest.exceptions.NoSuchCommandException;
import de.hsa.game.SquirrelGame.ui.consoletest.exceptions.ScanException;
import de.hsa.game.SquirrelGame.ui.consoletest.exceptions.WrongParamCountException;

public class CommandScanner {

	private CommandTypeInfo[] commandTypeInfos;
	private BufferedReader inputReader;
	private PrintStream outputStream;

	public CommandScanner(CommandTypeInfo[] commandTypeInfos, BufferedReader inputReader, PrintStream outputStream) {
		this.commandTypeInfos = commandTypeInfos;
		this.inputReader = inputReader;
		this.outputStream = outputStream;

	}

	public Command next() throws ScanException, IOException, NumberFormatException {
		String command = inputReader.readLine();
		String[] params = command.split(" ");
		for (int i = 0; i < commandTypeInfos.length; i++) {

			if (params[0].equals(commandTypeInfos[i].getName())) {
				if (commandTypeInfos[i].getParamTypes()[0] == null) {
					return new Command(commandTypeInfos[i], null);
				}
				if (commandTypeInfos[i].getParamTypes()[0] != null
						&& params.length - 1 != commandTypeInfos[i].getParamTypes().length) {
					throw new WrongParamCountException("Wrong number of param count");
				}

				Object[] param = new Object[params.length - 1];
					for (int j = 0; j < param.length; j++) {
						if (commandTypeInfos[i].getParamTypes()[j] == int.class) {
							param[j] = Integer.parseInt(params[j + 1]); 
						} else if (commandTypeInfos[i].getParamTypes()[j] == String.class) {
							param[j] = params[j + 1];
						} else if (commandTypeInfos[i].getParamTypes()[j] == float.class) {
							param[j] = Float.parseFloat(params[j + 1]);
						}
					}
				

				return new Command(commandTypeInfos[i], param);
			}

		}
		throw new NoSuchCommandException("No such command!");
	}
}
