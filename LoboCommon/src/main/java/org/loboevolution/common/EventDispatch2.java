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
