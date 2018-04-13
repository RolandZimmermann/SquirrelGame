package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.gamestats.XY;

public abstract class Character extends Entity {

	public Character(int id, XY position, int energy) {
		super(id, position, energy);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void nextStep();

}
