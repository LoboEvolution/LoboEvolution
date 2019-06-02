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
package org.lobobrowser.util;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
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
	public static class EntryInfo {
		public final int approximateSize;
		public final Class valueClass;

		public EntryInfo(final Class valueClass, final int approximateSize) {
			super();
			this.valueClass = valueClass;
			this.approximateSize = approximateSize;
		}

		@Override
		public String toString() {
			final Class vc = this.valueClass;
			final String vcName = vc == null ? "<none>" : vc.getName();
			return "[class=" + vcName + ",approx-size=" + this.approximateSize + "]";
		}
	}

	private class OrderedValue implements Comparable, java.io.Serializable {
		private static final long serialVersionUID = 340227625744215821L;
		private int approximateSize;
		private final Object key;
		private long timestamp;
		private Object value;

		private OrderedValue(Object key, Object value, int approxSize) {
			this.key = key;
			this.value = value;
			this.approximateSize = approxSize;
			touch();
		}

		@Override
		public int compareTo(Object arg0) {
			if (this == arg0) {
				return 0;
			}
			final OrderedValue other = (OrderedValue) arg0;
			final long diff = this.timestamp - other.timestamp;
			if (diff > 0) {
				return +1;
			} else if (diff < 0) {
				return -1;
			}
			int hc1 = System.identityHashCode(this);
			int hc2 = System.identityHashCode(other);
			if (hc1 == hc2) {
				hc1 = System.identityHashCode(this.value);
				hc2 = System.identityHashCode(other.value);
			}
			return hc1 - hc2;
		}

		private final void touch() {
			this.timestamp = System.currentTimeMillis();
		}
	}

	private class RemovalDispatch extends EventDispatch2 {
		@Override
		protected void dispatchEvent(EventListener listener, EventObject event) {
			((RemovalListener) listener).removed((RemovalEvent) event);
		}
	}

	private static final long serialVersionUID = 940427225784212823L;

	private int approxMaxSize;
	private final Map cacheMap = new HashMap();

	private int currentSize = 0;

	private volatile transient EventDispatch2 removalEvent;

	/**
	 * Ascending timestamp order. First is least recently used.
	 */
	private final TreeSet timedSet = new TreeSet();

	public LRUCache(int approxMaxSize) {
		this.approxMaxSize = approxMaxSize;
		this.removalEvent = new RemovalDispatch();
	}

	public void addRemovalListener(RemovalListener listener) {
		this.removalEvent.addListener(listener);
	}

	public Object get(Object key) {
		final OrderedValue ordVal = (OrderedValue) this.cacheMap.get(key);
		if (ordVal != null) {
			this.timedSet.remove(ordVal);
			ordVal.touch();
			this.timedSet.add(ordVal);
			return ordVal.value;
		} else {
			return null;
		}
	}

	public int getApproxMaxSize() {
		return this.approxMaxSize;
	}

	public int getApproxSize() {
		return this.currentSize;
	}

	public List getEntryInfoList() {
		final List list = new ArrayList();
		final Iterator i = this.cacheMap.values().iterator();
		while (i.hasNext()) {
			final OrderedValue ov = (OrderedValue) i.next();
			final Object value = ov.value;
			final Class vc = value == null ? null : value.getClass();
			list.add(new EntryInfo(vc, ov.approximateSize));
		}
		return list;
	}

	public int getNumEntries() {
		return this.cacheMap.size();
	}

	public void put(Object key, Object value, int approxSize) {
		if (approxSize > this.approxMaxSize) {
			// Can't be inserted.
			return;
		}
		OrderedValue ordVal = (OrderedValue) this.cacheMap.get(key);
		if (ordVal != null) {
			if (ordVal.value != value) {
				this.removalEvent.fireEvent(new RemovalEvent(this, ordVal.value));
			}
			this.currentSize += approxSize - ordVal.approximateSize;
			this.timedSet.remove(ordVal);
			ordVal.approximateSize = approxSize;
			ordVal.value = value;
			ordVal.touch();
			this.timedSet.add(ordVal);
		} else {
			ordVal = new OrderedValue(key, value, approxSize);
			this.cacheMap.put(key, ordVal);
			this.timedSet.add(ordVal);
			this.currentSize += approxSize;
		}
		while (this.currentSize > this.approxMaxSize) {
			removeLRU();
		}
	}

	private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
		// Need to initialize transient fields here.
		this.removalEvent = new RemovalDispatch();
	}

	public Object remove(Object key) {
		final OrderedValue ordVal = (OrderedValue) this.cacheMap.get(key);
		if (ordVal != null) {
			this.removalEvent.fireEvent(new RemovalEvent(this, ordVal.value));
			this.currentSize -= ordVal.approximateSize;
			this.timedSet.remove(ordVal);
			return ordVal.value;
		} else {
			return null;
		}
	}

	private void removeLRU() {
		final OrderedValue ordVal = (OrderedValue) this.timedSet.first();
		if (ordVal != null) {
			this.removalEvent.fireEvent(new RemovalEvent(this, ordVal.value));
			if (this.timedSet.remove(ordVal)) {
				this.cacheMap.remove(ordVal.key);
				this.currentSize -= ordVal.approximateSize;
			} else {
				throw new IllegalStateException("Could not remove existing tree node.");
			}
		} else {
			throw new IllegalStateException("Cannot remove LRU since the cache is empty.");
		}
	}

	public void removeRemovalListener(RemovalListener listener) {
		this.removalEvent.removeListener(listener);
	}

	public void setApproxMaxSize(int approxMaxSize) {
		this.approxMaxSize = approxMaxSize;
	}
}
