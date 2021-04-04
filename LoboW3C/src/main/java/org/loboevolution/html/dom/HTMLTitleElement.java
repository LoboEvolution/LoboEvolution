/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom;

/**
 * Contains the title for a document. This element inherits all of the
 * properties and methods of the HTMLElement interface.
 *
 *
 *
 */
public interface HTMLTitleElement extends HTMLElement {

	/**
	 * Retrieves or sets the text of the object as a string.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getText();

	/**
	 * <p>setText.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	void setText(String text);

}
