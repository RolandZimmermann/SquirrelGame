package de.hsa.game.SquirrelGame.general;

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
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.UI;

public abstract class Game {
	private static Logger logger = Logger.getLogger(Game.class.getName());

	private State state;
	private UI ui;
	private BoardView boardView;
	private EntityContext entityContext;
	private int FPS = 10000;
	private boolean multi = true;
	private boolean training = true;
	private int gameSteps;

	private MoveCommand moveCommand = null;

	public Game(State state, UI ui) {
		this.state = state;
		this.ui = ui;
		this.gameSteps = BoardConfig.GAME_STEPS;

		FlattenBoard flattenBoard = new FlattenBoard(state.getBoard());
		this.boardView = flattenBoard;
		this.entityContext = flattenBoard;
		state.getBoard().setBoardView(this.boardView);
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
			if (!training) {
				this.state.setBoard(BoardFactory.createBoard());
			} else {
			//	this.state.setBoard(BoardFactory.createTrainingBoard(bots));
			}
			this.gameSteps = BoardConfig.GAME_STEPS;

			FlattenBoard flattenBoard = new FlattenBoard(state.getBoard());
			this.boardView = flattenBoard;
			this.entityContext = flattenBoard;
			state.getBoard().setBoardView(this.boardView);
		}
	}
}
