package de.hsa.game.SquirrelGame.core.entity.character;


import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.proxy.ProxyFactory;
import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class MasterSquirrelBot extends MasterSquirrel implements BotController {
	
	public MasterSquirrelBot(int id, XY position) {
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
			return new XY(getPositionXY().x - VISION / 2, getPositionXY().y + VISION / 2);
		}

		@Override
		public XY getViewUpperRight() {
			return new XY(getPositionXY().x + VISION / 2, getPositionXY().y - VISION / 2);
		}

		@Override
		public EntityType getEntityAt(XY xy) {
//			if(!(xy.x > getViewUpperRight().x && xy.x < getViewLowerLeft().x && xy.y > getViewUpperRight().y && xy.y < getViewUpperRight().y)) {
//				try {
//					throw new OutOfViewException("Out of View!");
//				} catch (OutOfViewException e) {
//					//TODO: LOGGER????
//					e.printStackTrace();
//				}
//			}
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
			setPositionXY(direction.x, direction.y);
		}

		@Override
		public void spawnMiniBot(XY direction, int energy) {
			if (energy < 100 && Math.abs(direction.x) > 1 && Math.abs(direction.y) > 1) {
				try {
					throw new SpawnException("Wrong parameter");
				} catch (SpawnException e) {
					//TODO: LOGGER?????
					e.printStackTrace();
				}
				return;
			}
			entityContext.trySpawnMiniSquirrel(MasterSquirrelBot.this,direction,energy);
		}

		@Override
		public int getEnergy() {
			return MasterSquirrelBot.this.getEnergy();
		}

		@Override
		public void implode(int impactRadius) {
			throw new UnsupportedOperationException("Only for minisquirrels");
		}

		@Override
		public XY locate() {
			return MasterSquirrelBot.this.getPositionXY();
		}

		@Override
		public boolean isMine(XY xy) {
			if(!(xy.x > getViewUpperRight().x && xy.x < getViewLowerLeft().x && xy.y > getViewUpperRight().y && xy.y < getViewUpperRight().y)) {
				try {
					throw new OutOfViewException("Out of View!");
				} catch (OutOfViewException e1) {
					// TODO: LOGGER???
					e1.printStackTrace();
				}
				return false;
			}
			Entity e = entityContext.getEntityType(xy);
			if(e instanceof MiniSquirrel) {
				if(((MiniSquirrel) e).getMaster() == MasterSquirrelBot.this) {
					return true;
				}
			}
			return false;
		}

		@Override
		public XY directionOfMaster() {
			return XY.ZERO_ZERO;
		}

		@Override
		public long getRemainingSteps() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	@Override
	public void nextStep(ControllerContext view) {
		int moveX = (int) (Math.random() < 0.5 ? -1 : 1);
		int moveY = (int) (Math.random() < 0.5 ? -1 : 1);
		XY move = new XY(moveX, moveY);
		System.out.println(this.getPositionXY().toString());
		System.out.println(move.toString());
		if (view.getEntityAt(new XY(this.getPositionXY().x + move.x,
				this.getPositionXY().y + move.y)) == EntityType.NONE) {
			view.move(move);
			System.out.println(this.getPositionXY().toString());
		}
		view.move(move);
	}

	@Override
	public void nextStep(EntityContext entityContext) {
		//nextStep(new ControllerContextImpl(entityContext));
		nextStep((ControllerContext)ProxyFactory.newInstance(new ControllerContextImpl(entityContext)));
	}

}
