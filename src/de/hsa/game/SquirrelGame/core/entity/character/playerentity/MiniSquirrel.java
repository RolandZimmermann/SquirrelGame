package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.EntityContext;

import de.hsa.game.SquirrelGame.gamestats.XYsupport;
import de.hsa.games.fatsquirrel.util.XY;

/**
 * A class that implements the abstract class {@code PlayerEntity}
 * @author reich
 *
 */

public class MiniSquirrel extends PlayerEntity{
    MasterSquirrel master;

    /**
     * Creates a {@code MiniSquirrel} with given parameter
     * @param id
     *         {@code id} of the {@code MiniSquirrel}
     * @param position
     *          {@code position} of the {@code MiniSquirrel}
     * @param energy
     *          {@code energy} of the {@code MiniSquirrel}
     * @param master
     *          {@code master} of the {@code MiniSquirrel}
     */
    public MiniSquirrel(int id, XY position, int energy, MasterSquirrel master) {
        super(id, position, energy);
        this.master=master;
    }

    @Override
    /**
     * @code nextStep moves the {@code MiniSquirrel} randomly.
     * 
     * Lowers energy every step. If the energy is lower than 0,
     * the {@code MiniSquirrel} would be killed.
     */
    public void nextStep(EntityContext entityContext) {
    	//TODO: make move
    	updateEnergy(-1);
    	XY moveDirection = XYsupport.randomMove();
    	entityContext.tryMove(this, moveDirection);
    	if(getEnergy() <= 0) {
    		 
    		entityContext.kill(this);
    	}
        
    }
    
    public MasterSquirrel getMaster() {
    	
    	return master;
    }
    
    
    
}
