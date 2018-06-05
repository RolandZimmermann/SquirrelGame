package de.hsa.game.SquirrelGame.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.board.BoardConfig;
import de.hsa.game.SquirrelGame.core.board.BoardFactory;
import de.hsa.game.SquirrelGame.core.board.FlattenBoard;
import de.hsa.game.SquirrelGame.core.board.State;
import de.hsa.game.SquirrelGame.core.entity.character.MasterSquirrelBot;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.UI;
import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botimpls.MaToRoKi;

public abstract class Game {
	private static Logger logger = Logger.getLogger(Game.class.getName());

	private State state;
	private UI ui;
	private BoardView boardView;
	private EntityContext entityContext;
	private int FPS = 300000;
	private boolean multi = true;
	private boolean training = false;
	private int gameSteps;

	private int population = 60;
	private BotControllerFactory[] bots;

	private MoveCommand moveCommand = null;

	public Game(State state, UI ui) {
		this.state = state;
		this.ui = ui;
		this.gameSteps = 0;

		init();
	}

	private void init() {
		if (!training) {
			this.state.setBoard(BoardFactory.createBoard());
		} else {
			if (bots == null) {
				bots = new BotControllerFactory[population];
				for (int i = 0; i < bots.length; i++) {
					bots[i] = new MaToRoKi();
				}
			} else {

				selectBots();
			}

			this.state.setBoard(BoardFactory.createTrainingBoard(bots));
		}
		this.gameSteps = BoardConfig.GAME_STEPS;

		FlattenBoard flattenBoard = new FlattenBoard(state.getBoard());
		this.boardView = flattenBoard;
		this.entityContext = flattenBoard;
		state.getBoard().setBoardView(this.boardView);

	}

	private void selectBots() {
		List<MasterSquirrelBot> oldbots = state.getBoard().getBots();
		for(MasterSquirrelBot e: oldbots) {
			e.updateEnergy(-1000);
			if(e.getEnergy() < 0) {
				e.updateEnergy(-e.getEnergy());
			}
		}
				
		oldbots.sort((a, b) -> Integer.compare(b.getEnergy(), a.getEnergy()));
		
		
		List<MasterSquirrelBot> newbots = new ArrayList<>();
		for(MasterSquirrelBot e: oldbots) {
			for(int i = 0; i < e.getEnergy()+1; i ++) {
				newbots.add(e);
			}
		}
		
		for (int i = 0; i < population; i++) {
			MaToRoKi a = (MaToRoKi) newbots.get((int)Math.random()*newbots.size()).getBotController();
			a.mutate(0.4);
			bots[i] = (BotControllerFactory) a;
		}

	}

	public void run() throws InterruptedException {
		if (multi) {
			Timer t = new Timer();
			Timer m = new Timer();
			t.schedule(new TimerTask() {

				@Override
				public void run() {
					while (true) {
						render();
						update();
						try {
							Thread.sleep(1000 / FPS);
						} catch (InterruptedException e) {
							logger.log(Level.SEVERE, e.getMessage(), e);
							e.printStackTrace();
						}
					}
				}
			}, 1000);

			m.schedule(new TimerTask() {

				@Override
				public void run() {
					while (true) {
						processInput();
						try {
							Thread.sleep(1000 / FPS);
						} catch (InterruptedException e) {
							logger.log(Level.SEVERE, e.getMessage(), e);
							e.printStackTrace();
						}
					}
				}
			}, 1000);
		} else {
			while (true) {
				render();
				processInput();
				update();
			}
		}
	}

	public void render() {
		ui.render(boardView);
	}

	public void processInput() {
		moveCommand = ui.getCommand();

	}

	public void update() {
		if (gameSteps > 0) {
			state.update(moveCommand, entityContext);
			boardView.update();
			moveCommand = null;
			ui.message("Gamerounds left: " + gameSteps);
			gameSteps--;
		} else {
			this.state.restart();
			init();
		}
	}
}
