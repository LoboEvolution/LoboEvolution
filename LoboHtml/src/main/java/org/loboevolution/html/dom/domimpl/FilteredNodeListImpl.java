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
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.Collection;

import org.loboevolution.html.dom.NodeFilter;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
				if (this.filter.accept(node)) {
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
				if (this.filter.accept(node)) {
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
