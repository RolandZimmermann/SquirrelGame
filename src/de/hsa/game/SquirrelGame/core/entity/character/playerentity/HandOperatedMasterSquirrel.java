package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.gamestats.XY;
import de.hsa.game.SquirrelGame.ui.ConsoleUI;
import de.hsa.game.SquirrelGame.ui.UI;

public class HandOperatedMasterSquirrel extends MasterSquirrel{

	XY move;
	
    public HandOperatedMasterSquirrel(int id, XY position) {
        super(id, position);
        // TODO Auto-generated constructor stub
    }
    
    public void nextStep(EntityContext entityContext) {
    	UI ConsoleUI = new ConsoleUI ();
    	MoveCommand moveCommand = ConsoleUI.getCommand();
    	super.setPositionXY(moveCommand.xy.getX(), moveCommand.xy.getY());
    	
    	   	if(move != null) {
    	   		entityContext.tryMove(this, move);
    	   	}
    }
    
    public void getMove(MoveCommand moveCommand) {
    	this.move = moveCommand.xy;
    }

    

}
