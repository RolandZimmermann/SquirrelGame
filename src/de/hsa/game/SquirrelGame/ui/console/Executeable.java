package de.hsa.game.SquirrelGame.ui.console;


import de.hsa.game.SquirrelGame.gamestats.MoveCommand;

public interface Executeable {
	public MoveCommand execute();
	public MoveCommand execute(Object energy, Object position);
}
