/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
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
