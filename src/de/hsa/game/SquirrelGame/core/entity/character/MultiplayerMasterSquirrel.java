package de.hsa.game.SquirrelGame.core.entity.character;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.network.ServerConnection;
import de.hsa.games.fatsquirrel.util.XY;

public class MultiplayerMasterSquirrel extends MasterSquirrel {

	private int wallCounter = 0;
	private ServerConnection serverConnection;
	private MoveCommand moveDirection;

	public MultiplayerMasterSquirrel(int id, XY position, ServerConnection serverConnection) {
		super(id, position);
		this.serverConnection = serverConnection;
	}

	@Override
	public void wallCollison() {
		wallCounter = 4;
	}

	@Override
	public void nextStep(EntityContext entityContext) {
		if (wallCounter == 0) {
			if (moveDirection != null) {
				if (moveDirection == MoveCommand.MINI_DOWN) {
					entityContext.trySpawnMiniSquirrel(this, moveDirection.xy, moveDirection.energy);
				} else {
					entityContext.tryMove(this, moveDirection.xy);
				}
			}
		} else {
			wallCounter--;
		}
	}

	public void setCommand(String command) {
		if (command != null) {
			if (command.equals("W")) {
				moveDirection = MoveCommand.UP;
			} else if (command.equals("S")) {
				moveDirection = MoveCommand.DOWN;
			} else if (command.equals("A")) {
				moveDirection = MoveCommand.LEFT;
			} else if (command.equals("D")) {
				moveDirection = MoveCommand.RIGHT;
			} else if (command.equals("Q")) {
				moveDirection = MoveCommand.UPLEFT;
			} else if (command.equals("E")) {
				moveDirection = MoveCommand.UPRIGHT;
			} else if (command.equals("Y")) {
				moveDirection = MoveCommand.DOWNLEFT;
			} else if (command.equals("X")) {
				moveDirection = MoveCommand.UPRIGHT;
			} else if (command.equals("SPACE")) {
				moveDirection = MoveCommand.MINI_DOWN;
			}
		}
	}

	public ServerConnection getServerConnection() {
		return this.serverConnection;
	}

}
