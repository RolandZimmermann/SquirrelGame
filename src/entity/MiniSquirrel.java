package entity;

import general.XY;

public class MiniSquirrel extends Squirrel{
    MasterSquirrel master;

    public MiniSquirrel(int id, XY position, int energy, MasterSquirrel master) {
        super(id, position, energy);
        this.master=master;
    }

    @Override
    public void nextStep() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public String toString() {
		return "MiniSquirrel " + super.toString();
	}
    
    
}
