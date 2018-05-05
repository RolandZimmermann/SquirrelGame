package de.hsa.game.SquirrelGame.gamestats;

public enum MoveCommand {
	UP(new XY(0, -1)), DOWN(new XY(0, 1)), LEFT(new XY(-1, 0)), RIGHT(new XY(1, 0)), UPLEFT(new XY(-1, -1)), UPRIGHT(
			new XY(1, -1)), DOWNLEFT(new XY(-1, 1)), DOWNRIGHT(new XY(1, 1)), NON(new XY(0, 0)), MINI_UP(new XY(0, -1),
					0), MINI_DOWN(new XY(0, 1), 0), MINI_LEFT(new XY(-1, 0),
							0), MINI_RIGHT(new XY(1, 0), 0), MINI_UPLEFT(new XY(-1, -1),
									0), MINI_UPRIGHT(new XY(1, -1), 0), MINI_DOWNLEFT(new XY(-1, 1),
											0), MINI_DOWNRIGHT(new XY(1, 1), 0), MASTER(new XY(0, 0));
	public XY xy;
	public int energy;

	private MoveCommand(XY xy) {
		this.xy = xy;
	}

	private MoveCommand(XY xy, int energy) {
		this.xy = xy;
		this.energy = energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
		
	}

	
	
}
