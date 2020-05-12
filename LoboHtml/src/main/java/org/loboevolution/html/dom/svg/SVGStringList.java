/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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
package org.loboevolution.html.dom.svg;

import org.w3c.dom.DOMException;

/**
 * <p>SVGStringList interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGStringList {
	/**
	 * <p>getNumberOfItems.</p>
	 *
	 * @return a int.
	 */
	int getNumberOfItems();

	/**
	 * <p>clear.</p>
	 *
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void clear() throws DOMException;

	/**
	 * <p>initialize.</p>
	 *
	 * @param newItem a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	String initialize(String newItem) throws DOMException, SVGException;

	/**
	 * <p>getItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	String getItem(int index) throws DOMException;

	/**
	 * <p>insertItemBefore.</p>
	 *
	 * @param newItem a {@link java.lang.String} object.
	 * @param index a int.
	 * @return a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	String insertItemBefore(String newItem, int index) throws DOMException, SVGException;

	/**
	 * <p>replaceItem.</p>
	 *
	 * @param newItem a {@link java.lang.String} object.
	 * @param index a int.
	 * @return a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	String replaceItem(String newItem, int index) throws DOMException, SVGException;

	/**
	 * <p>removeItem.</p>
	 *
	 * @param index a int.
	 * @return a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	String removeItem(int index) throws DOMException;

	/**
	 * <p>appendItem.</p>
	 *
	 * @param newItem a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	String appendItem(String newItem) throws DOMException, SVGException;
}
