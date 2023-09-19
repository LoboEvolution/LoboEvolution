/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
