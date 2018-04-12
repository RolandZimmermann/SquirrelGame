package de.hsa.games.squirrelgame.entity;

import de.hsa.games.squirrelgame.gamestats.XY;

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
    
    @Override
	public String toString() {
		return "MasterSquirrel " + super.toString();
	}

}
