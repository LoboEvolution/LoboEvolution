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
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import java.util.List;

import org.loboevolution.html.dom.domimpl.AbstractList;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

/**
 * <p>NodeListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class NodeListImpl extends AbstractList implements NodeList {

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
	
	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} objects.
	 */
	public NodeImpl[] toArray() {
		return this.toArray(new NodeImpl[0]);
	}
	
	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} objects.
	 */
	public List<Node> toList() {
		return this;
	}
	
	@Override
	public String toString() {
		return "[object NodeList]";
	}
}
