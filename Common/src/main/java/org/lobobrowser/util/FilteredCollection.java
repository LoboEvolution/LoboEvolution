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
 * Created on Oct 8, 2005
 */
package org.lobobrowser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Class FilteredCollection.
 */
public class FilteredCollection implements Collection<Object> {
	/** The filter. */
	private final ObjectFilter filter;
	/** The source collection. */
	private final Collection<Object> sourceCollection;

	/**
	 * Instantiates a new filtered collection.
	 *
	 * @param sourceCollection
	 *            the source collection
	 * @param filter
	 *            the filter
	 */
	public FilteredCollection(Collection<Object> sourceCollection, ObjectFilter filter) {
		this.filter = filter;
		this.sourceCollection = sourceCollection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		int count = 0;
		Iterator<Object> i = this.sourceCollection.iterator();
		while (i.hasNext()) {
			if (this.filter.decode(i.next()) != null) {
				count++;
			}
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		Iterator<Object> i = this.sourceCollection.iterator();
		while (i.hasNext()) {
			if (this.filter.decode(i.next()) != null) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return this.sourceCollection.contains(this.filter.encode(o));
	}

	@Override
	public Iterator<Object> iterator() {
		final Iterator<Object> sourceIterator = this.sourceCollection.iterator();
		return new Iterator<Object>() {
			private Boolean hasNext;
			private Object next;

			private void scanNext() {
				while (sourceIterator.hasNext()) {
					Object item = filter.decode(sourceIterator.next());
					if (item != null) {
						hasNext = Boolean.TRUE;
						this.next = item;
					}
				}
				hasNext = Boolean.FALSE;
			}

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
				Object next = this.next;
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
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		return this.toArray(new Object[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	@Override
	public Object[] toArray(Object[] a) {
		Collection<Object> bucket = new ArrayList<Object>();
		Iterator<Object> i = this.sourceCollection.iterator();
		while (i.hasNext()) {
			Object item = this.filter.decode(i.next());
			if (item != null) {
				bucket.add(item);
			}
		}
		return bucket.toArray(a);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(Object o) {
		return this.sourceCollection.add(this.filter.encode(o));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		return this.sourceCollection.remove(this.filter.encode(o));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection c) {
		Iterator i = c.iterator();
		while (i.hasNext()) {
			if (!this.contains(i.next())) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection c) {
		boolean result = false;
		Iterator i = c.iterator();
		while (i.hasNext()) {
			if (this.add(i.next())) {
				result = true;
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection c) {
		boolean result = false;
		Iterator i = c.iterator();
		while (i.hasNext()) {
			if (this.remove(i.next())) {
				result = true;
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection c) {
		boolean result = false;
		Object[] values = this.toArray();
		for (int i = 0; i < values.length; i++) {
			if (!c.contains(values[i])) {
				result = this.remove(values[i]);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		Object[] values = this.toArray();
		for (Object value : values) {
			this.sourceCollection.remove(this.filter.encode(value));
		}
	}
}
