/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.js.object;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class Console.
 */
public class Console {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(Console.class);

	/** The time. */
	private Date time;

	/** The str time. */
	private String strTime;

	public void log(Object obj) {
		logger.error(obj.toString());
	}

	public void debug(Object obj) {
		logger.error(obj.toString());

	}

	public void info(Object obj) {
		logger.info(obj.toString());

	}

	public void warn(Object obj) {
		logger.warn(obj.toString());

	}

	public void error(Object obj) {
		logger.error(obj.toString());

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
