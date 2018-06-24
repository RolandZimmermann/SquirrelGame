package de.hsa.game.SquirrelGame.core;

import java.util.Map;

import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.games.fatsquirrel.botimpls.MaToRoKi;
import de.hsa.games.fatsquirrel.util.XY;
/**
 * Represents board
 * @author reich
 *
 */
public interface BoardView {
	public void update();
	public XY getSize();
	public Entity getEntityType(int x, int y);
	public MaToRoKi getBest();
	public void setGameSteps(long gameSteps);
	public Map<XY, Integer> getImplosionMap();
}
