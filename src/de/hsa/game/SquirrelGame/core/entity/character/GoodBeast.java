package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class GoodBeast extends Character {

    public GoodBeast(int id, XY position) {
        super(id, position, 200);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void nextStep(EntityContext entityContext) {
    	XY moveDirection = XY.randomMove();
    	entityContext.tryMove(this, moveDirection);
    }
    
}
