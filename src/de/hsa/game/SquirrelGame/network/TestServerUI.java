package de.hsa.game.SquirrelGame.network;

import java.net.ServerSocket;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import de.hsa.game.SquirrelGame.network.Message.Header;
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

public class TestServerUI extends Application {

	Server server = new Server(4242);
	Thread serverThread = new Thread(server);
	ServerSocket socket;
	ServerHandler serverHandler = new ServerHandler(server.getConnections());
	int counter = 0;
	int textAreaCount = 0;
	Button update;

	@Override
	public void start(Stage primaryStage) throws Exception {

		socket = server.getServer();
		serverThread.start();

		Scene root = createPane();

		primaryStage.setScene(root);
		primaryStage.show();
		primaryStage.setTitle("SERVERTEST");

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
					update.fire();
				}
			}
		}, 1000);
	}

	private Scene createPane() {
		AnchorPane root = new AnchorPane();
		Label newestMessage = new Label();
		TextField textField = new TextField("WHAT TO SEND?");
		TextArea textArea = new TextArea("Something Something");

		newestMessage.setText("PRESS UPDATE");
		Button update = new Button("UPDATE");
		Button sendMessage = new Button("SENDMESSAGE");
		root.getChildren().addAll(update, newestMessage, sendMessage, textField, textArea);

		this.update = update;

		AnchorPane.setBottomAnchor(update, 10d);
		AnchorPane.setBottomAnchor(sendMessage, 10d);
		AnchorPane.setLeftAnchor(update, 10d);
		AnchorPane.setRightAnchor(sendMessage, 10d);
		AnchorPane.setTopAnchor(newestMessage, 10d);
		AnchorPane.setTopAnchor(textField, 300d);
		AnchorPane.setLeftAnchor(textField, 10d);
		AnchorPane.setTopAnchor(textArea, 30d);
		AnchorPane.setLeftAnchor(textArea, 10d);

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
					for(String s : chatMessages) {
						a.setMessage(new Message(Header.CHAT, s));
					}
				}
			}
			textArea.appendText("");

		});

		textField.setOnAction(e -> {
			for (ServerConnection a : serverHandler.getConnections()) {
				a.setMessage(new Message(Header.CHAT, "SERVER: " + textField.getCharacters().toString()));
			}
		});

		sendMessage.setOnAction(e -> {
			for (ServerConnection a : serverHandler.getConnections()) {
				a.setMessage(new Message(Header.CHAT, "TEST : " + counter++));
			}
		});

		Scene scene = new Scene(root, 800, 600);

		return scene;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
