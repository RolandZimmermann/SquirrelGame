package de.hsa.game.SquirrelGame.network;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.network.Message.Header;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;
import javafx.application.Application;
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

public class TestClient extends Application{
	
	Client client = new Client("localhost", 4242);
	Thread thread = new Thread(client);
	Button update;
	
	private int textAreaCount;
	private byte[][] view;
	
	public static void main(String[] args) {
		Application.launch(args);		
		}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene newScene = createScene();
		thread.start();
		
		primaryStage.setScene(newScene);
		primaryStage.show();
		primaryStage.setTitle("CLIENT");
		
		System.setProperty("headless.geometry", "1600x1200-32");
		
		primaryStage.setOnCloseRequest(e -> {
			client.setMessage(new Message(Header.CHAT, "DISCONNECTED!"));
			client = null;
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
		
		client.setMessage(new Message(Header.CHAT, "*CONNECTED*"));
		client.setMessage(new Message(Header.ACTION, "W"));
	}

	private Scene createScene() {
		AnchorPane root = new AnchorPane();
		TextArea textArea = new TextArea();
		TextField input = new TextField();
		Button update = new Button();
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
		root.getChildren().addAll(textArea,input,label,canvas);
		
		Scene scene = new Scene(root, 1280, 720);
		
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
		
		update.setOnAction(e -> {
			Vector<String> chatMessanges = client.getChatMessage();
			for(String s : chatMessanges) {
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
			if(this.view == null) {
				return;
			}
			
			GraphicsContext gc = canvas.getGraphicsContext2D();
			
			int CELL_HEIGHT = (int) (canvas.getHeight()/view.length);
			int CELL_WIDTH = (int) (canvas.getWidth()/view[0].length);
			
			for(int y = 0; y < CELL_HEIGHT * view.length; y+=CELL_HEIGHT) {
				for(int x = 0; x < CELL_WIDTH * view[0].length; x+=CELL_WIDTH) {
					byte entity = view[y/CELL_HEIGHT][x/CELL_WIDTH];
					EntityType entityType = EntityType.NONE;
					switch(entity) {
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
					
					if(entityType == EntityType.NONE) {
						gc.setFill(Color.DARKOLIVEGREEN);
						gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
					} else if(entityType == EntityType.WALL) {
						gc.setFill(Color.ORANGE);
						gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
					} else if(entityType == EntityType.BAD_BEAST) {
						gc.setFill(Color.RED);
						gc.fillOval(x, y, CELL_WIDTH, CELL_HEIGHT);
					} else if(entityType == EntityType.BAD_PLANT) {
						gc.setFill(Color.DARKRED);
						gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
					} else if(entityType == EntityType.GOOD_BEAST) {
						gc.setFill(Color.LIGHTGREEN);
						gc.fillOval(x, y, CELL_WIDTH, CELL_HEIGHT);
					} else if(entityType == EntityType.GOOD_PLANT) {
						gc.setFill(Color.LIMEGREEN);
						gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
					} else if(entityType == EntityType.MASTER_SQUIRREL) {
						gc.setFill(Color.HOTPINK);
						gc.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
					} else if(entityType == EntityType.MINI_SQUIRREL) {
						gc.setFill(Color.PURPLE);
						gc.fillOval(x, y, CELL_WIDTH, CELL_HEIGHT);
					}
				}
			}
		});
		
		input.setOnAction(e -> {
			client.setMessage(new Message(Header.CHAT,input.getText()));
			input.deselect();
			root.requestFocus();
		});
		
		scene.setOnKeyPressed(e -> {
			client.setMessage(new Message(Header.ACTION, e.getCode().getName()));
		});
		
		return scene;
	}
}
