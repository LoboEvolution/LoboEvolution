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
package org.loboevolution.ua;

import java.io.File;

/**
 * Represents a URL parameter. It may be a GET or POST parameter.
 */
public interface Parameter {

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Checks if is text.
	 *
	 * @return true, if is text
	 */
	boolean isText();

	/**
	 * Gets the text value.
	 *
	 * @return the text value
	 */
	String getTextValue();

	/**
	 * Checks if is file.
	 *
	 * @return true, if is file
	 */
	boolean isFile();

	/**
	 * Gets the file value.
	 *
	 * @return the file value
	 */
	File[] getFileValue();
}
