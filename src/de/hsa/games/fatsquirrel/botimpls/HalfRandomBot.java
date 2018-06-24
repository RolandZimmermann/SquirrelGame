package de.hsa.games.fatsquirrel.botimpls;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class HalfRandomBot implements BotController,BotControllerFactory {

	@Override
	public void nextStep(ControllerContext view) {
		double nearest = 999999999;
		
		EntityType entity = EntityType.NONE;
		
		XY target = XY.ZERO_ZERO;
		XY us = view.locate();
		XY topleft = new XY(view.getViewLowerLeft().x, view.getViewUpperRight().y);
		XY downright = new XY(view.getViewUpperRight().x, view.getViewLowerLeft().y);
		
		if (topleft.x < 0) {
			topleft = new XY(0, topleft.y);
		} 
		if (topleft.y < 0) {
			topleft = new XY(topleft.x, 0);
		}
		
		for (int i = topleft.y; i < downright.y; i++) {
			for (int j = topleft.x; j < downright.x; j++) {

				try {
					entity = view.getEntityAt(new XY(j, i));
				} catch (OutOfViewException e) {
					// e.printStackTrace();
					entity = EntityType.NONE;
					continue;
				}
				if (entity == EntityType.GOOD_BEAST || entity == EntityType.GOOD_PLANT) {
					if (Math.sqrt(Math.pow(us.x - j, 2) + Math.pow(us.y - i, 2)) < nearest) {
						nearest = Math.sqrt(Math.pow(us.x - j, 2) + Math.pow(us.y - i, 2));
						target = new XY(j, i);
					}
				}
			}
		}

		int moveX = 0;
		int moveY = 0;

		if (target.x < us.x) {
			moveX = -1;
		} else if (target.x > us.x) {
			moveX = 1;
		}
		if (target.y < us.y) {
			moveY = -1;
		} else if (target.y > us.y) {
			moveY = 1;
		}
		if (view.getEntityAt(new XY(us.x + moveX, us.y + moveY)) != EntityType.GOOD_BEAST
				&& view.getEntityAt(new XY(us.x + moveX, us.y + moveY)) != EntityType.NONE) {
			if (view.getEntityAt(new XY(us.x + moveX, us.y + moveY)) != EntityType.GOOD_PLANT
					&& view.getEntityAt(new XY(us.x + moveX, us.y + moveY)) != EntityType.NONE) {
				moveX = Math.random() < 0.5 ? 1 : -1;
				moveY = Math.random() < 0.5 ? 1 : -1;
				view.move(new XY(moveX, moveY));
			}
		}
		view.move(new XY(moveX, moveY));

	}

	@Override
	public BotController createMasterBotController() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BotController createMiniBotController() {
		// TODO Auto-generated method stub
		return null;
	}
}
