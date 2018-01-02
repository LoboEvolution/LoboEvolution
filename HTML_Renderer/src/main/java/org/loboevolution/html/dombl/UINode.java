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
/*
 * Created on Jan 14, 2006
 */
package org.loboevolution.html.dombl;

import java.awt.Rectangle;

/**
 * A UI node abstraction that is used to send notifications back to the UI and
 * to obtain information the DOM needs from the UI (such as image dimensions).
 */
public interface UINode {

	/**
	 * Called.
	 *
	 * @param modelNode
	 *            the model node
	 */
	void repaint(ModelNode modelNode);

	/**
	 * Gets the bounds.
	 *
	 * @return the bounds
	 */
	Rectangle getBounds();

	/**
	 * Gets the bounds relative to block.
	 *
	 * @return the bounds relative to block
	 */
	Rectangle getBoundsRelativeToBlock();

	/**
	 * Focus.
	 */
	void focus();

	/**
	 * Blur.
	 */
	void blur();
}
