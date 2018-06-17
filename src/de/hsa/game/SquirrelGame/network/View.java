package de.hsa.game.SquirrelGame.network;

import java.io.Serializable;

import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class View implements Serializable {


	private static final long serialVersionUID = 2857229576283576807L;
	
	private EntityType[][] field;
	
	public View(EntityType[][] field) {
		this.field = field;
	}
	
	public EntityType getEntityAt(XY xy) {
		if(xy.x < 0 || xy.y < 0 || xy.x > field[0].length || xy.y > field.length) {
			return null;
		}
		return field[xy.y][xy.x];
	}
}
