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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteredCollection implements Collection {
	private final ObjectFilter filter;
	private final Collection sourceCollection;

	public FilteredCollection(Collection sourceCollection, ObjectFilter filter) {
		this.filter = filter;
		this.sourceCollection = sourceCollection;
	}

	@Override
	public boolean add(Object o) {
		return this.sourceCollection.add(this.filter.encode(o));
	}

	@Override
	public boolean addAll(Collection c) {
		boolean result = false;
		final Iterator i = c.iterator();
		while (i.hasNext()) {
			if (add(i.next())) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public void clear() {
		final Object[] values = this.toArray();
		for (final Object value : values) {
			this.sourceCollection.remove(this.filter.encode(value));
		}
	}

	@Override
	public boolean contains(Object o) {
		return this.sourceCollection.contains(this.filter.encode(o));
	}

	@Override
	public boolean containsAll(Collection c) {
		final Iterator i = c.iterator();
		while (i.hasNext()) {
			if (!contains(i.next())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		final Iterator i = this.sourceCollection.iterator();
		while (i.hasNext()) {
			if (this.filter.decode(i.next()) != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Iterator iterator() {
		final Iterator sourceIterator = this.sourceCollection.iterator();
		return new Iterator() {
			private Boolean hasNext;
			private Object next;

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Iterator#hasNext()
			 */
			@Override
			public boolean hasNext() {
				if (this.hasNext == null) {
					scanNext();
				}
				return this.hasNext.booleanValue();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Iterator#next()
			 */
			@Override
			public Object next() {
				if (this.hasNext == null) {
					scanNext();
				}
				if (Boolean.FALSE.equals(this.hasNext)) {
					throw new NoSuchElementException();
				}
				final Object next = this.next;
				this.hasNext = null;
				return next;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Iterator#remove()
			 */
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			private void scanNext() {
				while (sourceIterator.hasNext()) {
					final Object item = FilteredCollection.this.filter.decode(sourceIterator.next());
					if (item != null) {
						this.hasNext = Boolean.TRUE;
						this.next = item;
					}
				}
				this.hasNext = Boolean.FALSE;
			}
		};
	}

	@Override
	public boolean remove(Object o) {
		return this.sourceCollection.remove(this.filter.encode(o));
	}

	@Override
	public boolean removeAll(Collection c) {
		boolean result = false;
		final Iterator i = c.iterator();
		while (i.hasNext()) {
			if (remove(i.next())) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection c) {
		boolean result = false;
		final Object[] values = this.toArray();
		for (final Object value : values) {
			if (!c.contains(value)) {
				if (remove(value)) {
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public int size() {
		int count = 0;
		final Iterator i = this.sourceCollection.iterator();
		while (i.hasNext()) {
			if (this.filter.decode(i.next()) != null) {
				count++;
			}
		}
		return count;
	}

	@Override
	public Object[] toArray() {
		return this.toArray(new Object[0]);
	}

	@Override
	public Object[] toArray(Object[] a) {
		final Collection bucket = new ArrayList();
		final Iterator i = this.sourceCollection.iterator();
		while (i.hasNext()) {
			final Object item = this.filter.decode(i.next());
			if (item != null) {
				bucket.add(item);
			}
		}
		return bucket.toArray(a);
	}
}
