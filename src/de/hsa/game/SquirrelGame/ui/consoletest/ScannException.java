package de.hsa.game.SquirrelGame.ui.consoletest;

public class ScannException extends Throwable {
	private String exception;
	
	public ScannException(String exception) {
		this.exception = exception;
	}
	
	
	public String printException () {
		
		
		return exception;
	}

}
