package de.hsa.game.SquirrelGame.core.board;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;

public class State {
	private int highscore;
	private Board board; 
	
	public State(Board board) {
	    this.board = board;
	}
	
	public void update(MoveCommand moveCommand, EntityContext entityContext) {
	    board.update(moveCommand, entityContext);
	}
	
	public Board getBoard() {
	    return board;
	}
}
