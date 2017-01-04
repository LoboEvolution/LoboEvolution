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

import org.w3c.dom.Node;
import org.w3c.dom.ranges.Range;


/**
 * The public interface Selection.
 */
public interface Selection {
	// Selection
	/**
	 * Gets the anchor node.
	 *
	 * @return the anchor node
	 */
	Node getAnchorNode();

	/**
	 * Gets the anchor offset.
	 *
	 * @return the anchor offset
	 */
	int getAnchorOffset();

	/**
	 * Gets the focus node.
	 *
	 * @return the focus node
	 */
	Node getFocusNode();

	/**
	 * Gets the focus offset.
	 *
	 * @return the focus offset
	 */
	int getFocusOffset();

	/**
	 * Gets the checks if is collapsed.
	 *
	 * @return the checks if is collapsed
	 */
	boolean getIsCollapsed();

	/**
	 * Collapse.
	 *
	 * @param parentNode
	 *            the parent node
	 * @param offset
	 *            the offset
	 */
	void collapse(Node parentNode, int offset);

	/**
	 * Collapse to start.
	 */
	void collapseToStart();

	/**
	 * Collapse to end.
	 */
	void collapseToEnd();

	/**
	 * Select all children.
	 *
	 * @param parentNode
	 *            the parent node
	 */
	void selectAllChildren(Node parentNode);

	/**
	 * Delete from document.
	 */
	void deleteFromDocument();

	/**
	 * Gets the range count.
	 *
	 * @return the range count
	 */
	int getRangeCount();

	/**
	 * Gets the range at.
	 *
	 * @param index
	 *            the index
	 * @return the range at
	 */
	Range getRangeAt(int index);

	/**
	 * Adds the range.
	 *
	 * @param range
	 *            the range
	 */
	void addRange(Range range);

	/**
	 * Removes the range.
	 *
	 * @param range
	 *            the range
	 */
	void removeRange(Range range);

	/**
	 * Removes the all ranges.
	 */
	void removeAllRanges();

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	String toString();
}
