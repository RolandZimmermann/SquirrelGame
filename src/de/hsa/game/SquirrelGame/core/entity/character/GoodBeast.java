package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class GoodBeast extends Character {
	private int turnCounter = 4;

	public GoodBeast(int id, XY position) {
		super(id, position, 200);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void nextStep(EntityContext entityContext) {
		if (entityContext == null) {
			XY randmove = XY.randomMove();
			super.setPositionXY(randmove.getX(), randmove.getY());
			return;
		}

		if (turnCounter == 4) {
			turnCounter = 0;
			int x = this.getPositionXY().getX();
			int y = this.getPositionXY().getY();
			int moveX = 0;
			int moveY = 0;
			PlayerEntity playerEntity = entityContext.nearestPlayerEntity(this.getPositionXY());

			if (Math.abs(playerEntity.getPositionXY().getX() - x) < 6
					&& Math.abs(playerEntity.getPositionXY().getY() - y) < 6) {
				if (playerEntity.getPositionXY().getX() < x) {
					moveX = 1;
				}
				if (playerEntity.getPositionXY().getX() > x) {
					moveX = -1;
				}
				if (playerEntity.getPositionXY().getY() < y) {
					moveY = 1;
				}
				if (playerEntity.getPositionXY().getY() > y) {
					moveY = -1;
				}
			}
			if (moveX == 0 && moveY == 0) {
				XY moveDirection = XY.randomMove();
				entityContext.tryMove(this, moveDirection);
			} else {
				entityContext.tryMove(this, new XY(moveX, moveY));
			}
		} else {
			turnCounter++;
		}

	}

}