package de.hsa.game.SquirrelGame.general;

import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.BoardFactory;
import de.hsa.game.SquirrelGame.core.board.State;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    // Aufgabe 3
    public static void main(String[] args) {
        Board board = BoardFactory.createBoard();

        Game game = new GameImpl(new State(board));
        // game.run();
        startGame(game);

    }
    
    public static void startGame(Game game) {
        Timer t= new Timer();
        t.schedule(new TimerTask() {
            
            @Override
            public void run() {
                try {
                    game.run();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        }, 1000);
        }

}
