package de.hsa.game.SquirrelGame.core.board;

import java.util.Random;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class FlattenBoard implements BoardView, EntityContext {
	private Entity[][] cells;
	private Board database;

	public FlattenBoard(Board database) {
		this.database = database;
		update();
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

		Entity collided = checkCollision(miniSquirrel, moveDirection);
		if (collided instanceof MasterSquirrel) {

			if (((MasterSquirrel) collided).testSquirrel(miniSquirrel)) {
				collided.updateEnergy(miniSquirrel.getEnergy());
				kill(miniSquirrel);
			} else {

				kill(miniSquirrel);

			}
		} else if (collided instanceof MiniSquirrel) {

			if (!miniSquirrel.getMaster().equals(((MiniSquirrel) collided).getMaster())) {
				collided.updateEnergy(miniSquirrel.getEnergy());
				kill(miniSquirrel);
			}
		} if (collided == null) {
			miniSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());
		}

	} 

	@Override
	public void tryMove(GoodBeast goodBeast, XY moveDirection) {
		if (checkCollision(goodBeast, moveDirection) == null) {
			goodBeast.setPositionXY(moveDirection.getX(), moveDirection.getY());
		} else if (checkCollision(goodBeast, moveDirection) instanceof PlayerEntity) {
			checkCollision(goodBeast, moveDirection).updateEnergy(goodBeast.getEnergy());
		}

	}

	@Override
	public void tryMove(BadBeast badBeast, XY moveDirection) {

		if (checkCollision(badBeast, moveDirection) == null) {
			badBeast.setPositionXY(moveDirection.getX(), moveDirection.getY());
		} else if (checkCollision(badBeast, moveDirection) instanceof PlayerEntity) {
			badBeast.updatebiteCounter();
			checkCollision(badBeast, moveDirection).updateEnergy(badBeast.getEnergy());
		}

	}

	@Override
	public void tryMove(MasterSquirrel masterSquirrel, XY moveDirection) {
		Entity collided = checkCollision(masterSquirrel, moveDirection);

		if (collided == null) {
			masterSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());

		} else if (collided instanceof Wall) {

			masterSquirrel.updateEnergy(collided.getEnergy());
			masterSquirrel.wallCollison();

		} else if (collided instanceof GoodPlant) {

			masterSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			masterSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());

		} else if (collided instanceof BadPlant) {

			masterSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			masterSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());

		} else if (collided instanceof GoodBeast) {

			masterSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			masterSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());

		} else if (collided instanceof BadBeast) {

			masterSquirrel.updateEnergy(collided.getEnergy());

		} else if (collided instanceof MiniSquirrel) {

			if (masterSquirrel.testSquirrel(collided)) {
				masterSquirrel.updateEnergy(collided.getEnergy());
				kill(collided);
			} else {

				masterSquirrel.updateEnergy(150);
				kill(collided);

			}
			masterSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());
		}

	}

	@Override
	public PlayerEntity nearestPlayerEntity(XY pos) {
		XY nearest = new XY(1000, 1000);
		PlayerEntity output = null;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				Entity e = cells[i][j];
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
		}
		return output;
	}

	@Override
	public void kill(Entity entity) {
		database.kill(entity);

	}

	@Override
	public void killandReplace(Entity entity) {
		Entity insert = entity;
		database.kill(entity);

		Random a = new Random();

		XY newPos = new XY(a.nextInt((cells[0].length - 2) + 1), a.nextInt((cells.length - 2) + 1));
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j] != null) {
					if (XY.equalPosition(cells[i][j].getPositionXY(), newPos)) {
						newPos = new XY(a.nextInt((cells[0].length - 2) + 1), a.nextInt((cells.length - 2) + 1));
						i = 0;
						j = 0;
					}
				}
			}
		}

		database.killandReplace(insert, newPos);

	}

	@Override
	public Entity getEntityType(XY xy) {
		return getEntityType(xy.getX(), xy.getY());
	}

	private Entity checkCollision(Entity entity, XY moveDirection) {
		return cells[entity.getPositionXY().getY() + moveDirection.getY()][moveDirection.getX()
				+ entity.getPositionXY().getX()];
	}
	 public void trySpawnMiniSquirrel( MasterSquirrel master ,XY xy ,int energy) {
		 Entity location = getEntityType(xy);
		 if(location == null) {
			 database.spawnMiniSquirrel(master, xy, energy);
			 
		 }
		 
	 }
	
	
	
	
	
	

}
