package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.game.SquirrelGame.gamestats.Energy;
import de.hsa.game.SquirrelGame.gamestats.XYsupport;
import de.hsa.games.fatsquirrel.util.XY;

/**
 * Implements the GoodBeast.
 * Not controlled by user.
 * @author reich
 *
 */

public class GoodBeast extends Character {
	private int turnCounter = 4;

	/**
     * Creates a {@code GoodBeast} with given parameter.
     * @param id
     *         {@code id} of the {@code GoodBeast}
     * @param position
     *         {@code position} of the {@code GoodBeast}
     */
	public GoodBeast(int id, XY position) {
		super(id, position, Energy.GOODBEAST.energy);
	}

	/**
	 * Moves the GoodBeast if the turnCounter is 4.
	 * 
	 * The {@code GoodBeast} moves away from the nearest {@code PlayerEntity}.
	 * Otherwise the {@code GoodBeast} moves randomly.
	 */
	@Override
	
	public void nextStep(EntityContext entityContext) {

		if (turnCounter == 4) {
			turnCounter = 0;
			int x = this.getPositionXY().x;
			int y = this.getPositionXY().y;
			int moveX = 0;
			int moveY = 0;
			PlayerEntity playerEntity = entityContext.nearestPlayerEntity(this.getPositionXY());
			if (playerEntity != null)
				if (Math.abs(playerEntity.getPositionXY().x - x) < 6
						&& Math.abs(playerEntity.getPositionXY().y - y) < 6) {
					if (playerEntity.getPositionXY().x < x) {
						moveX = 1;
					}
					if (playerEntity.getPositionXY().x > x) {
						moveX = -1;
					}
					if (playerEntity.getPositionXY().y < y) {
						moveY = 1;
					}
					if (playerEntity.getPositionXY().y > y) {
						moveY = -1;
					}
				}
			if (moveX == 0 && moveY == 0) {
				XY moveDirection = XYsupport.randomMove();
				entityContext.tryMove(this, moveDirection);
			} else {
				if (!(entityContext
						.getEntityType(new XY(this.getPositionXY().x + moveX, this.getPositionXY().y)) == null)) {
					XY moveDirection = XYsupport.randomMove();
					entityContext.tryMove(this, moveDirection);
				}
				entityContext.tryMove(this, new XY(moveX, moveY));
			}
		} else {
			turnCounter++;
		}

	}

}
