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
 * The Interface HTMLOListElement.
 */
public interface HTMLOListElement extends HTMLElement {
	
	/**
	 * Gets the reversed.
	 *
	 * @return the reversed
	 */
	// HTMLOListElement
	public boolean getReversed();

	/**
	 * Sets the reversed.
	 *
	 * @param reversed
	 *            the new reversed
	 */
	public void setReversed(boolean reversed);

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public int getStart();

	/**
	 * Sets the start.
	 *
	 * @param start
	 *            the new start
	 */
	public void setStart(int start);

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type);

	/**
	 * Gets the compact.
	 *
	 * @return the compact
	 */
	// HTMLOListElement-24
	public boolean getCompact();

	/**
	 * Sets the compact.
	 *
	 * @param compact
	 *            the new compact
	 */
	public void setCompact(boolean compact);
}
