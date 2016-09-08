/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
 * Created on Oct 8, 2005
 */
package org.lobobrowser.util;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The Class WeakValueHashMap.
 */
public class WeakValueHashMap implements Map<Object, Object> {
    /** The map. */
    private final Map<Object, Object> map = new HashMap<Object, Object>();
    /** The queue. */
    private final ReferenceQueue<?> queue = new ReferenceQueue<Object>();
    
    /**
     * Instantiates a new weak value hash map.
     */
    public WeakValueHashMap() {
        super();
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#size()
     */
    @Override
    public int size() {
        return this.map.size();
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        WeakReference<?> wf = (WeakReference<?>) this.map.get(key);
        return (wf != null) && (wf.get() != null);
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#get(java.lang.Object)
     */
    @Override
    public Object get(Object key) {
        this.checkQueue();
        WeakReference<?> wf = (WeakReference<?>) this.map.get(key);
        return wf == null ? null : wf.get();
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public Object put(Object key, Object value) {
        this.checkQueue();
        return this.putImpl(key, value);
    }
    
    /**
     * Put impl.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @return the object
     */
    private final Object putImpl(Object key, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("null values not accepted");
        }
        Reference<?> ref = new LocalWeakReference(key, value, this.queue);
        WeakReference<?> oldWf = (WeakReference<?>) this.map.put(key, ref);
        return oldWf == null ? null : oldWf.get();
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#remove(java.lang.Object)
     */
    @Override
    public Object remove(Object key) {
        this.checkQueue();
        WeakReference<?> wf = (WeakReference<?>) this.map.remove(key);
        return wf == null ? null : wf.get();
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map<?, ?> t) {
        this.checkQueue();
        Iterator<?> i = t.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();
            this.putImpl(entry.getKey(), entry.getValue());
        }
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#clear()
     */
    @Override
    public void clear() {
        this.checkQueue();
        this.map.clear();
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#keySet()
     */
    @Override
    public Set<Object> keySet() {
        return this.map.keySet();
    }
    
    /**
     * Check queue.
     */
    private final void checkQueue() {
        ReferenceQueue<?> queue = this.queue;
        LocalWeakReference ref;
        while ((ref = (LocalWeakReference) queue.poll()) != null) {
            this.map.remove(ref.getKey());
        }
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#values()
     */
    @Override
    public Collection<Object> values() {
        return new FilteredCollection(this.map.values(), new LocalFilter());
    }
    
    /*
     * (non-Javadoc)
     * @see java.util.Map#entrySet()
     */
    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
