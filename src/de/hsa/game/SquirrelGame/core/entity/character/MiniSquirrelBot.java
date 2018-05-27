package de.hsa.game.SquirrelGame.core.entity.character;

import java.util.Random;

import de.hsa.game.SquirrelGame.botapi.BotController;
import de.hsa.game.SquirrelGame.botapi.ControllerContext;
import de.hsa.game.SquirrelGame.botapi.EntityType;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.gamestats.XY;
import de.hsa.game.SquirrelGame.proxy.ProxyFactory;

public class MiniSquirrelBot extends MasterSquirrel implements BotController {
	
	public MiniSquirrelBot(int id, XY position) {
		super(id, position);
	}

	public class ControllerContextImpl implements ControllerContext {
		private EntityContext entityContext;
		private final int VISION = 31;

		public ControllerContextImpl(EntityContext entityContext) {
			this.entityContext = entityContext;
		}

		@Override
		public XY getViewLowerLeft() {
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
			setPositionXY(direction.getX(), direction.getY());

		}

		@Override
		public void spawnMiniBot(XY direction, int energy) {
			throw new UnsupportedOperationException("Cannot spawn a MiniSquirrel");
		}

		@Override
		public int getEnergy() {
			return MiniSquirrelBot.this.getEnergy();
		}

		@Override
		public void implode(int impactRadius) {
			entityContext.impload(MiniSquirrelBot.this, impactRadius);
		}
	}

	@Override
	public void nextStep(ControllerContext view) {
		Random a = new Random();
		int impactRadius = a.nextInt(8) +2;
		view.implode(impactRadius);
	}

	@Override
	public void nextStep(EntityContext entityContext) {
		nextStep((ControllerContext)ProxyFactory.newInstance(new ControllerContextImpl(entityContext)));
	}

}
