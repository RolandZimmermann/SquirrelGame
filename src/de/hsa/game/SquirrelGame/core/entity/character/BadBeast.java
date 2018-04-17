package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class BadBeast extends Character {

	public BadBeast(int id, XY position) {
		super(id, position, -150);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void nextStep(EntityContext entityContext) {
		
		if(entityContext == null) {
			XY randmove = XY.randomMove();
			super.setPositionXY(randmove.getX(), randmove.getY());
			return;
		}
		XY moveDirection = XY.randomMove();
    	entityContext.tryMove(this, moveDirection);
	}

}
