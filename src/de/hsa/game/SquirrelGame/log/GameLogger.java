package de.hsa.game.SquirrelGame.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class GameLogger {
	public static Level loggerLevel = Level.INFO;
	private static Logger logger = init();
	private static FileHandler fh;
	

	private static Logger init() {
		Logger log = logger;
		if(log == null) {
		log = Logger.getLogger(GameLogger.class.getName());
		SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
		try {
			fh = new FileHandler("Logfile.log");
			fh.setFormatter(new Formatter() {
				@Override
				public String format(LogRecord record) {
					SimpleDateFormat logTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					Calendar cal = new GregorianCalendar();
					cal.setTimeInMillis(record.getMillis());
					return record.getLevel() + " " + logTime.format(cal.getTime()) + " || "
							+ record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1,
									record.getSourceClassName().length())
							+ "." + record.getSourceMethodName() + "() : " + record.getMessage()
							+ System.getProperty("line.separator");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.addHandler(fh);
		}
		log.setLevel(loggerLevel);
		return log;
	}
}
