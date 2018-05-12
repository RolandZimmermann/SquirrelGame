package de.hsa.game.SquirrelGame.ui;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;

public interface UI {
	public MoveCommand getCommand();
	public void render(BoardView boardView);
	void message(String msg);
}
