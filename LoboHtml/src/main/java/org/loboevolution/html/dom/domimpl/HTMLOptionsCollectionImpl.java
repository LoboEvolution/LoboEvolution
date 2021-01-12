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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.filter.OptionFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * <p>HTMLOptionsCollectionImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLOptionsCollectionImpl implements HTMLOptionsCollection {

	private final NodeImpl rootNode;

	private NodeListImpl rootList = null;

	/**
	 * <p>Constructor for HTMLOptionsCollectionImpl.</p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 */
	public HTMLOptionsCollectionImpl(NodeImpl rootNode) {
		this.rootNode = rootNode;
		rootList = (NodeListImpl) rootNode.getNodeList(new OptionFilter());
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		if (rootList == null) {
			return this.rootNode.getChildCount();
		} else {
			return this.rootList.getLength();
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
	
	/**
	 * <p>Getter for the field rootList.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.NodeListImpl} object.
	 */
	protected NodeListImpl getRootList() {
		return rootList;
	}
}
