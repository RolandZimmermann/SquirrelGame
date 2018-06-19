package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.MasterSquirrelBot.ControllerContextImpl;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.proxy.ProxyFactory;
import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

/**
 * Class implements the minisquirrel
 * @author reich
 *
 */
public class MiniSquirrelBot extends MiniSquirrel {
	BotControllerFactory botControllerFactory;
	BotController miniBotController;
/**
 * Creates {@code MiniSquirrelBot}
 * @param masterSquirrel
 *          set {@code masterSquirrel} of the mini
 * @param id
 * @param position
 * @param energy
 * @param botControllerFactory
 *          Creates a new {@code MiniBotController}.
 */
	public MiniSquirrelBot(MasterSquirrel masterSquirrel, int id, XY position, int energy,
			BotControllerFactory botControllerFactory) {
		super(id, position, energy, masterSquirrel);
		this.botControllerFactory = botControllerFactory;
		miniBotController = this.botControllerFactory.createMiniBotController();
	}
	
	/**
     * Implements {@code ControllerContext}
     * @author reich
     *
     */

	public class ControllerContextImpl implements ControllerContext {
		private EntityContext entityContext;
		private final int VISION = 21;

		  
        /**
         * Creates new {@code ControllerContextImpl}
         * @param entityContext
         */
		public ControllerContextImpl(EntityContext entityContext) {
			this.entityContext = entityContext;
		}
		/**
         * Returns the lowest left {@code XY}.
         */
		@Override
		public XY getViewLowerLeft() {
			int x = getPositionXY().x - VISION / 2;
			int y = getPositionXY().y + VISION / 2;

			return new XY(x, y);
		}

		  /**
         * Returns the highest right {@code XY}.
         */
		@Override
		public XY getViewUpperRight() {
			int x = getPositionXY().x + VISION / 2;
			int y = getPositionXY().y - VISION / 2;

			return new XY(x, y);
		}
		/**
         * Returns the {@code entity} at the given {@code XY}.
         */
		@Override
		public EntityType getEntityAt(XY xy) {
			if (!(xy.x <= getViewUpperRight().x && xy.x >= getViewLowerLeft().x && xy.y >= getViewUpperRight().y
					&& xy.y <= getViewLowerLeft().y)) {

				throw new OutOfViewException("Out of View!");

			}
			if (xy.x >= entityContext.getSize().x || xy.y >= entityContext.getSize().y || xy.x < 0 || xy.y < 0) {

				throw new OutOfViewException("Field is to small");

			}
			if (entityContext.getEntityType(xy) instanceof GoodPlant) {
				return EntityType.GOOD_PLANT;
			} else if (entityContext.getEntityType(xy) instanceof BadPlant) {
				return EntityType.BAD_PLANT;
			} else if (entityContext.getEntityType(xy) instanceof BadBeast) {
				return EntityType.BAD_BEAST;
			} else if (entityContext.getEntityType(xy) instanceof GoodBeast) {
				return EntityType.GOOD_BEAST;
			} else if (entityContext.getEntityType(xy) instanceof Wall) {
				return EntityType.WALL;
			} else if (entityContext.getEntityType(xy) instanceof MasterSquirrel) {
				return EntityType.MASTER_SQUIRREL;
			} else if (entityContext.getEntityType(xy) instanceof MiniSquirrel) {
				return EntityType.MINI_SQUIRREL;
			} else if (entityContext.getEntityType(xy) == null) {
				return EntityType.NONE;
			}
			return null;
		}
		/**
         * Moves the Bot in given direction.
         */
		@Override
		public void move(XY direction) {
			entityContext.tryMove(MiniSquirrelBot.this, direction);
		}
		/**
		 * Exception, because mini is unable to spawn
		 */
		@Override
		public void spawnMiniBot(XY direction, int energy) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getEnergy() {
			return MiniSquirrelBot.this.getEnergy();
		}

		@Override
		public void implode(int impactRadius) {
			entityContext.impload(MiniSquirrelBot.this, impactRadius);
		}

		@Override
		public XY locate() {
			return MiniSquirrelBot.this.getPositionXY();
		}
		/**
         * Tests if the {@code entity} on given {@code XY} is its own master.
         */
		@Override
		public boolean isMine(XY xy) {
			if (!(xy.x > getViewUpperRight().x && xy.x < getViewLowerLeft().x && xy.y > getViewUpperRight().y
					&& xy.y < getViewUpperRight().y)) {
				try {
					throw new OutOfViewException("Out of view");
				} catch (OutOfViewException e1) {
					e1.printStackTrace();
				}
				return false;
			}
			Entity e = entityContext.getEntityType(xy);
			if (e instanceof MiniSquirrel) {
				if (((MiniSquirrel) e).getMaster() == MiniSquirrelBot.this.getMaster()) {
					return true;
				}
			}
			if (e instanceof MasterSquirrel) {
				if (((MasterSquirrel) e).testSquirrel(MiniSquirrelBot.this)) {
					return true;
				}
			}
			return false;
		}
/**
 * returns the position of the master
 */
		@Override
		public XY directionOfMaster() {
			Entity e = MiniSquirrelBot.this.getMaster();
			int x = 0;
			int y = 0;

			if (e.getPositionXY().x < MiniSquirrelBot.this.getPositionXY().x) {
				x = -1;
			} else if (e.getPositionXY().x > MiniSquirrelBot.this.getPositionXY().x) {
				x = 1;
			}

			if (e.getPositionXY().y < MiniSquirrelBot.this.getPositionXY().y) {
				y = 1;
			} else if (e.getPositionXY().y > MiniSquirrelBot.this.getPositionXY().y) {
				y = -1;
			}

			return new XY(x, y);
		}

		@Override
		public long getRemainingSteps() {
			return entityContext.getRemainingSteps();
		}
	}
/**
 * Move the {@code MiniSquirrelBot}
 */
	@Override
	public void nextStep(EntityContext entityContext) {

		// miniBotController.nextStep(new ControllerContextImpl(entityContext));
		miniBotController
				.nextStep((ControllerContext) ProxyFactory.newInstance(new ControllerContextImpl(entityContext)));
		MiniSquirrelBot.this.updateEnergy(-1);
	}

}
