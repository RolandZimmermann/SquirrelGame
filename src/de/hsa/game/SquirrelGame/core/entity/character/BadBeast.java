package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class BadBeast extends Character {
    private int turnCounter = 4;
    private int biteCounter = 7;

    public BadBeast(int id, XY position) {
        super(id, position, -150);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void nextStep(EntityContext entityContext) {

        if (entityContext == null) {
            XY randmove = XY.randomMove();
            super.setPositionXY(randmove.getX(), randmove.getY());
            return;
        }

        if (biteCounter != 0) {

            if (turnCounter == 4) {
                turnCounter = 0;
                XY moveDirection = XY.randomMove();
                entityContext.tryMove(this, moveDirection);
            } else {
                turnCounter++;

            }
        } else {

            entityContext.killandReplace(this);

        }
    }

    public void updatebiteCounter() {
        biteCounter--;

    }

}
