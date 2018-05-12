package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.botapi.BotController;
import de.hsa.game.SquirrelGame.botapi.BotControllerFactory;
import de.hsa.game.SquirrelGame.botapi.ControllerContext;
import de.hsa.game.SquirrelGame.botapi.EntityType;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class MasterSquirrelBot extends MasterSquirrel {

	public MasterSquirrelBot(int id, XY position) {
		super(id, position);
		// TODO Auto-generated constructor stub
	}

	public class ControllerContextImpl implements ControllerContext {
		private EntityContext entityContext;
		private final int VISION = 31;

		public ControllerContextImpl(EntityContext entityContext) {
			this.entityContext = entityContext;
		}

		// TODO outofFieldException
		@Override
		public XY getViewLowerLeft() {

			// TODO Auto-generated method stub
			return new XY(getPositionXY().getX() - VISION / 2, getPositionXY().getY() + VISION / 2);
		}

		@Override
		public XY getViewUpperRight() {
			return new XY(getPositionXY().getX() + VISION / 2, getPositionXY().getY() - VISION / 2);
		}

		@Override
		public EntityType getEntityAt(XY xy) {

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

		@Override
		public void move(XY direction) {
			// TODO Auto-generated method stub

		}

		@Override
		public void spawnMiniBot(XY direction, int energy) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getEnergy() {
			// TODO Auto-generated method stub
			return this.getEnergy();
		}
	}

}
