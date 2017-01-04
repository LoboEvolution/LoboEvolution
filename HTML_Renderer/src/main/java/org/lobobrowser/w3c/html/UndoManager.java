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
