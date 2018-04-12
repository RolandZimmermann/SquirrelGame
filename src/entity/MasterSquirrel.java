package entity;

import general.XY;

public class MasterSquirrel extends Squirrel {

    public MasterSquirrel(int id, XY position, int energy) {
        super(id, position, energy);

    }

    public boolean testSquirrel(Entity mini) {
        if (((MiniSquirrel) mini).master.equals(this)) 
            return true;
        else
            return false;
        
    }
    
    

    @Override
    public void nextStep() {
        // TODO Auto-generated method stub
        
    }

}
