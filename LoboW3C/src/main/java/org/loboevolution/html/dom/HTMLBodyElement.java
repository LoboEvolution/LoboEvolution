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

import org.loboevolution.html.node.js.WindowEventHandlers;

/**
 * Provides special properties (beyond those inherited from the regular
 * HTMLElement interface) for manipulating &lt;body&gt; elements.
 *
 *
 *
 */
public interface HTMLBodyElement extends HTMLElement, WindowEventHandlers {

	/**
	 * <p>getALink.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getALink();

	/**
	 * <p>setALink.</p>
	 *
	 * @param aLink a {@link java.lang.String} object.
	 */
	void setALink(String aLink);

	/**
	 * <p>getBackground.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getBackground();

	/**
	 * <p>setBackground.</p>
	 *
	 * @param background a {@link java.lang.String} object.
	 */
	void setBackground(String background);

	/**
	 * <p>getBgColor.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getBgColor();

	/**
	 * <p>setBgColor.</p>
	 *
	 * @param bgColor a {@link java.lang.String} object.
	 */
	void setBgColor(String bgColor);

	/**
	 * <p>getBgProperties.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBgProperties();

	/**
	 * <p>setBgProperties.</p>
	 *
	 * @param bgProperties a {@link java.lang.String} object.
	 */
	void setBgProperties(String bgProperties);

	/**
	 * <p>getLink.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getLink();

	/**
	 * <p>setLink.</p>
	 *
	 * @param link a {@link java.lang.String} object.
	 */
	void setLink(String link);

	/**
	 * <p>isNoWrap.</p>
	 *
	 * @return a boolean.
	 */
	@Deprecated
	boolean isNoWrap();

	/**
	 * <p>setNoWrap.</p>
	 *
	 * @param noWrap a boolean.
	 */
	void setNoWrap(boolean noWrap);

	/**
	 * <p>getText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getText();

	/**
	 * <p>setText.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	void setText(String text);

	/**
	 * <p>getVLink.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getVLink();

	/**
	 * <p>setVLink.</p>
	 *
	 * @param vLink a {@link java.lang.String} object.
	 */
	void setVLink(String vLink);

}
