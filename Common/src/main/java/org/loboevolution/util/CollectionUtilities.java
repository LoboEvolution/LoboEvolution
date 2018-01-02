/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 9, 2005
 */
package org.loboevolution.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * The Class CollectionUtilities.
 *
 * @author J. H. S.
 */
public class CollectionUtilities {
	/**
	 * Instantiates a new collection utilities.
	 */
	private CollectionUtilities() {
		super();
	}

	/**
	 * Gets the iterator enumeration.
	 *
	 * @param i
	 *            the i
	 * @return the iterator enumeration
	 */
	public static Enumeration getIteratorEnumeration(final Iterator i) {
		return new Enumeration() {
			@Override
			public boolean hasMoreElements() {
				return i.hasNext();
			}

			@Override
			public Object nextElement() {
				return i.next();
			}
		};
	}

	/**
	 * Iterator union.
	 *
	 * @param iterators
	 *            the iterators
	 * @return the iterator
	 */
	public static Iterator iteratorUnion(final Iterator[] iterators) {
		return new Iterator() {
			private int iteratorIndex = 0;
			private Iterator current = iterators.length > 0 ? iterators[0] : null;

			@Override
			public boolean hasNext() {
				for (;;) {
					if (current == null) {
						return false;
					}
					if (current.hasNext()) {
						return true;
					}
					iteratorIndex++;
					current = iteratorIndex >= iterators.length ? null : iterators[iteratorIndex];
				}
			}

			@Override
			public Object next() {
				for (;;) {
					if (this.current == null) {
						throw new NoSuchElementException();
					}
					try {
						return this.current.next();
					} catch (NoSuchElementException nse) {
						this.iteratorIndex++;
						this.current = this.iteratorIndex >= iterators.length ? null : iterators[this.iteratorIndex];
					}
				}
			}

			@Override
			public void remove() {
				if (this.current == null) {
					throw new NoSuchElementException();
				}
				this.current.remove();
			}
		};
	}

	/**
	 * Reverse.
	 *
	 * @param collection
	 *            the collection
	 * @return the collection
	 */
	public static Collection reverse(Collection collection) {
		LinkedList newCollection = new LinkedList();
		Iterator i = collection.iterator();
		while (i.hasNext()) {
			newCollection.addFirst(i.next());
		}
		return newCollection;
	}

	/**
	 * Singleton iterator.
	 *
	 * @param item
	 *            the item
	 * @return the iterator
	 */
	public static Iterator singletonIterator(final Object item) {
		return new Iterator() {
			private boolean gotItem = false;

			@Override
			public boolean hasNext() {
				return !this.gotItem;
			}

			@Override
			public Object next() {
				if (this.gotItem) {
					throw new NoSuchElementException();
				}
				this.gotItem = true;
				return item;
			}

			@Override
			public void remove() {
				if (!this.gotItem) {
					this.gotItem = true;
				} else {
					throw new NoSuchElementException();
				}
			}
		};
	}
}
