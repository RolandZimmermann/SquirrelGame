package de.hsa.game.SquirrelGame.general;

import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.BoardConfig;
import de.hsa.game.SquirrelGame.core.board.BoardFactory;
import de.hsa.game.SquirrelGame.core.board.State;
import de.hsa.game.SquirrelGame.gamemode.GameMode;
import de.hsa.game.SquirrelGame.ui.console.ConsoleUI;
import de.hsa.game.SquirrelGame.ui.jfx.Fx3dUI;
import de.hsa.game.SquirrelGame.ui.jfx.FxUI;
import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("restriction")
public class Launcher extends Application {

	private static Board board = BoardFactory.createBoard();

	private static Game game;

	private static final GameMode gameMode = GameMode.JFX3D;

	public static void main(String[] args) {

		if (gameMode == GameMode.CONSOLE) {
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, 1000);
	}

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		if(gameMode == GameMode.JFX3D) {
			start3dGame(primaryStage);
			return;
		}
	
		FxUI fxUI = FxUI.createInstance(BoardConfig.getSize());

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


}
