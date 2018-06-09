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

public abstract class Game {
	private static Logger logger = Logger.getLogger(Game.class.getName());

	private State state;
	private UI ui;
	private BoardView boardView;
	private EntityContext entityContext;
	private int FPS = 60;
	private boolean multi = true;
	private boolean training = false;
	private boolean oldAI = false;
	private int gameSteps;

	private int population = 30;
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
			if (oldAI) {
				if (bots == null) {
					bots = new BotControllerFactory[population];
					for (int i = 0; i < bots.length; i++) {
						bots[i] = new MaToRoKiold();
					}
				} else {

					selectBots();
				}
			} else {
				if (bots == null) {
					bots = new BotControllerFactory[population];
					for (int i = 0; i < bots.length; i++) {
						bots[i] = new MaToRoKi();
					}
				} else {

					selectBots();
				}
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
		if (oldAI) {
			List<MasterSquirrelBot> oldbots = state.getBoard().getBots();
			for (MasterSquirrelBot e : oldbots) {
				e.updateEnergy(-1000);
				if (e.getEnergy() < 0) {
					e.updateEnergy(-e.getEnergy());
				}
			}

			oldbots.sort((a, b) -> Integer.compare(b.getEnergy(), a.getEnergy()));

			List<MasterSquirrelBot> newbots = new ArrayList<>();
			for (MasterSquirrelBot e : oldbots) {
				for (int i = 0; i < e.getEnergy() + 1; i++) {
					newbots.add(e);
				}
			}

			for (int i = 0; i < population; i++) {
				MaToRoKiold a = (MaToRoKiold) newbots.get((int) Math.random() * newbots.size()).getBotController();
				a.mutate(0.4);
				bots[i] = (BotControllerFactory) a;
			}
		} else {
			List<MasterSquirrelBot> oldbots = state.getBoard().getBots();

			float totalfitness = 0;
			MasterSquirrelBot best = oldbots.get(0);

			for (MasterSquirrelBot e : oldbots) {
				e.updateEnergy(-1000);
				if (e.getEnergy() < 0) {
					e.updateEnergy(-e.getEnergy());
				}

				if (best.getEnergy() < e.getEnergy()) {
					best = e;
				}

				totalfitness += e.getEnergy();
			}

			bots[0] = (BotControllerFactory) ((MaToRoKi) best.getBotController());
			System.out.println((((MaToRoKi) best.getBotController()).toString()) + " | " + best.getEnergy());

			for (int i = 1; i < population; i++) {

				MaToRoKi child;

				if (Math.random() < 0.25) {
					child = (MaToRoKi) selectParent(oldbots).getBotController();
				} else {

					MasterSquirrelBot parent1 = selectParent(oldbots);
					MasterSquirrelBot parent2 = selectParent(oldbots);

					if (parent1.getEnergy() < parent2.getEnergy()) {
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
			totalfitness += oldbots.get(i).getEnergy();
		}

		float rand = (float) (Math.random() * totalfitness);
		float runningSum = 0;

		for (int i = 0; i < oldbots.size(); i++) {
			runningSum += oldbots.get(i).getEnergy();
			if (runningSum >= rand) {
				return oldbots.get(i);
			}
		}

		return null;
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
	
	public State getState () {
		return this.state;
		
		
	}
	
	
}
