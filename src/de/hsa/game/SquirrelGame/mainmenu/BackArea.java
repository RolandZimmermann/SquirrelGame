package de.hsa.game.SquirrelGame.mainmenu;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class BackArea {
	private final AnchorPane root = new AnchorPane();
	private final Button backButton = new Button("Back");
	
	public BackArea(CallableWithoutException<Void> back) {
		AnchorPane.setBottomAnchor(backButton, 10d);
		AnchorPane.setLeftAnchor(backButton, 10d);
		
		root.getChildren().add(backButton);
		
		backButton.setOnAction(e -> {
			back.call();
		});
	}
	
	public Node getNode() {
		return root;
	}
}
