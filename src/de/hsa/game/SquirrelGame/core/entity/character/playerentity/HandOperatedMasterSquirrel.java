package de.hsa.game.SquirrelGame.core.entity.character.playerentity;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
/**
 * A class to create a HandOperatedMasterSquirrel.
 */
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.ui.exceptions.NotEnoughEnergyException;
import de.hsa.game.SquirrelGame.ui.exceptions.WrongParamInputException;
import de.hsa.games.fatsquirrel.util.XY;

public class HandOperatedMasterSquirrel extends MasterSquirrel {

    private MoveCommand move;
    private int wallCounter = 0;

    /**
     * Create a HandOperatedMasterSquirrel with the given parameter
     * 
     * @param id
     *            The id of the MasterSquirrel.
     * @param position
     *            The position of the MasterSquirrel.
     */
    public HandOperatedMasterSquirrel(int id, XY position) {
        super(id, position);
    }

    /**
     * Makes the {@code nextStep} of the {@code HandOperatedMasterSquirrel}. Only makes a step, when
     * the {@code wallCounter} is 0.
     * 
     * The {@code HandOperatedMasterSquirrel} is able to spawn a {@code MiniSquirrel}, but only if
     * the energy for the mini is lower than the energy of the master.
     * 
     * If the {@code wallCounter} is set, the Master is unable to move. Every {@code nextStep} lower
     * the {@code wallCounter} till the counter is 0.
     * 
     */
    public void nextStep(EntityContext entityContext) {
        if (this.getEnergy() <= 0) {
            this.updateEnergy(-this.getEnergy());
        }
        if (wallCounter == 0) {
            if (move == null) {
                return;
            }
            if (move == MoveCommand.MINI_UPRIGHT || move == MoveCommand.MINI_UPLEFT || move == MoveCommand.MINI_UP
                    || move == MoveCommand.MINI_RIGHT || move == MoveCommand.MINI_LEFT
                    || move == MoveCommand.MINI_DOWNRIGHT || move == MoveCommand.MINI_DOWNLEFT
                    || move == MoveCommand.MINI_DOWN) {
                if (move.energy < 0) {
                    try {
                        throw new WrongParamInputException("Negative Value");
                    } catch (WrongParamInputException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return;
                }
                if (move.energy > this.getEnergy()) {
                    try {
                        throw new NotEnoughEnergyException("Not enough energy to spawn miniSquirrel");
                    } catch (NotEnoughEnergyException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return;
                }
                entityContext.trySpawnMiniSquirrel(this,
                        new XY(this.getPositionXY().x + move.xy.x, this.getPositionXY().y + move.xy.y), move.energy);
                return;
            }
            entityContext.tryMove(this, move.xy);
            move = null;
        } else {
            wallCounter--;
        }
    }

    /**
     * Set move to the given {@code moveCommand}.
     * 
     * @param moveCommand
     */
    public void getMove(MoveCommand moveCommand) {
        this.move = moveCommand;
    }

    /**
    * Set the {@code wallCounter} to 4.
    * 
    */
    public void wallCollison() {
        wallCounter = 4;
    }

}
