package de.hsa.game.SquirrelGame.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.hsa.games.fatsquirrel.util.XY;
/**
 * Class to test XY class
 * @author reich
 *
 */
public class XYtest {
	
	XY xy1 = new XY(1,1);
	XY xy2 = new XY(2,2);
	
	/**
	 * checks if xy1 and new xy are equal
	 */
	@Test
	public void testXY() {
		assertEquals(xy1, new XY(1,1));
	}
/**
 * Tests adding.
 * 
 */
	@Test
	public void testPlus() {
		assertEquals(new XY(3,3), xy1.plus(xy2));
	}
	
	/**
	 * Tests subtraction.
	 * 
	 */

	@Test
	public void testMinus() {
		assertEquals(new XY(1,1), xy2.minus(xy1));
	}
/**
 * Tests timing
 */
	@Test
	public void testTimes() {
		assertEquals(new XY(10,10), xy1.times(10));
	}
/**
 * test the length
 */
	@Test
	public void testLength() {
		assertEquals(Math.sqrt(Math.pow(Math.abs(2), 2) + Math.pow(Math.abs(2), 2)), xy2.length(), 0.0002);
	}
/**
 * Tests the distanceFrom method
 */
	@Test
	public void testDistanceFrom() {
		assertEquals(Math.sqrt(Math.pow(Math.abs(1), 2) + Math.pow(Math.abs(1), 2)),xy1.distanceFrom(xy2),0.0005);
	}
/**
 * Test equalsObject method
 */
	@Test
	public void testEqualsObject() {
		assertEquals(false, xy1.equals(xy2));
	}
/**
 * Tests toString method
 */
	@Test
	public void testToString() {
		assertEquals("[x: " + "1" + ", y: " + "1" + "]", xy1.toString());
	}

}
