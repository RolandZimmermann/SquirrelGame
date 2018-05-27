package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.gamestats.Energy;
import de.hsa.games.fatsquirrel.util.XY;

public abstract class MasterSquirrel extends PlayerEntity {
	private int wallCounter = 0;
    public MasterSquirrel(int id, XY position) {
        super(id, position, Energy.MASTERSQUIRREL.energy);

    }

    public boolean testSquirrel(Entity mini) {
        if (((MiniSquirrel) mini).master.equals(this)) 
            return true;
        else
            return false;
        
    }
    
    
    public void wallCollison() {
    	wallCounter = 4;
    }
    
    
}
