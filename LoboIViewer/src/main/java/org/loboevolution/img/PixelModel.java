/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
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
 * Author Kazo Csaba
 *
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
	public void addChangeListener(final ChangeListener listener) {
		listenerList.add(ChangeListener.class, listener);
	}

	/**
	 * Removes a {@code ChangeListener} from the model.
	 *
	 * @param listener
	 *            the listener to remove
	 */
	public void removeChangeListener(final ChangeListener listener) {
		listenerList.remove(ChangeListener.class, listener);
	}

	/**
	 * Notifies the registered listeners that the model has changed.
	 */
	protected void fireChange() {
		final Object[] listeners = listenerList.getListenerList();
		final ChangeEvent event = new ChangeEvent(this);
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
	 * @param x the new x coordinate
	 * @param y the new y coordinate
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
