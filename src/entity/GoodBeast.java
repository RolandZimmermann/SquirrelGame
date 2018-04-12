package entity;

import general.XY;

public class GoodBeast extends Entity {

    public GoodBeast(int id, XY position, int energy) {
        super(id, position, energy);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void nextStep() {
        getPositionXY().randomMove();          
    }

}
