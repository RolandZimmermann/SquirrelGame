package de.hsa.game.SquirrelGame.mainmenu;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import de.hsa.game.SquirrelGame.core.board.BoardConfig;
import de.hsa.game.SquirrelGame.gamemode.GameMode;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

public class OptionsArea {
	private final AnchorPane root = new AnchorPane();

	private Label optionsLabel = new Label("Options");

	private Button save = new Button("Save");

	private Label gameModeLabel = new Label("Select gameMode:");
	private ChoiceBox<GameMode> gameModeBox = new ChoiceBox<GameMode>(
			FXCollections.observableArrayList(GameMode.JFX, GameMode.JFX3D, GameMode.MULTIPLAYER));

	private Label fpsLabel = new Label("FPS:");
	private TextField fpsInput = new TextField(Integer.toString(BoardConfig.FPS));

	private Label multiThreadedlabel = new Label("Multi-Threaded:");
	private ChoiceBox<String> multiThreadedBox = new ChoiceBox<String>(
			FXCollections.observableArrayList("true", "false"));

	private Label gameStepsLabel = new Label("Gamesteps:");
	private TextField gameStepsField = new TextField(Integer.toString(BoardConfig.GAME_STEPS));

	private Label portLabel = new Label("Port:");
	private TextField portField = new TextField(Integer.toString(BoardConfig.PORT));

	private Label cellSizeLabel = new Label("Cellsize:");
	private TextField cellSizeField = new TextField(Integer.toString(BoardConfig.CELL_SIZE));

	private Label widthSizeLabel = new Label("Widthsize:");
	private TextField widthSizeField = new TextField(Integer.toString(BoardConfig.WIDTH_SIZE));
	
	private Label heightSizeLabel = new Label("Heightsize:");
	private TextField heightSizeField = new TextField(Integer.toString(BoardConfig.HEIGHT_SIZE));
	
	
	public OptionsArea() {
		optionsLabel.setScaleX(3d);
		optionsLabel.setScaleY(3d);
		optionsLabel.setTextAlignment(TextAlignment.CENTER);

		optionsLabel.setTooltip(new Tooltip("Just the optionsmenu"));

		AnchorPane.setTopAnchor(optionsLabel, 30d);
		AnchorPane.setLeftAnchor(optionsLabel, 600d);

		AnchorPane.setTopAnchor(gameModeLabel, 155d);
		AnchorPane.setLeftAnchor(gameModeLabel, 100d);
		gameModeBox.setTooltip(new Tooltip("Select the gameMode"));
		gameModeBox.getSelectionModel().select(GameMode.JFX);

		AnchorPane.setTopAnchor(gameModeBox, 150d);
		AnchorPane.setLeftAnchor(gameModeBox, 250d);

		AnchorPane.setTopAnchor(fpsLabel, 200d);
		AnchorPane.setLeftAnchor(fpsLabel, 100d);
		AnchorPane.setTopAnchor(fpsInput, 200d);
		AnchorPane.setLeftAnchor(fpsInput, 250d);
		fpsInput.setTooltip(new Tooltip("Set the frames per second"));

		AnchorPane.setBottomAnchor(save, 30d);
		AnchorPane.setLeftAnchor(save, 600d);
		save.setTooltip(new Tooltip("Save all settings"));
		save.setScaleX(1.5d);
		save.setScaleY(1.5d);

		save.setOnMouseEntered(e -> {
			save.setScaleX(2d);
			save.setScaleY(2d);
		});

		save.setOnMouseExited(e -> {
			save.setScaleX(1.5d);
			save.setScaleY(1.5d);
		});

		AnchorPane.setLeftAnchor(multiThreadedlabel, 100d);
		AnchorPane.setTopAnchor(multiThreadedlabel, 250d);
		AnchorPane.setLeftAnchor(multiThreadedBox, 250d);
		AnchorPane.setTopAnchor(multiThreadedBox, 250d);
		multiThreadedBox.getSelectionModel().select(BoardConfig.MULTI_THREAD ? "true" : "false");
		multiThreadedBox.setTooltip(new Tooltip("Select if the game should run in multiple threads"));

		AnchorPane.setTopAnchor(gameStepsLabel, 300d);
		AnchorPane.setLeftAnchor(gameStepsLabel, 100d);
		AnchorPane.setTopAnchor(gameStepsField, 300d);
		AnchorPane.setLeftAnchor(gameStepsField, 250d);
		gameStepsField.setTooltip(new Tooltip("Set the number of rounds per game"));

		AnchorPane.setTopAnchor(portLabel, 350d);
		AnchorPane.setLeftAnchor(portLabel, 100d);
		AnchorPane.setTopAnchor(portField, 350d);
		AnchorPane.setLeftAnchor(portField, 250d);
		portField.setTooltip(new Tooltip("The port for the server"));

		AnchorPane.setTopAnchor(cellSizeLabel, 400d);
		AnchorPane.setLeftAnchor(cellSizeLabel, 100d);
		AnchorPane.setTopAnchor(cellSizeField, 400d);
		AnchorPane.setLeftAnchor(cellSizeField, 250d);
		cellSizeField.setTooltip(new Tooltip("The size of a tile"));

		AnchorPane.setTopAnchor(widthSizeLabel, 450d);
		AnchorPane.setLeftAnchor(widthSizeLabel, 100d);
		AnchorPane.setTopAnchor(widthSizeField, 450d);
		AnchorPane.setLeftAnchor(widthSizeField, 250d);
		widthSizeField.setTooltip(new Tooltip("The width of the board"));
		
		AnchorPane.setTopAnchor(heightSizeLabel, 500d);
		AnchorPane.setLeftAnchor(heightSizeLabel, 100d);
		AnchorPane.setTopAnchor(heightSizeField, 500d);
		AnchorPane.setLeftAnchor(heightSizeField, 250d);
		heightSizeField.setTooltip(new Tooltip("The height of the board"));

		root.getChildren().addAll(optionsLabel, gameModeLabel, gameModeBox, fpsLabel, fpsInput, save,
				multiThreadedlabel, multiThreadedBox, gameStepsLabel, gameStepsField, portLabel, portField,
				cellSizeField, cellSizeLabel, widthSizeField, widthSizeLabel, heightSizeField, heightSizeLabel);
	}

	public Node getNode() {
		return root;
	}
}
