package de.hsa.game.SquirrelGame.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.board.FlattenBoard;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.PlayerEntity;
import de.hsa.games.fatsquirrel.util.XY;

public class BeastTest {

	@Mock
	EntityContext entityContext;
	GoodBeast goodBeast;
	BadBeast badBeast;
	XY pos;

	@Before
	public void setUp() {
		entityContext = mock(FlattenBoard.class);
		pos = mock(XY.class);
		goodBeast = new GoodBeast(300, pos);
		badBeast = new BadBeast(299,pos);
		when(entityContext.nearestPlayerEntity(any(XY.class))).thenReturn(any(PlayerEntity.class));
	}
	
	
	@Test
	public void testNextStepGoodBeast1() {
		goodBeast.nextStep(entityContext);
		verify(entityContext).nearestPlayerEntity(pos);
		verify(entityContext).tryMove(any(GoodBeast.class), any(XY.class));
	}

	@Test
	public void testNextStepGoodBeast2() {
		goodBeast.nextStep(entityContext);
		goodBeast.nextStep(entityContext);
		goodBeast.nextStep(entityContext);
		verify(entityContext, times(1)).tryMove(any(GoodBeast.class), any(XY.class));
	}
	
	@Test
	public void testNextStepBadBeast1() {
		badBeast.nextStep(entityContext);
		verify(entityContext).nearestPlayerEntity(pos);
		verify(entityContext).tryMove(any(BadBeast.class), any(XY.class));
		badBeast.nextStep(entityContext);
		assertEquals(7, badBeast.getBiteCounter());
		verify(entityContext, times(1)).tryMove(any(BadBeast.class), any(XY.class));
	}
	
}
