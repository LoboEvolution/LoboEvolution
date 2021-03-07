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
package org.loboevolution.html.js;

import java.util.Collection;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.html.node.Node;

/**
 * <p>FilteredObjectList class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class FilteredObjectList extends AbstractScriptableDelegate {
	private final ObjectFilter filter;
	private final Object lock;
	private final Collection<Node> sourceNodeList;

	/**
	 * <p>Constructor for FilteredObjectList.</p>
	 *
	 * @param filter a {@link org.loboevolution.html.node.js.ObjectFilter} object.
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
