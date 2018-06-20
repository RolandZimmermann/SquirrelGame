package de.hsa.game.SquirrelGame.mainmenu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.hsa.game.SquirrelGame.core.board.BoardConfig;
import de.hsa.game.SquirrelGame.gamemode.GameMode;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

public class OptionsArea {
	private final AnchorPane root = new AnchorPane();
	
	private Label optionsLabel = new Label("Options");
	
	
	private Label gameModeLabel = new Label("Select gameMode:");
	private ChoiceBox<GameMode> gameModeBox = new ChoiceBox<GameMode>(FXCollections.observableArrayList(
		    GameMode.JFX,GameMode.JFX3D,GameMode.MULTIPLAYER));
	
	private Label fpsLabel = new Label("FPS:");
	private TextField fpsInput = new TextField(Integer.toString(BoardConfig.FPS));
	
	public OptionsArea() {
		optionsLabel.setPrefSize(60, 60);
		optionsLabel.setTextAlignment(TextAlignment.CENTER);
		
		
		AnchorPane.setTopAnchor(optionsLabel, 10d);
		AnchorPane.setLeftAnchor(optionsLabel, 600d);
		AnchorPane.setRightAnchor(optionsLabel, 500d);
		
		
		AnchorPane.setTopAnchor(gameModeLabel, 155d);
		AnchorPane.setLeftAnchor(gameModeLabel, 100d);
		gameModeBox.setTooltip(new Tooltip("Select the gameMode"));
		gameModeBox.getSelectionModel().select(GameMode.JFX);
		
		AnchorPane.setTopAnchor(gameModeBox, 150d);
		AnchorPane.setLeftAnchor(gameModeBox, 250d);
		
		AnchorPane.setTopAnchor(fpsLabel, 200d);
		AnchorPane.setLeftAnchor(fpsLabel, 100d);
		AnchorPane.setTopAnchor(fpsInput, 190d);
		AnchorPane.setLeftAnchor(fpsInput, 250d);
		
		
		
		
		root.getChildren().addAll(optionsLabel, gameModeLabel, gameModeBox, fpsLabel, fpsInput);
	}
	
	
	public Node getNode() {
		return root;
	}
}
