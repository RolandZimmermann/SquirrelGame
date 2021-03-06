package de.hsa.game.SquirrelGame.core.board;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
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
import de.hsa.game.SquirrelGame.core.entity.character.MultiplayerMasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.HandOperatedMasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MasterSquirrel;
import de.hsa.game.SquirrelGame.core.entity.character.playerentity.MiniSquirrel;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.BadPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.GoodPlant;
import de.hsa.game.SquirrelGame.core.entity.noncharacter.Wall;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;
import de.hsa.game.SquirrelGame.gamestats.XYsupport;
import de.hsa.game.SquirrelGame.network.ServerConnection;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botimpls.MaToRoKi;
import de.hsa.games.fatsquirrel.util.XY;

/**
 * Board is the database for the game.
 * 
 * @author Roland
 *
 */
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
	private List<HandOperatedMasterSquirrel> player = new ArrayList<HandOperatedMasterSquirrel>();
	private Vector<MultiplayerMasterSquirrel> multiplayer = new Vector<>();

	private MaToRoKi best;

	/**
	 * Create a Board with the given param.
	 * 
	 * @param boardWidth
	 *            the width of the board
	 * @param boardHeight
	 *            the Height of the board
	 * @param countBadPlant
	 *            the amount of bad plants in the game
	 * @param countGoodPlant
	 *            the amount of the good plants in the game
	 * @param countBadBeast
	 *            the amount of the bad beasts in the game
	 * @param countGoodBeast
	 *            the amount of the good beasts in the game
	 * @param countHandOperatedMastersquirrel
	 *            the amount of the squirrels controlled by the player
	 * @param countWall
	 *            the amount of wallstructures in the game
	 * @param bots
	 *            an array of names for the bots
	 */
	public Board(int boardWidth, int boardHeight, int countBadPlant, int countGoodPlant, int countBadBeast,
			int countGoodBeast, int countWall, String[] bots) {

		logger.finer("Initialising");

		this.BOARD_HEIGHT = boardHeight;
		this.BOARD_WIDTH = boardWidth;

		int counter = countBadPlant + countGoodPlant + countBadBeast + countGoodBeast + bots.length;

		List<XY> setpositions = new ArrayList<XY>();

		for (int i = 0; i < countWall; i++) {
			ArrayList<XY> start = generateRandomLocations(1, null);

			Random a = new Random();
			int randomX = 0;
			int randomY = 0;
			switch (a.nextInt(4)) {
			case 0:
				randomX = 1;
				randomY = 0;
				break;
			case 1:
				randomX = 0;
				randomY = 1;
				break;
			case 2:
				randomX = -1;
				randomY = 0;
				break;
			case 3:
				randomX = 0;
				randomY = -1;
				break;
			}

			for (int j = 0; j < BoardConfig.WALL_LENGTH; j++) {
				XY newXY = new XY(start.get(0).x + randomX * j, start.get(0).y + randomY * j);
				if (newXY.x < 1 || newXY.y < 1 || newXY.x > BOARD_WIDTH - 1 || newXY.y > BOARD_HEIGHT - 1) {
					continue;
				}
				getEntitySet().add(new Wall(id++, newXY));
				setpositions.add(newXY);
			}
		}

		List<XY> randomlocations = generateRandomLocations(counter, setpositions);

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

		for (int i = 0; i < bots.length; i++) {

			URL[] urls = null;
			try {
				// what is the real path??
				File dir = new File("de.game.SquirrelGame.core.board");
				URL url = dir.toURI().toURL();
				urls = new URL[] { url };
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}

			@SuppressWarnings("resource")
			ClassLoader cl = new URLClassLoader(urls);

			BotControllerFactory botControllerFactory = null;
			try {
				// Package name needed too
				Class<?> cls = cl.loadClass("de.hsa.games.fatsquirrel.botimpls." + bots[i]);
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

	public Board(int boardWidth, int boardHeight, int countBadPlant, int countGoodPlant, int countBadBeast,
			int countGoodBeast, int handOperatedMasterSquirrel, int countWall, String[] name) {

		logger.finer("Initialising");

		this.BOARD_HEIGHT = boardHeight;
		this.BOARD_WIDTH = boardWidth;

		int counter = countBadPlant + countGoodPlant + countBadBeast + countGoodBeast + handOperatedMasterSquirrel;

		List<XY> setpositions = new ArrayList<XY>();

		for (int i = 0; i < countWall; i++) {
			ArrayList<XY> start = generateRandomLocations(1, null);

			Random a = new Random();
			int randomX = 0;
			int randomY = 0;
			switch (a.nextInt(4)) {
			case 0:
				randomX = 1;
				randomY = 0;
				break;
			case 1:
				randomX = 0;
				randomY = 1;
				break;
			case 2:
				randomX = -1;
				randomY = 0;
				break;
			case 3:
				randomX = 0;
				randomY = -1;
				break;
			}

			for (int j = 0; j < BoardConfig.WALL_LENGTH; j++) {
				XY newXY = new XY(start.get(0).x + randomX * j, start.get(0).y + randomY * j);
				if (newXY.x < 1 || newXY.y < 1 || newXY.x > BOARD_WIDTH - 1 || newXY.y > BOARD_HEIGHT - 1) {
					continue;
				}
				getEntitySet().add(new Wall(id++, newXY));
				setpositions.add(newXY);
			}
		}

		List<XY> randomlocations = generateRandomLocations(counter, setpositions);

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

		for (int i = 0; i < handOperatedMasterSquirrel; i++) {

			HandOperatedMasterSquirrel e = new HandOperatedMasterSquirrel(id++, randomlocations.get(0), name[i]);

			getEntitySet().add(e);
			getPlayer().add(e);
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

	/**
	 * Create a Board with the given param (used for training a neural net).
	 * 
	 * @param boardWidth
	 *            the width of the board
	 * @param boardHeight
	 *            the Height of the board
	 * @param countBadPlant
	 *            the amount of bad plants in the game
	 * @param countGoodPlant
	 *            the amount of the good plants in the game
	 * @param countBadBeast
	 *            the amount of the bad beasts in the game
	 * @param countGoodBeast
	 *            the amount of the good beasts in the game
	 * @param countHandOperatedMastersquirrel
	 *            the amount of the squirrels controlled by the player
	 * @param countWall
	 *            the amount of wallstructures in the game
	 * @param bots
	 *            an array of botcontrollerfactorys to be placed in the game
	 */
	public Board(int boardWidth, int boardHeight, int countBadPlant, int countGoodPlant, int countBadBeast,
			int countGoodBeast, int countWall, BotControllerFactory[] bots) {

		logger.finer("Initialising Training Board");

		this.BOARD_HEIGHT = boardHeight;
		this.BOARD_WIDTH = boardWidth;

		int counter = countBadPlant + countGoodPlant + countBadBeast + countGoodBeast + bots.length;

		List<XY> setpositions = new ArrayList<XY>();

		for (int i = 0; i < countWall; i++) {
			ArrayList<XY> start = generateRandomLocations(1, null);

			Random a = new Random();
			int randomX = 0;
			int randomY = 0;
			switch (a.nextInt(4)) {
			case 0:
				randomX = 1;
				randomY = 0;
				break;
			case 1:
				randomX = 0;
				randomY = 1;
				break;
			case 2:
				randomX = -1;
				randomY = 0;
				break;
			case 3:
				randomX = 0;
				randomY = -1;
				break;
			}

			for (int j = 0; j < BoardConfig.WALL_LENGTH; j++) {
				XY newXY = new XY(start.get(0).x + randomX * j, start.get(0).y + randomY * j);
				if (newXY.x < 1 || newXY.y < 1 || newXY.x > BOARD_WIDTH - 1 || newXY.y > BOARD_HEIGHT - 1) {
					continue;
				}
				getEntitySet().add(new Wall(id++, newXY));
				setpositions.add(newXY);
			}
		}

		List<XY> randomlocations = generateRandomLocations(counter, setpositions);

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

	/**
	 * for muliplayer mode
	 * 
	 * @param boardWidth
	 * @param boardHeight
	 * @param countBadPlant
	 * @param countGoodPlant
	 * @param countBadBeast
	 * @param countGoodBeast
	 * @param countWall
	 * @param serverConnections
	 */
	public Board(int boardWidth, int boardHeight, int countBadPlant, int countGoodPlant, int countBadBeast,
			int countGoodBeast, int countWall, Vector<ServerConnection> serverConnections) {

		logger.finer("Initialising Training Board");

		this.BOARD_HEIGHT = boardHeight;
		this.BOARD_WIDTH = boardWidth;

		int countMultiplayer = serverConnections.size();

		int counter = countBadPlant + countGoodPlant + countBadBeast + countGoodBeast + countMultiplayer;

		List<XY> setpositions = new ArrayList<XY>();

		for (int i = 0; i < countWall; i++) {
			ArrayList<XY> start = generateRandomLocations(1, null);

			Random a = new Random();
			int randomX = 0;
			int randomY = 0;
			switch (a.nextInt(4)) {
			case 0:
				randomX = 1;
				randomY = 0;
				break;
			case 1:
				randomX = 0;
				randomY = 1;
				break;
			case 2:
				randomX = -1;
				randomY = 0;
				break;
			case 3:
				randomX = 0;
				randomY = -1;
				break;
			}

			for (int j = 0; j < BoardConfig.WALL_LENGTH; j++) {
				XY newXY = new XY(start.get(0).x + randomX * j, start.get(0).y + randomY * j);
				if (newXY.x < 1 || newXY.y < 1 || newXY.x > BOARD_WIDTH - 1 || newXY.y > BOARD_HEIGHT - 1) {
					continue;
				}
				getEntitySet().add(new Wall(id++, newXY));
				setpositions.add(newXY);
			}
		}

		List<XY> randomlocations = generateRandomLocations(counter, setpositions);

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
		for (int i = 0; i < countMultiplayer; i++) {

			MultiplayerMasterSquirrel msq = new MultiplayerMasterSquirrel(id++, randomlocations.get(0),
					serverConnections.get(i));

			getEntitySet().add(msq);
			randomlocations.remove(0);
			getMultiplayer().add(msq);
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

	/**
	 * Kills and replaces an entity to a new position
	 * 
	 * @param entity
	 *            which entity to be replaced
	 * @param newPos
	 *            the new position where the new entity is going to be
	 */
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

	/**
	 * Kills a given entity
	 * 
	 * @param entity
	 *            = the entity to be killed
	 */
	public void kill(Entity entity) {
		getRemoveID().add(entity);
		logger.finer(entity.toString());
	}

	/**
	 * Spawn a mini squirrel
	 * 
	 * @param master
	 *            the master
	 * @param xy
	 *            the position of the mini squirrel
	 * @param energy
	 *            the energy of the minisquirrel
	 */
	public void spawnMiniSquirrel(MasterSquirrel master, XY xy, int energy) {
		getAddID().add(new MiniSquirrel(id++, xy, energy, master));
		logger.finer("Spawning mini squirrel: " + xy.toString() + energy);
	}

	/**
	 * A method to generate random location for entitys
	 * 
	 * @param count
	 *            the amount of random location
	 * @param alreadySet
	 *            a list of positions that are already set
	 * @return an ArrayList of new Positions
	 */
	private ArrayList<XY> generateRandomLocations(int count, List<XY> alreadySet) {

		ArrayList<XY> randomLocations = new ArrayList<>();
		Random a = new Random();

		XY b = new XY(0, 0);
		randomLocations.add(b);

		for (int i = 0; i < count; i++) {
			boolean inserted = false;
			boolean alreadyUsed = false;
			XY xy = new XY(a.nextInt(BOARD_WIDTH - 2) + 1, a.nextInt(BOARD_HEIGHT - 2) + 1);

			for (XY positions : randomLocations) {
				if (alreadySet != null) {
					for (XY alreadyUsedPos : alreadySet) {
						if (XYsupport.equalPosition(alreadyUsedPos, xy)) {
							alreadyUsed = true;
							break;

						}
					}
					if (alreadyUsed == true)
						break;
				}
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

	/**
	 * generates a two dimensional array of the Board
	 * 
	 * @return a two dimensional array of the Board
	 */
	public Entity[][] flatten() {

		Entity[][] entityarry = new Entity[BOARD_HEIGHT][BOARD_WIDTH];

		for (Entity e : getEntitySet()) {
			entityarry[e.getPositionXY().y][e.getPositionXY().x] = e;
		}

		return entityarry;
	}

	/**
	 * Sets the boards view
	 * 
	 * @param boardView
	 *            boardview to be set
	 */
	public void setBoardView(BoardView boardView) {
		this.boardView = boardView;
	}
	
	public BoardView getBoardView() {
		return this.boardView;
	}

	/**
	 * Updates every entity in the database, delets and also creates new entitys
	 * 
	 * @param moveCommand
	 *            the move command for the mastersquirrel controlled by the player
	 * @param entityContext
	 *            an interface
	 */
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

	public List<HandOperatedMasterSquirrel> getPlayer() {
		return player;
	}

	public Vector<MultiplayerMasterSquirrel> getMultiplayer() {
		return this.multiplayer;
	}

	public String toString() {
		return entitySet.toString();
	}

	/**
	 * spawn a mini squirrel with the given botfactory
	 * 
	 * @param master
	 *            the master of the mini squirrel
	 * @param xy
	 *            the position of the mini squirrel
	 * @param energy
	 *            the energy of the mini squirrel
	 * @param botControllerFacotry
	 *            the botfactory to create a botcontroller
	 */
	public void spawnMiniSquirrelBot(MasterSquirrel master, XY xy, int energy,
			BotControllerFactory botControllerFacotry) {
		getAddID().add(new MiniSquirrelBot(master, id++, xy, energy, botControllerFacotry));
		logger.finer("Spawning mini squirrel: " + xy.toString() + energy);

	}

	public MaToRoKi getBest() {
		return best;
	}

	public void setBest(MaToRoKi e) {
		this.best = e;
	}

	public byte[][] generateView(ServerConnection sc) {
		byte[][] entityType = new byte[32][32];
		for (MultiplayerMasterSquirrel msq : multiplayer) {
			if (msq.getServerConnection() == sc) {
				for (int y = msq.getPositionXY().y - 15; y < msq.getPositionXY().y + 15; y++) {
					for (int x = msq.getPositionXY().x - 15; x < msq.getPositionXY().x + 15; x++) {
						if (y >= 0 && y < boardView.getSize().y && x >= 0 && x < boardView.getSize().y) {
							Entity entity = boardView.getEntityType(x, y);
							if (entity instanceof Wall) {
								entityType[y - (msq.getPositionXY().y - 15)][x - (msq.getPositionXY().x - 15)] = 5;
							} else if (entity instanceof GoodPlant) {
								entityType[y - (msq.getPositionXY().y - 15)][x - (msq.getPositionXY().x - 15)] = 2;
							} else if (entity instanceof GoodBeast) {
								entityType[y - (msq.getPositionXY().y - 15)][x - (msq.getPositionXY().x - 15)] = 1;
							} else if (entity instanceof BadBeast) {
								entityType[y - (msq.getPositionXY().y - 15)][x - (msq.getPositionXY().x - 15)] = 3;
							} else if (entity instanceof BadPlant) {
								entityType[y - (msq.getPositionXY().y - 15)][x - (msq.getPositionXY().x - 15)] = 4;
							} else if (entity instanceof MasterSquirrel) {
								entityType[y - (msq.getPositionXY().y - 15)][x - (msq.getPositionXY().x - 15)] = 6;
							} else if (entity instanceof MiniSquirrel) {
								entityType[y - (msq.getPositionXY().y - 15)][x - (msq.getPositionXY().x - 15)] = 7;
							} else {
								entityType[y - (msq.getPositionXY().y - 15)][x - (msq.getPositionXY().x - 15)] = 0;
							}
						} else {
							entityType[y - (msq.getPositionXY().y - 15)][x - (msq.getPositionXY().x - 15)] = 0;
						}
					}
				}
				return entityType;
			}
		}

		return null;
	}
}
