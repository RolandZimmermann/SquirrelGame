package de.hsa.games.squirrelgame.general;

public class Game {
	public void run() {
	    while (true) {
	        render();
	        processInput();
	        update();
	    }
	}
	
	public abstract void render();
	public abstract void processInput();
}
