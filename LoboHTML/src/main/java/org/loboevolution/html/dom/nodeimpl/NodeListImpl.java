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
