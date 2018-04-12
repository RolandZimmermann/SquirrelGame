package de.hsa.games.squirrelgame.entity;

import de.hsa.games.squirrelgame.gamestats.XY;

public class MiniSquirrel extends PlayerEntity{
    MasterSquirrel master;

    public MiniSquirrel(int id, XY position, int energy, MasterSquirrel master) {
        super(id, position, energy);
        this.master=master;
    }

    @Override
    public void nextStep() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public String toString() {
		return "MiniSquirrel " + super.toString();
	}
    
    
}
