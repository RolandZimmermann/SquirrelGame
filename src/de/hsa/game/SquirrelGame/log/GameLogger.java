package de.hsa.game.SquirrelGame.log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
/**
 * Class to log game events
 * @author reich
 *
 */
public class GameLogger {
	public static Level loggerLevel = Level.INFO;
	private static Logger logger = Logger.getLogger(GameLogger.class.getName());
	

//	private static Logger initLogger() {
//		Logger log = logger;
//		if(log == null) {
//		log = Logger.getLogger(GameLogger.class.getName());
//		SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
//		try {
//			fh = new FileHandler("Logfile.log");
//			fh.setFormatter(new Formatter() {
//				@Override
//				public String format(LogRecord record) {
//					SimpleDateFormat logTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//					Calendar cal = new GregorianCalendar();
//					cal.setTimeInMillis(record.getMillis());
//					return record.getLevel() + " " + logTime.format(cal.getTime()) + " || "
//							+ record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1,
//									record.getSourceClassName().length())
//							+ "." + record.getSourceMethodName() + "() : " + record.getMessage()
//							+ System.getProperty("line.separator");
//				}
//			});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log.addHandler(fh);
//		}
//		log.setLevel(loggerLevel);
//		return log;
//	}

/**
 *initialize logger 
 */
	public static void init() {
		InputStream ins = null;
		try {
			ins = new FileInputStream("src/de/hsa/game/SquirrelGame/log/log_config.properties");
			LogManager.getLogManager().readConfiguration(ins);
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		} 
	}
}
