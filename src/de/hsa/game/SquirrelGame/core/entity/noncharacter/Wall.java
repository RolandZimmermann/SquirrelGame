package de.hsa.game.SquirrelGame.core.entity.noncharacter;


import de.hsa.game.SquirrelGame.gamestats.XY;

public class Wall extends NonCharacter {

    public Wall(int id, XY position) {
        super(id, position, -10);
    }
}
