package de.hsa.game.SquirrelGame.mainmenu;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.general.Launcher;
import de.hsa.game.SquirrelGame.network.MaToRoMultiplayerClient;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenu extends Application {

	private BorderPane root;
	private Stage primaryStage;
	private int HEIGHT = 720;
	private int WIDTH = 1280;
	
	private Logger logger = Logger.getLogger(MainMenu.class.getName());

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		root = createBorderPane();

		primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
		primaryStage.show();
		primaryStage.setTitle("MaToRo");
	}

	private BorderPane createBorderPane() {

		StartArea startArea = new StartArea(this::startGame,this::startClient, this::setOptions, this::endGame);

		BorderPane root = new BorderPane();
		root.setCenter(startArea.getNode());

		return root;
	}

	private BorderPane createOptionStage() {
		OptionsArea optionsArea = new OptionsArea();
		BackArea backArea = new BackArea(() -> {
			if (optionsArea.getOkay()) {
				root = createBorderPane();
				primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
				return null;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Some false inputs");
				alert.setHeaderText("Please check your Input");
				alert.setContentText("Some of your inputs are invalid");
				alert.show();
				return null;
			}
		});

		BorderPane root = new BorderPane();

		root.setBottom(backArea.getNode());
		root.setCenter(optionsArea.getNode());
		return root;
	}

	private Void startGame() {
		Launcher.decide(primaryStage);
		return null;
	}
	
	private Void startClient() {
		MaToRoMultiplayerClient client = new MaToRoMultiplayerClient();
		try {
			client.start(primaryStage);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	private Void setOptions() {
		BorderPane optionStage = createOptionStage();

		root = optionStage;
		primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
		return null;
	}

	private Void endGame() {
		System.exit(0);
		return null;
	}

	public void go(String[] args) {
		Application.launch(args);

	}

}
