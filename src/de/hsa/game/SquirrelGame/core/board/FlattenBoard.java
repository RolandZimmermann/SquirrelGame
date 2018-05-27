package de.hsa.game.SquirrelGame.core.board;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import de.hsa.game.SquirrelGame.log.GameLogger;
import de.hsa.games.fatsquirrel.util.XY;

public class FlattenBoard implements BoardView, EntityContext {
	private static Logger logger = Logger.getLogger(GameLogger.class.getName());
	static {
		new GameLogger();
	}

	private Entity[][] cells;
	private Board database;

	public FlattenBoard(Board database) {
		this.database = database;
		update();
	}

	public void update() {
		this.cells = this.database.flatten();

		logger.finest("update flattenBoard");
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

		logger.finer(miniSquirrel.toString() + moveDirection.toString());

		Entity collided = checkCollision(miniSquirrel, moveDirection);
		if (collided instanceof MasterSquirrel) {

			logger.fine(miniSquirrel.toString() + "collided with" + collided.toString());
			if (((MasterSquirrel) collided).testSquirrel(miniSquirrel)) {
				collided.updateEnergy(miniSquirrel.getEnergy());
				kill(miniSquirrel);
			} else {

				kill(miniSquirrel);

			}
		} else if (collided instanceof MiniSquirrel) {

			logger.fine(miniSquirrel.toString() + "collided with" + collided.toString());
			if (!miniSquirrel.getMaster().equals(((MiniSquirrel) collided).getMaster())) {
				collided.updateEnergy(miniSquirrel.getEnergy());
				kill(miniSquirrel);
			}
		}
		if (collided == null) {
			miniSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());
		}

	}

	@Override
	public void tryMove(GoodBeast goodBeast, XY moveDirection) {

		logger.finer(goodBeast.toString() + moveDirection.toString());

		if (checkCollision(goodBeast, moveDirection) == null) {
			goodBeast.setPositionXY(moveDirection.getX(), moveDirection.getY());
		} else if (checkCollision(goodBeast, moveDirection) instanceof PlayerEntity) {
			logger.fine(goodBeast.toString() + "collided with" + checkCollision(goodBeast, moveDirection).toString());
			checkCollision(goodBeast, moveDirection).updateEnergy(goodBeast.getEnergy());
		}

	}

	@Override
	public void tryMove(BadBeast badBeast, XY moveDirection) {

		logger.finer(badBeast.toString() + moveDirection.toString());

		if (checkCollision(badBeast, moveDirection) == null) {
			badBeast.setPositionXY(moveDirection.getX(), moveDirection.getY());
		} else if (checkCollision(badBeast, moveDirection) instanceof PlayerEntity) {
			badBeast.updatebiteCounter();
			logger.fine(badBeast.toString() + "collided with" + checkCollision(badBeast, moveDirection).toString());
			checkCollision(badBeast, moveDirection).updateEnergy(badBeast.getEnergy());
		}

	}

	@Override
	public void tryMove(MasterSquirrel masterSquirrel, XY moveDirection) {

		logger.finer(masterSquirrel.toString() + moveDirection.toString());

		Entity collided = checkCollision(masterSquirrel, moveDirection);

		if (collided == null) {
			masterSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());

		} else if (collided instanceof Wall) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());
			masterSquirrel.wallCollison();

		} else if (collided instanceof GoodPlant) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			masterSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());

		} else if (collided instanceof BadPlant) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			masterSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());

		} else if (collided instanceof GoodBeast) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			masterSquirrel.setPositionXY(moveDirection.getX(), moveDirection.getY());

		} else if (collided instanceof BadBeast) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());

		} else if (collided instanceof MiniSquirrel) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

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
					double diste = Math.sqrt(Math.pow(Math.abs(e.getPositionXY().x - pos.x), 2)
							+ Math.pow(Math.abs(e.getPositionXY().y) - pos.y), 2));
					double distnearest = Math.sqrt(Math.pow(Math.abs(nearest.x) - pos.x), 2)
							+ Math.pow(Math.abs(nearest.y - pos.y), 2));

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

		while (cells[newPos.y)][newPos.x] != null) {
			newPos = new XY(a.nextInt((cells[0].length - 2) + 1), a.nextInt((cells.length - 2) + 1));
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

	public void trySpawnMiniSquirrel(MasterSquirrel master, XY xy, int energy) {
		Entity location = getEntityType(xy);
		if (location == null) {
			database.spawnMiniSquirrel(master, xy, energy);
			master.updateEnergy(-energy);
		}
		logger.finer("tryied spawing at " + xy.toString());
	}

	@Override
	public void impload(Entity entity, int impactRadius) {
		if (!(entity instanceof MiniSquirrel)) {
			try {
				throw new Exception("not possible, entity needs to be an instance of MiniSquirrel");
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				e.printStackTrace();
			}
		}
		int collectedEnergy = 0;
		for (int y = 0; y < cells.length; y++) {
			for (int x = 0; x < cells.length; x++) {
				Entity e = cells[y][x];
				int distance = (int) Math
						.sqrt(Math.pow(Math.abs(e.getPositionXY().getX() - entity.getPositionXY().getX()), 2)
								+ Math.pow(Math.abs(e.getPositionXY().getY() - entity.getPositionXY().getY()), 2));
				int energyLoss = (int) (200 * (entity.getEnergy() / impactRadius * impactRadius * Math.PI)
						* (1 - distance / impactRadius));
				

				if (e instanceof MasterSquirrel) {
					if (((MiniSquirrel) entity).getMaster() == e) {
						continue;
					}
					if (e.getEnergy() >= energyLoss) {
						e.updateEnergy(-energyLoss);
						collectedEnergy += energyLoss;
					} else {
						collectedEnergy += e.getEnergy();
						e.updateEnergy(-e.getEnergy());
					}
				} else if (e instanceof MiniSquirrel) {
					if (((MiniSquirrel) entity).getMaster() == ((MiniSquirrel) e).getMaster()) {
						continue;
					}
					if (e.getEnergy() >= energyLoss) {
						e.updateEnergy(-energyLoss);
						collectedEnergy += energyLoss;
					} else {
						collectedEnergy += e.getEnergy();
						e.updateEnergy(-e.getEnergy());
					}
					if(e.getEnergy()<=0) {
						kill(e);
					}
				} else if (e instanceof GoodBeast || e instanceof GoodPlant) {
					if (e.getEnergy() >= energyLoss) {
						e.updateEnergy(-energyLoss);
						collectedEnergy += energyLoss;
					} else {
						collectedEnergy += e.getEnergy();
						e.updateEnergy(-e.getEnergy());
					}
					if(e.getEnergy()<=0) {
						killandReplace(e);
					}
				} else if (e instanceof BadBeast || e instanceof BadPlant) {
					if(Math.abs(e.getEnergy()) >= energyLoss) {
						e.updateEnergy(energyLoss);
					} else {
						e.updateEnergy(e.getEnergy());
					}
					if(e.getEnergy()>=0) {
						killandReplace(e);
					}
				}
			}
		}
		((MiniSquirrel) entity).getMaster().updateEnergy(collectedEnergy);
	}

}
