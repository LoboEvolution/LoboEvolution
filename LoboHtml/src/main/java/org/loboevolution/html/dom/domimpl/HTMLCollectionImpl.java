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

import java.util.List;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.NodeFilter;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * <p>HTMLCollectionImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLCollectionImpl extends AbstractList implements HTMLCollection {
	
	private NodeImpl rootNode;


	/**
	 * <p>Constructor for HTMLCollectionImpl.</p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	 public HTMLCollectionImpl(NodeImpl rootNode) {
		super(rootNode);
		this.rootNode = rootNode;
	}

	/**
	 * <p>
	 * Constructor for HTMLCollectionImpl.
	 * </p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 */
	public HTMLCollectionImpl(List<Node> nodeList) {
		super(nodeList);
	}
	
	/**
	 * <p>Constructor for HTMLCollectionImpl.</p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 * @param nodeFilter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 */
	public HTMLCollectionImpl(NodeImpl rootNode, NodeFilter nodeFilter) {
		super(rootNode, nodeFilter);
		this.rootNode = rootNode;
	}

	@Override
	public int getLength() {
		return this.size();
	}

	@Override
	public Node item(int index) {
		return this.get(index);
	}

	@Override
	public Element namedItem(String name) {
		final Document doc = this.rootNode.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		final Node node = doc.getElementById(name);
		if (node != null && node.getParentNode() == this.rootNode) {
			return (Element) node;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "[object HTMLCollection]";
	}
}
