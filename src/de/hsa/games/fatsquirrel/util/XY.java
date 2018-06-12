package de.hsa.games.fatsquirrel.util;

public class XY {
	public final int x;
	public final int y;

	public static final XY ZERO_ZERO = new XY(0, 0);
	public static final XY RIGHT = new XY(1, 0);
	public static final XY LEFT = new XY(-1, 0);
	public static final XY UP = new XY(0, -1);
	public static final XY DOWN = new XY(0, 1);
	public static final XY RIGHT_UP = new XY(1, -1);
	public static final XY RIGHT_DOWN = new XY(1, 1);
	public static final XY LEFT_UP = new XY(-1, -1);
	public static final XY LEFT_DOWN = new XY(-1, 1);
	public static final String test="s";

	public XY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public XY plus(XY xy) {
		return new XY(x + xy.x, y + xy.y);
	}

	public XY minus(XY xy) {
		return new XY(x - xy.x, y - xy.y);
	}

	public XY times(int factor) {
		return new XY(x * factor, y * factor);
	}

	public double length() {
		return Math.sqrt(Math.pow(Math.abs(x), 2) + Math.pow(Math.abs(y), 2));
	}

	/**
     * 
     * @param xy a second coordinate pair
     * @return the euklidian distance (pythagoras)
     */
    public double distanceFrom(XY xy) {
    	return Math.sqrt(Math.pow(Math.abs(x-xy.x), 2) + Math.pow(Math.abs(y-xy.y), 2));
    }

	public int hashCode() {
		return 0;}

	public boolean equals(Object obj) {
		if (obj instanceof XY) {
			if (x == ((XY) obj).x && y == ((XY) obj).y) {
	    		return true;
	    	}
	    	return false;
		}
		return false;
	}

	public String toString() {
        return "[x: " + x + ", y: " + y + "]";
    }

}