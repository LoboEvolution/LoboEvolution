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
package org.loboevolution.html.js;

import java.util.Collection;
import java.util.Iterator;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.Node;

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
				if (this.filter.accept(node)) {
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
