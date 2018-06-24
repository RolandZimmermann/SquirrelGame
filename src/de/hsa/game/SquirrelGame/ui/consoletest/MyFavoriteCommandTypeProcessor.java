package de.hsa.game.SquirrelGame.ui.consoletest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.hsa.game.SquirrelGame.ui.exceptions.ScanException;

public class MyFavoriteCommandTypeProcessor {

	private PrintStream outputStream = System.out;
	private BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

	public void process() {

		CommandScanner commandScanner = new CommandScanner(MyFavoriteCommandType.values(), inputReader, outputStream);

		while (true) { // the loop over all commands with one input line for every command

			Command command = null;

			try {
				command = commandScanner.next();
			} catch (ScanException | IOException | NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (command == null) {
				continue;
			}

			Object[] params = command.getParams();

			MyFavoriteCommandType commandType = (MyFavoriteCommandType) command.getCommandType();

			try {
				Class<?>[] methodParameters;
				if (params != null) {
					methodParameters = new Class[] { PrintStream.class, params[0].getClass(), params[1].getClass() };
					Method method = commandType.getClass().getMethod(commandType.getName(), methodParameters);
					method.invoke(commandType, outputStream, params[0], params[1]);
				} else {
					methodParameters = new Class[] { PrintStream.class };
					Method method = commandType.getClass().getMethod(commandType.getName(), methodParameters);
					method.invoke(commandType, outputStream);
				}
				

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String args[]) {
		MyFavoriteCommandTypeProcessor myFavoriteCommandTypeProcessor = new MyFavoriteCommandTypeProcessor();

		myFavoriteCommandTypeProcessor.process();
	}
}
