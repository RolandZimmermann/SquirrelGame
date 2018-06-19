package de.hsa.game.SquirrelGame.network;

import java.net.ServerSocket;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import de.hsa.game.SquirrelGame.core.board.BoardConfig;
import de.hsa.game.SquirrelGame.core.board.State;
import de.hsa.game.SquirrelGame.core.entity.character.MultiplayerMasterSquirrel;
import de.hsa.game.SquirrelGame.network.Message.Header;
import de.hsa.game.SquirrelGame.general.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Multiplayer extends Application {

	Server server;
	Thread serverThread;
	ServerSocket socket;
	ServerHandler serverHandler;
	int counter = 0;
	int textAreaCount = 0;
	Button update;
	Vector<MultiplayerMasterSquirrel> player;
	Game game;
	private boolean started = false;

	@Override
	public void start(Stage primaryStage) throws Exception {
		server = new Server(4242);
		serverThread = new Thread(server);
		socket = server.getServer();
		serverHandler = new ServerHandler(server.getConnections());
		serverThread.start();

		Scene root = createPane();

		primaryStage.setScene(root);
		primaryStage.show();
		primaryStage.setTitle("Multiplayer SERVER");

		primaryStage.setOnCloseRequest(e -> {
			serverThread.interrupt();
			serverThread = null;
			for (Thread threads : Thread.getAllStackTraces().keySet()) {
				threads.interrupt();
				threads = null;
			}
		});
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> {
						update.fire();
					});
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}, 1000);

		Timer m = new Timer();
		m.schedule(new TimerTask() {

			@Override
			public void run() {
				while (true) {
					updateActions();
					try {
						Thread.sleep(1000 / BoardConfig.FPS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}, 1000);
	}

	private Scene createPane() {
		AnchorPane root = new AnchorPane();
		Label playerOnline = new Label();
		TextField textField = new TextField();
		Label chat = new Label("Chat:");
		TextArea textArea = new TextArea();
		Button update = new Button("UPDATE");
		Button start = new Button("START GAME");
		Button end = new Button("END GAME");

		playerOnline.setText("Players Online: 0");

		root.getChildren().addAll(playerOnline, start, chat, end, textField, textArea);

		this.update = update;

		AnchorPane.setLeftAnchor(playerOnline, 10d);
		AnchorPane.setTopAnchor(playerOnline, 10d);
		AnchorPane.setTopAnchor(textField, 300d);
		AnchorPane.setLeftAnchor(textField, 60d);
		AnchorPane.setTopAnchor(chat, 305d);
		AnchorPane.setLeftAnchor(chat, 10d);
		AnchorPane.setTopAnchor(textArea, 30d);
		AnchorPane.setLeftAnchor(textArea, 10d);
		AnchorPane.setBottomAnchor(start, 10d);
		AnchorPane.setRightAnchor(start, 10d);
		AnchorPane.setBottomAnchor(end, 10d);
		AnchorPane.setRightAnchor(end, 130d);

		textArea.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				textArea.setScrollTop(Double.MAX_VALUE); // this will scroll to the bottom
				// use Double.MIN_VALUE to scroll to the top
			}
		});

		update.setOnAction(e -> {

			if (serverHandler.getConnections().size() > 0) {
				Vector<String> chatMessages = serverHandler.getChatMessage();
				for (String sc : chatMessages) {
					if (textAreaCount < 100) {
						textArea.setText(textArea.getText() + "\n" + sc);
						textAreaCount++;
					} else {
						textArea.clear();
						textAreaCount = 0;
					}
				}
				for (ServerConnection a : serverHandler.getConnections()) {
					for (String s : chatMessages) {
						a.setMessage(new Message(Header.CHAT, s));
					}
				}
			}
			if (serverHandler.getConnections().size() <= 0) {
				end.fire();
			}
			textArea.appendText("");

			playerOnline.setText("Players Online: " + serverHandler.getConnections().size());
		});

		textField.setOnAction(e -> {
			for (ServerConnection a : serverHandler.getConnections()) {
				a.setMessage(new Message(Header.CHAT, "SERVER: " + textField.getCharacters().toString()));
			}
		});

		start.setOnAction(e -> {
			startGame();
		});

		end.setOnAction(e -> {
			endGame();
		});

		Scene scene = new Scene(root, 800, 600);

		return scene;
	}

	private void updateActions() {
		if (started) {
			Map<ServerConnection, String> actions = serverHandler.getEvents();
			player = game.getState().getBoard().getMultiplayer();
			for (ServerConnection sc : actions.keySet()) {
				for (MultiplayerMasterSquirrel msb : player) {
					if (msb.getServerConnection() == sc) {
						msb.setCommand(actions.get(sc));
						actions.put(sc, "");
					}
				}
			}
			for (ServerConnection sc : serverHandler.getConnections()) {
				sc.setMessage(new Message(Header.UPDATE, game.getState().getBoard().generateView(sc)));
			}
			if (game.getGameSteps() <= 1) {
				Vector<MultiplayerMasterSquirrel> player = game.getState().getBoard().getMultiplayer();

				player.sort((a, b) -> Integer.compare(b.getEnergy(), a.getEnergy()));

				for (ServerConnection sc : serverHandler.getConnections()) {
					sc.setMessage(new Message(Header.CHAT, "SERVER: SCORES:"));
				}

				for (MultiplayerMasterSquirrel msq : player) {

					msq.getServerConnection().setMessage(
							new Message(Header.CHAT, msq.getServerConnection().getName() + " : " + msq.getEnergy()));

				}

				for (ServerConnection sc : serverHandler.getConnections()) {
					sc.setMessage(new Message(Header.CHAT, "\nSERVER: Restarting!"));
				}
			}
		}
	}

	private void endGame() {
		if (started) {
			game.endGame();
			for (ServerConnection sc : serverHandler.getConnections()) {
				sc.setMessage(new Message(Header.CHAT, "\nSERVER: Match Ended!"));
			}
			started = false;
		}
	}

	private void startGame() {
		if (!started) {
			Timer t = new Timer();
			t.schedule(new TimerTask() {

				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						game = new GameImpl(new State(), null,
								(Vector<ServerConnection>) serverHandler.getConnections());
						player = game.getState().getBoard().getMultiplayer();
						Thread.sleep(1000);
						game.run();
						started = true;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}, 1);
			for (ServerConnection sc : serverHandler.getConnections()) {
				sc.setMessage(new Message(Header.CHAT, "SERVER: Match Started!"));
			}
		}

	}

	public void go(String[] args) {
		Application.launch(args);
	}

}
