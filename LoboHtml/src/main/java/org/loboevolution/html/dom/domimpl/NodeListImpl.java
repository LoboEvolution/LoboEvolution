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
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>NodeListImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class NodeListImpl extends AbstractScriptableDelegate implements NodeList {

	private final List<Node> nodeList = Collections.synchronizedList(new ArrayList<Node>());

	/**
	 * <p>Constructor for NodeListImpl.</p>
	 */
	public NodeListImpl() {
	}

	/**
	 * <p>Constructor for NodeListImpl.</p>
	 *
	 * @param collection a {@link java.util.List} object.
	 */
	public NodeListImpl(List<Node> collection) {
		this.nodeList.addAll(collection);
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.nodeList.size();
	}

	/** {@inheritDoc} */
	@Override
	public Node item(int index) {
		int size = this.nodeList.size();
		if (size > index && index > -1) {
			return this.nodeList.get(index);
		} else {
			return null;
		}
	}

	/**
	 * <p>add.</p>
	 *
	 * @param newChild a {@link org.w3c.dom.Node} object.
	 */
	public void add(Node newChild) {
		this.nodeList.add(newChild);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param firstIdx a int.
	 * @param textNode a {@link org.w3c.dom.Node} object.
	 */
	public void add(int firstIdx, Node textNode) {
		this.nodeList.add(firstIdx, textNode);
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @param child a {@link org.w3c.dom.Node} object.
	 * @return a int.
	 */
	public int indexOf(Node child) {
		return this.nodeList.indexOf(child);
	}

	/**
	 * <p>remove.</p>
	 *
	 * @param i a int.
	 * @return a {@link org.w3c.dom.Node} object.
	 */
	public Node remove(int i) {
		return this.nodeList.remove(i);
	}

	/**
	 * <p>get.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.w3c.dom.Node} object.
	 */
	public Node get(int index) {
		return this.nodeList.get(index);
	}

	/**
	 * <p>remove.</p>
	 *
	 * @param oldChild a {@link org.w3c.dom.Node} object.
	 * @return a boolean.
	 */
	public boolean remove(Node oldChild) {
		return this.nodeList.remove(oldChild);
	}

	/**
	 * <p>clear.</p>
	 */
	public void clear() {
		this.nodeList.clear();
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of {@link org.loboevolution.html.dom.domimpl.NodeImpl} objects.
	 */
	public NodeImpl[] toArray() {
		return this.nodeList.toArray(new NodeImpl[0]);
	}

	/**
	 * <p>set.</p>
	 *
	 * @param idx a int.
	 * @param newChild a {@link org.w3c.dom.Node} object.
	 */
	public void set(int idx, Node newChild) {
		this.nodeList.set(idx, newChild);
	}

	/**
	 * <p>removeAll.</p>
	 *
	 * @param toDelete a {@link java.util.List} object.
	 */
	public void removeAll(List<Node> toDelete) {
		this.nodeList.removeAll(toDelete);
	}
}
