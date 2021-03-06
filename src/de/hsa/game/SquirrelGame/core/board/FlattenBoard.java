
package de.hsa.game.SquirrelGame.core.board;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
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
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botimpls.MaToRoKi;
import de.hsa.games.fatsquirrel.util.XY;

/**
 * A class with the logic of the game
 * 
 * @author Roland
 *
 */
public class FlattenBoard implements BoardView, EntityContext {
	private static Logger logger = Logger.getLogger(FlattenBoard.class.getName());

	private Entity[][] cells;

	private Board database;
	
	private Map<XY, Integer> implosionMap = new ConcurrentHashMap<>();
	
	private long gameSteps;

	/**
	 * Constuct an instance with a given dataset
	 * 
	 * @param database
	 *            the database for the game
	 */
	public FlattenBoard(Board database) {
		this.database = database;
		update();
	}

	/**
	 * updates every entity in the game
	 */
	public void update() {
		this.cells = this.database.flatten();

		logger.finest("update flattenBoard");
	}

	@Override
	/**
	 * returns the size of the game
	 */
	public XY getSize() {
		return new XY(getCells()[0].length, getCells().length);
	}

	@Override
	/**
	 * returns the entity at a given position
	 * 
	 * @param x
	 *            the x position in the game
	 * @param y
	 *            the y position in the game
	 */
	public Entity getEntityType(int x, int y) {
		return getCells()[y][x];
	}

	@Override
	/**
	 * a method to decide what happens if a mini squirrel wants to move in a
	 * direction
	 */
	public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {

		logger.finer(miniSquirrel.toString() + moveDirection.toString());

		Entity collided = checkCollision(miniSquirrel, moveDirection);
		if (collided == null) {
			miniSquirrel.setPositionXY(moveDirection.x, moveDirection.y);

		} else if (collided instanceof MasterSquirrel) {

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
		} else if (collided instanceof GoodPlant) {

			logger.fine(miniSquirrel.toString() + "collided with " + collided.toString());

			miniSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			miniSquirrel.setPositionXY(moveDirection.x, moveDirection.y);

		} else if (collided instanceof BadPlant) {

			logger.fine(miniSquirrel.toString() + "collided with " + collided.toString());

			miniSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			miniSquirrel.setPositionXY(moveDirection.x, moveDirection.y);

		} else if (collided instanceof GoodBeast) {

			logger.fine(miniSquirrel.toString() + "collided with " + collided.toString());

			miniSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			miniSquirrel.setPositionXY(moveDirection.x, moveDirection.y);

		} else if (collided instanceof BadBeast) {

			logger.fine(miniSquirrel.toString() + "collided with " + collided.toString());

			miniSquirrel.updateEnergy(collided.getEnergy());
			((BadBeast) collided).updatebiteCounter();

		} else if (collided instanceof Wall) {
			logger.fine(miniSquirrel.toString() + "collided with " + collided.toString());
			miniSquirrel.updateEnergy(collided.getEnergy());
		}
	}

	@Override
	/**
	 * a method to decide what happens if a good beast wants to move in a direction
	 */
	public void tryMove(GoodBeast goodBeast, XY moveDirection) {

		logger.finer(goodBeast.toString() + moveDirection.toString());

		if (checkCollision(goodBeast, moveDirection) == null) {
			goodBeast.setPositionXY(moveDirection.x, moveDirection.y);
		} else if (checkCollision(goodBeast, moveDirection) instanceof PlayerEntity) {
			logger.fine(goodBeast.toString() + "collided with" + checkCollision(goodBeast, moveDirection).toString());
			checkCollision(goodBeast, moveDirection).updateEnergy(goodBeast.getEnergy());
		}

	}

	@Override
	/**
	 * a method to decide what happens if a bad beast wants to move in a direction
	 */
	public void tryMove(BadBeast badBeast, XY moveDirection) {

		logger.finer(badBeast.toString() + moveDirection.toString());

		if (checkCollision(badBeast, moveDirection) == null) {
			badBeast.setPositionXY(moveDirection.x, moveDirection.y);
		} else if (checkCollision(badBeast, moveDirection) instanceof PlayerEntity) {
			badBeast.updatebiteCounter();
			logger.fine(badBeast.toString() + "collided with" + checkCollision(badBeast, moveDirection).toString());
			checkCollision(badBeast, moveDirection).updateEnergy(badBeast.getEnergy());
		}

	}

