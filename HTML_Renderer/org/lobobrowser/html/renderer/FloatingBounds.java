/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.renderer;


/**
 * The Interface FloatingBounds.
 */
interface FloatingBounds {
	
	/**
	 * The offset from the left at the given Y, not counting insets.
	 *
	 * @param y the y
	 * @return the left
	 */
	public int getLeft(int y);

	/**
	 * The offset from the right at the given Y, not counting insets.
	 *
	 * @param y the y
	 * @return the right
	 */
	public int getRight(int y);

	/**
	 * The Y at which the float clears starting at the given Y.
	 *
	 * @param y the y
	 * @return the clear y
	 */
	public int getClearY(int y);

	/**
	 * The Y at which the first float clears. It does not recurse to check other
	 * floats.
	 *
	 * @param y the y
	 * @return the first clear y
	 */
	public int getFirstClearY(int y);

	/**
	 * Gets the left clear y.
	 *
	 * @param y the y
	 * @return the left clear y
	 */
	public int getLeftClearY(int y);

	/**
	 * Gets the right clear y.
	 *
	 * @param y the y
	 * @return the right clear y
	 */
	public int getRightClearY(int y);

	/**
	 * Gets the max y.
	 *
	 * @return the max y
	 */
	public int getMaxY();
}
