package de.hsa.game.SquirrelGame.mainmenu;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class BackArea {
	private final AnchorPane root = new AnchorPane();
	private final Button backButton = new Button("Back");
	
	public BackArea(CallableWithoutException<Void> back) {
		AnchorPane.setBottomAnchor(backButton, 30d);
		AnchorPane.setLeftAnchor(backButton, 30d);
		
		root.getChildren().add(backButton);
		
		backButton.setOnAction(e -> {
			back.call();
		});
		
		backButton.setOnMouseEntered(e -> {
			backButton.setScaleX(1.5d);
			backButton.setScaleY(1.5d);
		});
		
		backButton.setOnMouseExited(e -> {
			backButton.setScaleX(1d);
			backButton.setScaleY(1d);
		});
	}
	
	public Node getNode() {
		return root;
	}
}
