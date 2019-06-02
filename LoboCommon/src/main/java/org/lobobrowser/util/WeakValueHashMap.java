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

public class WeakValueHashMap implements Map {
	private class LocalFilter implements ObjectFilter {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.util.ObjectFilter#decode(java.lang.Object)
		 */
		@Override
		public Object decode(Object source) {
			final WeakReference wf = (WeakReference) source;
			return wf == null ? null : wf.get();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xamjwg.util.ObjectFilter#encode(java.lang.Object)
		 */
		@Override
		public Object encode(Object source) {
			throw new java.lang.UnsupportedOperationException("Read-only collection.");
		}
	}

	private static class LocalWeakReference extends WeakReference {
		private final Object key;

		public LocalWeakReference(Object key, Object target, ReferenceQueue queue) {
			super(target, queue);
			this.key = key;
		}

		@Override
		public boolean equals(Object other) {
			final Object target1 = this.get();
			final Object target2 = other instanceof LocalWeakReference ? ((LocalWeakReference) other).get() : null;
			return Objects.equals(target1, target2);
		}

		public Object getKey() {
			return this.key;
		}

		@Override
		public int hashCode() {
			final Object target = this.get();
			return target == null ? 0 : target.hashCode();
		}
	}

	private final Map map = new HashMap();

	private final ReferenceQueue queue = new ReferenceQueue();

	public WeakValueHashMap() {
		super();
	}

	private final void checkQueue() {
		final ReferenceQueue queue = this.queue;
		LocalWeakReference ref;
		while ((ref = (LocalWeakReference) queue.poll()) != null) {
			this.map.remove(ref.getKey());
		}
	}

	@Override
	public void clear() {
		checkQueue();
		this.map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		final WeakReference wf = (WeakReference) this.map.get(key);
		return wf != null && wf.get() != null;
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(Object key) {
		checkQueue();
		final WeakReference wf = (WeakReference) this.map.get(key);
		return wf == null ? null : wf.get();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public Set keySet() {
		return this.map.keySet();
	}

	@Override
	public Object put(Object key, Object value) {
		checkQueue();
		return putImpl(key, value);
	}

	@Override
	public void putAll(Map t) {
		checkQueue();
		final Iterator i = t.entrySet().iterator();
		while (i.hasNext()) {
			final Map.Entry entry = (Map.Entry) i.next();
			putImpl(entry.getKey(), entry.getValue());
		}
	}

	private final Object putImpl(Object key, Object value) {
		if (value == null) {
			throw new IllegalArgumentException("null values not accepted");
		}
		final Reference ref = new LocalWeakReference(key, value, this.queue);
		final WeakReference oldWf = (WeakReference) this.map.put(key, ref);
		return oldWf == null ? null : oldWf.get();
	}

	@Override
	public Object remove(Object key) {
		checkQueue();
		final WeakReference wf = (WeakReference) this.map.remove(key);
		return wf == null ? null : wf.get();
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public Collection values() {
		return new FilteredCollection(this.map.values(), new LocalFilter());
	}
}
