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
 * Provides special properties (beyond the HTMLElement interface it also has
 * available to it inheritance) for manipulating single or grouped table column
 * elements.
 *
 *
 *
 */
public interface HTMLTableColElement extends HTMLElement {

	/**
	 * Sets or retrieves the alignment of the object relative to the display or
	 * table.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getAlign();

	/**
	 * <p>setAlign.</p>
	 *
	 * @param align a {@link java.lang.String} object.
	 */
	void setAlign(String align);

	/**
	 * <p>getCh.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getCh();

	/**
	 * <p>setCh.</p>
	 *
	 * @param ch a {@link java.lang.String} object.
	 */
	void setCh(String ch);

	/**
	 * <p>getChOff.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getChOff();

	/**
	 * <p>setChOff.</p>
	 *
	 * @param chOff a {@link java.lang.String} object.
	 */
	void setChOff(String chOff);

	/**
	 * Sets or retrieves the number of columns in the group.
	 *
	 * @return a int.
	 */
	int getSpan();

	/**
	 * <p>setSpan.</p>
	 *
	 * @param span a int.
	 */
	void setSpan(int span);

	/**
	 * <p>getvAlign.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getvAlign();

	/**
	 * <p>setvAlign.</p>
	 *
	 * @param vAlign a {@link java.lang.String} object.
	 */
	void setvAlign(String vAlign);

	/**
	 * Sets or retrieves the width of the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getWidth();

	/**
	 * <p>setWidth.</p>
	 *
	 * @param width a {@link java.lang.String} object.
	 */
	void setWidth(String width);

}
