package de.hsa.game.SquirrelGame.core.entity;

import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class EntitySet {
	private Entity[] entitySet = new Entity[20];
	private int pointer = 0;

	public void insert(Entity entity) {
		for (int i = 0; i < entitySet.length; i++) {
			if (entitySet[i] != null)
				if (Entity.isSameEntity(entitySet[i], entity)) {
					System.err.println("Entity already in array!");
				}
		}
		if (!isFull()) {
			entitySet[pointer] = entity;
		} else {
			Entity[] newEntitySet = new Entity[entitySet.length * 2];
			for (int i = 0; i < entitySet.length; i++) {
				newEntitySet[i] = entitySet[i];
			}
			entitySet = newEntitySet;
		}
	}

	public void delete(Entity entity) {
		for (int i = 0; i < entitySet.length; i++) {
			if (Entity.isSameEntity(entitySet[i], entity)) {
				entitySet[i] = null;
				return;
			}
		}
		System.err.println("This entity is not in the array!");
	}

	public boolean isFull() {
		for (int i = 0; i < entitySet.length; i++) {
			if (entitySet[i] == null) {
				pointer = i;
				return false;
			}
		}
		return true;
	}

	public void nextStep() {
		for (int i = 0; i < entitySet.length; i++) {
			if (entitySet[i] == null)
				continue;

			//entitySet[i].nextStep();
			if (entitySet[i] instanceof MasterSquirrel) {
				for (int j = 0; j < entitySet.length; j++) {
					if (entitySet[j] instanceof GoodPlant) {
						if (XY.equalPosition(entitySet[i].getPositionXY(), entitySet[j].getPositionXY())) {
							entitySet[i].updateEnergy(entitySet[j].getEnergy());
							delete(entitySet[j]);
							
						}
					}
				}
			}
		}
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < entitySet.length; i++) {
			if (entitySet[i] != null)
				s += entitySet[i].toString() + "\n";
		}
		return s;
	}
}
