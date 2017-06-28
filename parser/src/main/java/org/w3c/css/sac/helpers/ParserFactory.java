/*
 * Copyright (c) 1999 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 *
 * $Id: ParserFactory.java 477010 2006-11-20 02:54:38Z mrglavas $
 */
package org.w3c.css.sac.helpers;

import org.w3c.css.sac.Parser;

/**
 * A factory for creating Parser objects.
 *
 * @author Philippe Le Hegaret
 * @version $Revision: 477010 $
 */
public class ParserFactory {

	/**
	 * Create a parser with given selectors factory and conditions factory.
	 *
	 * @return the parser
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws NullPointerException
	 *             the null pointer exception
	 * @throws ClassCastException
	 *             the class cast exception
	 */
	public Parser makeParser() throws ClassNotFoundException, IllegalAccessException, InstantiationException,
			NullPointerException, ClassCastException {
		String className = System.getProperty("org.w3c.css.sac.parser");
		if (className == null) {
			throw new NullPointerException("No value for sac.parser property");
		} else {
			return (Parser) Class.forName(className).newInstance();
		}
	}
}
