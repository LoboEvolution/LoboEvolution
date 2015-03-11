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
/*
 * Created on Sep 3, 2005
 */
package org.lobobrowser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;


/**
 * The Class ListSet.
 */
public class ListSet {
	
	/** The list. */
	private final List list = new ArrayList();
	
	/** The set. */
	private final Set set = new HashSet();

	/**
	 * Instantiates a new list set.
	 */
	public ListSet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add(int, E)
	 */
	/**
	 * Adds the.
	 *
	 * @param index the index
	 * @param element the element
	 */
	public void add(int index, Object element) {
		if (this.set.add(element)) {
			list.add(index, element);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add(E)
	 */
	/**
	 * Adds the.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	public boolean add(Object o) {
		if (this.set.add(o)) {
			return this.list.add(o);
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	/**
	 * Adds the all.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	public boolean addAll(Collection c) {
		boolean changed = false;
		Iterator i = c.iterator();
		while (i.hasNext()) {
			Object element = i.next();
			if (this.add(element)) {
				changed = true;
			}
		}
		return changed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	/**
	 * Adds the all.
	 *
	 * @param index the index
	 * @param c the c
	 * @return true, if successful
	 */
	public boolean addAll(int index, Collection c) {
		boolean changed = false;
		int insertIndex = index;
		Iterator i = c.iterator();
		while (i.hasNext()) {
			Object element = i.next();
			if (this.set.add(element)) {
				this.list.add(insertIndex++, element);
				changed = true;
			}
		}
		return changed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#clear()
	 */
	/**
	 * Clear.
	 */
	public void clear() {
		this.set.clear();
		this.list.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#contains(Object)
	 */
	/**
	 * Contains.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	public boolean contains(Object o) {
		return this.set.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	/**
	 * Contains all.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	public boolean containsAll(Collection c) {
		return this.set.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#get(int)
	 */
	/**
	 * Gets the.
	 *
	 * @param index the index
	 * @return the object
	 */
	public Object get(int index) {
		return this.list.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#indexOf(Object)
	 */
	/**
	 * Index of.
	 *
	 * @param o the o
	 * @return the int
	 */
	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#isEmpty()
	 */
	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return this.set.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#iterator()
	 */
	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	public Iterator iterator() {
		return this.list.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#lastIndexOf(Object)
	 */
	/**
	 * Last index of.
	 *
	 * @param o the o
	 * @return the int
	 */
	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator()
	 */
	/**
	 * List iterator.
	 *
	 * @return the list iterator
	 */
	public ListIterator listIterator() {
		return this.list.listIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator(int)
	 */
	/**
	 * List iterator.
	 *
	 * @param index the index
	 * @return the list iterator
	 */
	public ListIterator listIterator(int index) {
		return this.list.listIterator(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(int)
	 */
	/**
	 * Removes the.
	 *
	 * @param index the index
	 * @return the object
	 */
	public Object remove(int index) {
		Object element = this.list.remove(index);
		if (element != null) {
			this.set.remove(element);
		}
		return element;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(Object)
	 */
	/**
	 * Removes the.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	public boolean remove(Object o) {
		if (this.set.remove(o)) {
			this.list.remove(o);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	/**
	 * Removes the all.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	public boolean removeAll(Collection c) {
		if (this.set.removeAll(c)) {
			this.list.removeAll(c);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	/**
	 * Retain all.
	 *
	 * @param c the c
	 * @return true, if successful
	 */
	public boolean retainAll(Collection c) {
		if (this.set.retainAll(c)) {
			this.list.retainAll(c);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#set(int, E)
	 */
	/**
	 * Sets the.
	 *
	 * @param index the index
	 * @param element the element
	 * @return the object
	 */
	public Object set(int index, Object element) {
		this.set.add(element);
		return this.list.set(index, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#size()
	 */
	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return this.list.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#subList(int, int)
	 */
	/**
	 * Sub list.
	 *
	 * @param fromIndex the from index
	 * @param toIndex the to index
	 * @return the list
	 */
	public List subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray()
	 */
	/**
	 * To array.
	 *
	 * @return the object[]
	 */
	public Object[] toArray() {
		return this.list.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray(T[])
	 */
	/**
	 * To array.
	 *
	 * @param a the a
	 * @return the object[]
	 */
	public Object[] toArray(Object[] a) {
		return this.list.toArray(a);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other instanceof ListSet
				&& this.list.equals(((ListSet) other).list);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.list.hashCode();
	}
}
