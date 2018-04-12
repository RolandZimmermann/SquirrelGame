package entity;

import general.XY;

public class GoodPlant extends Entity{

    public GoodPlant(int id, XY position) {
        super(id, position, 100);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void nextStep() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public String toString() {
		return "GoodPlant " + super.toString();
	}

}
