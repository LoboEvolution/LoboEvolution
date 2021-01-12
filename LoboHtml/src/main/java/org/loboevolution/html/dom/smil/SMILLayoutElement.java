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

package org.loboevolution.html.dom.smil;

/**
 * Declares layout type for the document. See the LAYOUT element definition .
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SMILLayoutElement extends SMILElement {
	/**
	 * The mime type of the layout langage used in this layout element.The
	 * default value of the type attribute is "text/smil-basic-layout".
	 *
	 * @return a {@link java.lang.String} object.
	 */
    String getType();

	/**
	 * true if the player can understand the mime type,
	 * false otherwise.
	 *
	 * @return a boolean.
	 */
    boolean getResolved();

}
