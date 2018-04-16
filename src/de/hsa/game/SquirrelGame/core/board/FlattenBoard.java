package de.hsa.game.SquirrelGame.core.board;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
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
		int x = goodBeast.getPositionXY().getX();
		int y = goodBeast.getPositionXY().getY();
		int moveX = 0;
		int moveY = 0;
		PlayerEntity playerEntity = nearestPlayerEntity(goodBeast.getPositionXY());
	
		if (Math.abs(playerEntity.getPositionXY().getX() - x) < 6 && Math.abs(playerEntity.getPositionXY().getY() - y) < 6 ) {
			if(playerEntity.getPositionXY().getX() < x) {
				moveX = 1;
			}
			if(playerEntity.getPositionXY().getX() > x) {
				moveX = -1;
			}
			if(playerEntity.getPositionXY().getY() < y) {
				moveY = 1;
			}
			if(playerEntity.getPositionXY().getY() > y) {
				moveY = -1;
			}
			goodBeast.setPositionXY(moveX, moveY);
		}
		if(checkCollision(goodBeast, moveDirection) == null) {
			goodBeast.setPositionXY(moveDirection.getX(), moveDirection.getY());
		}
		
		
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
		XY nearest = new XY(1000, 1000);
		PlayerEntity output = null;
		for (Entity e : database.getEntitySet()) {
			if (e instanceof PlayerEntity) {
				double diste = Math.sqrt(Math.pow(Math.abs(e.getPositionXY().getX() - pos.getX()), 2)
						+ Math.pow(Math.abs(e.getPositionXY().getY() - pos.getY()), 2));
				double distnearest = Math.sqrt(Math.pow(Math.abs(nearest.getX() - pos.getX()), 2)
						+ Math.pow(Math.abs(nearest.getY() - pos.getY()), 2));
				
				if (diste < distnearest) {
					nearest = e.getPositionXY();
					output = (PlayerEntity) e;
				}
			
			}
		}
		return output;
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

	private Entity checkCollision(Entity entity, XY moveDirection) {
		return cells[entity.getPositionXY().getY() + moveDirection.getY()][moveDirection.getX()
				+ entity.getPositionXY().getX()];
	}

}
