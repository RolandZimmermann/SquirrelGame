package entity;

import general.XY;

public class GoodBeast extends Entity {

    public GoodBeast(int id, XY position) {
        super(id, position, 200);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void nextStep() {
        getPositionXY().randomMove();          
    }
    @Override
	public String toString() {
		return "GoodBeast " + super.toString();
	}

}
