package de.hsa.game.SquirrelGame.core.entity;

import de.hsa.games.fatsquirrel.util.XY;
/**
 * Represents the entities of the game
 * @author reich
 *
 */

public abstract class Entity {

	private final int id;
	private XY position;
	private int energy;
/**
 * Creates new {@code Entity} with int x and int y
 * @param id
 * @param X
 * @param Y
 * @param energy
 */
	public Entity(int id, int X, int Y, int energy) {

		this.id = id;
		this.position = new XY(X, Y);
		this.energy = energy;
	}
	/**
	 * Creates new {@code Entity} with {@code XY} position
	 * @param id
	 * @param X
	 * @param Y
	 * @param energy
	 */
	public Entity(int id, XY position, int energy) {

		this.id = id;
		this.position = position;
		this.energy = energy;
	}
	/**
	 * Checks if given {@code entity} is same
	 * @param entity1
	 *         {@code entity} to check
	 * @param entity2
	 *         {@code entity} to check
	 * @return
	 */
	public static boolean isSameEntity(Entity entity1, Entity entity2) {
		if(entity1.equals(entity2)) {
			return true;
		}
		return false;
	}
/**
 * updates energy by given energy
 * @param energy
 *      given energy
 */
	public void updateEnergy(int energy) {
		this.energy += energy;
	}

	public void setPositionXY(int deltaX, int deltaY) {
		position = position.plus(new XY(deltaX,deltaY));
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "\t[id: " + id + ",\t position: " + position.toString() + ",\t energy: " + energy + "]";
	}

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
