package de.hsa.game.SquirrelGame.ui;

import java.util.Scanner;

public class Input {
	public static char charInput() {
		Scanner s = new Scanner(System.in);
		
		char h = s.next().charAt(0);
		return h;
	}
}
