/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.js;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Console class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class Console {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(Console.class.getName());


	/** The time. */
	private Date time;

	/** The str time. */
	private String strTime;

	/**
	 * <p>log.</p>
	 *
	 * @param obj a {@link java.lang.Object} object.
	 */
	public void log(Object obj) {
		logger.log(Level.FINE, obj.toString());
	}

	/**
	 * <p>debug.</p>
	 *
	 * @param obj a {@link java.lang.Object} object.
	 */
	public void debug(Object obj) {
		logger.log(Level.ALL, obj.toString());

	}

	/**
	 * <p>info.</p>
	 *
	 * @param obj a {@link java.lang.Object} object.
	 */
	public void info(Object obj) {
		logger.log(Level.INFO, obj.toString());

	}

	/**
	 * <p>warn.</p>
	 *
	 * @param obj a {@link java.lang.Object} object.
	 */
	public void warn(Object obj) {
		logger.log(Level.WARNING, obj.toString());

	}

	/**
	 * <p>error.</p>
	 *
	 * @param obj a {@link java.lang.Object} object.
	 */
	public void error(Object obj) {
		logger.log(Level.SEVERE ,obj.toString());

	}

	/**
	 * <p>time.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void time(String name) {
		time = new Date();
		logger.info(name + ": timer started");
		strTime = name;

	}

	/**
	 * <p>timeEnd.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void timeEnd(String name) {
		if (name.equals(strTime)) {
			Date date = new Date();
			Date result = new Date(time.getTime() - date.getTime());
			logger.info(strTime + ": " + result);
		}

	}
}
