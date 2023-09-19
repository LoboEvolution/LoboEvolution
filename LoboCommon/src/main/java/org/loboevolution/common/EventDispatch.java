/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package org.loboevolution.common;

import java.util.Collection;
import java.util.EventObject;
import java.util.LinkedList;

/**
 * <p>EventDispatch class.</p>
 *
 * Author J. H. S.
 *
 */
public class EventDispatch {
	private Collection<GenericEventListener> listeners;

	/**
	 * <p>Constructor for EventDispatch.</p>
	 */
	public EventDispatch() {
	}

	/**
	 * <p>addListener.</p>
	 *
	 * @param listener a {@link org.loboevolution.common.GenericEventListener} object.
	 */
	public final void addListener(GenericEventListener listener) {
		synchronized (this) {
			if (this.listeners == null) {
				this.listeners = createListenerCollection();
			}
			this.listeners.add(listener);
		}
	}

	/**
	 * <p>createListenerCollection.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<GenericEventListener> createListenerCollection() {
		return new LinkedList<>();
	}

	/**
	 * <p>fireEvent.</p>
	 *
	 * @param event a {@link java.util.EventObject} object.
	 */
	public final void fireEvent(EventObject event) {
		GenericEventListener[] larray = null;
		synchronized (this) {
			if (this.listeners != null) {
				larray = this.listeners.toArray(GenericEventListener.EMPTY_ARRAY);
			}
		}
		if (larray != null) {
			for (final GenericEventListener element : larray) {
				// Call holding no locks
				element.processEvent(event);
			}
		}
	}

	/**
	 * <p>removeListener.</p>
	 *
	 * @param listener a {@link org.loboevolution.common.GenericEventListener} object.
	 */
	public final void removeListener(GenericEventListener listener) {
		synchronized (this) {
			if (this.listeners != null) {
				this.listeners.remove(listener);
			}
		}
	}
}
