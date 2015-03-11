/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.w3c;

import org.w3c.dom.Node;
import org.w3c.dom.ranges.Range;


/**
 * The Interface Selection.
 */
public interface Selection {
	// Selection
	/**
	 * Gets the anchor node.
	 *
	 * @return the anchor node
	 */
	public Node getAnchorNode();

	/**
	 * Gets the anchor offset.
	 *
	 * @return the anchor offset
	 */
	public int getAnchorOffset();

	/**
	 * Gets the focus node.
	 *
	 * @return the focus node
	 */
	public Node getFocusNode();

	/**
	 * Gets the focus offset.
	 *
	 * @return the focus offset
	 */
	public int getFocusOffset();

	/**
	 * Gets the checks if is collapsed.
	 *
	 * @return the checks if is collapsed
	 */
	public boolean getIsCollapsed();

	/**
	 * Collapse.
	 *
	 * @param parentNode the parent node
	 * @param offset the offset
	 */
	public void collapse(Node parentNode, int offset);

	/**
	 * Collapse to start.
	 */
	public void collapseToStart();

	/**
	 * Collapse to end.
	 */
	public void collapseToEnd();

	/**
	 * Select all children.
	 *
	 * @param parentNode the parent node
	 */
	public void selectAllChildren(Node parentNode);

	/**
	 * Delete from document.
	 */
	public void deleteFromDocument();

	/**
	 * Gets the range count.
	 *
	 * @return the range count
	 */
	public int getRangeCount();

	/**
	 * Gets the range at.
	 *
	 * @param index the index
	 * @return the range at
	 */
	public Range getRangeAt(int index);

	/**
	 * Adds the range.
	 *
	 * @param range the range
	 */
	public void addRange(Range range);

	/**
	 * Removes the range.
	 *
	 * @param range the range
	 */
	public void removeRange(Range range);

	/**
	 * Removes the all ranges.
	 */
	public void removeAllRanges();

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString();
}
