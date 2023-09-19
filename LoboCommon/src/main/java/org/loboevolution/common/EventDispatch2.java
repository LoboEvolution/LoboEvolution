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

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;

/**
 * <p>Abstract EventDispatch2 class.</p>
 *
 * Author J. H. S.
 *
 */
public abstract class EventDispatch2 {
	private static final EventListener[] EMPTY_ARRAY = new EventListener[0];
	private Collection<EventListener> listeners;

	/**
	 * <p>Constructor for EventDispatch2.</p>
	 */
	public EventDispatch2() {
	}

	/**
	 * <p>addListener.</p>
	 *
	 * @param listener a {@link java.util.EventListener} object.
	 */
	public final void addListener(EventListener listener) {
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
	public Collection<EventListener> createListenerCollection() {
		return new ArrayList<>();
	}

	/**
	 * <p>dispatchEvent.</p>
	 *
	 * @param listener a {@link java.util.EventListener} object.
	 * @param event a {@link java.util.EventObject} object.
	 */
	protected abstract void dispatchEvent(EventListener listener, EventObject event);

	/**
	 * <p>fireEvent.</p>
	 *
	 * @param event a {@link java.util.EventObject} object.
	 * @return a boolean.
	 */
	public final boolean fireEvent(EventObject event) {
		EventListener[] larray;
		synchronized (this) {
			final Collection<EventListener> listeners = this.listeners;
			if (listeners == null || listeners.size() == 0) {
				return false;
			}
			larray = this.listeners.toArray(EMPTY_ARRAY);
		}
		for (EventListener eventListener : larray) {
			dispatchEvent(eventListener, event);
		}
		return true;
	}

	/**
	 * <p>removeListener.</p>
	 *
	 * @param listener a {@link java.util.EventListener} object.
	 */
	public final void removeListener(EventListener listener) {
		synchronized (this) {
			if (this.listeners != null) {
				this.listeners.remove(listener);
			}
		}
	}
}
