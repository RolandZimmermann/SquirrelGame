package de.hsa.games.fatsquirrel.botimpls;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.util.XY;

public class testImpload implements BotController, BotControllerFactory{

	@Override
	public BotController createMasterBotController() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BotController createMiniBotController() {
		// TODO Auto-generated method stub
		return new BotController() {

			@Override
			public void nextStep(ControllerContext view) {
				view.implode(9);
				
			}
			
		};
	}

	@Override
	public void nextStep(ControllerContext view) {
		view.spawnMiniBot(XY.UP, 100);
		
	}

}
