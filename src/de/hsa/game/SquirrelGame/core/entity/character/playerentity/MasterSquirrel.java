package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.entity.Entity;

import de.hsa.game.SquirrelGame.gamestats.Energy;
import de.hsa.games.fatsquirrel.util.XY;

/**
 * A class with abstract implementations of {@code PlayerEntity}
 * @author reich
 *
 */

public abstract class MasterSquirrel extends PlayerEntity {
    
    /**
     * Create a MasterSquirrel with the given parameter
     * @param id
     *          The id of the {@code MasterSquirrel}.
     * @param position
     *          The {@code XY} position of the Mastersquirrel
     */
    public MasterSquirrel(int id, XY position) {
        super(id, position, Energy.MASTERSQUIRREL.energy);

    }

    /**
     * Test if the given {@code Entity mini} is its own.
     * @param mini
     *          {@code Entity to test}
     * @return
     *          returns a boolean
     */
    public boolean testSquirrel(Entity mini) {
        if (((MiniSquirrel) mini).master.equals(this)) 
            return true;
        else
            return false;
        
    }
    
    
    public abstract void wallCollison();
    
    
}
