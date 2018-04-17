package de.hsa.game.SquirrelGame.general;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.board.Board;
import de.hsa.game.SquirrelGame.core.board.BoardFactory;
import de.hsa.game.SquirrelGame.core.board.FlattenBoard;
import de.hsa.game.SquirrelGame.core.entity.EntitySet;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.HandOperatedMasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.gamestats.XY;
import de.hsa.game.SquirrelGame.ui.ConsoleUI;
import de.hsa.game.SquirrelGame.ui.UI;

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
	
	//Aufgabe 3
//	public static void main(String[] args) {
//		Board board =  BoardFactory.createBoard();
//		BoardView boardView = new FlattenBoard(board);
//		UI ui = new ConsoleUI();
//		
//		boardView.update();
//		ui.render(boardView);
//	}
	
}
