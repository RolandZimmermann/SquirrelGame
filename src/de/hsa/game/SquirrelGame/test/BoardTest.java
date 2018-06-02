package de.hsa.game.SquirrelGame.test;

import static org.junit.Assert.assertEquals;
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

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.BoardConfig;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.games.fatsquirrel.util.XY;

public class BoardTest {
	
	Board board;
	@Spy
	Board spyboard;
	@Mock
	Entity entity;
	@Mock
	XY pos;
	
	Entity[][] test = new Entity[2][2];

	@Before
	public void setUp() throws Exception {
		
//		when(BoardConfig.COUNT_BADBEAST).thenReturn(3);
//		when(BoardConfig.COUNT_GOODBEAST).thenReturn(3);
//		when(BoardConfig.COUNT_BADPLANT).thenReturn(3);
//		when(BoardConfig.COUNT_GOODPLANT).thenReturn(3);
//		when(BoardConfig.COUNT_WALL).thenReturn(3);
//		when(BoardConfig.COUNT_MASTERSQUIRREL).thenReturn(1);
//		when(BoardConfig.COUNT_HANDOPERATED_MASTERSQUIRREL).thenReturn(1);
//		when(BoardConfig.HEIGHT_SIZE).thenReturn(20);
//		when(BoardConfig.WIDTH_SIZE).thenReturn(20);
		
		BoardConfig.COUNT_MASTERSQUIRREL = 0;
		
		board = new Board(BoardConfig.WIDTH_SIZE, BoardConfig.HEIGHT_SIZE, BoardConfig.COUNT_BADPLANT,
				BoardConfig.COUNT_GOODPLANT, BoardConfig.COUNT_BADBEAST, BoardConfig.COUNT_GOODBEAST,
				BoardConfig.COUNT_HANDOPERATED_MASTERSQUIRREL, BoardConfig.COUNT_WALL, BoardConfig.COUNT_BOTS);
		spyboard = spy(board);
		
		entity = mock(GoodBeast.class);
		pos = mock(XY.class);
		when(spyboard.flatten()).thenReturn(test);
	}



	@Test
	public void testKillandReplace() {
		spyboard.killandReplace(entity, pos);
		verify(spyboard).killandReplace(any(Entity.class), any(XY.class));
		//How to test getting a variable without getter?
		verify(spyboard).getAddID(); 
	}

	@Test
	public void testKill() {
		spyboard.kill(entity);
		verify(spyboard).kill(any(Entity.class));
		verify(spyboard).getRemoveID();
	}

	@Test
	public void testSpawnMiniSquirrel() {
		spyboard.spawnMiniSquirrel(mock(MasterSquirrel.class), pos, 100);
		verify(spyboard).getAddID();
	}

	@Test
	public void testFlatten() {
		spyboard.flatten();
		Entity[][] output = spyboard.flatten();
		boolean tested = false;
		if(output == test) {
			tested = true;
		}

		assertEquals(true, tested);
	}

	@Test
	public void testSetBoardView() {
		spyboard.setBoardView(mock(BoardView.class));
		verify(spyboard).setBoardView(any(BoardView.class));
	}

	@Test
	public void testUpdate() {
		spyboard.update(MoveCommand.NON, mock(EntityContext.class));
		verify(spyboard, atLeastOnce()).getEntitySet();
		verify(spyboard, atLeastOnce()).getAddID();
		verify(spyboard, atLeastOnce()).getRemoveID();
	}

}
