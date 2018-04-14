package de.hsa.game.SquirrelGame.core;

import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.gamestats.XY;

public interface BoardView {
	public void update();
	public XY getSize();
	public Entity getEntityType(int x, int y);
}
