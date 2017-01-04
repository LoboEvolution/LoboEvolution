/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Mar 19, 2005
 */
package org.lobobrowser.util;

import java.util.Collection;
import java.util.EventObject;
import java.util.LinkedList;

/**
 * The Class EventDispatch.
 *
 * @author J. H. S.
 */
public class EventDispatch {
    /** The listeners. */
    private Collection listeners;
    
    /**
     * Instantiates a new event dispatch.
     */
    public EventDispatch() {
    }
    
    /**
     * Creates the listener collection.
     *
     * @return the collection
     */
    public Collection createListenerCollection() {
        return new LinkedList();
    }
    
    /**
     * Adds the listener.
     *
     * @param listener
     *            the listener
     */
    public final void addListener(GenericEventListener listener) {
        synchronized (this) {
            if (this.listeners == null) {
                this.listeners = this.createListenerCollection();
            }
            this.listeners.add(listener);
        }
    }
    
    /**
     * Removes the listener.
     *
     * @param listener
     *            the listener
     */
    public final void removeListener(GenericEventListener listener) {
        synchronized (this) {
            if (this.listeners != null) {
                this.listeners.remove(listener);
            }
        }
    }
    
    /**
     * Fire event.
     *
     * @param event
     *            the event
     */
    public final void fireEvent(EventObject event) {
        GenericEventListener[] larray = null;
        synchronized (this) {
            if (this.listeners != null) {
                larray = (GenericEventListener[]) this.listeners
                        .toArray(GenericEventListener.EMPTY_ARRAY);
            }
        }
        if (larray != null) {
            for (int i = 0; i < larray.length; i++) {
                // Call holding no locks
                larray[i].processEvent(event);
            }
        }
    }
}
