package entity;

import general.XY;

public class Wall extends Entity {

    public Wall(int id, XY position) {
        super(id, position, -10);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void nextStep() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public String toString() {
		return "Wall " + super.toString();
	}

}
