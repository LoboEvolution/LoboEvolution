/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;

import org.lobobrowser.html.js.Window;
import org.w3c.dom.Document;

/**
 * The Interface HTMLFrameElement.
 */
public interface HTMLFrameElement extends HTMLElement {

	/**
	 * Gets the frame border.
	 *
	 * @return the frame border
	 */
	// HTMLFrameElement
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
	 * Gets the no resize.
	 *
	 * @return the no resize
	 */
	public boolean getNoResize();

	/**
	 * Sets the no resize.
	 *
	 * @param noResize
	 *            the new no resize
	 */
	public void setNoResize(boolean noResize);

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

	/**
	 * Gets the src.
	 *
	 * @return the src
	 */
	public String getSrc();

	/**
	 * Sets the src.
	 *
	 * @param src
	 *            the new src
	 */
	public void setSrc(String src);

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
}
