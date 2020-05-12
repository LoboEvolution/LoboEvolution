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

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html.renderer;

interface FloatingBounds {
	/**
	 * The Y at which the float clears starting at the given Y.
	 *
	 * @param y a int.
	 * @return a int.
	 */
	int getClearY(int y);

	/**
	 * The Y at which the first float clears. It does not recurse to check other
	 * floats.
	 *
	 * @param y a int.
	 * @return a int.
	 */
	int getFirstClearY(int y);

	/**
	 * The offset from the left at the given Y, not counting insets.
	 *
	 * @param y a int.
	 * @return a int.
	 */
	int getLeft(int y);

	/**
	 * <p>getLeftClearY.</p>
	 *
	 * @param y a int.
	 * @return a int.
	 */
	int getLeftClearY(int y);

	/**
	 * <p>getMaxY.</p>
	 *
	 * @return a int.
	 */
	int getMaxY();

	/**
	 * The offset from the right at the given Y, not counting insets.
	 *
	 * @param y a int.
	 * @return a int.
	 */
	int getRight(int y);

	/**
	 * <p>getRightClearY.</p>
	 *
	 * @param y a int.
	 * @return a int.
	 */
	int getRightClearY(int y);
}
