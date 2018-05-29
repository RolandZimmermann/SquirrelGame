package de.hsa.game.SquirrelGame.core.entity.character.bots;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class RandomBot implements BotController{

	@Override
	public void nextStep(ControllerContext view) {
		int moveX = (int) (Math.random() < 0.5 ? -1 : 1);
		int moveY = (int) (Math.random() < 0.5 ? -1 : 1);
		
		XY us = view.locate();
		XY move = new XY(moveX, moveY);
		if (view.getEntityAt(new XY(us.x + move.x,
				us.y + move.y)) == EntityType.NONE) {
			view.move(move);
		}
		
	}

}
