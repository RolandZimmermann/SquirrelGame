package de.hsa.game.SquirrelGame.core.entity.playerentity;

import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.gamestats.XY;

public abstract class PlayerEntity extends Entity{

    public PlayerEntity(int id, XY position, int energy) {
        super(id, position, energy);
       
    }

}
