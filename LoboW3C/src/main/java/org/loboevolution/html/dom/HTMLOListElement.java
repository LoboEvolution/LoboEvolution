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
 * Provides special properties (beyond those defined on the regular HTMLElement
 * interface it also has available to it by inheritance) for manipulating
 * ordered list elements.
 *
 *
 *
 */
public interface HTMLOListElement extends HTMLElement {

	/**
	 * <p>isCompact.</p>
	 *
	 * @return a boolean.
	 */
	@Deprecated
	boolean isCompact();

	/**
	 * <p>setCompact.</p>
	 *
	 * @param compact a boolean.
	 */
	void setCompact(boolean compact);

	/**
	 * <p>isReversed.</p>
	 *
	 * @return a boolean.
	 */
	boolean isReversed();

	/**
	 * <p>setReversed.</p>
	 *
	 * @param reversed a boolean.
	 */
	void setReversed(boolean reversed);

	/**
	 * The starting number.
	 *
	 * @return a int.
	 */
	int getStart();

	/**
	 * <p>setStart.</p>
	 *
	 * @param start a int.
	 */
	void setStart(int start);

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * <p>setType.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

	/**
	 * <p>getCompact.</p>
	 *
	 * @return a boolean.
	 */
	boolean getCompact();

}