	@Override
	/**
	 * a method to decide what happens if a master squirrel wants to move in a
	 * direction
	 */
	public void tryMove(MasterSquirrel masterSquirrel, XY moveDirection) {

		logger.finer(masterSquirrel.toString() + moveDirection.toString());

		Entity collided = checkCollision(masterSquirrel, moveDirection);

		if (collided == null) {
			masterSquirrel.setPositionXY(moveDirection.x, moveDirection.y);

		} else if (collided instanceof Wall) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());
			masterSquirrel.wallCollison();

		} else if (collided instanceof GoodPlant) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			masterSquirrel.setPositionXY(moveDirection.x, moveDirection.y);

		} else if (collided instanceof BadPlant) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			masterSquirrel.setPositionXY(moveDirection.x, moveDirection.y);

		} else if (collided instanceof GoodBeast) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());

			killandReplace(collided);
			masterSquirrel.setPositionXY(moveDirection.x, moveDirection.y);

		} else if (collided instanceof BadBeast) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			masterSquirrel.updateEnergy(collided.getEnergy());
			((BadBeast) collided).updatebiteCounter();

		} else if (collided instanceof MiniSquirrel) {

			logger.fine(masterSquirrel.toString() + "collided with " + collided.toString());

			if (masterSquirrel.testSquirrel(collided)) {
				masterSquirrel.updateEnergy(collided.getEnergy());
				kill(collided);
			} else {

				masterSquirrel.updateEnergy(150);
				kill(collided);

			}
			masterSquirrel.setPositionXY(moveDirection.x, moveDirection.y);
		}

	}

	@Override
	/**
	 * returns the nearest playerentity by a given position
	 */
	public PlayerEntity nearestPlayerEntity(XY pos) {
		XY nearest = new XY(Integer.MAX_VALUE, Integer.MAX_VALUE);
		PlayerEntity output = null;
		for (int i = 0; i < getCells().length; i++) {
			for (int j = 0; j < getCells()[0].length; j++) {
				Entity e = getCells()[i][j];
				if (e instanceof PlayerEntity) {
					double diste = Math.sqrt(Math.pow(Math.abs(e.getPositionXY().x - pos.x), 2)
							+ Math.pow(Math.abs(e.getPositionXY().y - pos.y), 2));
					double distnearest = Math
							.sqrt(Math.pow(Math.abs(nearest.x - pos.x), 2) + Math.pow(Math.abs(nearest.y - pos.y), 2));

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
	/**
	 * kills an entity in the game
	 */
	public void kill(Entity entity) {
		database.kill(entity);

	}

	@Override
	/**
	 * kills and replaces an entity
	 */
	public void killandReplace(Entity entity) {
		Entity insert = entity;
		database.kill(entity);

		Random a = new Random();

		XY newPos = new XY(a.nextInt((getCells()[0].length - 2) + 1), a.nextInt((getCells().length - 2) + 1));

		while (getCells()[newPos.y][newPos.x] != null) {
			newPos = new XY(a.nextInt((getCells()[0].length - 2) + 1), a.nextInt((getCells().length - 2) + 1));
		}

		database.killandReplace(insert, newPos);

	}

	@Override
	public Entity getEntityType(XY xy) {
		return getEntityType(xy.x, xy.y);
	}

	// public only to be able to use it in UNIT TESTS;
	/**
	 * 
	 * 
	 * @param entity
	 * @param moveDirection
	 * @return returns a entity in a given position from the entity
	 */
	public Entity checkCollision(Entity entity, XY moveDirection) {
		return getCells()[entity.getPositionXY().y + moveDirection.y][moveDirection.x + entity.getPositionXY().x];
	}

	/**
	 * trys to spawn a mini Squirrel
	 */
	public void trySpawnMiniSquirrel(MasterSquirrel master, XY xy, int energy) {
		Entity location = getEntityType(xy);
		if (location == null) {
			database.spawnMiniSquirrel(master, xy, energy);
			master.updateEnergy(-energy);
		}
		logger.finer("tryied spawing at " + xy.toString());
	}

	/**
	 * trys to spawn a miniSquirrelBot
	 */
	public void trySpawnMiniSquirrelBot(MasterSquirrel master, XY xy, int energy,
			BotControllerFactory botControllerFacotry) {
		Entity location = getEntityType(xy);
		if (location == null && master.getEnergy() >= energy) {
			database.spawnMiniSquirrelBot(master, xy, energy, botControllerFacotry);
			int minusEnergy = energy - 2 * energy;
			master.updateEnergy(minusEnergy);
		}
		logger.finer("tryied spawing at " + xy.toString());
	}

	@Override
	/**
	 * the impload method
	 */
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
		for (int y = 0; y < getCells().length; y++) {
			for (int x = 0; x < getCells().length; x++) {
				Entity e = getCells()[y][x];
				if (e == null) {
					continue;
				}
				int distance = (int) Math.sqrt(Math.pow(Math.abs(e.getPositionXY().x - entity.getPositionXY().x), 2)
						+ Math.pow(Math.abs(e.getPositionXY().y - entity.getPositionXY().y), 2));
				if (distance > impactRadius) {
					continue;
				}
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
					if (e.getEnergy() <= 0) {
						kill(e);
					}
				} else if (e instanceof GoodBeast || e instanceof GoodPlant) {
					if (e.getEnergy() <= 0) {
						continue;
					}

					if (e.getEnergy() >= energyLoss) {
						e.updateEnergy(-energyLoss);
						collectedEnergy += energyLoss;
					} else {
						collectedEnergy += e.getEnergy();
						e.updateEnergy(-e.getEnergy());
					}
					if (e.getEnergy() <= 0) {
						killandReplace(e);
					}
				}else if (e instanceof BadBeast || e instanceof BadPlant) {
					if (e.getEnergy() >= 0) {
						continue;
					}
					if (Math.abs(e.getEnergy()) >= energyLoss) {
						e.updateEnergy(energyLoss);
						collectedEnergy -= energyLoss;
					} else {
						collectedEnergy -= e.getEnergy();
						e.updateEnergy(-e.getEnergy());
					}
					if (e.getEnergy() >= 0) {
						killandReplace(e);
					}
				}
			}
			implosionMap.put(entity.getPositionXY(), impactRadius);
		}
		((MiniSquirrel) entity).getMaster().updateEnergy(collectedEnergy);
		database.kill(entity);
	}

	public Entity[][] getCells() {
		return cells;
	}

	@Override
	public MaToRoKi getBest() {
		return database.getBest();
	}

	@Override
	public long getRemainingSteps() {
		return gameSteps;
	}
	@Override
	public Map<XY, Integer> getImplosionMap() {
		return this.implosionMap;
	}
	
	@Override
	public void setGameSteps(long gameSteps) {
		this.gameSteps = gameSteps;
		
	}
}
