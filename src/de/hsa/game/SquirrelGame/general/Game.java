package de.hsa.game.SquirrelGame.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.UI;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botimpls.MaToRoKi;
import de.hsa.games.fatsquirrel.botimpls.MaToRoKiold;

/**
 * Class initialize game
 * 
 * @author reich
 *
 */
public abstract class Game {
	private static Logger logger = Logger.getLogger(Game.class.getName());

	private State state;
	private UI ui;
	private BoardView boardView;
	private EntityContext entityContext;
	private int FPS = 10000000;
	private boolean multi = true;
	private boolean training = BoardConfig.TRAINING;
	private boolean oldAI = BoardConfig.OLD_AI;
	private int gameSteps;

	private int population = 30;
	private BotControllerFactory[] bots;

	private MoveCommand moveCommand = null;

	/**
	 * create game with given methode
	 * 
	 * @param state
	 * @param ui
	 */
	public Game(State state, UI ui) {
		this.state = state;
		this.ui = ui;
		this.gameSteps = 0;

		init();
	}

	/**
	 * initialize the board
	 */
	private void init() {
		if (!training) {
			this.state.setBoard(BoardFactory.createBoard());
		} else {
			if (oldAI) {
				MaToRoKiold ki = null;
				if (state.loadNN() != null && bots == null) {
					ki = state.loadNN();
					System.out.println("Geladen");
				}
				if (bots == null) {
					bots = new BotControllerFactory[population];
					for (int i = 0; i < bots.length; i++) {
						if (ki == null) {
							bots[i] = new MaToRoKiold();
						} else {
							bots[i] = ki;

						}
					}
				} else {

					selectBots();
				}
			} else {
				MaToRoKi ki = null;
				if (state.loadObject() != null && bots == null) {
					ki = state.loadObject();
					state.getBoard().setBest(ki);
					System.out.println("Geladen");
				}
				if (bots == null) {
					bots = new BotControllerFactory[population];
					for (int i = 0; i < bots.length; i++) {
						if (ki == null) {
							bots[i] = new MaToRoKi();
						} else {
							bots[i] = ki;

						}
					}
				} else {
					selectBots();
				}
			}
			this.state.setBoard(BoardFactory.createTrainingBoard(bots));
			this.state.load();
		}
		this.gameSteps = BoardConfig.GAME_STEPS;

		FlattenBoard flattenBoard = new FlattenBoard(state.getBoard());
		this.boardView = flattenBoard;
		this.entityContext = flattenBoard;
		state.getBoard().setBoardView(this.boardView);

	}

	/**
	 * Select the bot
	 */
	private void selectBots() {
		if (oldAI) {
			List<MasterSquirrelBot> oldbots = state.getBoard().getBots();
			MasterSquirrelBot best = oldbots.get(0);
			float totalfitness = 0;
			for (MasterSquirrelBot e : oldbots) {
				e.fitness();
				if (e.fitness < 0) {
					e.fitness = 0;
				}
				if (e.fitness > best.fitness) {
					best = e;
				}
				
				totalfitness += e.fitness;
			}

			// oldbots.sort((a, b) -> Integer.compare(b.getEnergy(), a.getEnergy()));

			state.saveObject((MaToRoKiold) best.getBotController());
			System.out.println("Saved");

			for (int i = 0; i < population; i++) {
				MasterSquirrelBot b = selectParent(oldbots);
				MaToRoKiold a = (MaToRoKiold) b.getBotController();
				a.mutate(0.4);
				bots[i] = (BotControllerFactory) a;
				System.out.println(b.fitness);
			}
			
			System.out.println("TotalFitness: " + totalfitness);
		} else {
			List<MasterSquirrelBot> oldbots = state.getBoard().getBots();

			float totalfitness = 0;
			MasterSquirrelBot best = oldbots.get(0);

			for (MasterSquirrelBot e : oldbots) {
				e.fitness();

				if (e.fitness < 0) {
					e.fitness = 0;
				}

				if (best.fitness < e.fitness) {
					best = e;
				}

				totalfitness += e.fitness;
			}

			state.getBoard().setBest((MaToRoKi) best.getBotController());

			bots[0] = (BotControllerFactory) ((MaToRoKi) best.getBotController());
			System.out.println((((MaToRoKi) best.getBotController()).toString()) + " || " + best.fitness);
			state.saveObject((MaToRoKi) best.getBotController());
			System.out.println("SAVED");
			System.out.println("TotalFitness: " + totalfitness );

			for (int i = 1; i < population; i++) {

				MaToRoKi child;

				if (Math.random() < 0.25) {
					child = (MaToRoKi) selectParent(oldbots).getBotController();
				} else {

					MasterSquirrelBot parent1 = selectParent(oldbots);
					MasterSquirrelBot parent2 = selectParent(oldbots);

					if (parent1.fitness < parent2.fitness) {
						child = ((MaToRoKi) parent2.getBotController())
								.crossover(((MaToRoKi) parent1.getBotController()).getGenome());
					} else {
						child = ((MaToRoKi) parent1.getBotController())
								.crossover(((MaToRoKi) parent2.getBotController()).getGenome());
					}

					child.mutate();

				}

				bots[i] = (BotControllerFactory) child;

			}
		}
	}

	private MasterSquirrelBot selectParent(List<MasterSquirrelBot> oldbots) {

		float totalfitness = 0;

		for (int i = 0; i < oldbots.size(); i++) {
			totalfitness += oldbots.get(i).fitness;
		}

		float rand = (float) (Math.random() * totalfitness);
		float runningSum = 0;

		for (int i = 0; i < oldbots.size(); i++) {
			runningSum += oldbots.get(i).fitness;
			if (runningSum >= rand) {
				return oldbots.get(i);
			}
		}

		return oldbots.get((int) (Math.random() * oldbots.size()));
	}

	/**
	 * starts the different threads
	 * 
	 * @throws InterruptedException
	 */
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

	/**
	 * renders ui
	 */
	public void render() {
		ui.render(boardView);
	}

	/**
	 * set the {@code moveCommand}
	 */
	public void processInput() {
		moveCommand = ui.getCommand();

	}

	/**
	 * updates the {@code state} and the {@code boardView}
	 */
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

	public State getState() {
		return this.state;

	}

}
