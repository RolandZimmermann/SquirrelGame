package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.entity.character.Character;
import de.hsa.game.SquirrelGame.gamestats.XY;

public abstract class PlayerEntity extends Character{

    public PlayerEntity(int id, XY position, int energy) {
        super(id, position, energy);
       
    }

}