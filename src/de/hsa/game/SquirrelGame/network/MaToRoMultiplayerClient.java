package de.hsa.game.SquirrelGame.network;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import de.hsa.game.SquirrelGame.network.Message.Header;
import de.hsa.games.fatsquirrel.core.EntityType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MaToRoMultiplayerClient extends Application {

	private Client client;
	private Thread threadClient = new Thread(client);
	private String ip;
	private int port;
	private Button update;
	private Label connectionFailed;

	private final int WINDOW_HEIGHT = 720;
	private final int WINDOW_WIDTH = 1280;

	private int textAreaCount;
	private byte[][] view;

	private Stage primaryStage;
	private boolean connected = false;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Scene newScene = startScene();

		primaryStage.setScene(newScene);
		primaryStage.show();
		primaryStage.setTitle("MaToRo-Multiplayer");

		System.setProperty("headless.geometry", "1600x1200-32");

	}

	private void update() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				update.fire();

			}
		});

	}

	private Scene createScene() {
		AnchorPane root = new AnchorPane();
		TextArea textArea = new TextArea();
		TextField input = new TextField();
		Button update = new Button();
		Button back = new Button("Disconnect");
		Label label = new Label("Chat:");

		Canvas canvas = new Canvas(1260, 450);

		this.update = update;

		AnchorPane.setBottomAnchor(label, 15d);
		AnchorPane.setLeftAnchor(label, 10d);
		AnchorPane.setLeftAnchor(input, 50d);
		AnchorPane.setBottomAnchor(input, 10d);
		AnchorPane.setBottomAnchor(textArea, 50d);
		AnchorPane.setLeftAnchor(canvas, 10d);
		AnchorPane.setRightAnchor(canvas, 10d);
		AnchorPane.setTopAnchor(canvas, 10d);
		AnchorPane.setBottomAnchor(canvas, 450d);
		AnchorPane.setBottomAnchor(back, 10d);
		AnchorPane.setRightAnchor(back, 10d);
		root.getChildren().addAll(textArea, input, label, canvas, back);

		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		textArea.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				textArea.setScrollTop(Double.MAX_VALUE); // this will scroll to the bottom
				// use Double.MIN_VALUE to scroll to the top
			}
		});

		textArea.setPrefHeight(200d);
		textArea.setPrefWidth(500d);
		textArea.setEditable(false);
		textArea.setMouseTransparent(true);
		textArea.setFocusTraversable(false);

		back.setOnAction(e -> {
			client.setMessage(new Message(Header.CHAT, "*DISCONNECTED*"));
			connected = false;
			client.close();
			client = null;
			primaryStage.setScene(startScene());
		});

		update.setOnAction(e -> {
			if (connected) {
				Vector<String> chatMessanges = client.getChatMessage();
				for (String s : chatMessanges) {
					if (textAreaCount < 100) {
						textArea.setText(textArea.getText() + "\n" + s);
						textAreaCount++;
					} else {
						textArea.clear();
						textAreaCount = 0;
					}
				}
				textArea.appendText("");

				this.view = client.getView();
				if (this.view == null) {
					return;
				}

				GraphicsContext gc = canvas.getGraphicsContext2D();

				int CELL_HEIGHT = (int) (canvas.getHeight() / view.length);
				int CELL_WIDTH = (int) (canvas.getWidth() / view[0].length);

				gc.setFill(Color.DARKOLIVEGREEN);
				gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

				for (int y = 0; y < CELL_HEIGHT * view.length; y += CELL_HEIGHT) {
					for (int x = 0; x < CELL_WIDTH * view[0].length; x += CELL_WIDTH) {
						byte entity = view[y / CELL_HEIGHT][x / CELL_WIDTH];
						EntityType entityType = EntityType.NONE;
						switch (entity) {
						case 0:
							entityType = EntityType.NONE;
							break;
						case 1:
							entityType = EntityType.GOOD_BEAST;
							break;
						case 2:
							entityType = EntityType.GOOD_PLANT;
							break;
						case 3:
							entityType = EntityType.BAD_BEAST;
							break;
						case 4:
							entityType = EntityType.BAD_PLANT;
							break;
						case 5:
							entityType = EntityType.WALL;
							break;
						case 6:
							entityType = EntityType.MASTER_SQUIRREL;
							break;
						case 7:
							entityType = EntityType.MINI_SQUIRREL;
							break;
						}

						if (entityType == EntityType.NONE) {
							gc.setFill(Color.DARKOLIVEGREEN);
							gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
						} else if (entityType == EntityType.WALL) {
							gc.setFill(Color.ORANGE);
							gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
						} else if (entityType == EntityType.BAD_BEAST) {
							gc.setFill(Color.RED);
							gc.fillOval(x, y, CELL_WIDTH, CELL_HEIGHT);
						} else if (entityType == EntityType.BAD_PLANT) {
							gc.setFill(Color.DARKRED);
							gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
						} else if (entityType == EntityType.GOOD_BEAST) {
							gc.setFill(Color.LIGHTGREEN);
							gc.fillOval(x, y, CELL_WIDTH, CELL_HEIGHT);
						} else if (entityType == EntityType.GOOD_PLANT) {
							gc.setFill(Color.LIMEGREEN);
							gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
						} else if (entityType == EntityType.MASTER_SQUIRREL) {
							gc.setFill(Color.HOTPINK);
							gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
						} else if (entityType == EntityType.MINI_SQUIRREL) {
							gc.setFill(Color.PURPLE);
							gc.fillOval(x, y, CELL_WIDTH, CELL_HEIGHT);
						}
					}
				}
			}
		});

		input.setOnAction(e -> {
			client.setMessage(new Message(Header.CHAT, input.getText()));
			input.deselect();
			root.requestFocus();
		});

		scene.setOnKeyPressed(e -> {
			client.setMessage(new Message(Header.ACTION, e.getCode().getName()));
		});

		return scene;
	}

	private Scene startScene() {
		AnchorPane root = new AnchorPane();
		Label titel = new Label("MaToRo-Multiplayer");
		Label ipLabel = new Label("IP:");
		Label portLabel = new Label("Port:");
		TextField ipField = new TextField("127.0.0.1");
		TextField portField = new TextField("54321");
		Button join = new Button("Join");
		Button exit = new Button("Exit");
		Label connectionFailed = new Label("Connection Failed");

		AnchorPane.setTopAnchor(titel, 30d);
		AnchorPane.setLeftAnchor(titel, 540d);
		titel.setScaleX(3.0d);
		titel.setScaleY(3.0d);

		AnchorPane.setBottomAnchor(join, 30d);
		AnchorPane.setRightAnchor(join, 50d);
		AnchorPane.setLeftAnchor(join, WINDOW_WIDTH - 170d);
		AnchorPane.setTopAnchor(join, WINDOW_HEIGHT - 60d);

		AnchorPane.setBottomAnchor(exit, 30d);
		AnchorPane.setRightAnchor(exit, WINDOW_WIDTH - 170d);
		AnchorPane.setLeftAnchor(exit, 50d);
		AnchorPane.setTopAnchor(exit, WINDOW_HEIGHT - 60d);

		AnchorPane.setTopAnchor(ipLabel, 150d);
		AnchorPane.setLeftAnchor(ipLabel, 450d);

		AnchorPane.setTopAnchor(ipField, 150d);
		AnchorPane.setLeftAnchor(ipField, 500d);

		AnchorPane.setTopAnchor(portLabel, 200d);
		AnchorPane.setLeftAnchor(portLabel, 450d);

		AnchorPane.setTopAnchor(portField, 200d);
		AnchorPane.setLeftAnchor(portField, 500d);

		AnchorPane.setBottomAnchor(connectionFailed, 30d);
		AnchorPane.setLeftAnchor(connectionFailed, 500d);
		AnchorPane.setRightAnchor(connectionFailed, 500d);

		root.getChildren().addAll(titel, ipLabel, portLabel, ipField, portField, join, exit, connectionFailed);

		Scene start = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		exit.setOnAction(e -> {
			System.exit(0);
		});

		exit.setOnMouseEntered(e -> {
			exit.setScaleX(1.5d);
			exit.setScaleY(1.5d);
		});

		exit.setOnMouseExited(e -> {
			exit.setScaleX(1d);
			exit.setScaleY(1d);
		});

		join.setOnMouseEntered(e -> {
			join.setScaleX(1.5d);
			join.setScaleY(1.5d);
		});

		join.setOnMouseExited(e -> {
			join.setScaleX(1d);
			join.setScaleY(1d);
		});

		join.setOnAction(e -> {
			this.ip = ipField.getText();
			try {
				this.port = Integer.parseInt(portField.getText());
			} catch (NumberFormatException e3) {
				portField.setText("INVALID");
				;
				connectionFailed.setDisable(true);
				return;
			}
			connectionFailed.setDisable(true);
			connectionFailed.setScaleX(1.5d);
			connectionFailed.setScaleY(1.5d);
			go();
		});

		connectionFailed.setDisable(true);
		this.connectionFailed = connectionFailed;

		return start;
	}

	private void go() {
		client = new Client(ip, port);
		try {
			client.init();
		} catch (ClassNotFoundException | IOException e1) {
			connectionFailed.setDisable(false);
			connectionFailed.setScaleX(1d);
			connectionFailed.setScaleY(1d);
			return;
		}

		primaryStage.setScene(createScene());
		threadClient = new Thread(client);
		threadClient.start();
		connected = true;

		System.setProperty("headless.geometry", "1600x1200-32");

		primaryStage.setOnCloseRequest(e -> {
			if (client != null) {
				client.setMessage(new Message(Header.CHAT, "*DISCONNECTED*"));
				client.close();
			}
			connected = false;
			client = null;
			System.exit(0);
		});
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				while (connected) {
					update();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}, 1000);

		client.setMessage(new Message(Header.CHAT, "*CONNECTED*"));
		client.setMessage(new Message(Header.ACTION, "W"));

	}
}
