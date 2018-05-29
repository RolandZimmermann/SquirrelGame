package de.hsa.game.SquirrelGame.core.entity.character.bots;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class HalfRandomBot implements BotController {

    @Override
    public void nextStep(ControllerContext view) {
        double nearest = 999999999;
        EntityType entity = EntityType.NONE;
        XY target = XY.ZERO_ZERO;
        XY us = view.locate();
        XY topleft = new XY(view.getViewLowerLeft().x, view.getViewUpperRight().y);
        XY downright = new XY(view.getViewUpperRight().x, view.getViewLowerLeft().y);
        for (int i = topleft.y; i < downright.y; i++) {
            for (int j = topleft.x; j < downright.x; j++) {
                if (i < 0 || j < 0) {
                    continue;
                }

                try {
                    entity = view.getEntityAt(new XY(j, i));
                } catch (OutOfViewException e) {
                    e.printStackTrace();
                    continue;
                }
                if (entity != EntityType.WALL) {
                    if (entity == EntityType.GOOD_BEAST || entity == EntityType.GOOD_PLANT) {
                        if (Math.sqrt(Math.pow(us.x - j, 2) + Math.pow(us.y - i, 2)) < nearest) {
                            nearest = Math.sqrt(Math.pow(us.x - j, 2) + Math.pow(us.y - i, 2));
                            target = new XY(j, i);
                        }
                    }

                    int moveX = 0;
                    int moveY = 0;

                    if (target.x < us.x) {
                        moveX = -1;
                    } else if (target.x > us.x) {
                        moveX = 1;
                    }
                    if (target.y < us.y) {
                        moveY = -1;
                    } else if (target.y > us.y) {
                        moveY = 1;
                    }
                    view.move(new XY(moveX, moveY));
                } else {
                    int moveX = (int) (Math.random() < 0.5 ? -1 : 1);
                    int moveY = (int) (Math.random() < 0.5 ? -1 : 1);

                    us = view.locate();
                    
                    view.move(new XY(moveX, moveY));
                }

            }

        }
    }
}
