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
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.mozilla.javascript.ES6Iterator;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeArrayIterator;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>NodeListImpl class.</p>
 */
public class NodeListImpl extends AbstractList<Node> implements NodeList {

	/**
	 * <p>Constructor for NodeListImpl.</p>
	 *
	 * * @param rootNode a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public NodeListImpl() {
	}

	/**
	 * <p>Constructor for NodeListImpl.</p>
	 *
	 * @param collection a {@link java.util.List} object.
	 */
	public NodeListImpl(List<Node> collection) {
		super(collection);
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.size();
	}

	/** {@inheritDoc} */
	@Override
	public Node item(int index) {
		int size = this.size();
		if (size > index && index > -1) {
			return this.get(index);
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public ES6Iterator entries() {
		return new NativeArrayIterator(getScriptable(), getScriptable(), NativeArrayIterator.ARRAY_ITERATOR_TYPE.ENTRIES);
	}

	/** {@inheritDoc}*/
	@Override
	public ES6Iterator keys() {
		return new NativeArrayIterator(getScriptable(), getScriptable(), NativeArrayIterator.ARRAY_ITERATOR_TYPE.KEYS);
	}

	/** {@inheritDoc} */
	@Override
	public ES6Iterator values() {
		return new NativeArrayIterator(getScriptable(), getScriptable(), NativeArrayIterator.ARRAY_ITERATOR_TYPE.VALUES);
	}

	/** {@inheritDoc} */
	@Override
	public void forEach(final Function function) {
		AtomicInteger integer = new AtomicInteger(0);
		this.forEach(node -> {
			final int i = integer.getAndIncrement();
			final NodeImpl n = (NodeImpl) node;
			Executor.executeFunction(n, function, null, new Object[] { n.getScriptable(), i, this});
		});
	}

	/** {@inheritDoc} */
	@Override
	public Node[] toArray() {
		return this.toArray(new Node[0]);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object NodeList]";
	}
}
