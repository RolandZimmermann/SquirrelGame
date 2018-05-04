package de.hsa.game.SquirrelGame.ui.consoletest;

import java.io.PrintStream;

public interface CommandTypeInfo {
	
	public String getName ();
	public String getHelpText();
	public Class<?> [] getParamTypes();
	
	public void help(PrintStream outputStream);
	public void exit(PrintStream outputStream);
	public void addi(PrintStream outputStream, Integer a, Integer b);
	public void addf(PrintStream outputStream, Float a, Float b);
	public void echo(PrintStream outputStream, String echo, Integer a);

}
