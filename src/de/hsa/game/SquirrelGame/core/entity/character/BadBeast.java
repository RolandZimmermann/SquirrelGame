package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;

import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.game.SquirrelGame.gamestats.Energy;
import de.hsa.game.SquirrelGame.gamestats.XYsupport;
import de.hsa.games.fatsquirrel.util.XY;

/**
 * A class implements the {@code BadBeast} which are not controlled by the user.
 * @author reich
 *
 */

public class BadBeast extends Character {
	private int turnCounter = 4;
	private int biteCounter = 7;

	/**
	 * Creates a {@code BadBest} with given parameter.
	 * @param id
	 *         {@code id} of the {@code BadBeast}
	 * @param position
	 *         {@code position} of the {@code BadBeast}
	 */
	public BadBeast(int id, XY position) {
		super(id, position, Energy.BADBEAST.energy);
		// TODO Auto-generated constructor stub
	}
	/**
	 * {@code nextStep} of the BadBeast.
	 * 
	 * If the {@code biteCounter} not 0 the {@code BadBeast} moves to the nearest {@code PlayerEntity}.
	 * If distance to the nearest {@code PlayerEntity} is bigger than 6, the {@code BadBeast} moves randomly.
	 * 
	 * When the {@code biteCounter} is 0, the Entity would be killed and replaced.
	 * 
	 */
	@Override
	public void nextStep(EntityContext entityContext) {

		if (biteCounter != 0) {

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
							moveX = -1;
						}
						if (playerEntity.getPositionXY().x > x) {
							moveX = 1;
						}
						if (playerEntity.getPositionXY().y < y) {
							moveY = -1;
						}
						if (playerEntity.getPositionXY().y > y) {
							moveY = 1;
						}
					}

				if (moveX == 0 && moveY == 0) {
					XY moveDirection = XYsupport.randomMove();
					entityContext.tryMove(this, moveDirection);
				} else {
					if (!(entityContext.getEntityType(
							new XY(this.getPositionXY().x + moveX, this.getPositionXY().y)) instanceof PlayerEntity)
							&& !(entityContext.getEntityType(
									new XY(this.getPositionXY().x + moveX, this.getPositionXY().y)) == null)) {
						XY moveDirection = XYsupport.randomMove();
						entityContext.tryMove(this, moveDirection);
					}
					entityContext.tryMove(this, new XY(moveX, moveY));
				}
			} else {
				turnCounter++;

			}
		} else {
			entityContext.killandReplace(this);

		}
	}

	/**
	 * Lowers {@code biteCounter} by one.
	 */
	public void updatebiteCounter() {
		biteCounter--;
	}

	public int getBiteCounter() {
		return biteCounter;
	}

}
