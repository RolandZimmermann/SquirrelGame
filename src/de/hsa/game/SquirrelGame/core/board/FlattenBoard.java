package de.hsa.game.SquirrelGame.core.board;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class FlattenBoard implements BoardView {
	private Entity[][] cells;
	private Board database; 
	
	public FlattenBoard(Board database) {
		this.database = database;
	}
	
	public void update() {
		this.cells = this.database.flatten();
	}

	@Override
	public XY getSize() {
		return new XY(cells[0].length, cells.length);
	}

	@Override
	public Entity getEntityType(int x, int y) {
		return cells[y][x];
	}
	
	
	
	

}
