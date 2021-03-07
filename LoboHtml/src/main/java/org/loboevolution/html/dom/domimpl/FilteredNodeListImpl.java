/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.Collection;

import org.loboevolution.html.dom.NodeFilter;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

class FilteredNodeListImpl extends AbstractScriptableDelegate implements NodeList {
	private final NodeFilter filter;
	private final Object lock;
	private final Collection<Node> sourceNodeList;

	/**
	 * <p>Constructor for FilteredNodeListImpl.</p>
	 *
	 * @param filter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 * @param list a {@link java.util.Collection} object.
	 * @param lock a {@link java.lang.Object} object.
	 */
	public FilteredNodeListImpl(NodeFilter filter, Collection<Node> list, Object lock) {
		this.filter = filter;
		this.sourceNodeList = list;
		this.lock = lock;
	}

	/** {@inheritDoc} */
	@Override
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

	/** {@inheritDoc} */
	@Override
	public Node item(int index) {
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

	@Override
	public Node[] toArray() {
		return (Node[]) sourceNodeList.toArray();
	}
}
