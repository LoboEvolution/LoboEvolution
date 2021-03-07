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

import org.loboevolution.html.node.js.console.Console;

/**
 * <p>ConsoleImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ConsoleImpl implements Console {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ConsoleImpl.class.getName());


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

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void count(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void count() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(int message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(double message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(boolean message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(int message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(double message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(boolean message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exception(Object message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exception() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void group(String groupTitle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void group() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void groupCollapsed(String groupTitle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void groupCollapsed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void groupEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(int message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(double message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(boolean message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void log(int message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void log(double message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void log(boolean message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markTimeline(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markTimeline() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void profile(String reportName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void profile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void profileEnd(String reportName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void profileEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void time() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeStamp(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeStamp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeline(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeline() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timelineEnd(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timelineEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Object message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(int message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(double message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(boolean message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(int message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(double message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(boolean message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn() {
		// TODO Auto-generated method stub
		
	}
}
