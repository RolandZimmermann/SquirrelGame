package de.hsa.game.SquirrelGame.ui;

import java.util.Scanner;

public class InputSinglePlayerMode implements UI {
	public char charinput() {
		Scanner s = new Scanner(System.in);
		
		char h = s.next().charAt(0);
		return h;
	}
}
