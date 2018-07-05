/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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

package org.loboevolution.w3c.html;

import org.loboevolution.html.js.object.Window;
import org.w3c.dom.Document;

/**
 * The Interface HTMLIFrameElement.
 */
public interface HTMLIFrameElement extends HTMLElement {

	/**
	 * Gets the src.
	 *
	 * @return the src
	 */
	// HTMLIFrameElement
	public String getSrc();

	/**
	 * Sets the src.
	 *
	 * @param src
	 *            the new src
	 */
	public void setSrc(String src);

	/**
	 * Gets the srcdoc.
	 *
	 * @return the srcdoc
	 */
	public String getSrcdoc();

	/**
	 * Sets the srcdoc.
	 *
	 * @param srcdoc
	 *            the new srcdoc
	 */
	public void setSrcdoc(String srcdoc);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name);

	/**
	 * Gets the sandbox.
	 *
	 * @return the sandbox
	 */
	public DOMSettableTokenList getSandbox();

	/**
	 * Sets the sandbox.
	 *
	 * @param sandbox
	 *            the new sandbox
	 */
	public void setSandbox(String sandbox);

	/**
	 * Gets the seamless.
	 *
	 * @return the seamless
	 */
	public boolean getSeamless();

	/**
	 * Sets the seamless.
	 *
	 * @param seamless
	 *            the new seamless
	 */
	public void setSeamless(boolean seamless);

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public String getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(String width);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public String getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(String height);

	/**
	 * Gets the content document.
	 *
	 * @return the content document
	 */
	public Document getContentDocument();

	/**
	 * Gets the content window.
	 *
	 * @return the content window
	 */
	public Window getContentWindow();

	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	// HTMLIFrameElement-15
	public String getAlign();

	/**
	 * Sets the align.
	 *
	 * @param align
	 *            the new align
	 */
	public void setAlign(String align);

	/**
	 * Gets the frame border.
	 *
	 * @return the frame border
	 */
	public String getFrameBorder();

	/**
	 * Sets the frame border.
	 *
	 * @param frameBorder
	 *            the new frame border
	 */
	public void setFrameBorder(String frameBorder);

	/**
	 * Gets the long desc.
	 *
	 * @return the long desc
	 */
	public String getLongDesc();

	/**
	 * Sets the long desc.
	 *
	 * @param longDesc
	 *            the new long desc
	 */
	public void setLongDesc(String longDesc);

	/**
	 * Gets the margin height.
	 *
	 * @return the margin height
	 */
	public String getMarginHeight();

	/**
	 * Sets the margin height.
	 *
	 * @param marginHeight
	 *            the new margin height
	 */
	public void setMarginHeight(String marginHeight);

	/**
	 * Gets the margin width.
	 *
	 * @return the margin width
	 */
	public String getMarginWidth();

	/**
	 * Sets the margin width.
	 *
	 * @param marginWidth
	 *            the new margin width
	 */
	public void setMarginWidth(String marginWidth);

	/**
	 * Gets the scrolling.
	 *
	 * @return the scrolling
	 */
	public String getScrolling();

	/**
	 * Sets the scrolling.
	 *
	 * @param scrolling
	 *            the new scrolling
	 */
	public void setScrolling(String scrolling);
}
