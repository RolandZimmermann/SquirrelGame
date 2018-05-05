package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.gamestats.XY;
import de.hsa.game.SquirrelGame.ui.UI;
import de.hsa.game.SquirrelGame.ui.console.ConsoleUI;

public class HandOperatedMasterSquirrel extends MasterSquirrel{

	private XY move;
	private int wallCounter = 0;
	
	
    public HandOperatedMasterSquirrel(int id, XY position) {
        super(id, position);
    }
    
    public void nextStep(EntityContext entityContext) {
    	
    	if(this.getEnergy()<= 0) {
    		this.updateEnergy(-this.getEnergy());
    	}
    	   	if(wallCounter == 0) {
    	        entityContext.tryMove(this, move);
    	        } else {
    	            wallCounter--;
    	        }
    }
    
    public void getMove(MoveCommand moveCommand) {
    	this.move = moveCommand.xy;
    }
    
    
    public void wallCollison() {
        wallCounter = 4;
    }

    

}
