package de.hsa.game.SquirrelGame.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.BoardFactory;
import de.hsa.game.SquirrelGame.core.board.FlattenBoard;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.HandOperatedMasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
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

	@Mock
	Board testBoard;
	@Spy
	FlattenBoard newtestflattenboard;

	@Before
	public void setUp() throws Exception {
	

		testBoard = BoardFactory.createBoard();
		testBoard.spawnMiniSquirrel(new HandOperatedMasterSquirrel(22, new XY(23, 23)), new XY(2, 1), 100);
		newtestflattenboard = spy(new FlattenBoard(testBoard));

		// doesnt work with a mock system. need a prepared full board

		dataset = mock(Board.class);
		spy = spy(new FlattenBoard(dataset));
		
		Entity[][] e = new Entity[3][3];
		e[0][0] = new BadBeast(20, new XY(0, 0));
		when(dataset.flatten()).thenReturn(e);
		mini = mock(MiniSquirrel.class);
		moveDirection = mock(XY.class);
		when(spy.getCells()).thenReturn(e); // NullPointerException when used.

	}

	@Test
	public void testUpdate() {
		verify(dataset).flatten();
	}

	@Test
	public void testGetEntityTypeIntInt() {
		spy.getEntityType(1, 1);
		verify(spy).getEntityType(1, 1);
	}

	@Test
	public void testTryMoveMiniSquirrelXY() {

		newtestflattenboard.tryMove(new MiniSquirrel(10, new XY(1, 1), 100, new HandOperatedMasterSquirrel(99, new XY(32,32))), new XY(1, 0));
		verify(newtestflattenboard).tryMove(any(MiniSquirrel.class), any(XY.class));
		verify(newtestflattenboard).checkCollision(any(Entity.class), any(XY.class));
	}

	@Test
	public void testTryMoveGoodBeastXY() {
		newtestflattenboard.tryMove(new GoodBeast(10, new XY(1, 1)), new XY(1, 0));
		verify(newtestflattenboard).tryMove(any(GoodBeast.class), any(XY.class));
		verify(newtestflattenboard).checkCollision(any(Entity.class), any(XY.class));	
	}

	@Test
	public void testTryMoveBadBeastXY() {
		newtestflattenboard.tryMove(new BadBeast(10, new XY(1, 1)), new XY(1, 0));
		verify(newtestflattenboard).tryMove(any(BadBeast.class), any(XY.class));
		verify(newtestflattenboard).checkCollision(any(Entity.class), any(XY.class));
	}

	@Test
	public void testTryMoveMasterSquirrelXY() {
		newtestflattenboard.tryMove(new HandOperatedMasterSquirrel(10, new XY(1, 1)), new XY(1, 0));
		verify(newtestflattenboard).tryMove(any(HandOperatedMasterSquirrel.class), any(XY.class));
		verify(newtestflattenboard).checkCollision(any(Entity.class), any(XY.class));
	}

	@Test
	public void testNearestPlayerEntity() {
		spy.nearestPlayerEntity(moveDirection);
		verify(spy).nearestPlayerEntity(any(XY.class));
		verify(spy, atLeastOnce()).getCells();
	}

	@Test
	public void testKill() {
		spy.kill(mini);
		verify(spy).kill(any(Entity.class));
		verify(dataset).kill(any(Entity.class));
	}

	@Test
	public void testKillandReplace() {
		spy.killandReplace(new GoodBeast(30, new XY(1,1)));
		verify(dataset).kill(any(Entity.class));
		verify(dataset).killandReplace(any(Entity.class), any(XY.class));
	}

	@Test
	public void testGetEntityTypeXY() {
		spy.getEntityType(new XY(1, 1));
		verify(spy).getEntityType(any(XY.class));
	}

	@Test
	public void testTrySpawnMiniSquirrel() {
		spy.trySpawnMiniSquirrel(mock(MasterSquirrel.class), moveDirection, 100);
		verify(spy).getEntityType(moveDirection);
	}

	@Test
	public void testImpload() {
		
		//Exception even without mocks???
		
		newtestflattenboard.impload(new MiniSquirrel(30, new XY(1, 1), 100, new HandOperatedMasterSquirrel(21, new XY(52, 34))), 3);
		verify(newtestflattenboard).impload(any(MiniSquirrel.class), any(int.class));
		verify(newtestflattenboard, atLeastOnce()).getCells();
	}

}
