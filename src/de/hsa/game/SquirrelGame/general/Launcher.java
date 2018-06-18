package de.hsa.game.SquirrelGame.general;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.BoardConfig;
import de.hsa.game.SquirrelGame.core.board.BoardFactory;
import de.hsa.game.SquirrelGame.core.board.State;
import de.hsa.game.SquirrelGame.gamemode.GameMode;
import de.hsa.game.SquirrelGame.log.GameLogger;
import de.hsa.game.SquirrelGame.network.Multiplayer;
import de.hsa.game.SquirrelGame.ui.console.ConsoleUI;
import de.hsa.game.SquirrelGame.ui.jfx.Fx3dUI;
import de.hsa.game.SquirrelGame.ui.jfx.FxUI;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main class of the project
 * 
 * @author reich
 *
 */
public class Launcher extends Application {


    private static Game game;

    private static final GameMode gameMode = GameMode.JFX;

    private static Logger logger = Logger.getLogger(Launcher.class.getName());

    /**
     * starts the game
     * 
     * @param args
     */
    public static void main(String[] args) {

        BoardConfig.load();
        GameLogger.init();
        logger.info("START");
        if(gameMode == GameMode.MULTIPLAYER) {
        	Multiplayer multiplayer = new Multiplayer();
        	multiplayer.go(args);
        	return;
        }
        

        if (gameMode == GameMode.CONSOLE) {
            logger.info("Starting Console Mode");
            game = new GameImpl(new State(), new ConsoleUI(), null);
            startGame(game);
        } else if (gameMode == GameMode.JFX || gameMode == GameMode.JFX3D) {
            Application.launch(args);
        }

    }

    /**
     * starts game in 3d
     * 
     * @param primaryStage
     */
    private static void start3dGame(Stage primaryStage) {
        Fx3dUI fxUI = Fx3dUI.createInstance(BoardConfig.getSize());

        game = new GameImpl(new State(), fxUI, null);
        primaryStage.setScene(fxUI);
        primaryStage.setTitle("MaToRo");
        fxUI.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent evt) {
                game.getState().save();
                System.exit(-1);
            }
        });

        primaryStage.show();

        startGame(game);

    }
/**	
 * starts game
 * @param game
 */
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
/**
 * starts game with set mode (3d or console)
 */
    @Override
    public void start(Stage primaryStage) throws Exception {

        if (gameMode == GameMode.JFX3D) {
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
                game.getState().save();
                System.exit(-1);
            }
        });

        primaryStage.show();

        game = new GameImpl(new State(), fxUI, null);

        startGame(game);
    }

}
