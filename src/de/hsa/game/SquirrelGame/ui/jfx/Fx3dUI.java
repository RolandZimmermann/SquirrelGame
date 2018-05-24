package de.hsa.game.SquirrelGame.ui.jfx;

import java.io.IOException;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.gamestats.XY;
import de.hsa.game.SquirrelGame.ui.UI;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.AmbientLight;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;

import javafx.scene.PointLight;

import javafx.scene.Scene;

import javafx.scene.SceneAntialiasing;

import javafx.scene.input.KeyCode;

import javafx.scene.input.MouseButton;

import javafx.scene.paint.Color;

import javafx.scene.paint.PhongMaterial;

import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import javafx.scene.transform.Translate;

public class Fx3dUI extends Scene implements UI {

	private Group root;

	private static final double sceneWidth = 1280;
	private static final double sceneHeight = 720;

	private static final int CELL_SIZE = 32;
	private static MoveCommand moveCommand;

	static double mousePosX;
	static double mousePosY;
	static double mousePosOldX;
	static double mousePosOldY;

	private Group objWall;
	private Group objBadPlant;
	private Group objGoodPlant;
	private Group objBadBeast;
	private Group objGoodBeast;
	private Group objMasterSquirrel;
	private Group objMiniSquirrel;

	public Fx3dUI(Group root) {
		super(root, sceneWidth, sceneHeight, true, SceneAntialiasing.BALANCED);
		this.root = root;
		loadObjects();
		editScene();
	}

	public static Fx3dUI createInstance(XY size) {

		Group root = new Group();

		PointLight light = new PointLight(Color.RED);
		root.getChildren().add(light);
		root.getChildren().add(new AmbientLight(Color.WHITE));

		final Fx3dUI fx3dUI = new Fx3dUI(root);

		return fx3dUI;
	}

	@Override
	public MoveCommand getCommand() {
		MoveCommand tmp =  moveCommand;
		moveCommand = MoveCommand.NON;
		return tmp;
	}

