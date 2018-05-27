package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.games.fatsquirrel.util.XY;

public abstract class Character extends Entity {

	public Character(int id, XY position, int energy) {
		super(id, position, energy);
	}
	
	public abstract void nextStep(EntityContext entityContext);

}
