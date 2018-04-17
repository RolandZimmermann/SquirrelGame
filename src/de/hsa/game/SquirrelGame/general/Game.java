package de.hsa.game.SquirrelGame.general;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.board.FlattenBoard;
import de.hsa.game.SquirrelGame.core.board.State;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.ConsoleUI;
import de.hsa.game.SquirrelGame.ui.UI;

public class Game {
    private State state;
    private UI ui;
    private BoardView boardView;
    private EntityContext entityContext;
    
    private MoveCommand moveCommand = null;
    
    public Game(State state) {
        this.state = state;
        this.ui = new ConsoleUI();
        
        
        FlattenBoard flattenBoard = new FlattenBoard(state.getBoard());
        this.boardView = flattenBoard;
        this.entityContext = flattenBoard;
    }
    
	public void run() {
	    while (true) {
	        render();
	        processInput();
	        update();
	    }
	}
	
	public void render() {
	    ui.render(boardView);
	}
	public void processInput() {
	    moveCommand = ui.getCommand();
	}
	public void update() {
	    state.update(moveCommand, entityContext);
	    boardView.update();
	}
}
