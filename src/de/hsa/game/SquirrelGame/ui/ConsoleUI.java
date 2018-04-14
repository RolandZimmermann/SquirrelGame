package de.hsa.game.SquirrelGame.ui;

import java.util.Scanner;

import de.hsa.game.SquirrelGame.core.BoardView;

public class ConsoleUI implements UI {
	public char charinput() {
		Scanner s = new Scanner(System.in);
		
		char h = s.next().charAt(0);
		return h;
	}
	
	public void render(BoardView boardView) {
		char[][] field = new char[boardView.getSize().getY()][boardView.getSize().getX()];
		for(int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[0].length; x++) {
				if(boardView.getEntityType(x, y) != null) {
					if(boardView.getEntityType(x, y).getClass().getSimpleName() == "Wall") {
						System.out.println("geht");
					}
				}
			}
		}
	}
}
