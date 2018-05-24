package de.hsa.game.SquirrelGame.general;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.board.FlattenBoard;
import de.hsa.game.SquirrelGame.core.board.State;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.UI;
import de.hsa.game.SquirrelGame.ui.console.ConsoleUI;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Game {
    private State state;
    private UI ui;
    private BoardView boardView;
    private EntityContext entityContext;
    private int FPS = 5;
    private boolean multi = true;
    
    

    private MoveCommand moveCommand = null;

    public Game(State state, UI ui) {
        this.state = state;
        this.ui = ui;

        FlattenBoard flattenBoard = new FlattenBoard(state.getBoard());
        this.boardView = flattenBoard;
        this.entityContext = flattenBoard;
    }

    public void run() throws InterruptedException {
        if (multi) {
            Timer t = new Timer();
            Timer m = new Timer();
            t.schedule(new TimerTask() {

                @Override
                public void run() {
                    while (true) {
                        render();
                        update();
                        try {
                            Thread.sleep(1000 / FPS);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }, 1000);

            m.schedule(new TimerTask() {

                @Override
                public void run() {
                    while (true) {
                        processInput();
                        try {
                            Thread.sleep(1000 / FPS);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }, 1000);
        }else {
            while (true) {
                render();
                processInput();
                update();
            }
        }

    }

    public void render() {
        ui.render(boardView);
    }

    public void processInput() {
        moveCommand = ui.getCommand();
        
    }

    public void update() {
        state.update(moveCommand, entityContext);
        boardView.update();
        moveCommand = null;
    }
}
