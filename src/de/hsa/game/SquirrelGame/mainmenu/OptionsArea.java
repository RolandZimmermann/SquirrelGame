package de.hsa.game.SquirrelGame.mainmenu;

import de.hsa.game.SquirrelGame.core.board.BoardConfig;
import de.hsa.game.SquirrelGame.gamemode.GameMode;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

public class OptionsArea {
	
	private boolean okay = true;
	
	private final AnchorPane root = new AnchorPane();

	private Label optionsLabel = new Label("Options");

	private Button save = new Button("Save");

	private Label gameModeLabel = new Label("Select gameMode:");
	private ChoiceBox<GameMode> gameModeBox = new ChoiceBox<GameMode>(
			FXCollections.observableArrayList(GameMode.JFX, GameMode.JFX3D, GameMode.MULTIPLAYER));

	private Label fpsLabel = new Label("FPS:");
	private TextField fpsInput = new TextField(Integer.toString(BoardConfig.FPS));

	private Label multiThreadedlabel = new Label("Multi-Threaded:");
	private CheckBox multiThreadedBox = new CheckBox();

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

	private Label wallCountLabel = new Label("Wall count:");
	private TextField wallCountField = new TextField(Integer.toString(BoardConfig.COUNT_WALL));

	private Label wallLengthLabel = new Label("Walllength:");
	private TextField wallLengthField = new TextField(Integer.toString(BoardConfig.WALL_LENGTH));

	private Label goodBeastCountLabel = new Label("GoodBeast count:");
	private TextField goodBeastCountField = new TextField(Integer.toString(BoardConfig.COUNT_GOODBEAST));

	private Label goodPlantCountLabel = new Label("GoodPlant count:");
	private TextField goodPlantCountField = new TextField(Integer.toString(BoardConfig.COUNT_GOODPLANT));

	private Label badBeastCountLabel = new Label("BadBeast count:");
	private TextField badBeastCountField = new TextField(Integer.toString(BoardConfig.COUNT_BADBEAST));

	private Label badPlantCountLabel = new Label("BadPlant count:");
	private TextField badPlantCountField = new TextField(Integer.toString(BoardConfig.COUNT_BADPLANT));

	private Label withBotsLabel = new Label("With Bots:");
	private CheckBox withBotsBox = new CheckBox();

	private Label countBotsLabel = new Label("Count Bots:");
	private TextField countBotsField = new TextField(BoardConfig.COUNT_BOTS_STRING);

	private Label trainingLabel = new Label("Training:");
	private CheckBox trainingBox = new CheckBox();

	private Label oldAILabel = new Label("Simple AI:");
	private CheckBox oldAIBox = new CheckBox();

	private Label aiPopulationLabel = new Label("AI Population:");
	private TextField aiPopulationField = new TextField(Integer.toString(BoardConfig.AI_POPULATION));

