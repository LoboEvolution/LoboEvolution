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
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.js;

import org.loboevolution.html.node.Node;
import org.loboevolution.js.AbstractScriptableDelegate;

import java.util.Collection;

/**
 * <p>FilteredObjectList class.</p>
 */
public class FilteredObjectList extends AbstractScriptableDelegate {
	private final ObjectFilter filter;
	private final Object lock;
	private final Collection<Node> sourceNodeList;

	/**
	 * <p>Constructor for FilteredObjectList.</p>
	 *
	 * @param filter a {@link org.loboevolution.html.js.ObjectFilter} object.
	 * @param list a {@link java.util.Collection} object.
	 * @param lock a {@link java.lang.Object} object.
	 */
	public FilteredObjectList(ObjectFilter filter, Collection<Node> list, Object lock) {
		this.filter = filter;
		this.sourceNodeList = list;
		this.lock = lock;
	}

	/**
	 * <p>getLength.</p>
	 *
	 * @return a int.
	 */
	public int getLength() {
		synchronized (this.lock) {
			int count = 0;
			for (Node node : sourceNodeList) {
				if (this.filter.acceptNode(node)) {
					count++;
				}
			}
			return count;
		}
	}

	/**
	 * <p>item.</p>
	 *
	 * @param index a int.
	 * @return a {@link java.lang.Object} object.
	 */
	public Object item(int index) {
		synchronized (this.lock) {
			int count = 0;
			for (Node node : sourceNodeList) {
				if (this.filter.acceptNode(node)) {
					if (count == index) {
						return node;
					}
					count++;
				}
			}
			return null;
		}
	}
}
