package de.hsa.game.SquirrelGame.ui.consoletest;

public interface CommandTypeInfo {
	
	public String getName ();
	public String getHelpText();
	public Class<?> [] getParamTypes();
	
	

}
