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
 * Created on Jun 9, 2005
 */
package org.lobobrowser.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author J. H. S.
 */
public class CollectionUtilities {
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

	public static Iterator iteratorUnion(final Iterator[] iterators) {
		return new Iterator() {
			private Iterator current = iterators.length > 0 ? iterators[0] : null;
			private int iteratorIndex = 0;

			@Override
			public boolean hasNext() {
				for (;;) {
					if (this.current == null) {
						return false;
					}
					if (this.current.hasNext()) {
						return true;
					}
					this.iteratorIndex++;
					this.current = this.iteratorIndex >= iterators.length ? null : iterators[this.iteratorIndex];
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
					} catch (final NoSuchElementException nse) {
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

	public static Collection reverse(Collection collection) {
		final LinkedList newCollection = new LinkedList();
		final Iterator i = collection.iterator();
		while (i.hasNext()) {
			newCollection.addFirst(i.next());
		}
		return newCollection;
	}

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

	/**
	 * 
	 */
	private CollectionUtilities() {
		super();
	}
}
