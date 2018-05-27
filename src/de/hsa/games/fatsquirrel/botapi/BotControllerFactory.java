package de.hsa.games.fatsquirrel.botapi;
import de.hsa.game.SquirrelGame.core.entity.character.MasterSquirrelBot;
import de.hsa.games.fatsquirrel.util.XY;

public interface BotControllerFactory {
	
	public static BotController createMasterBotController (int id, XY xy) {
		return (BotController) new MasterSquirrelBot(id, xy);
	}
	public BotController createMiniBotController();
	


}
