package general;

import java.util.Random;

import gameStats.Direction;

public final class XY {

    private int x;
    private int y;

    public XY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    

    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
    
    public void move(Direction direction) {
    	move(direction.xy.getX(),direction.xy.getY());
    }
    
    public void randomMove () {
       Random random = new Random();
       int direct = random.nextInt(8);
       
       switch (direct) {
       case 0:
           move(Direction.UP);
           break;
           
       case 1:
    	   move(Direction.DOWN);
           break;
           
       case 2:
    	   move(Direction.LEFT);
           break;
           
       case 3:
    	   move(Direction.RIGHT);
           break;
           
       case 4:
    	   move(Direction.UPLEFT);
           break;
           
       case 5:
    	   move(Direction.UPRIGHT);
           break;
           
       case 6:
    	   move(Direction.DOWNLEFT);
           break;
           
       case 7:
    	   move(Direction.DOWNRIGHT);
           break;      
           
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
