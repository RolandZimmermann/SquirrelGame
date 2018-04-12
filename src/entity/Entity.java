package entity;

import general.XY;

public abstract class Entity {

	private final int id;
	private XY position;
	private int energy;

	public Entity(int id, int X, int Y, int energy) {

		this.id = id;
		this.position = new XY(X, Y);
		this.energy = energy;
	}

	public Entity(int id, XY position, int energy) {

		this.id = id;
		this.position = position;
		this.energy = energy;
	}
	
	public static boolean isSameEntity(Entity entity1, Entity entity2) {
		if(entity1.equals(entity2)) {
			return true;
		}
		return false;
	}

	public void updateEnergy(int energy) {
		this.energy += energy;
	}

	public void setPositionXY(int deltaX, int deltaY) {
		position.move(deltaX, deltaY);
	}

	@Override
	public String toString() {
		return "\t[id: " + id + ",\t position: " + position.toString() + ",\t energy: " + energy + "]";
	}

	public abstract void nextStep();

	public int getId() {
		return id;
	}

	public int getEnergy() {
		return energy;
	}

	public XY getPositionXY() {
		return position;
	}

}
