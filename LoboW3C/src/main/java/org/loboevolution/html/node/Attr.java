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

package org.loboevolution.html.node;

import org.loboevolution.html.node.Node;

/**
 * A DOM element's attribute as an object. In most DOM methods, you will
 * probably directly retrieve the attribute as a string (e.g.,
 * Element.getAttribute(), but certain functions (e.g.,
 * Element.getAttributeNode()) or means of iterating give Attr types.
 *
 *
 *
 */
public interface Attr extends Node {

	/**
	 * <p>getLocalName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLocalName();

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * <p>getNamespaceURI.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getNamespaceURI();

	/**
	 * <p>getOwnerElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	Element getOwnerElement();

	/**
	 * <p>getPrefix.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getPrefix();

	/**
	 * <p>isSpecified.</p>
	 *
	 * @return a boolean.
	 */
	boolean isSpecified();

	/**
	 * <p>getValue.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValue();

	/**
	 * <p>setValue.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	void setValue(String value);
	
	/**
	 * <p>isId.</p>
	 *
	 * @return a boolean.
	 */
	boolean isId();

}
