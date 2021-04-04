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

import org.loboevolution.html.node.DOMTokenList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.js.WindowProxy;

/**
 * Provides special properties and methods (beyond those of the HTMLElement
 * interface it also has available to it by inheritance) for manipulating the
 * layout and presentation of inline frame elements.
 *
 *
 *
 */
public interface HTMLIFrameElement extends HTMLElement {

	/**
	 * Sets or retrieves how the object is aligned with adjacent text.
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
	 * <p>getAllow.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAllow();

	/**
	 * <p>setAllow.</p>
	 *
	 * @param allow a {@link java.lang.String} object.
	 */
	void setAllow(String allow);

	/**
	 * <p>isAllowFullscreen.</p>
	 *
	 * @return a boolean.
	 */
	boolean isAllowFullscreen();

	/**
	 * <p>setAllowFullscreen.</p>
	 *
	 * @param allowFullscreen a boolean.
	 */
	void setAllowFullscreen(boolean allowFullscreen);

	/**
	 * <p>isAllowPaymentRequest.</p>
	 *
	 * @return a boolean.
	 */
	boolean isAllowPaymentRequest();

	/**
	 * <p>setAllowPaymentRequest.</p>
	 *
	 * @param allowPaymentRequest a boolean.
	 */
	void setAllowPaymentRequest(boolean allowPaymentRequest);

	/**
	 * Retrieves the document object of the page or frame.
	 *
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document getContentDocument();

	/**
	 * Retrieves the object of the specified.
	 *
	 * @return a {@link org.loboevolution.html.node.js.WindowProxy} object.
	 */
	WindowProxy getContentWindow();

	/**
	 * Sets or retrieves whether to display a border for the frame.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getFrameBorder();

	/**
	 * <p>setFrameBorder.</p>
	 *
	 * @param frameBorder a {@link java.lang.String} object.
	 */
	void setFrameBorder(String frameBorder);

	/**
	 * Sets or retrieves the height of the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHeight();

	/**
	 * <p>setHeight.</p>
	 *
	 * @param height a {@link java.lang.String} object.
	 */
	void setHeight(String height);

	/**
	 * Sets or retrieves a URI to a long description of the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getLongDesc();

	/**
	 * <p>setLongDesc.</p>
	 *
	 * @param longDesc a {@link java.lang.String} object.
	 */
	void setLongDesc(String longDesc);

	/**
	 * Sets or retrieves the top and bottom margin heights before displaying the
	 * text in a frame.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getMarginHeight();

	/**
	 * <p>setMarginHeight.</p>
	 *
	 * @param marginHeight a {@link java.lang.String} object.
	 */
	void setMarginHeight(String marginHeight);

	/**
	 * Sets or retrieves the left and right margin widths before displaying the text
	 * in a frame.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getMarginWidth();

	/**
	 * <p>setMarginWidth.</p>
	 *
	 * @param marginWidth a {@link java.lang.String} object.
	 */
	void setMarginWidth(String marginWidth);

	/**
	 * Sets or retrieves the frame name.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * <p>setName.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * <p>getSandbox.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.DOMTokenList} object.
	 */
	DOMTokenList getSandbox();

	/**
	 * Sets or retrieves whether the frame can be scrolled.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getScrolling();

	/**
	 * <p>setScrolling.</p>
	 *
	 * @param scrolling a {@link java.lang.String} object.
	 */
	void setScrolling(String scrolling);

	/**
	 * Sets or retrieves a URL to be loaded by the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getSrc();

	/**
	 * <p>setSrc.</p>
	 *
	 * @param src a {@link java.lang.String} object.
	 */
	void setSrc(String src);

	/**
	 * Sets or retrives the content of the page that is to contain.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getSrcdoc();

	/**
	 * <p>setSrcdoc.</p>
	 *
	 * @param srcdoc a {@link java.lang.String} object.
	 */
	void setSrcdoc(String srcdoc);

	/**
	 * Sets or retrieves the width of the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getWidth();

	/**
	 * <p>setWidth.</p>
	 *
	 * @param width a {@link java.lang.String} object.
	 */
	void setWidth(String width);

	/**
	 * <p>getSVGDocument.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document getSVGDocument();

}
