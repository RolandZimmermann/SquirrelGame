package de.hsa.game.SquirrelGame.mainmenu;

import de.hsa.game.SquirrelGame.gamemode.GameMode;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

public class OptionsArea {
	private final AnchorPane root = new AnchorPane();
	private ChoiceBox<GameMode> gameModeBox = new ChoiceBox<GameMode>(FXCollections.observableArrayList(
		    GameMode.JFX,GameMode.JFX3D,GameMode.MULTIPLAYER));
	
	public OptionsArea() {
		AnchorPane.setTopAnchor(gameModeBox, 100d);
		
		gameModeBox.setTooltip(new Tooltip("Select the gameMode"));
		gameModeBox.getSelectionModel().select(GameMode.JFX);
		
		root.getChildren().add(gameModeBox);
	}
	
	
	public Node getNode() {
		return root;
	}
}
