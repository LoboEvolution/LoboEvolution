package org.loboevolution.html.js;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Console {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(Console.class.getName());


	/** The time. */
	private Date time;

	/** The str time. */
	private String strTime;

	public void log(Object obj) {
		logger.log(Level.FINE, obj.toString());
	}

	public void debug(Object obj) {
		logger.log(Level.ALL, obj.toString());

	}

	public void info(Object obj) {
		logger.log(Level.INFO, obj.toString());

	}

	public void warn(Object obj) {
		logger.log(Level.WARNING, obj.toString());

	}

	public void error(Object obj) {
		logger.log(Level.SEVERE ,obj.toString());

	}

	public void time(String name) {
		time = new Date();
		logger.info(name + ": timer started");
		strTime = name;

	}

	public void timeEnd(String name) {
		if (name.equals(strTime)) {
			Date date = new Date();
			Date result = new Date(time.getTime() - date.getTime());
			logger.info(strTime + ": " + result);
		}

	}
}