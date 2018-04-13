package de.hsa.game.SquirrelGame.core.entity.playerentity;

import de.hsa.game.SquirrelGame.gamestats.Direction;
import de.hsa.game.SquirrelGame.gamestats.XY;
import de.hsa.game.SquirrelGame.ui.InputSinglePlayerMode;
import de.hsa.game.SquirrelGame.ui.UI;

public class HandOperatedMasterSquirrel extends MasterSquirrel{

    public HandOperatedMasterSquirrel(int id, XY position) {
        super(id, position);
        // TODO Auto-generated constructor stub
    }
    
    public void nextStep() {
    	UI ui = new InputSinglePlayerMode();
    	
    	char input = ui.charinput();    	
    	
    	switch (input) {
		case 'w':
		case 'W':
			getPositionXY().move(Direction.UP);
			break;
		case 'a':
		case 'A':
			getPositionXY().move(Direction.LEFT);
			break;
		case 's':
		case 'S':
			getPositionXY().move(Direction.DOWN);
			break;
		case 'd':
		case 'D':
			getPositionXY().move(Direction.RIGHT);
			break;
		case 'q':
		case 'Q':
			getPositionXY().move(Direction.UPLEFT);
			break;
		case 'e':
		case 'E':
			getPositionXY().move(Direction.UPRIGHT);
			break;
		case 'y':
		case 'Y':
			getPositionXY().move(Direction.DOWNLEFT);
			break;
		case 'x':
		case 'X':
			getPositionXY().move(Direction.DOWNRIGHT);
			break;

		default:
			break;
		}
    }

    

}
