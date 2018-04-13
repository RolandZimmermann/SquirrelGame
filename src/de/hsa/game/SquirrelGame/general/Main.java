package de.hsa.game.SquirrelGame.general;

import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.BoardFactory;
import de.hsa.game.SquirrelGame.core.entity.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.EntitySet;
import de.hsa.game.SquirrelGame.core.entity.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.Wall;
import de.hsa.game.SquirrelGame.core.entity.playerentity.HandOperatedMasterSquirrel;
import de.hsa.game.SquirrelGame.gamestats.XY;

public class Main {
	
	//Aufgabe 2
	public static void main(String[] args) {
		EntitySet entitySet = new EntitySet();
		int id = 0;
		
		entitySet.insert(new BadBeast(id++,new XY(10,10)));
		entitySet.insert(new BadPlant(id++,new XY(10,30)));
		entitySet.insert(new GoodBeast(id++,new XY(2,10)));
		entitySet.insert(new GoodPlant(id++,new XY(16,18)));
		entitySet.insert(new HandOperatedMasterSquirrel(id++,new XY(1,1)));
		entitySet.insert(new Wall(id++,new XY(77,19)));
		
		while (true) {
			System.out.println(entitySet.toString());
			entitySet.nextStep();
			
		}
	}
	/*
	//Aufgabe 3
	public static void main(String[] args) {
		Board board =  BoardFactory.createBoard();
		System.out.println(board.toString());
	}
	*/
}
