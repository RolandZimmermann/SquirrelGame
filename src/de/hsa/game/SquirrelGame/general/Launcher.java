package de.hsa.game.SquirrelGame.general;

import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.BoardConfig;
import de.hsa.game.SquirrelGame.core.board.BoardFactory;
import de.hsa.game.SquirrelGame.core.board.State;
import de.hsa.game.SquirrelGame.gamemode.GameMode;
import de.hsa.game.SquirrelGame.log.GameLogger;
import de.hsa.game.SquirrelGame.ui.console.ConsoleUI;
import de.hsa.game.SquirrelGame.ui.jfx.Fx3dUI;
import de.hsa.game.SquirrelGame.ui.jfx.FxUI;
import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("restriction")
public class Launcher extends Application {

	private static Board board = BoardFactory.createBoard();

	private static Game game;

	private static final GameMode gameMode = GameMode.JFX3D;
	
	private static Logger logger = Logger.getLogger(GameLogger.class.getName());

	public static void main(String[] args) {
		//start Logging
		
		//TODO: Why can you only change the level of the logger in the class itself??
		new GameLogger();
		logger.info("START");

		if (gameMode == GameMode.CONSOLE) {
			logger.info("Starting Console Mode");
			game = new GameImpl(new State(board), new ConsoleUI());
			startGame(game);
		} else if (gameMode == GameMode.JFX || gameMode == GameMode.JFX3D) {
			Application.launch(args);
		}

	}

	private static void start3dGame(Stage primaryStage) {
		Fx3dUI fxUI = Fx3dUI.createInstance(BoardConfig.getSize());

		game = new GameImpl(new State(board), fxUI);
		primaryStage.setScene(fxUI);
		primaryStage.setTitle("MaToRo");
		fxUI.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent evt) {
				System.exit(-1);
			}
		});

		primaryStage.show();

		startGame(game);
		
	}

	private static void startGame(Game game) {

		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					game.run();
				} catch (InterruptedException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
					e.printStackTrace();
				}

			}
		}, 1000);
	}

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		if(gameMode == GameMode.JFX3D) {
			logger.info("Starting JFX3D game");
			start3dGame(primaryStage);
			return;
		}
		
		logger.info("Starting JFX game");
		
		FxUI fxUI = FxUI.createInstance(BoardConfig.getSize());

		primaryStage.setScene(fxUI);
		primaryStage.setTitle("MaToRo");
		fxUI.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent evt) {
				System.exit(-1);
			}
		});

		primaryStage.show();

		startGame(game);
	}


}
