package gameStats;

import general.XY;

public enum Direction {
	UP(new XY(0,-1)),
	DOWN(new XY(0,1)),
	LEFT(new XY(-1,0)),
	RIGHT(new XY(1,0)),
	UPLEFT(new XY(-1,-1)),
	UPRIGHT(new XY(1,-1)),
	DOWNLEFT(new XY(-1,1)),
	DOWNRIGHT(new XY(1,1));
	
	public XY xy;
	
	private Direction(XY xy) {
		this.xy = xy;
	}
}
