/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.lobobrowser.util.EventDispatch2;
import org.lobobrowser.util.RemovalDispatch;
import org.lobobrowser.util.RemovalEvent;
import org.lobobrowser.util.RemovalListener;

/**
 * A cache with least-recently-used policy. Note that this class is not thread
 * safe by itself.
 */
public class LRUCache implements java.io.Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 940427225784212823L;
    /** The approx max size. */
    private int approxMaxSize;
    /** The cache map. */
    private final Map<Object, OrderedValue> cacheMap = new HashMap<Object, OrderedValue>();
    /** The removal event. */
    private volatile transient EventDispatch2 removalEvent;
    /**
     * Ascending timestamp order. First is least recently used.
     */
    private final TreeSet<OrderedValue> timedSet = new TreeSet<OrderedValue>();
    /** The current size. */
    private int currentSize = 0;
    
    /**
     * Instantiates a new LRU cache.
     *
     * @param approxMaxSize
     *            the approx max size
     */
    public LRUCache(int approxMaxSize) {
        this.approxMaxSize = approxMaxSize;
        this.removalEvent = new RemovalDispatch();
    }
    
    /**
     * Read object.
     *
     * @param in
     *            the in
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // Need to initialize transient fields here.
        this.removalEvent = new RemovalDispatch();
    }
    
    /** Gets the approx max size.
	 *
	 * @return the approx max size
	 */
    public int getApproxMaxSize() {
        return approxMaxSize;
    }
    
    /** Sets the approx max size.
	 *
	 * @param approxMaxSize
	 *            the new approx max size
	 */
    public void setApproxMaxSize(int approxMaxSize) {
        this.approxMaxSize = approxMaxSize;
    }
    
    /**
     * Put.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @param approxSize
     *            the approx size
     */
    public void put(Object key, Object value, int approxSize) {
        if (approxSize > this.approxMaxSize) {
            // Can't be inserted.
            return;
        }
        OrderedValue ordVal = this.cacheMap.get(key);
        if (ordVal != null) {
            if (ordVal.getValue() != value) {
                this.removalEvent
                        .fireEvent(new RemovalEvent(this, ordVal.getValue()));
            }
            this.currentSize += (approxSize - ordVal.getApproximateSize());
            this.timedSet.remove(ordVal);
            ordVal.setApproximateSize(approxSize);
            ordVal.setValue(value);
            ordVal.touch();
            this.timedSet.add(ordVal);
        } else {
            ordVal = new OrderedValue(key, value, approxSize);
            this.cacheMap.put(key, ordVal);
            this.timedSet.add(ordVal);
            this.currentSize += approxSize;
        }
        while (this.currentSize > this.approxMaxSize) {
            this.removeLRU();
        }
    }
    
    /**
     * Removes the lru.
     */
    private void removeLRU() {
        if (this.timedSet != null && this.timedSet.size() > 0) {
            OrderedValue ordVal = this.timedSet.first();
            if (ordVal != null) {
                this.removalEvent
                        .fireEvent(new RemovalEvent(this, ordVal.getValue()));
                if (this.timedSet.remove(ordVal)) {
                    this.cacheMap.remove(ordVal.getKey());
                    this.currentSize -= ordVal.getApproximateSize();
                } else {
                    throw new IllegalStateException(
                            "Could not remove existing tree node.");
                }
            } else {
                throw new IllegalStateException(
                        "Cannot remove LRU since the cache is empty.");
            }
        }
    }
    
    /**
     * Gets the.
     *
     * @param key
     *            the key
     * @return the object
     */
    public Object get(Object key) {
        OrderedValue ordVal = this.cacheMap.get(key);
        if (ordVal != null) {
            this.timedSet.remove(ordVal);
            ordVal.touch();
            this.timedSet.add(ordVal);
            return ordVal.getValue();
        } else {
            return null;
        }
    }
    
    /**
     * Removes the.
     *
     * @param key
     *            the key
     * @return the object
     */
    public Object remove(Object key) {
        OrderedValue ordVal = this.cacheMap.get(key);
        if (ordVal != null) {
            this.removalEvent
                    .fireEvent(new RemovalEvent(this, ordVal.getValue()));
            this.currentSize -= ordVal.getApproximateSize();
            this.timedSet.remove(ordVal);
            return ordVal.getValue();
        } else {
            return null;
        }
    }
    
    /**
     * Adds the removal listener.
     *
     * @param listener
     *            the listener
     */
    public void addRemovalListener(RemovalListener listener) {
        this.removalEvent.addListener(listener);
    }
    
    /**
     * Removes the removal listener.
     *
     * @param listener
     *            the listener
     */
    public void removeRemovalListener(RemovalListener listener) {
        this.removalEvent.removeListener(listener);
    }
    
    /** Gets the approx size.
	 *
	 * @return the approx size
	 */
    public int getApproxSize() {
        return this.currentSize;
    }
    
    /** Gets the num entries.
	 *
	 * @return the num entries
	 */
    public int getNumEntries() {
        return this.cacheMap.size();
    }
    
    /** Gets the entry info list.
	 *
	 * @return the entry info list
	 */
    public List<EntryInfo> getEntryInfoList() {
        List<EntryInfo> list = new ArrayList<EntryInfo>();
        Iterator<OrderedValue> i = this.cacheMap.values().iterator();
        while (i.hasNext()) {
            OrderedValue ov = i.next();
            Object value = ov.getValue();
            Class<?> vc = value == null ? null : value.getClass();
            list.add(new EntryInfo(vc, ov.getApproximateSize()));
        }
        return list;
    }
}
