package de.hsa.game.SquirrelGame.gamestats;

public enum Energy {
	WALL(-10), GOODBEAST(200), BADBEAST(-150), MASTERSQUIRREL(1000), GOODPLANT(100), BADPLANT(-100);

	public int energy;

	private Energy(int energy) {
		this.energy = energy;
	}
}
