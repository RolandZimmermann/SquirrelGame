package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.game.SquirrelGame.gamestats.Energy;
import de.hsa.games.fatsquirrel.util.XY;
public class BadBeast extends Character {
    private int turnCounter = 4;
    private int biteCounter = 7;

    public BadBeast(int id, XY position) {
        super(id, position, Energy.BADBEAST.energy);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void nextStep(EntityContext entityContext) {


        if (biteCounter != 0) {

            if (turnCounter == 4) {
                turnCounter = 0;
                int x = this.getPositionXY().x;
        		int y = this.getPositionXY().y;
                int moveX = 0;
        		int moveY = 0;
        		PlayerEntity playerEntity = entityContext.nearestPlayerEntity(this.getPositionXY());

        		if (Math.abs(playerEntity.getPositionXY().getX() - x) < 6
        				&& Math.abs(playerEntity.getPositionXY().getY() - y) < 6) {
        			if (playerEntity.getPositionXY().getX() < x) {
        				moveX = -1;
        			}
        			if (playerEntity.getPositionXY().getX() > x) {
        				moveX = 1;
        			}
        			if (playerEntity.getPositionXY().getY() < y) {
        				moveY = -1;
        			}
        			if (playerEntity.getPositionXY().getY() > y) {
        				moveY = 1;
        			}
        		}
        		if (moveX == 0 && moveY == 0) {
                XY moveDirection = XY.randomMove();
                entityContext.tryMove(this, moveDirection);
        		} else {
        			entityContext.tryMove(this, new XY(moveX, moveY));
        		}
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