	public OptionsArea() {
		optionsLabel.setScaleX(3d);
		optionsLabel.setScaleY(3d);
		optionsLabel.setTextAlignment(TextAlignment.CENTER);

		optionsLabel.setTooltip(new Tooltip("Just the optionsmenu"));

		AnchorPane.setTopAnchor(optionsLabel, 30d);
		AnchorPane.setLeftAnchor(optionsLabel, 600d);

		AnchorPane.setTopAnchor(gameModeLabel, 150d);
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
		multiThreadedBox.setIndeterminate(false);
		multiThreadedBox.setSelected(BoardConfig.MULTI_THREAD);
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

		AnchorPane.setTopAnchor(wallCountLabel, 550d);
		AnchorPane.setLeftAnchor(wallCountLabel, 100d);
		AnchorPane.setTopAnchor(wallCountField, 550d);
		AnchorPane.setLeftAnchor(wallCountField, 250d);
		wallCountField.setTooltip(new Tooltip("The amount of walls"));

		AnchorPane.setTopAnchor(wallLengthLabel, 600d);
		AnchorPane.setLeftAnchor(wallLengthLabel, 100d);
		AnchorPane.setTopAnchor(wallLengthField, 600d);
		AnchorPane.setLeftAnchor(wallLengthField, 250d);
		wallLengthField.setTooltip(new Tooltip("The length of walls"));

		AnchorPane.setTopAnchor(goodBeastCountLabel, 150d);
		AnchorPane.setLeftAnchor(goodBeastCountLabel, 500d);
		AnchorPane.setTopAnchor(goodBeastCountField, 150d);
		AnchorPane.setLeftAnchor(goodBeastCountField, 650d);
		goodBeastCountField.setTooltip(new Tooltip("The amount of good beasts"));

		AnchorPane.setTopAnchor(goodPlantCountLabel, 200d);
		AnchorPane.setLeftAnchor(goodPlantCountLabel, 500d);
		AnchorPane.setTopAnchor(goodPlantCountField, 200d);
		AnchorPane.setLeftAnchor(goodPlantCountField, 650d);
		goodPlantCountField.setTooltip(new Tooltip("The amount of good plants"));

		AnchorPane.setTopAnchor(badBeastCountLabel, 250d);
		AnchorPane.setLeftAnchor(badBeastCountLabel, 500d);
		AnchorPane.setTopAnchor(badBeastCountField, 250d);
		AnchorPane.setLeftAnchor(badBeastCountField, 650d);
		badBeastCountField.setTooltip(new Tooltip("The amount of bad beasts"));

		AnchorPane.setTopAnchor(badPlantCountLabel, 300d);
		AnchorPane.setLeftAnchor(badPlantCountLabel, 500d);
		AnchorPane.setTopAnchor(badPlantCountField, 300d);
		AnchorPane.setLeftAnchor(badPlantCountField, 650d);
		badPlantCountField.setTooltip(new Tooltip("The amount of bad plants"));

		AnchorPane.setTopAnchor(withBotsLabel, 350d);
		AnchorPane.setLeftAnchor(withBotsLabel, 500d);
		AnchorPane.setTopAnchor(withBotsBox, 350d);
		AnchorPane.setLeftAnchor(withBotsBox, 650d);
		withBotsBox.setTooltip(new Tooltip("Decide if the game is played by bots"));
		withBotsBox.setAllowIndeterminate(false);
		withBotsBox.setSelected(BoardConfig.WITH_BOTS);

		AnchorPane.setTopAnchor(countBotsLabel, 400d);
		AnchorPane.setLeftAnchor(countBotsLabel, 500d);
		AnchorPane.setTopAnchor(countBotsField, 400d);
		AnchorPane.setLeftAnchor(countBotsField, 650d);
		countBotsField.setTooltip(new Tooltip("The name of the bots (example: Bot1,Bot2)"));

		AnchorPane.setTopAnchor(trainingLabel, 450d);
		AnchorPane.setLeftAnchor(trainingLabel, 500d);
		AnchorPane.setTopAnchor(trainingBox, 450d);
		AnchorPane.setLeftAnchor(trainingBox, 650d);
		trainingBox.setTooltip(new Tooltip("Decide if the game is played by bots self learning bots"));
		trainingBox.setAllowIndeterminate(false);
		trainingBox.setSelected(BoardConfig.TRAINING);

		AnchorPane.setTopAnchor(oldAILabel, 500d);
		AnchorPane.setLeftAnchor(oldAILabel, 500d);
		AnchorPane.setTopAnchor(oldAIBox, 500d);
		AnchorPane.setLeftAnchor(oldAIBox, 650d);
		oldAIBox.setTooltip(new Tooltip(
				"Decide if the game is played by bots self learning bots with a strict neural net (true) or self building neural net (false)"));
		oldAIBox.setAllowIndeterminate(false);
		oldAIBox.setSelected(BoardConfig.OLD_AI);

		AnchorPane.setTopAnchor(aiPopulationLabel, 550d);
		AnchorPane.setLeftAnchor(aiPopulationLabel, 500d);
		AnchorPane.setTopAnchor(aiPopulationField, 550d);
		AnchorPane.setLeftAnchor(aiPopulationField, 650d);
		aiPopulationField.setTooltip(new Tooltip("The amount of AIs"));

		save.setOnAction(e -> {
			okay = true;
			BoardConfig.gameMode = gameModeBox.getValue();
			
			try {
				int fps = Integer.parseInt(fpsInput.getText());
				BoardConfig.FPS = fps;
			} catch (NumberFormatException n) {
				fpsInput.setText("INVALID");
				okay = false;
			}
			
			BoardConfig.MULTI_THREAD = multiThreadedBox.isSelected();
			
			try {
				int gameSteps = Integer.parseInt(gameStepsField.getText());
				BoardConfig.GAME_STEPS = gameSteps;
			} catch (NumberFormatException n) {
				gameStepsField.setText("INVALID");
				okay = false;
			}
			
			try {
				int port = Integer.parseInt(portField.getText());
				BoardConfig.PORT = port;
			} catch (NumberFormatException n) {
				portField.setText("INVALID");
				okay = false;
			}
			
			try {
				int cellSize = Integer.parseInt(cellSizeField.getText());
				BoardConfig.CELL_SIZE = cellSize;
			} catch (NumberFormatException n) {
				cellSizeField.setText("INVALID");
				okay = false;
			}
			
			try {
				int widthSize = Integer.parseInt(widthSizeField.getText());
				BoardConfig.WIDTH_SIZE = widthSize;
			} catch (NumberFormatException n) {
				widthSizeField.setText("INVALID");
				okay = false;
			}
			
			try {
				int heightSize = Integer.parseInt(heightSizeField.getText());
				BoardConfig.HEIGHT_SIZE = heightSize;
			} catch (NumberFormatException n) {
				heightSizeField.setText("INVALID");
				okay = false;
			}
			
			try {
				int wallCount = Integer.parseInt(wallCountField.getText());
				BoardConfig.COUNT_WALL = wallCount;
			} catch (NumberFormatException n) {
				wallCountField.setText("INVALID");
				okay = false;
			}
			
			try {
				int wallLength = Integer.parseInt(wallLengthField.getText());
				BoardConfig.WALL_LENGTH = wallLength;
			} catch (NumberFormatException n) {
				wallLengthField.setText("INVALID");
				okay = false;
			}
			
			try {
				int goodBeastCount = Integer.parseInt(goodBeastCountField.getText());
				BoardConfig.COUNT_GOODBEAST = goodBeastCount;
			} catch (NumberFormatException n) {
				goodBeastCountField.setText("INVALID");
				okay = false;
			}
			
			try {
				int goodPlantCount = Integer.parseInt(goodPlantCountField.getText());
				BoardConfig.COUNT_GOODPLANT = goodPlantCount;
			} catch (NumberFormatException n) {
				goodPlantCountField.setText("INVALID");
				okay = false;
			}
			
			try {
				int badBeastCount = Integer.parseInt(badBeastCountField.getText());
				BoardConfig.COUNT_BADBEAST = badBeastCount;
			} catch (NumberFormatException n) {
				badBeastCountField.setText("INVALID");
				okay = false;
			}
			
			try {
				int badPlantCount = Integer.parseInt(badPlantCountField.getText());
				BoardConfig.COUNT_BADPLANT = badPlantCount;
			} catch (NumberFormatException n) {
				badPlantCountField.setText("INVALID");
				okay = false;
			}
			
			try {
				int aiPopulation = Integer.parseInt(aiPopulationField.getText());
				BoardConfig.AI_POPULATION = aiPopulation;
			} catch (NumberFormatException n) {
				aiPopulationField.setText("INVALID");
				okay = false;
			}
			
			BoardConfig.WITH_BOTS = withBotsBox.isSelected();
			
			BoardConfig.OLD_AI = oldAIBox.isSelected();
			
			String bots = countBotsField.getText().trim();
			BoardConfig.COUNT_BOTS_STRING = bots;
			BoardConfig.COUNT_BOTS = bots.split(",");
			
			if(BoardConfig.HEIGHT_SIZE* BoardConfig.WIDTH_SIZE < BoardConfig.COUNT_GOODBEAST+BoardConfig.COUNT_BADPLANT+BoardConfig.COUNT_BADBEAST+BoardConfig.COUNT_GOODPLANT+BoardConfig.COUNT_HANDOPERATED_MASTERSQUIRREL+BoardConfig.COUNT_BOTS.length+BoardConfig.COUNT_WALL*BoardConfig.WALL_LENGTH) {
				okay = false;
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("TOO MANY ENITITYS");
				alert.setTitle("WRONG INPUT");
				alert.setContentText("Change the size of the board or the amount of entitys");
				alert.show();
			}
			if(okay) {
				BoardConfig.save();
			}
		});

		root.getChildren().addAll(optionsLabel, gameModeLabel, gameModeBox, fpsLabel, fpsInput, save,
				multiThreadedlabel, multiThreadedBox, gameStepsLabel, gameStepsField, portLabel, portField,
				cellSizeField, cellSizeLabel, widthSizeField, widthSizeLabel, heightSizeField, heightSizeLabel,
				wallCountField, wallCountLabel, wallLengthField, wallLengthLabel, goodBeastCountField,
				goodBeastCountLabel, goodPlantCountField, goodPlantCountLabel, badBeastCountField, badBeastCountLabel,
				badPlantCountField, badPlantCountLabel, withBotsBox, withBotsLabel, aiPopulationField,
				aiPopulationLabel, oldAIBox, oldAILabel, trainingBox, trainingLabel, countBotsField, countBotsLabel);
	}

	public Node getNode() {
		return root;
	}
	
	public boolean getOkay() {
		return okay;
	}
}
