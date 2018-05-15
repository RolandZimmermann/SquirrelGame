package de.hsa.game.SquirrelGame.botapi;

import de.hsa.game.SquirrelGame.core.entity.character.MasterSquirrelBot;
import de.hsa.game.SquirrelGame.gamestats.XY;

public interface BotControllerFactory {
	
	public static BotController createMasterBotController (int id, XY xy) {
		return new MasterSquirrelBot(id, xy);
	}
	public BotController createMiniBotController();
	


}
