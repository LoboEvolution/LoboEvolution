/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * A cache with least-recently-used policy. Note that this class is not thread
 * safe by itself.
 */
public class LRUCache implements java.io.Serializable {
	private static final long serialVersionUID = 940427225784212823L;
	private int approxMaxSize;

	private final Map<Object, OrderedValue> cacheMap = new HashMap<Object, OrderedValue>();
	private volatile transient EventDispatch2 removalEvent;

	/**
	 * Ascending timestamp order. First is least recently used.
	 */
	private final TreeSet<OrderedValue> timedSet = new TreeSet<OrderedValue>();
	private int currentSize = 0;

	public LRUCache(int approxMaxSize) {
		this.approxMaxSize = approxMaxSize;
		this.removalEvent = new RemovalDispatch();
	}

	private void readObject(ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		// Need to initialize transient fields here.
		this.removalEvent = new RemovalDispatch();
	}

	public int getApproxMaxSize() {
		return approxMaxSize;
	}

	public void setApproxMaxSize(int approxMaxSize) {
		this.approxMaxSize = approxMaxSize;
	}

	public void put(Object key, Object value, int approxSize) {
		if (approxSize > this.approxMaxSize) {
			// Can't be inserted.
			return;
		}
		OrderedValue ordVal = (OrderedValue) this.cacheMap.get(key);
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

	private void removeLRU() {
		OrderedValue ordVal = (OrderedValue) this.timedSet.first();
		if (ordVal != null) {
			this.removalEvent.fireEvent(new RemovalEvent(this, ordVal.getValue()));
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

	public Object get(Object key) {
		OrderedValue ordVal = (OrderedValue) this.cacheMap.get(key);
		if (ordVal != null) {
			this.timedSet.remove(ordVal);
			ordVal.touch();
			this.timedSet.add(ordVal);
			return ordVal.getValue();
		} else {
			return null;
		}
	}

	public Object remove(Object key) {
		OrderedValue ordVal = (OrderedValue) this.cacheMap.get(key);
		if (ordVal != null) {
			this.removalEvent.fireEvent(new RemovalEvent(this, ordVal.getValue()));
			this.currentSize -= ordVal.getApproximateSize();
			this.timedSet.remove(ordVal);
			return ordVal.getValue();
		} else {
			return null;
		}
	}

	public void addRemovalListener(RemovalListener listener) {
		this.removalEvent.addListener(listener);
	}

	public void removeRemovalListener(RemovalListener listener) {
		this.removalEvent.removeListener(listener);
	}

	public int getApproxSize() {
		return this.currentSize;
	}

	public int getNumEntries() {
		return this.cacheMap.size();
	}

	public List<EntryInfo> getEntryInfoList() {
		List<EntryInfo> list = new ArrayList<EntryInfo>();
		Iterator<OrderedValue> i = this.cacheMap.values().iterator();
		while (i.hasNext()) {
			OrderedValue ov = (OrderedValue) i.next();
			Object value = ov.getValue();
			Class<?> vc = value == null ? null : value.getClass();
			list.add(new EntryInfo(vc, ov.getApproximateSize()));
		}
		return list;
	}

	public static class EntryInfo {
		public final Class<?> valueClass;
		public final int approximateSize;

		public EntryInfo(final Class<?> valueClass, final int approximateSize) {
			super();
			this.valueClass = valueClass;
			this.approximateSize = approximateSize;
		}

		public String toString() {
			Class<?> vc = this.valueClass;
			String vcName = vc == null ? "<none>" : vc.getName();
			return "[class=" + vcName + ",approx-size=" + this.approximateSize
					+ "]";
		}
	}
}
