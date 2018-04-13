package de.hsa.game.SquirrelGame.core.board;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.entity.Entity;

public class FlattenBoard implements BoardView {
	private Entity[][] cells;
	private Board database; 
	
	public FlattenBoard(Board database) {
		this.database = database;
	}
	
	public void update() {
		this.cells = this.database.flatten();
	}
	
	
	
	

}
