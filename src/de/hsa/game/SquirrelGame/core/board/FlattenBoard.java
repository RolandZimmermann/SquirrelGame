package de.hsa.game.SquirrelGame.core.board;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class FlattenBoard implements BoardView, EntityContext {
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

	@Override
	public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tryMove(GoodBeast goodBeast, XY moveDirection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tryMove(BadBeast badbeast, XY moveDirection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tryMove(MasterSquirrel masterSquirrel, XY moveDirection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerEntity nearestPlayerEntity(XY pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void kill(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void killandReplace(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity getEntityType(XY xy) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
