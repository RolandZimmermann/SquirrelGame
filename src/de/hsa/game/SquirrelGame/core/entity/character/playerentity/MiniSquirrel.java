package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class MiniSquirrel extends PlayerEntity{
    MasterSquirrel master;

    public MiniSquirrel(int id, XY position, int energy, MasterSquirrel master) {
        super(id, position, energy);
        this.master=master;
    }

    @Override
    public void nextStep(EntityContext entityContext) {
    	//TODO: make move
    	updateEnergy(-1);
    	XY moveDirection = null;
    	entityContext.tryMove(this, moveDirection);
    	if(getEnergy() <= 0) {
    		 
    		entityContext.kill(this);
    	}
        
    }
    
    public MasterSquirrel getMaster() {
    	
    	return master;
    }
    
    
    
}
