package de.hsa.game.SquirrelGame.ui.exceptions;
/**
 * Class extends Exception
 * @author reich
 *
 */
public class ScanException extends Exception {
	private String exception;
/**
 * Creates new {@code ScanException}	
 * @param exception
 */
	public ScanException(String exception) {
		super(exception);
	}
}
