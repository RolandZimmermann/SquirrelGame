package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.gamestats.XY;
import de.hsa.game.SquirrelGame.ui.UI;
import de.hsa.game.SquirrelGame.ui.console.ConsoleUI;

public class HandOperatedMasterSquirrel extends MasterSquirrel{

	private MoveCommand move;
	private int wallCounter = 0;
	
	
    public HandOperatedMasterSquirrel(int id, XY position) {
        super(id, position);
    }
    
    public void nextStep(EntityContext entityContext) {
    	
    	if(this.getEnergy()<= 0) {
    		this.updateEnergy(-this.getEnergy());
    	}
    	   	if(wallCounter == 0) {
    	   		if (move == null)
    	   			return;
    	   		if(move == MoveCommand.MINI_UPRIGHT|| move == MoveCommand.MINI_UPLEFT|| move == MoveCommand.MINI_UP
    	   				|| move == MoveCommand.MINI_RIGHT || move == MoveCommand.MINI_LEFT
    	   				|| move == MoveCommand.MINI_DOWNRIGHT || move == MoveCommand.MINI_DOWNLEFT
    	   				|| move == MoveCommand.MINI_DOWN) {
    	   			if (move.energy> this.getEnergy()) {
    	   				return;
    	   			}
    	   			entityContext.trySpawnMiniSquirrel(this, new XY(this.getPositionXY().getX()+ move.xy.getX(), 
    	   					this.getPositionXY().getY()+ move.xy.getY()), move.energy);
    	   			return;
    	   		}
    	        entityContext.tryMove(this, move.xy);
    	        } else {
    	            wallCounter--;
    	        }
    }
    
    public void getMove(MoveCommand moveCommand) {
    	this.move = moveCommand;
    }
    
    
    public void wallCollison() {
        wallCounter = 4;
    }

    

}
