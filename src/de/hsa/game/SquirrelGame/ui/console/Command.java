package de.hsa.game.SquirrelGame.ui.console;

public class Command {

	private CommandTypeInfo commandType;
	private Object[] params;

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
