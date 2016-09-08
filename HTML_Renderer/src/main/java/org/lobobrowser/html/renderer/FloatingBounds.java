/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.html.renderer;

/**
 * The Interface FloatingBounds.
 */
public interface FloatingBounds {

	/**
	 * The offset from the left at the given Y, not counting insets.
	 *
	 * @param y
	 *            the y
	 * @return the left
	 */
	int getLeft(int y);

	/**
	 * The offset from the right at the given Y, not counting insets.
	 *
	 * @param y
	 *            the y
	 * @return the right
	 */
	int getRight(int y);

	/**
	 * The Y at which the float clears starting at the given Y.
	 *
	 * @param y
	 *            the y
	 * @return the clear y
	 */
	int getClearY(int y);

	/**
	 * The Y at which the first float clears. It does not recurse to check other
	 * floats.
	 *
	 * @param y
	 *            the y
	 * @return the first clear y
	 */
	int getFirstClearY(int y);

	/**
	 * Gets the left clear y.
	 *
	 * @param y
	 *            the y
	 * @return the left clear y
	 */
	int getLeftClearY(int y);

	/**
	 * Gets the right clear y.
	 *
	 * @param y
	 *            the y
	 * @return the right clear y
	 */
	int getRightClearY(int y);

	/**
	 * Gets the max y.
	 *
	 * @return the max y
	 */
	int getMaxY();
}
