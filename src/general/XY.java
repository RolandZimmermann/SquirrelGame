package general;

import java.util.Random;

public final class XY {

    private int x;
    private int y;

    public XY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
    
    public void randomMove () {
       Random random = new Random();
       int direct = random.nextInt(8);
       
       switch (direct) {
       case 0:
           move(0,1);
           break;
           
       case 1:
           move(0,-1);
           break;
           
       case 2:
           move(1,0);
           break;
           
       case 3:
           move(-1,0);
           break;
           
       case 4:
           move(1,1);
           break;
           
       case 5:
           move(1,-1);
           break;
           
       case 6:
           move(-1,1);
           break;
           
       case 7:
           move(-1,-1);
           break;      
           
       }
    }

    @Override
    public String toString() {
        return "XY [x=" + x + ", y=" + y + "]";
    }

}
