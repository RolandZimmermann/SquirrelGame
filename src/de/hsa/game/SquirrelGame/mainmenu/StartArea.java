package de.hsa.game.SquirrelGame.mainmenu;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class StartArea {
	private final Button startGameButton = new Button("Start Game");
	private final Button optionsButton = new Button("Options");
	private final Button endButton = new Button("End Game");
	private final Label maToRoLabel = new Label("MaToRo");

	private final AnchorPane root = new AnchorPane();

	public StartArea(CallableWithoutException<Void> start, CallableWithoutException<Void> options,
			CallableWithoutException<Void> end) {
		AnchorPane.setTopAnchor(startGameButton, 150d);
		AnchorPane.setLeftAnchor(startGameButton, 500d);
		AnchorPane.setRightAnchor(startGameButton, 500d);

		AnchorPane.setTopAnchor(maToRoLabel, 30d);
		AnchorPane.setLeftAnchor(maToRoLabel, 600d);

		AnchorPane.setTopAnchor(optionsButton, 200d);
		AnchorPane.setLeftAnchor(optionsButton, 500d);
		AnchorPane.setRightAnchor(optionsButton, 500d);

		AnchorPane.setTopAnchor(endButton, 250d);
		AnchorPane.setLeftAnchor(endButton, 500d);
		AnchorPane.setRightAnchor(endButton, 500d);

		root.getChildren().addAll(startGameButton, optionsButton, endButton, maToRoLabel);

		maToRoLabel.setScaleX(3d);
		maToRoLabel.setScaleY(3d);
		
		maToRoLabel.setOnMouseEntered(e -> {
			maToRoLabel.setText("Marion Tobi Roland");
		});
		
		maToRoLabel.setOnMouseExited(e -> {
			maToRoLabel.setText("MaToRo");
		});
		
		startGameButton.setOnAction(e -> {
			start.call();
		});

		optionsButton.setOnAction(e -> {
			options.call();
		});

		endButton.setOnAction(e -> {
			end.call();
		});

		startGameButton.setOnMouseEntered(e -> {
			startGameButton.setScaleX(1.5d);
			startGameButton.setScaleY(1.5d);
		});

		optionsButton.setOnMouseEntered(e -> {
			optionsButton.setScaleX(1.5d);
			optionsButton.setScaleY(1.5d);
		});

		endButton.setOnMouseEntered(e -> {
			endButton.setScaleX(1.5d);
			endButton.setScaleY(1.5d);
		});

		startGameButton.setOnMouseExited(e -> {
			startGameButton.setScaleX(1d);
			startGameButton.setScaleY(1d);
		});

		optionsButton.setOnMouseExited(e -> {
			optionsButton.setScaleX(1d);
			optionsButton.setScaleY(1d);
		});

		endButton.setOnMouseExited(e -> {
			endButton.setScaleX(1d);
			endButton.setScaleY(1d);
		});
	}

	public Node getNode() {
		return root;
	}
}
