package de.hsa.game.SquirrelGame.core;

import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.game.SquirrelGame.gamestats.XY;

public interface EntityContext {
	public XY getSize();
	
	public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection);
	public void tryMove(GoodBeast goodBeast, XY moveDirection);
	public void tryMove(BadBeast badbeast, XY moveDirection);
	public void tryMove(MasterSquirrel masterSquirrel, XY moveDirection);
	
	public PlayerEntity nearestPlayerEntity(XY pos);
	
	public void kill(Entity entity);
	public void killandReplace(Entity entity);
	public Entity getEntityType(XY xy);
}
