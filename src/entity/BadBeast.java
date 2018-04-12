package entity;

import general.XY;

public class BadBeast extends Entity {

	public BadBeast(int id, XY position) {
		super(id, position, -150);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void nextStep() {
		getPositionXY().randomMove();
	}

	@Override
	public String toString() {
		return "BadBeast " + super.toString();
	}

}
