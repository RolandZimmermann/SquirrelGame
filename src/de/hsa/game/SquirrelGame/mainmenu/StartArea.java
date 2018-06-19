package de.hsa.game.SquirrelGame.mainmenu;

import java.util.concurrent.Callable;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class StartArea {
	private final Button startGameButton = new Button("Start Game");
	private final Button optionsButton = new Button("Options");
	private final Button endButton = new Button("End Game");
	
	private final AnchorPane root = new AnchorPane();
	
	public StartArea(CallableWithoutException<Void> start, CallableWithoutException<Void> options, CallableWithoutException<Void> end) {
		AnchorPane.setTopAnchor(startGameButton, 150d);
		AnchorPane.setLeftAnchor(startGameButton, 500d);
		AnchorPane.setRightAnchor(startGameButton, 500d);
		
		AnchorPane.setTopAnchor(optionsButton, 200d);
		AnchorPane.setLeftAnchor(optionsButton, 500d);
		AnchorPane.setRightAnchor(optionsButton, 500d);
		
		AnchorPane.setTopAnchor(endButton, 250d);
		AnchorPane.setLeftAnchor(endButton, 500d);
		AnchorPane.setRightAnchor(endButton, 500d);
		
		root.getChildren().addAll(startGameButton, optionsButton, endButton);
		
		
		startGameButton.setOnAction(e -> {
			start.call();
		});
		
		optionsButton.setOnAction(e -> {
			options.call();
		});
		
		endButton.setOnAction(e -> {
			end.call();
		});
	}
	
	public Node getNode() {
		return root;
	}
}
