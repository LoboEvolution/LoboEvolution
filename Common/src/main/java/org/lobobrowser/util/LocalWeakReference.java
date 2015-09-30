/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * The Class LocalWeakReference.
 */
public class LocalWeakReference extends WeakReference {
    /** The key. */
    private final Object key;
    
    /**
     * Instantiates a new local weak reference.
     *
     * @param key
     *            the key
     * @param target
     *            the target
     * @param queue
     *            the queue
     */
    public LocalWeakReference(Object key, Object target, ReferenceQueue queue) {
        super(target, queue);
        this.key = key;
    }
    
    /** Gets the key.
	 *
	 * @return the key
	 */
    public Object getKey() {
        return key;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        Object target1 = this.get();
        Object target2 = other instanceof LocalWeakReference
                ? ((LocalWeakReference) other).get() : null;
        return Objects.equals(target1, target2);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        Object target = this.get();
        return target == null ? 0 : target.hashCode();
    }
}
