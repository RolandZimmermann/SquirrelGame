package de.hsa.game.SquirrelGame.ui;

import java.util.Map;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.games.fatsquirrel.util.XY;

public interface UI {
	public MoveCommand getCommand();
	public void render(BoardView boardView);
	public void message(String msg);
	public void implosions(Map<XY, Integer> implosionMap);
}
