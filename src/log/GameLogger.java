package log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GameLogger {
	private static GameLogger logger = new GameLogger();
	private static FileHandler fh;

	public GameLogger() {
//		startLogger();
		// just to make our log file nicer :)
				Logger logger = Logger.getLogger(GameLogger.class.toString());
						SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
						try {
							//fh = new FileHandler("MyGameLogFile_" + format.format(Calendar.getInstance().getTime()) + ".log");
							fh = new FileHandler("textnew.txt");
							fh.setFormatter(new Formatter() {
								@Override
								public String format(LogRecord record) {
									SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
									Calendar cal = new GregorianCalendar();
									cal.setTimeInMillis(record.getMillis());
									return record.getLevel() + logTime.format(cal.getTime()) + " || "
											+ record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1,
													record.getSourceClassName().length())
											+ "." + record.getSourceMethodName() + "() : " + record.getMessage() + "\n";
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println(fh);
						logger.addHandler(fh);
		
	}
	
//	private static Logger startLogger() {
//		
//				return logger;
//	}

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(GameLogger.class.toString());
//		GameLogger logger = new GameLogger();
		logger.info("info msg");
		logger.severe("error message");
		logger.fine("fine message"); // won't show because to high level of logging
		logger.log(Level.INFO,"test");
	}
}
