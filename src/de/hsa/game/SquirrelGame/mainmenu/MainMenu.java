package de.hsa.game.SquirrelGame.mainmenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenu extends Application {
	
	private BorderPane root;
	private Stage primaryStage;
	private int HEIGHT = 720;
	private int WIDTH = 1280;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		root = createBorderPane();
		
		primaryStage.setScene(new Scene(root,WIDTH,HEIGHT));
		primaryStage.show();
		primaryStage.setTitle("MaToRo");
	}

	private BorderPane createBorderPane() {
		
		StartArea startArea = new StartArea(this::startGame, this::setOptions, this::endGame);
		
		BorderPane root = new BorderPane();
		root.setCenter(startArea.getNode());
		
		return root;
	}
	
	
	private BorderPane createOptionStage() {
		BackArea backArea = new BackArea(() ->{
			root = createBorderPane();
			primaryStage.setScene(new Scene(root,WIDTH,HEIGHT));
			return null;
		});
		OptionsArea optionsArea = new OptionsArea();
		
		BorderPane root = new BorderPane();
		
		root.setBottom(backArea.getNode());
		root.setCenter(optionsArea.getNode());
		return root;
	}
	
	private Void startGame() {
		return null;
	}
	
	private Void setOptions() {
		BorderPane optionStage = createOptionStage();
		
		root = optionStage;
		primaryStage.setScene(new Scene(root,WIDTH,HEIGHT));
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
