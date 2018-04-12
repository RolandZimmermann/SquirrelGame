package de.hsa.games.squirrelgames.UI;

import java.util.Scanner;

public class Input {
	public static char charInput() {
		Scanner s = new Scanner(System.in);
		
		char h = s.next().charAt(0);
		return h;
	}
}
