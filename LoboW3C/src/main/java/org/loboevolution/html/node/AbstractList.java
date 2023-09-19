/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

	public void setList(List<E> list) {
		this.list = list;
	}
}
