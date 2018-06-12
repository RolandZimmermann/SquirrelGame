package de.hsa.game.SquirrelGame.core.entity.noncharacter;

import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.games.fatsquirrel.util.XY;;
/**
 * Represents entities not moveable
 * @author reich
 *
 */
public abstract class NonCharacter extends Entity{

	public NonCharacter(int id, XY position, int energy) {
		super(id, position, energy);
	}

}
