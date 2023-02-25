/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.node;

import java.util.*;
import org.loboevolution.js.AbstractScriptableDelegate;

/**
 * <p>AbstractList class.</p>
 */
public class AbstractList<E> extends AbstractScriptableDelegate implements List<E> {

    private List<E> list = Collections.synchronizedList(new ArrayList<>());
	
	/**
	 * <p>Constructor for AbstractList.</p>
	 */
	public AbstractList() {}

	/**
	 * <p>
	 * Constructor for AbstractList.
	 * </p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.node.Node} object.
	 */
	public AbstractList(E rootNode) {
		list.add(rootNode);
	}
	
	/**
	 * <p>
	 * Constructor for AbstractList.
	 * </p>
	 *
	 * @param list a {@link java.util.List} object.
	 */
	public AbstractList(List<E> list) {
		this.list = list;
	}

	/** {@inheritDoc} */
	@Override
	public int size() {
		return list.size();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public E[] toArray() {
		return (E[]) list.toArray();
	}

	/** {@inheritDoc} */
	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(E e) {
		return list.add(e);
	}

	/** {@inheritDoc} */
	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return list.addAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return list.addAll(index, c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		list.clear();
	}

	/** {@inheritDoc} */
	@Override
	public E get(int index) {
		return list.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public E set(int index, E element) {
		return list.set(index, element);
	}

	/** {@inheritDoc} */
	@Override
	public void add(int index, E element) {
		list.add(index, element);
	}

	/** {@inheritDoc} */
	@Override
	public E remove(int index) {
		return list.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	/** {@inheritDoc} */
	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	/** {@inheritDoc} */
	@Override
	public ListIterator<E> listIterator() {
		return list.listIterator();
	}

	/** {@inheritDoc} */
	@Override
	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}

	/** {@inheritDoc} */
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
	
	/**
	 * <p>Getter for the field <code>list</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	protected List<E> getList() {
		return list;
	}
}
