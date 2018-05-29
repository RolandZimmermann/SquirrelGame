package de.hsa.game.SquirrelGame.core.board;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.EntitySet;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.HandOperatedMasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.gamestats.XYsupport;
import de.hsa.game.SquirrelGame.log.GameLogger;
import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.game.SquirrelGame.core.entity.character.*;
import de.hsa.game.SquirrelGame.core.entity.character.Character;
import de.hsa.game.SquirrelGame.core.entity.character.bots.BotControllerFactoryImpl;
import de.hsa.game.SquirrelGame.core.entity.character.bots.RandomBot;

public class Board {

	// starting Logging
	private static Logger logger = Logger.getLogger(Board.class.getName());

	private final int BOARD_WIDTH;
	private final int BOARD_HEIGHT;

	private int id;

	private ArrayList<Entity> entitySet = new ArrayList<Entity>();
	private ArrayList<Entity> removeID = new ArrayList<Entity>();
	private ArrayList<Entity> addID = new ArrayList<Entity>();

	Board(int boardWidth, int boardHeight, int countBadPlant, int countGoodPlant, int countBadBeast, int countGoodBeast,
			int countHandOperatedMastersquirrel, int countMastersquirrel, int countWall) {

		logger.finer("Initialising");
		logger.finest("test");
		logger.log(Level.FINEST, "test2", new Throwable());

		this.BOARD_HEIGHT = boardHeight;
		this.BOARD_WIDTH = boardWidth;

		int counter = countBadPlant + countGoodPlant + countBadBeast + countGoodBeast + countHandOperatedMastersquirrel
				+ countMastersquirrel + countWall;

		List<XY> randomlocations = generateRandomLocations(counter);

		for (int i = 0; i < countBadPlant; i++) {
			entitySet.add(new BadPlant(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}

		for (int i = 0; i < countGoodPlant; i++) {
			entitySet.add(new GoodPlant(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}

		for (int i = 0; i < countBadBeast; i++) {
			entitySet.add(new BadBeast(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countGoodBeast; i++) {
			entitySet.add(new GoodBeast(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countWall; i++) {
			entitySet.add(new Wall(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countHandOperatedMastersquirrel; i++) {
			entitySet.add(new HandOperatedMasterSquirrel(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countMastersquirrel; i++) {
			entitySet.add(new MasterSquirrelBot(id++, randomlocations.get(0), new BotControllerFactoryImpl()));
			randomlocations.remove(0);
		}

		for (int i = 0; i < boardHeight; i++) {
			entitySet.add(new Wall(id++, new XY(0, i)));
		}
		for (int i = 0; i < boardWidth; i++) {
			entitySet.add(new Wall(id++, new XY(i, 0)));
		}
		for (int i = boardHeight; i > 0; i--) {
			entitySet.add(new Wall(id++, new XY(boardWidth - 1, i - 1)));
		}
		for (int i = boardWidth; i > 0; i--) {
			entitySet.add(new Wall(id++, new XY(i - 1, boardHeight - 1)));
		}

	}

	public void killandReplace(Entity entity, XY newPos) {
		if (entity instanceof GoodPlant) {
			addID.add(new GoodPlant(id++, new XY(newPos.x, newPos.y)));
		}
		if (entity instanceof BadPlant) {
			addID.add(new BadPlant(id++, new XY(newPos.x, newPos.y)));
		}
		if (entity instanceof GoodBeast) {
			addID.add(new GoodBeast(id++, new XY(newPos.x, newPos.y)));
		}
		if (entity instanceof BadBeast) {
			addID.add(new BadBeast(id++, new XY(newPos.x, newPos.y)));
		}

		logger.finer(entity.toString() + newPos.toString());
	}

	public void kill(Entity entity) {
		removeID.add(entity);
		logger.finer(entity.toString());
	}

	public void spawnMiniSquirrel(MasterSquirrel master, XY xy, int energy) {
		addID.add(new MiniSquirrel(id++, xy, energy, master));
		logger.finer("Spawning mini squirrel: " + xy.toString() + energy);
	}

	private ArrayList<XY> generateRandomLocations(int count) {

		ArrayList<XY> randomLocations = new ArrayList<>();
		Random a = new Random();

		XY b = new XY(0, 0);
		randomLocations.add(b);

		for (int i = 0; i < count; i++) {
			boolean inserted = false;
			XY xy = new XY(a.nextInt(BOARD_WIDTH - 2) + 1, a.nextInt(BOARD_HEIGHT - 2) + 1);
			for (XY positions : randomLocations) {
				if (!XYsupport.equalPosition(positions, xy)) {
					randomLocations.add(xy);
					inserted = true;
					break;
				}
			}
			if (!inserted) {
				count++;
			}
		}
		randomLocations.remove(b);
		return randomLocations;
	}

	public Entity[][] flatten() {

		Entity[][] entityarry = new Entity[BOARD_HEIGHT][BOARD_WIDTH];

		for (Entity e : entitySet) {
			entityarry[e.getPositionXY().y][e.getPositionXY().x] = e;
		}

		return entityarry;
	}

	public void update(MoveCommand moveCommand, EntityContext entityContext) {
		for (int i = 0; i < entitySet.size(); i++) {
			Entity c = entitySet.get(i);
			if (c instanceof Character) {
				if (c instanceof HandOperatedMasterSquirrel) {
					((HandOperatedMasterSquirrel) c).getMove(moveCommand);
				}
				((Character) c).nextStep(entityContext);
			}
		}
		for (int i = 0; i < removeID.size(); i++) {
			entitySet.remove(removeID.get(i));
		}
		for (int i = 0; i < addID.size(); i++) {
			entitySet.add(addID.get(i));
		}
		removeID = new ArrayList<>();
		addID = new ArrayList<>();

		logger.finest("Updated");
	}

	public String toString() {
		return entitySet.toString();
	}
}
