package de.hsa.games.squirrelgame.entity;

import de.hsa.games.squirrelgame.gamestats.XY;

public abstract class PlayerEntity extends Entity{

    public PlayerEntity(int id, XY position, int energy) {
        super(id, position, energy);
       
    }

}
