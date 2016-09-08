/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.js;

/**
 * The Interface Console.
 */
public interface Console {

	/**
	 * Log.
	 *
	 * @param obj
	 *            the obj
	 */
	void log(Object obj);

	/**
	 * Debug.
	 *
	 * @param obj
	 *            the obj
	 */
	void debug(Object obj);

	/**
	 * Info.
	 *
	 * @param obj
	 *            the obj
	 */
	void info(Object obj);

	/**
	 * Warn.
	 *
	 * @param obj
	 *            the obj
	 */
	void warn(Object obj);

	/**
	 * Error.
	 *
	 * @param obj
	 *            the obj
	 */
	void error(Object obj);

	/**
	 * Time.
	 *
	 * @param name
	 *            the name
	 */
	void time(String name);

	/**
	 * Time end.
	 *
	 * @param name
	 *            the name
	 */
	void timeEnd(String name);
}
