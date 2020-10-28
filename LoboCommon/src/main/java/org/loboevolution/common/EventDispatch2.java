/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
/*
 * Created on Mar 19, 2005
 */
package org.loboevolution.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;

/**
 * <p>Abstract EventDispatch2 class.</p>
 *
 * @author J. H. S.
 * @version $Id: $Id
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
		return new ArrayList<EventListener>();
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
			larray = (EventListener[]) this.listeners.toArray(EMPTY_ARRAY);
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
