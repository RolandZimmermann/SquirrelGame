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
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class MiniSquirrelBot extends MiniSquirrel implements BotController {
	
	public MiniSquirrelBot(MasterSquirrel masterSquirrel, int id, XY position, int energy) {
		super(id, position, energy, masterSquirrel);
	}

	public class ControllerContextImpl implements ControllerContext {
		private EntityContext entityContext;
		private final int VISION = 21;

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
			if(!(xy.x > getViewUpperRight().x && xy.x < getViewLowerLeft().x && xy.y > getViewUpperRight().y && xy.y < getViewUpperRight().y)) {
				try {
					throw new OutOfViewException("Out of View");
				} catch (OutOfViewException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
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

		@Override
		public void move(XY direction) {
			setPositionXY(direction.x, direction.y);
		}

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

		@Override
		public boolean isMine(XY xy) {
			if(!(xy.x > getViewUpperRight().x && xy.x < getViewLowerLeft().x && xy.y > getViewUpperRight().y && xy.y < getViewUpperRight().y)) {
				try {
					throw new OutOfViewException("Out of view");
				} catch (OutOfViewException e1) {
					e1.printStackTrace();
				}
				return false;
			}
			Entity e = entityContext.getEntityType(xy);
			if(e instanceof MiniSquirrel) {
				if(((MiniSquirrel) e).getMaster() == MiniSquirrelBot.this.getMaster()) {
					return true;
				}
			}
			if(e instanceof MasterSquirrel) {
				if(((MasterSquirrel)e).testSquirrel(MiniSquirrelBot.this)) {
					return true;
				}
			}
			return false;
		}

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
			
			return new XY(x,y);
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
		if (view.getEntityAt(new XY(this.getPositionXY().x + move.x,
				this.getPositionXY().y + move.y)) == EntityType.NONE) {
			view.move(move);
		}
	}

	@Override
	public void nextStep(EntityContext entityContext) {
		nextStep((ControllerContext)ProxyFactory.newInstance(new ControllerContextImpl(entityContext)));
	}

}
