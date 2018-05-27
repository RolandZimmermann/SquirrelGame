package de.hsa.game.SquirrelGame.core.entity.noncharacter;

import de.hsa.game.SquirrelGame.gamestats.Energy;
import de.hsa.games.fatsquirrel.util.XY;

public class BadPlant extends NonCharacter {

    public BadPlant(int id, XY position) {
        super(id, position, Energy.BADPLANT.energy);
    }
}
  