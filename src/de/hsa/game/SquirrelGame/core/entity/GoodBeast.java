package de.hsa.game.SquirrelGame.core.entity;

import de.hsa.game.SquirrelGame.gamestats.XY;

public class GoodBeast extends Entity {

    public GoodBeast(int id, XY position) {
        super(id, position, 200);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void nextStep() {
        getPositionXY().randomMove();          
    }
    
}
