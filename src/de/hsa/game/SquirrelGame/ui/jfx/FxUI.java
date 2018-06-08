package de.hsa.game.SquirrelGame.ui.jfx;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.UI;
import de.hsa.games.fatsquirrel.util.XY;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class FxUI extends Scene implements UI {
	
	private static Logger logger = Logger.getLogger(FxUI.class.getName());

	private Canvas boardCanvas;
	private Label msgLabel;
	private static final int CELL_SIZE = 16;
	private static MoveCommand moveCommand;

	private static Image sprWall;
	private static Image sprGoodPlant;
	private static Image sprBadPlant;
	private static Image sprMasterSquirrel;
	private static Image sprGoodBeast;
	private static Image sprBadBeast;
	private static Image sprMiniSquirrel;
	private static Image sprEmpty;

	public FxUI(Parent parent, Canvas boardCanvas, Label msgLabel) {
		super(parent);
		this.boardCanvas = boardCanvas;
		this.msgLabel = msgLabel;
		
		logger.fine("Loaded UI");
		
	}

	private static void loadImages() {
		
		try {
			File file = new File("ressource/spirtes/BadBeast.png");
			sprBadBeast = new Image(file.toURI().toString(), CELL_SIZE, CELL_SIZE, true, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		try {
			File file = new File("ressource/sprites/Empty.png");
			sprEmpty = new Image(file.toURI().toString(), CELL_SIZE, CELL_SIZE, true, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		try {
			File file = new File("ressource/sprites/Wall.png");
			sprWall = new Image(file.toURI().toString(), CELL_SIZE, CELL_SIZE, true, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		try {
			File file = new File("ressource/sprites/BadPlant.png");
			sprBadPlant = new Image(file.toURI().toString(), CELL_SIZE, CELL_SIZE, true, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		try {
			File file = new File("ressource/sprites/GoodPlant.png");
			sprGoodPlant = new Image(file.toURI().toString(), CELL_SIZE, CELL_SIZE, true, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		try {
			File file = new File("ressource/spirtes/MasterSquirrel.png");
			sprMasterSquirrel = new Image(file.toURI().toString(), CELL_SIZE, CELL_SIZE, true, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		try {
			File file = new File("ressource/spirtes/MiniSquirrel.png");
			sprMiniSquirrel = new Image(file.toURI().toString(), CELL_SIZE, CELL_SIZE, true, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		try {
			File file = new File("ressource/spirtes/GoodBeast.png");
			sprGoodBeast = new Image(file.toURI().toString(), CELL_SIZE, CELL_SIZE, true, true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	public static FxUI createInstance(XY xy) {
		loadImages();
		Canvas boardCanvas = new Canvas(xy.x * CELL_SIZE, xy.y * CELL_SIZE);
		Label statusLabel = new Label();
		AnchorPane top = new AnchorPane();
		
		top.getChildren().add(boardCanvas);
		top.getChildren().add(statusLabel);
		
		AnchorPane.setBottomAnchor(boardCanvas, 20d);
		AnchorPane.setBottomAnchor(statusLabel, 0d);
	
		statusLabel.setText("Hier könnte Ihre Werbung stehen");

		final FxUI fxUI = new FxUI(top, boardCanvas, statusLabel);

		fxUI.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				switch (keyEvent.getCode()) {
				case A:
					setCommand(MoveCommand.LEFT);
					break;
				case W:
					setCommand(MoveCommand.UP);
					break;
				case S:
					setCommand(MoveCommand.DOWN);
					break;
				case D:
					setCommand(MoveCommand.RIGHT);
					break;
				case SPACE:
					MoveCommand moveCommand = MoveCommand.MINI_DOWN;
					moveCommand.setEnergy(100);
					setCommand(moveCommand);
					break;
				case Q:
                    setCommand(MoveCommand.UPLEFT);
                    break;
				case E:
                    setCommand(MoveCommand.UPRIGHT);
                    break;
				case Y:
                    setCommand(MoveCommand.DOWNLEFT);
                    break;
				case C:
                    setCommand(MoveCommand.DOWNRIGHT);
                    break;
					
					
				default:
					break;

				}
			}
		});
		return fxUI;
	}

	@Override
	public void render(final BoardView view) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				repaintBoardCanvas(view);
			}
		});
	}

	@SuppressWarnings("restriction")
	private void repaintBoardCanvas(BoardView view) {
		GraphicsContext gc = boardCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
		XY viewSize = view.getSize();
		
		gc.drawImage(sprGoodBeast, 0, 0);

		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, viewSize.x * CELL_SIZE, viewSize.y * CELL_SIZE);

		for (int y = 0; y < viewSize.y * CELL_SIZE; y += CELL_SIZE) {
			for (int x = 0; x < viewSize.x * CELL_SIZE; x += CELL_SIZE) {
				Entity entity = view.getEntityType(x / CELL_SIZE, y / CELL_SIZE);
				if (entity instanceof Wall) {
					if (sprWall.isError()) {
						gc.setFill(Color.ORANGE);
						gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
					} else {
						gc.drawImage(sprWall, x, y);
					}
				} else if (entity instanceof GoodBeast) {
					if (sprGoodBeast.isError()) {
						gc.setFill(Color.LIGHTGREEN);
						gc.fillOval(x, y, CELL_SIZE, CELL_SIZE);
					} else {
						gc.drawImage(sprGoodBeast, x, y);
					}
				} else if (entity instanceof BadBeast) {
					if (sprBadBeast.isError()) {
						gc.setFill(Color.DARKRED);
						gc.fillOval(x, y, CELL_SIZE, CELL_SIZE);
					} else {
						gc.drawImage(sprBadBeast, x, y);
					}
				} else if (entity instanceof GoodPlant) {
					if (sprGoodPlant.isError()) {
						gc.setFill(Color.LIGHTGREEN);
						gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
					} else {
						gc.drawImage(sprGoodPlant, x, y);
					}
				} else if (entity instanceof BadPlant) {
					if (sprBadPlant.isError()) {
						gc.setFill(Color.RED);
						gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
					} else {
						gc.drawImage(sprBadPlant, x, y);
					}
				} else if (entity instanceof MasterSquirrel) {
					if (sprMasterSquirrel.isError()) {
						gc.setFill(Color.MAGENTA);
						gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
					} else {
						gc.drawImage(sprMasterSquirrel, x, y);
					}
				} else if (entity instanceof MiniSquirrel) {
					if (sprMiniSquirrel.isError()) {
						gc.setFill(Color.BLUEVIOLET);
						gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
					} else {
						gc.drawImage(sprMiniSquirrel, x, y);
					}
				} else {
					if (sprEmpty.isError()) {
						gc.setFill(Color.GREEN);
						gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
					} else {
						gc.drawImage(sprEmpty, x, y);
					}
				}
			}
		}
		
		logger.finest("Updated UI");

	}

	@Override
	public MoveCommand getCommand() {
		MoveCommand toReturn = moveCommand;
		moveCommand = null;
		logger.finer("returned Command");
		return toReturn;
	}

	public static void setCommand(MoveCommand move) {
		moveCommand = move;
	}

	@Override
	public void message(final String msg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				msgLabel.setText(msg);
			}
		});
	}
}
