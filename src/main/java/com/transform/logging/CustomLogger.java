package com.transform.logging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CustomLogger {
	private static Logger LOGGER;
	//private static final String logsDirectory = System.getProperty("user.home") + "/." + ConfigExtractor.returnConfig("uri") + "logs/";
	private static final String logsDirectory = "/tmp/";
	public static void log(String logLevel, String message) {
		LOGGER = Logger.getLogger("CustomLogger");
		//Handler consoleHandler = null;
		Handler fileHandler  = null;
		try{
			//consoleHandler = new ConsoleHandler();
			String logFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			if(fileHandler == null) {
				if(logLevel.equals("SEVERE")) {
					fileHandler  = new FileHandler(logsDirectory + "pesalink-error-" + logFileName + ".log", true);
				} else {
			        fileHandler  = new FileHandler(logsDirectory + "pesalink-" + logFileName + ".log", true);
				}
			}
			fileHandler.setFormatter(new SimpleFormatter());
			//LOGGER.addHandler(consoleHandler);
			LOGGER.addHandler(fileHandler);
			//Setting levels to handlers and LOGGER
			//consoleHandler.setLevel(Level.ALL);
			fileHandler.setLevel(Level.ALL);
			LOGGER.setLevel(Level.ALL);
			//LOGGER.removeHandler(consoleHandler);
			//LOGGER.removeHandler(fileHandler);
		}catch(IOException exception){
			LOGGER.log(Level.SEVERE, "Error occur in FileHandler." + exception.getMessage());
		}
		
		Level level = logLevel == "ALL" ? Level.ALL :
			logLevel == "CONFIG" ? Level.CONFIG :
				logLevel == "INFO" ? Level.INFO :
					logLevel == "SEVERE" ? Level.SEVERE :
						logLevel == "WARNING" ? Level.WARNING : 
							logLevel == "FINE" ? Level.FINE : Level.SEVERE;
		LOGGER.log(level, message);
		//consoleHandler.close();
		if(fileHandler != null)fileHandler.close();
	}
}
