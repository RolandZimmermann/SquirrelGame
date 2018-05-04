package de.hsa.game.SquirrelGame.ui.consoletest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class CommandScanner {

	private CommandTypeInfo[] commandTypeInfos;
	private BufferedReader inputReader;
	private PrintStream outputStream;

	public CommandScanner(CommandTypeInfo[] commandTypeInfos, BufferedReader inputReader, PrintStream outputStream) {
		this.commandTypeInfos = commandTypeInfos;
		this.inputReader = inputReader;
		this.outputStream = outputStream;

	}

	public Command next () throws ScannException {
		String command = inputReader.toString();	
		String[] params = command.split(" ");
		for (int i = 0; i < commandTypeInfos.length; i++) {
				
				if(params[0] == commandTypeInfos[i].getName()) {
					if(params.length != 1 || params.length != 3) {
						throw new WrongParamCount("Wrong ParamCount!");
					}
					if(params.length == 1 ) {
						return new Command(commandTypeInfos[i], null);
					}
					
					if(params.length == 3 ) {
						Class<?> class1 = commandTypeInfos[i].getParamTypes()[0];
						int param1;
						if(class1 == int.class) {
					param1 = Integer.parseInt(params[1]);
						}
						
						Object[] param = {param1};
						return new Command(commandTypeInfos[i], param );
					}
					
				}
					
		
		}
		return null;
	}
}
