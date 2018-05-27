package de.hsa.game.SquirrelGame.gamestats;

import java.util.Random;

import de.hsa.games.fatsquirrel.util.XY;

public final class XYsupport {
     
    public static XY randomMove() {
       Random random = new Random();
       int direct = random.nextInt(8);
       
       switch (direct) {
       case 0:
           return MoveCommand.UP.xy;
           
       case 1:
    	   return MoveCommand.DOWN.xy;
           
       case 2:
    	   return MoveCommand.LEFT.xy;
           
       case 3:
    	   return MoveCommand.RIGHT.xy;
           
       case 4:
    	   return MoveCommand.UPLEFT.xy;
           
       case 5:
    	   return MoveCommand.UPRIGHT.xy;
           
       case 6:
    	   return MoveCommand.DOWNLEFT.xy;
           
       case 7:
    	   return MoveCommand.DOWNRIGHT.xy;     
       default:
    	   return MoveCommand.UP.xy;
       }
       
       
    }
    
    public static boolean equalPosition(XY xy1, XY xy2) {
    	if (xy1.x== xy2.x && xy1.y == xy2.y) {
    		return true;
    	}
    	return false;
    }

}