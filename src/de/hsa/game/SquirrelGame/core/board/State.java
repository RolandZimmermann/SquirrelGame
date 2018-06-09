package de.hsa.game.SquirrelGame.core.board;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.core.EntityContext;
import de.hsa.game.SquirrelGame.core.entity.character.MasterSquirrelBot;
import de.hsa.game.SquirrelGame.gamestats.MoveCommand;

public class State {
	private int highscore = 0;
	private Board board;
	private HashMap<String, List <Integer>> map = new HashMap<>();
	
	private Logger logger = Logger.getLogger(State.class.getName());

	public State(Board board) {
		this.board = board;
		load();
		
		
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

	public HashMap<String,  List <Integer>> getMap() {
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
		
			map.get(e.getBotController().getClass().getName()).add(e.getEnergy());
			
			int score = e.getEnergy();
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
	
	public void load () {
		List <MasterSquirrelBot> e = board.getBots();
		 for (MasterSquirrelBot i : e) {
			 map.put(i.getBotController().getClass().getName(), new ArrayList<Integer>());
			 }
		 Properties prop = new Properties();
			InputStream input = null;
			
			
			try {
				input = new FileInputStream("ressource/configs/highscore.properties");
				prop.load(input);
				highscore =Integer.parseInt( prop.getProperty("highscore"));
				
				
				
			} catch (IOException o) {
				// TODO Auto-generated catch block
				
				logger.log(Level.SEVERE, o.getMessage(), o);;
			} 
		 finally {
			
			if (input != null) {
				
				try {
					input.close();
				}catch (IOException o) {
					logger.log(Level.SEVERE, o.getMessage(), o);
				}
			}
		}
		
	}
	
	public void save() {
	
		Properties prop = new Properties ();
		OutputStream output = null;
		try {
			output = new FileOutputStream("ressource/configs/highscore.properties");
			List <MasterSquirrelBot> bots = board.getBots();
			for(MasterSquirrelBot e : bots) {
				
				prop.setProperty(e.getBotController().getClass().getName(), map.get(e.getBotController().getClass().getName()).toString());
			}
			prop.setProperty("highscore", Integer.toString(highscore));
			
			prop.store(output, null);
			
		} catch ( IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
			
		}finally {
			
			if (output != null) {
				
				try {
					
					
					output.close();
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
