package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class MasterSquirrel extends PlayerEntity {
	private int wallCounter = 0;
    public MasterSquirrel(int id, XY position) {
        super(id, position, 1000);

    }

    public boolean testSquirrel(Entity mini) {
        if (((MiniSquirrel) mini).master.equals(this)) 
            return true;
        else
            return false;
        
    }
    
    

    @Override
    public void nextStep(EntityContext entityContext) {
    	//TODO: ai...
    	XY moveDirection = null;
    	if(wallCounter == 0) {
    	entityContext.tryMove(this, moveDirection);
    	} else {
    		wallCounter--;
    	}
    }
    
    public void wallCollison() {
    	wallCounter = 4;
    }
    
    
}
