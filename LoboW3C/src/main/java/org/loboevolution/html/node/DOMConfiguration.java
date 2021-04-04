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

/**
 * <p>DOMConfiguration interface.</p>
 *
 *
 *
 */
public interface DOMConfiguration {

	/**
	 * <p>canSetParameter.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.Object} object.
	 * @return a boolean.
	 */
	boolean canSetParameter(String name, Object value);

	/**
	 * <p>getParameter.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.lang.Object} object.
	 */
	Object getParameter(String name);

	/**
	 * <p>getParameterNames.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.DOMStringList} object.
	 */
	DOMStringList getParameterNames();

	/**
	 * <p>setParameter.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.Object} object.
	 */
	public void setParameter(String name, Object value);
}
