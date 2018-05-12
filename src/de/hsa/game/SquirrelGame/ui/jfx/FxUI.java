package de.hsa.game.SquirrelGame.ui.jfx;

import javax.swing.text.html.MinimalHTMLWriter;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.entity.*;
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
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class FxUI extends Scene implements UI{
	
	private Canvas boardCanvas;
	private Label msgLabel;
	private static final int CELL_SIZE = 16;
	
	public FxUI(Parent parent, Canvas boardCanvas, Label msgLabel) {
        super(parent);
        this.boardCanvas = boardCanvas;
        this.msgLabel = msgLabel;
    }
    
    public static FxUI createInstance(XY boardSize) {
        Canvas boardCanvas = new Canvas(boardSize.getX() * CELL_SIZE, boardSize.getY() * CELL_SIZE);
        Label statusLabel = new Label();
        VBox top = new VBox();
        
        top.getChildren().add(boardCanvas);
        top.getChildren().add(statusLabel);
        statusLabel.setText("Hier könnte Ihre Werbung stehen");
        
        final FxUI fxUI = new FxUI(top, boardCanvas, statusLabel); 
        
        fxUI.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                   @Override
                   public void handle(KeyEvent keyEvent) {
                      System.out.println("Es wurde folgende Taste gedrückt: " + keyEvent.getCode() + " bitte behandeln!");
                      // TODO handle event 
                   }
                }
          );
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
    
    private void repaintBoardCanvas(BoardView view) {
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
        XY viewSize = view.getSize();

        gc.setFill(Color.GREEN);
        gc.fillRect(0,0,viewSize.getX()*CELL_SIZE, viewSize.getY()*CELL_SIZE);
		
        for(int y = 0; y < viewSize.getY()*CELL_SIZE; y+=CELL_SIZE) {
        	for(int x = 0; x < viewSize.getX()*CELL_SIZE; x+=CELL_SIZE) {
        		Entity entity = view.getEntityType(x/CELL_SIZE, y/CELL_SIZE);
        		if(entity instanceof Wall) {
        			gc.setFill(Color.ORANGE);
        			gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        		} else if(entity instanceof GoodBeast) {
        			gc.setFill(Color.LIGHTGREEN);
        			gc.fillOval(x, y, CELL_SIZE, CELL_SIZE);
        		} else if(entity instanceof BadBeast) {
        			gc.setFill(Color.DARKRED);
        			gc.fillOval(x, y, CELL_SIZE,CELL_SIZE);
        		} else if(entity instanceof GoodPlant) {
        			gc.setFill(Color.GREEN);
        			gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        		} else if(entity instanceof BadPlant) {
        			gc.setFill(Color.RED);
        			gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        		} else if(entity instanceof MasterSquirrel) {
        			gc.setFill(Color.MAGENTA);
        			gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        		} else if(entity instanceof MiniSquirrel) {
        			gc.setFill(Color.BLUEVIOLET);
        			gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        		}
        	}
        }
        
    }

	@Override
	public MoveCommand getCommand() {
		// TODO Auto-generated method stub
		return null;
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


