package de.hsa.game.SquirrelGame.gamestats;

import java.util.Random;

public final class XY {

    private final int x;
    private final int y;

    public XY(int x, int y) {
        this.x = x;
        this.y = y;
    }

 

    public XY move(int deltaX, int deltaY) {
    	int x = this.x + deltaX;
        int y = this.y + deltaY;
        return new XY(x,y);
    }
    
    public XY move(XY moveDirection) {
    	return move(moveDirection.getX(), moveDirection.getY());
    }
    
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
    	if (xy1.getX() == xy2.getX() && xy1.getY() == xy2.getY()) {
    		return true;
    	}
    	return false;
    }

    @Override
    public String toString() {
        return "[x: " + x + ", y: " + y + "]";
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
