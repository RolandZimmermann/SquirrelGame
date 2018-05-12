package de.hsa.game.SquirrelGame.botapi;

import de.hsa.game.SquirrelGame.gamestats.XY;

public interface ControllerContext {
	
	public XY getViewLowerLeft();
	public XY getViewUpperRight();
	public EntityType getEntityAt(XY xy);
	public void move (XY direction);
	public void spawnMiniBot (XY direction, int energy);
	public int getEntergy();
	

}
