package de.hsa.game.SquirrelGame.general;

import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.BoardFactory;
import de.hsa.game.SquirrelGame.core.board.State;

public class Main {
	
	//Aufgabe 3
	public static void main(String[] args) {
		Board board =  BoardFactory.createBoard();
		
		Game game = new GameImpl(new State(board));
		game.run();
	}
	
}
