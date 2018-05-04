package de.hsa.game.SquirrelGame.ui.consoletest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class MyFavoriteCommandTypeProcessor {
	
	
	private PrintStream outputStream= System.out;
	private BufferedReader inputReader = new BufferedReader( new InputStreamReader(System.in));
	
	public void process () {
		
		CommandScanner commandScanner = new CommandScanner(MyFavoriteCommandType.values(), inputReader, outputStream);
        
//		 while (true) { // the loop over all commands with one input line for every command
//		     
//		     command = commandScanner.next();
//
//		             
//
//		     Object[] params = command.getParams();
//
//		     MyFavoriteCommandType commandType = (MyFavoriteCommandType) command.getCommandType();
//		     
//		     switch (commandType) {
//		     case EXIT:
//		         System.exit(0);
//		     case HELP: 
//		         help();
//		         break;
//		     case ADDI:
		
	}
	private void help() {
		
	}

	public static void main (String args []) {
		PrintStream outputStream= System.out;
		BufferedReader inputReader = new BufferedReader( new InputStreamReader(System.in));
		CommandScanner commandScanner = new CommandScanner(MyFavoriteCommandType.values(), inputReader, outputStream);
		MyFavoriteCommandType[] commandTypeInfo = MyFavoriteCommandType.values();
		System.out.println(commandTypeInfo[0].getParamTypes()[0]);
	}
}
