/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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
package org.loboevolution.html.dom.domimpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.loboevolution.html.dom.NodeFilter;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.js.AbstractScriptableDelegate;

/**
 * <p>AbstractList class.</p>
 *
 *
 *
 */
public class AbstractList extends AbstractScriptableDelegate implements List<Node> {

    private List<Node> nodeList = Collections.synchronizedList(new ArrayList<>());
	
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
	public AbstractList(Node rootNode) {
		nodeList.add(rootNode);
	}
	
	/**
	 * <p>
	 * Constructor for AbstractList.
	 * </p>
	 *
	 * @param nodeList a {@link java.util.List} object.
	 */
	public AbstractList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	
	/**
	 * <p>Constructor for AbstractList.</p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.node.Node} object.
	 * @param nodeFilter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 */
	public AbstractList(Node rootNode, NodeFilter nodeFilter) {
		final NodeImpl impl = (NodeImpl)rootNode;
		nodeList = ((NodeListImpl)impl.getNodeList(nodeFilter)).toList();
	}

	/** {@inheritDoc} */
	@Override
	public int size() {
		return nodeList.size();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEmpty() {
		return nodeList.isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(Object o) {
		return nodeList.contains(o);
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<Node> iterator() {
		return nodeList.iterator();
	}

	/** {@inheritDoc} */
	@Override
	public Node[] toArray() {
		return nodeList.stream().toArray(Node[]::new);
	}

	/** {@inheritDoc} */
	@Override
	public <T> T[] toArray(T[] a) {
		return nodeList.toArray(a);
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(Node e) {
		return nodeList.add(e);
	}

	/** {@inheritDoc} */
	@Override
	public boolean remove(Object o) {
		return nodeList.remove(o);
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsAll(Collection<?> c) {
		return nodeList.containsAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(Collection<? extends Node> c) {
		return nodeList.addAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(int index, Collection<? extends Node> c) {
		return nodeList.addAll(index, c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean removeAll(Collection<?> c) {
		return nodeList.removeAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public boolean retainAll(Collection<?> c) {
		return nodeList.retainAll(c);
	}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		nodeList.clear();
	}

	/** {@inheritDoc} */
	@Override
	public Node get(int index) {
		return nodeList.get(index);
	}

	/** {@inheritDoc} */
	@Override
	public Node set(int index, Node element) {
		return nodeList.set(index, element);
	}

	/** {@inheritDoc} */
	@Override
	public void add(int index, Node element) {
		nodeList.add(index, element);
	}

	/** {@inheritDoc} */
	@Override
	public Node remove(int index) {
		return nodeList.remove(index);
	}

	/** {@inheritDoc} */
	@Override
	public int indexOf(Object o) {
		return nodeList.indexOf(o);
	}

	/** {@inheritDoc} */
	@Override
	public int lastIndexOf(Object o) {
		return nodeList.lastIndexOf(o);
	}

	/** {@inheritDoc} */
	@Override
	public ListIterator<Node> listIterator() {
		return nodeList.listIterator();
	}

	/** {@inheritDoc} */
	@Override
	public ListIterator<Node> listIterator(int index) {
		return nodeList.listIterator(index);
	}

	/** {@inheritDoc} */
	@Override
	public List<Node> subList(int fromIndex, int toIndex) {
		return nodeList.subList(fromIndex, toIndex);
	}
	
	/**
	 * <p>Getter for the field <code>nodeList</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	protected List<Node> getNodeList() {
		return nodeList;
	}
}
