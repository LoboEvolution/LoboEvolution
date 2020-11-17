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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.NodeFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * <p>HTMLCollectionImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLCollectionImpl implements HTMLCollection {

	private final NodeImpl rootNode;
	
	private NodeListImpl rootList = null;
	
	/**
	 * <p>Constructor for HTMLCollectionImpl.</p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 */
	public HTMLCollectionImpl(NodeImpl rootNode) {
		this.rootNode = rootNode;
	}

	/**
	 * <p>Constructor for HTMLCollectionImpl.</p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 * @param nodeFilter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 */
	public HTMLCollectionImpl(NodeImpl rootNode, NodeFilter nodeFilter) {
		this.rootNode = rootNode;
		rootList = (NodeListImpl) rootNode.getNodeList(nodeFilter);
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		if(rootList == null ) {
			return this.rootNode.getChildCount();
		} else {
			return this.rootList.getLength();
		}
	}

	/**
	 * <p>indexOf.</p>
	 *
	 * @param node a {@link org.w3c.dom.Node} object.
	 * @return a int.
	 */
	public int indexOf(Node node) {
		if (rootList == null) {
			return this.rootNode.getChildIndex(node);
		} else {
			return this.rootList.indexOf(node);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node item(int index) {
		if (rootList == null) {
			return this.rootNode.getChildAtIndex(index);
		} else {
			return this.rootList.get(index);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node namedItem(String name) {
		final Document doc = this.rootNode.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		final Node node = doc.getElementById(name);
		if (node != null && node.getParentNode() == this.rootNode) {
			return node;
		}
		return null;
	}
}
