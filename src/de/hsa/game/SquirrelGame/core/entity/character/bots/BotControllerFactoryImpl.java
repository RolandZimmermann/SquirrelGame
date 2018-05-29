package de.hsa.game.SquirrelGame.core.entity.character.bots;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;

public class BotControllerFactoryImpl implements BotControllerFactory {

	@Override
	public BotController createMasterBotController() {
		return new HalfRandomBot();
	}

	@Override
	public BotController createMiniBotController() {
		// TODO Auto-generated method stub
		return null;
	}

}
