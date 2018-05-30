package de.hsa.game.SquirrelGame.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.FlattenBoard;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.games.fatsquirrel.util.XY;

public class FlattenBoardTest {

	@Spy
	FlattenBoard spy;
	@Mock
	Board dataset;
	@Mock
	MiniSquirrel mini;
	@Mock
	XY moveDirection;
	
	@Before
	public void setUp() throws Exception {
		dataset = mock(Board.class);
		spy = spy(new FlattenBoard(dataset));
		mini = mock(MiniSquirrel.class);
		moveDirection = mock(XY.class);
		// cant use this?
		//when(spy.checkCollision(mini, moveDirection)).thenReturn(mock(GoodBeast.class));
	}

	@Test
	public void testUpdate() {
		verify(dataset).flatten();
	}

	@Test
	public void testGetEntityTypeIntInt() {
		verify(spy).getCells();
	}

	@Test
	public void testTryMoveMiniSquirrelXY() {
		
		spy.tryMove(mini, moveDirection);
		verify(spy).tryMove(mini, moveDirection);
	}

	@Test
	public void testTryMoveGoodBeastXY() {
		fail("Not yet implemented");
	}

	@Test
	public void testTryMoveBadBeastXY() {
		fail("Not yet implemented");
	}

	@Test
	public void testTryMoveMasterSquirrelXY() {
		fail("Not yet implemented");
	}

	@Test
	public void testNearestPlayerEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testKill() {
		fail("Not yet implemented");
	}

	@Test
	public void testKillandReplace() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEntityTypeXY() {
		fail("Not yet implemented");
	}

	@Test
	public void testTrySpawnMiniSquirrel() {
		fail("Not yet implemented");
	}

	@Test
	public void testImpload() {
		fail("Not yet implemented");
	}

}
