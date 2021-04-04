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
