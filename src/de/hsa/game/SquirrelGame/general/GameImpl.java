package de.hsa.game.SquirrelGame.general;

import java.util.Vector;

import de.hsa.game.SquirrelGame.core.board.State;
import de.hsa.game.SquirrelGame.network.ServerConnection;
import de.hsa.game.SquirrelGame.ui.UI;
/**
 * implements abstract class game
 * @author reich
 *
 */
public class GameImpl extends Game {

	public GameImpl(State state, UI ui, Vector<ServerConnection> serverConnections) {
		super(state, ui, serverConnections);
	}

}
