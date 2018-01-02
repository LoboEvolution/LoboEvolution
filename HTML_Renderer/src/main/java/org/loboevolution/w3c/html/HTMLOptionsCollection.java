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

import org.w3c.dom.Node;

/**
 * The Interface HTMLOptionsCollection.
 */
public interface HTMLOptionsCollection extends HTMLCollection {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLCollection#getLength()
	 */
	// HTMLOptionsCollection
	@Override
	public int getLength();

	/**
	 * Sets the length.
	 *
	 * @param length
	 *            the new length
	 */
	public void setLength(int length);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLCollection#namedItem(java.lang.String)
	 */
	@Override
	public Node namedItem(String name);

	/**
	 * Adds the.
	 *
	 * @param element
	 *            the element
	 */
	public void add(HTMLElement element);

	/**
	 * Adds the.
	 *
	 * @param element
	 *            the element
	 * @param before
	 *            the before
	 */
	public void add(HTMLElement element, HTMLElement before);

	/**
	 * Adds the.
	 *
	 * @param element
	 *            the element
	 * @param before
	 *            the before
	 */
	public void add(HTMLElement element, int before);

	/**
	 * Removes the.
	 *
	 * @param index
	 *            the index
	 */
	public void remove(int index);

	/**
	 * Gets the selected index.
	 *
	 * @return the selected index
	 */
	public int getSelectedIndex();

	/**
	 * Sets the selected index.
	 *
	 * @param selectedIndex
	 *            the new selected index
	 */
	public void setSelectedIndex(int selectedIndex);
}
