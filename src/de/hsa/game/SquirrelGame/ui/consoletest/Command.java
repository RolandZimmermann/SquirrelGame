package de.hsa.game.SquirrelGame.ui.consoletest;
/**
 * Class to create Command
 * @author reich
 *
 */
public class Command {

	private CommandTypeInfo commandType;
	private Object[] params;
/**
 * Create new Command with given parameters
 * @param commandType
 * @param params
 */
	public Command(CommandTypeInfo commandType, Object[] params) {

		this.commandType = commandType;
		this.params = params;
	}

	public Object[] getParams() {
		return params;

	}
	
	public CommandTypeInfo getCommandType() {
		return commandType;
	}

}