	@Override
	public void render(BoardView view) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				repaintBoardCanvas(view);
			}
		});
	}

	private void loadObjects() {
		// TODO: Load Objects here
	}

	private void repaintBoardCanvas(BoardView view) {

		root.getChildren().clear();

		Box floor = new Box(view.getSize().getX() * CELL_SIZE, view.getSize().getY() * CELL_SIZE, CELL_SIZE);
		floor.setMaterial(new PhongMaterial(Color.GREEN));
		floor.setTranslateX((view.getSize().getX() * CELL_SIZE) / 2 - CELL_SIZE / 2);
		floor.setTranslateY((view.getSize().getY() * CELL_SIZE) / 2 - CELL_SIZE / 2);
		floor.setTranslateZ(CELL_SIZE);
		root.getChildren().add(floor);

		for (int y = 0; y < view.getSize().getY() * CELL_SIZE; y += CELL_SIZE) {
			for (int x = 0; x < view.getSize().getX() * CELL_SIZE; x += CELL_SIZE) {
				if (view.getEntityType(x / CELL_SIZE, y / CELL_SIZE) instanceof Wall) {
					Box object = new Box(CELL_SIZE, CELL_SIZE, CELL_SIZE);
					object.setMaterial(new PhongMaterial(Color.BROWN));
					object.setTranslateX(x);
					object.setTranslateY(y);
					root.getChildren().add(object);
				} else if (view.getEntityType(x / CELL_SIZE, y / CELL_SIZE) instanceof BadBeast) {
					Sphere object = new Sphere(CELL_SIZE / 2);
					object.setMaterial(new PhongMaterial(Color.RED));
					object.setTranslateX(x);
					object.setTranslateY(y);
					root.getChildren().add(object);
				} else if (view.getEntityType(x / CELL_SIZE, y / CELL_SIZE) instanceof GoodBeast) {
					Sphere object = new Sphere(CELL_SIZE / 2);
					object.setMaterial(new PhongMaterial(Color.GREENYELLOW));
					object.setTranslateX(x);
					object.setTranslateY(y);
					root.getChildren().add(object);
				} else if (view.getEntityType(x / CELL_SIZE, y / CELL_SIZE) instanceof GoodPlant) {
					Box object = new Box(CELL_SIZE, CELL_SIZE, CELL_SIZE / 2);
					object.setMaterial(new PhongMaterial(Color.BLUE));
					object.setTranslateX(x);
					object.setTranslateY(y);
					root.getChildren().add(object);
				} else if (view.getEntityType(x / CELL_SIZE, y / CELL_SIZE) instanceof BadPlant) {
					Box object = new Box(CELL_SIZE, CELL_SIZE, CELL_SIZE / 2);
					object.setMaterial(new PhongMaterial(Color.ORANGERED));
					object.setTranslateX(x);
					object.setTranslateY(y);
					root.getChildren().add(object);
				} else if (view.getEntityType(x / CELL_SIZE, y / CELL_SIZE) instanceof MasterSquirrel) {
					Sphere object = new Sphere(CELL_SIZE / 2);
					object.setMaterial(new PhongMaterial(Color.LIGHTPINK));
					object.setTranslateX(x);
					object.setTranslateY(y);
					root.getChildren().add(object);
				} else if (view.getEntityType(x / CELL_SIZE, y / CELL_SIZE) instanceof MiniSquirrel) {
					Sphere object = new Sphere(CELL_SIZE / 2);
					object.setMaterial(new PhongMaterial(Color.PURPLE));
					object.setTranslateX(x);
					object.setTranslateY(y);
					root.getChildren().add(object);
				}
			}
		}
	}

	@Override
	public void message(String msg) {
		// TODO Auto-generated method stub

	}

	private void editScene() {

		PerspectiveCamera camera;

		final Rotate rotateX = new Rotate(180, Rotate.X_AXIS);
		final Rotate rotateY = new Rotate(-180, Rotate.Y_AXIS);
		final Rotate rotateZ = new Rotate(180, Rotate.Z_AXIS);

		camera = new PerspectiveCamera(true);
		camera.setVerticalFieldOfView(false);
		camera.setNearClip(0.5);
		camera.setFarClip(100000.0);

		Translate transl = new Translate(0, 0, 0);
		camera.getTransforms().addAll(rotateX, rotateY, rotateZ, transl);

		transl.setX(0);
		transl.setY(0);
		transl.setZ(-1750);

		camera.setTranslateX(transl.getX());
		camera.setTranslateY(transl.getY());
		camera.setTranslateZ(transl.getZ());

		camera.setLayoutX(500);
		camera.setLayoutY(500);

		rotateX.setPivotX(camera.getLayoutX());
		rotateY.setPivotY(camera.getLayoutY());
		rotateZ.setPivotZ(camera.getTranslateZ());

		super.setCamera(camera);

		super.setFill(Color.LIGHTSKYBLUE);

		super.setOnScroll(e -> {
			transl.setZ(transl.getZ() + e.getDeltaY());
		});

		super.setOnMousePressed(e -> {
			mousePosOldX = e.getSceneX();
			mousePosOldY = e.getSceneY();
		});

		super.setOnMouseDragged(e -> {
			mousePosX = e.getSceneX();
			mousePosY = e.getSceneY();
			if (e.getButton() == MouseButton.MIDDLE) {
				rotateX.setAngle(rotateX.getAngle() + (mousePosY - mousePosOldY));
				rotateY.setAngle(rotateY.getAngle() + (mousePosX - mousePosOldX));
			} else if (e.getButton() == MouseButton.PRIMARY) {
				transl.setY(transl.getY() - (mousePosY - mousePosOldY));
				transl.setX(transl.getX() - (mousePosX - mousePosOldX));
			}

			mousePosOldX = mousePosX;
			mousePosOldY = mousePosY;
		});

		super.setOnKeyPressed(e -> {
			switch (e.getCode()) {
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
		});

	}

	private void setCommand(MoveCommand moveCommand) {
		this.moveCommand = moveCommand;
	}
}