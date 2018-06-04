package de.hsa.game.SquirrelGame.core.board;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.core.BoardView;
import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.Entity;
import de.hsa.game.SquirrelGame.core.entity.character.BadBeast;
import de.hsa.game.SquirrelGame.core.entity.character.Character;
import de.hsa.game.SquirrelGame.core.entity.character.GoodBeast;
import de.hsa.game.SquirrelGame.core.entity.character.MasterSquirrelBot;
import de.hsa.game.SquirrelGame.core.entity.character.MiniSquirrelBot;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.HandOperatedMasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.gamestats.XYsupport;
import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.util.XY;

public class Board {

	private static Logger logger = Logger.getLogger(Board.class.getName());

	private final int BOARD_WIDTH;
	private final int BOARD_HEIGHT;

	private BoardView boardView;

	private int id;

	private List<Entity> entitySet = new ArrayList<Entity>();
	private List<Entity> removeID = new ArrayList<Entity>();
	private List<Entity> addID = new ArrayList<Entity>();
	private List<MasterSquirrelBot> bots = new ArrayList<MasterSquirrelBot>();

	public Board(int boardWidth, int boardHeight, int countBadPlant, int countGoodPlant, int countBadBeast, int countGoodBeast,
			int countHandOperatedMastersquirrel, int countWall, String[] bots) {

		logger.finer("Initialising");
		
		this.BOARD_HEIGHT = boardHeight;
		this.BOARD_WIDTH = boardWidth;

		int counter = countBadPlant + countGoodPlant + countBadBeast + countGoodBeast + countHandOperatedMastersquirrel
				+ bots.length + countWall;

		List<XY> randomlocations = generateRandomLocations(counter);

		for (int i = 0; i < countBadPlant; i++) {
			getEntitySet().add(new BadPlant(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}

		for (int i = 0; i < countGoodPlant; i++) {
			getEntitySet().add(new GoodPlant(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}

		for (int i = 0; i < countBadBeast; i++) {
			getEntitySet().add(new BadBeast(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countGoodBeast; i++) {
			getEntitySet().add(new GoodBeast(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countWall; i++) {
			getEntitySet().add(new Wall(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countHandOperatedMastersquirrel; i++) {
			getEntitySet().add(new HandOperatedMasterSquirrel(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < bots.length; i++) {
			
			URL[] urls = null;
			try
			{
				//what is the real path??
			File dir = new File("");
			URL url = dir.toURI().toURL();
			urls = new URL[]{url};
			}
			catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		
			ClassLoader cl = new URLClassLoader(urls);
			
			BotControllerFactory botControllerFactory = null;
			try {
				//Package name needed too
				Class cls = cl.loadClass("de.hsa.games.fatsquirrel.botimpls." + bots[i]);
				botControllerFactory = (BotControllerFactory) cls.newInstance();
			} catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				continue;
			}
			
			Entity e = new MasterSquirrelBot(id++, randomlocations.get(0), botControllerFactory);
			
			getEntitySet().add(e);
			getBots().add((MasterSquirrelBot) e);
			randomlocations.remove(0);
		}

		for (int i = 0; i < boardHeight; i++) {
			getEntitySet().add(new Wall(id++, new XY(0, i)));
		}
		for (int i = 0; i < boardWidth; i++) {
			getEntitySet().add(new Wall(id++, new XY(i, 0)));
		}
		for (int i = boardHeight; i > 0; i--) {
			getEntitySet().add(new Wall(id++, new XY(boardWidth - 1, i - 1)));
		}
		for (int i = boardWidth; i > 0; i--) {
			getEntitySet().add(new Wall(id++, new XY(i - 1, boardHeight - 1)));
		}

	}

	public Board(int boardWidth, int boardHeight, int countBadPlant, int countGoodPlant, int countBadBeast, int countGoodBeast,
			int countHandOperatedMastersquirrel, int countWall, BotControllerFactory[] bots) {
		
		logger.finer("Initialising Training Board");
		
		this.BOARD_HEIGHT = boardHeight;
		this.BOARD_WIDTH = boardWidth;

		int counter = countBadPlant + countGoodPlant + countBadBeast + countGoodBeast + countHandOperatedMastersquirrel
				+ bots.length + countWall;

		List<XY> randomlocations = generateRandomLocations(counter);

		for (int i = 0; i < countBadPlant; i++) {
			getEntitySet().add(new BadPlant(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}

		for (int i = 0; i < countGoodPlant; i++) {
			getEntitySet().add(new GoodPlant(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}

		for (int i = 0; i < countBadBeast; i++) {
			getEntitySet().add(new BadBeast(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countGoodBeast; i++) {
			getEntitySet().add(new GoodBeast(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countWall; i++) {
			getEntitySet().add(new Wall(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < countHandOperatedMastersquirrel; i++) {
			getEntitySet().add(new HandOperatedMasterSquirrel(id++, randomlocations.get(0)));
			randomlocations.remove(0);
		}
		for (int i = 0; i < bots.length; i++) {
			
			
			Entity e = new MasterSquirrelBot(id++, randomlocations.get(0), bots[i]);
			
			getEntitySet().add(e);
			getBots().add((MasterSquirrelBot) e);
			randomlocations.remove(0);
		}

		for (int i = 0; i < boardHeight; i++) {
			getEntitySet().add(new Wall(id++, new XY(0, i)));
		}
		for (int i = 0; i < boardWidth; i++) {
			getEntitySet().add(new Wall(id++, new XY(i, 0)));
		}
		for (int i = boardHeight; i > 0; i--) {
			getEntitySet().add(new Wall(id++, new XY(boardWidth - 1, i - 1)));
		}
		for (int i = boardWidth; i > 0; i--) {
			getEntitySet().add(new Wall(id++, new XY(i - 1, boardHeight - 1)));
		}

	}

	public void killandReplace(Entity entity, XY newPos) {
		if (entity instanceof GoodPlant) {
			getAddID().add(new GoodPlant(id++, new XY(newPos.x, newPos.y)));
		}
		if (entity instanceof BadPlant) {
			getAddID().add(new BadPlant(id++, new XY(newPos.x, newPos.y)));
		}
		if (entity instanceof GoodBeast) {
			getAddID().add(new GoodBeast(id++, new XY(newPos.x, newPos.y)));
		}
		if (entity instanceof BadBeast) {
			getAddID().add(new BadBeast(id++, new XY(newPos.x, newPos.y)));
		}

		logger.finer(entity.toString() + newPos.toString());
	}

	
	public void kill(Entity entity) {
		getRemoveID().add(entity);
		logger.finer(entity.toString());
	}

	public void spawnMiniSquirrel(MasterSquirrel master, XY xy, int energy) {
		getAddID().add(new MiniSquirrel(id++, xy, energy, master));
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

		for (Entity e : getEntitySet()) {
			entityarry[e.getPositionXY().y][e.getPositionXY().x] = e;
		}

		return entityarry;
	}

	public void setBoardView(BoardView boardView) {
		this.boardView = boardView;
	}

	public void update(MoveCommand moveCommand, EntityContext entityContext) {
		for (int i = 0; i < getEntitySet().size(); i++) {
			Entity c = getEntitySet().get(i);
			if (c instanceof Character) {
				if (c instanceof HandOperatedMasterSquirrel) {
					((HandOperatedMasterSquirrel) c).getMove(moveCommand);
				}
				((Character) c).nextStep(entityContext);
				if (boardView != null) {
					boardView.update();
				}
			}
		}
		for (int i = 0; i < getRemoveID().size(); i++) {
			getEntitySet().remove(getRemoveID().get(i));
		}
		for (int i = 0; i < getAddID().size(); i++) {
			getEntitySet().add(getAddID().get(i));
		}
		removeID = new ArrayList<>();
		addID = new ArrayList<>();

		logger.finest("Updated");
	}
	
	public List<Entity> getEntitySet() {
		return entitySet;
	}

	public List<Entity> getRemoveID() {
		return removeID;
	}

	public List<Entity> getAddID() {
		return addID;
	}

	public List<MasterSquirrelBot> getBots() {
		return bots;
	}

	public String toString() {
		return entitySet.toString();
	}

	public void spawnMiniSquirrelBot(MasterSquirrel master, XY xy, int energy, BotControllerFactory botControllerFacotry) {
		getAddID().add(new MiniSquirrelBot(master,id++, xy, energy,botControllerFacotry));
		logger.finer("Spawning mini squirrel: " + xy.toString() + energy);
		
	}
}
