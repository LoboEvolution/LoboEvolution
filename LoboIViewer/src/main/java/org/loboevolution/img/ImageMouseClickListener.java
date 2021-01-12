/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.img;

import java.util.EventListener;

/**
 * Interface for receiving mouse click events on an image.
 *
 * @author csaba
 * @version $Id: $Id
 */
public interface ImageMouseClickListener extends EventListener {
	/**
	 * Invoked when a mouse button has been clicked over a pixel of an image.
	 *
	 * @param e
	 *            the event object containing attributes of the event
	 */
    void mouseClicked(ImageMouseEvent e);
}
