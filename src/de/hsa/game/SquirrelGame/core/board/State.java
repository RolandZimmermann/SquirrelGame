package de.hsa.game.SquirrelGame.core.board;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.character.MasterSquirrelBot;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;

public class State {
	private int highscore = 0;
	private Board board;
	private HashMap<String, Integer> map = new HashMap<>();
	
	private Logger logger = Logger.getLogger(State.class.getName());

	public State(Board board) {
		this.board = board;
	}

	public void update(MoveCommand moveCommand, EntityContext entityContext) {
		board.update(moveCommand, entityContext);
	}

	public Board getBoard() {
		return board;
	}

	public int getHighscore() {
		return highscore;
	}

	public HashMap<String, Integer> getMap() {
		return map;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void restart() {
		List<MasterSquirrelBot> bots = board.getBots();
		for (MasterSquirrelBot e : bots) {
			if(e.getEnergy() < 0) {
				e.updateEnergy(-e.getEnergy());
			}
			map.put(e.getBotController().getClass().getName(), e.getEnergy());
			
			int score = map.get(e.getBotController().getClass().getName()).intValue();
			if(score > highscore) {
				highscore = score;
			}
		}

		bots.sort((a,b) -> Integer.compare(b.getEnergy(), a.getEnergy()));
		
		System.out.println("Bot-Scores:");
		logger.info("Bot-Scores:");
		for(MasterSquirrelBot bot : bots) {
			System.out.println(bot.getBotController().getClass().getName() + "| Score:" + bot.getEnergy());
			logger.info(bot.getBotController().getClass().getName() + "| Score:" + bot.getEnergy());
		}
		System.out.println("Highscore: " + highscore);
		System.out.println();
	}
}
