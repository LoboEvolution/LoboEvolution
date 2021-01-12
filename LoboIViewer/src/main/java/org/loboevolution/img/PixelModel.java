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

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * Stores the x and y coordinate of a pixel. Either both coordinate is positive
 * or zero, indicating a valid pixel, or both are -1, indicating no pixel is
 * selected or should be shown.
 *
 * @author Kazo Csaba
 * @version $Id: $Id
 */
public class PixelModel {

	private final EventListenerList listenerList = new EventListenerList();
	private int x = -1, y = -1;

	/**
	 * Adds a {@code ChangeListener} to the model.
	 *
	 * @param listener
	 *            the listener to add
	 */
	public void addChangeListener(ChangeListener listener) {
		listenerList.add(ChangeListener.class, listener);
	}

	/**
	 * Removes a {@code ChangeListener} from the model.
	 *
	 * @param listener
	 *            the listener to remove
	 */
	public void removeChangeListener(ChangeListener listener) {
		listenerList.remove(ChangeListener.class, listener);
	}

	/**
	 * Notifies the registered listeners that the model has changed.
	 */
	protected void fireChange() {
		Object[] listeners = listenerList.getListenerList();
		ChangeEvent event = new ChangeEvent(this);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				((ChangeListener) listeners[i + 1]).stateChanged(event);
			}
		}
	}

	/**
	 * Changes the pixel stored in this model. If either coordinate is negative, the
	 * new value of the model will be (-1, -1).
	 *
	 * @param x
	 *            the new x coordinate
	 * @param y
	 *            the new y coordinate
	 */
	public void set(int x, int y) {
		if (x < 0 || y < 0) {
			x = -1;
			y = -1;
		}
		if (this.x != x || this.y != y) {
			this.x = x;
			this.y = y;
			fireChange();
		}
	}

	/**
	 * Returns the current x coordinate.
	 *
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the current y coordinate.
	 *
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns whether the pixel is marked as invalid by setting one of the
	 * coordinates to -1.
	 *
	 * @return {@code true} if the model does not have a valid pixel
	 */
	public boolean isInvalid() {
		return x < 0;
	}
}
