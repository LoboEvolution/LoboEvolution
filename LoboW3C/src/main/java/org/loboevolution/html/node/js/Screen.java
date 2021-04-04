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

package org.loboevolution.html.node.js;

/**
 * A screen, usually the one on which the current window is being rendered, and
 * is obtained using window.screen.
 *
 *
 *
 */
public interface Screen {

	/**
	 * <p>getAvailHeight.</p>
	 *
	 * @return a int.
	 */
	int getAvailHeight();

	/**
	 * <p>getAvailWidth.</p>
	 *
	 * @return a int.
	 */
	int getAvailWidth();

	/**
	 * <p>getColorDepth.</p>
	 *
	 * @return a int.
	 */
	int getColorDepth();

	/**
	 * <p>getHeight.</p>
	 *
	 * @return a int.
	 */
	int getHeight();

	/**
	 * <p>getPixelDepth.</p>
	 *
	 * @return a int.
	 */
	int getPixelDepth();

	/**
	 * <p>getWidth.</p>
	 *
	 * @return a int.
	 */
	int getWidth();

}
