package entity;

import general.XY;

public class BadPlant extends Entity {

    public BadPlant(int id, XY position) {
        super(id, position, -100);
        // TODO Auto-generated constructor stub
    }
   
   public void nextStep() {
       
   }
   @Override
	public String toString() {
		return "BadPlant " + super.toString();
	}
}