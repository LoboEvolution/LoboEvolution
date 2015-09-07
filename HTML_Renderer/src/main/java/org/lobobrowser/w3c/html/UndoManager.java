/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General License for more details. You should have received a copy of
 * the GNU Lesser General License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.w3c.html;

/**
 * The public interface UndoManager.
 */
public interface UndoManager {
	// UndoManager
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	int getLength();

	/**
	 * Item.
	 *
	 * @param index
	 *            the index
	 * @return the object
	 */
	Object item(int index);

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	int getPosition();

	/**
	 * Adds the.
	 *
	 * @param data
	 *            the data
	 * @param title
	 *            the title
	 * @return the int
	 */
	int add(Object data, String title);

	/**
	 * Removes the.
	 *
	 * @param index
	 *            the index
	 */
	void remove(int index);

	/**
	 * Clear undo.
	 */
	void clearUndo();

	/**
	 * Clear redo.
	 */
	void clearRedo();
}
