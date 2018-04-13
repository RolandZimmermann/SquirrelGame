package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.gamestats.XY;

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
    
    
    
}
