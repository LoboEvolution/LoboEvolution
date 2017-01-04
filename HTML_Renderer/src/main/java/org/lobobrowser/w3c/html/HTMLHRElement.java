/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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


/**
 * The Interface HTMLHRElement.
 */
public interface HTMLHRElement extends HTMLElement {
	// HTMLHRElement
	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	// HTMLHRElement-13
	public String getAlign();

	/**
	 * Sets the align.
	 *
	 * @param align
	 *            the new align
	 */
	public void setAlign(String align);

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor();

	/**
	 * Sets the color.
	 *
	 * @param color
	 *            the new color
	 */
	public void setColor(String color);

	/**
	 * Gets the no shade.
	 *
	 * @return the no shade
	 */
	public boolean getNoShade();

	/**
	 * Sets the no shade.
	 *
	 * @param noShade
	 *            the new no shade
	 */
	public void setNoShade(boolean noShade);

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public String getSize();

	/**
	 * Sets the size.
	 *
	 * @param size
	 *            the new size
	 */
	public void setSize(String size);

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
}
