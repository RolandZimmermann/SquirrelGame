package entity;

import general.XY;

public class BadBeast extends Entity {

    public BadBeast(int id, XY position, int energy) {
        super(id, position, energy);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void nextStep() {
        getPositionXY().randomMove();        
    }

    @Override
    public String toString() {
        return "BadBeast [id=" + getId() + ", position=" + getPositionXY() + ", energy=" + getEnergy() + "]";
    }
    
    
    

}
