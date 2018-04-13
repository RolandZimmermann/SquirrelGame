package de.hsa.game.SquirrelGame.core.entity.playerentity;

import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class MasterSquirrel extends PlayerEntity {

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
    public void nextStep() {
        // TODO Auto-generated method stub
        
    }
    
}
