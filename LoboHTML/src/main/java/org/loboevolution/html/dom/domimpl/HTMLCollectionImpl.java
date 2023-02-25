/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

import java.util.List;
import java.util.Optional;

/**
 * <p>HTMLCollectionImpl class.</p>
 */
public class HTMLCollectionImpl extends AbstractList<Node> implements HTMLCollection {
	
	private final NodeImpl rootNode;

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
	 * @param rootNode a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 * @param nodeList a {@link java.util.List} object.
	 */
	public HTMLCollectionImpl(NodeImpl rootNode, List<Node> nodeList) {
		super(nodeList);
		this.rootNode = rootNode;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.size();
	}

	/** {@inheritDoc} */
	@Override
	public Node item(Object index) {
		try {
			double idx = Double.parseDouble(index.toString());
			if (idx >= getLength() || idx == -1) return null;
			return this.get((int) idx);
		} catch (NumberFormatException e) {
			return this.get(0);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Element namedItem(String name) {
		final Document doc = this.rootNode.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		final HTMLCollectionImpl nodeList = (HTMLCollectionImpl) doc.getElementsByName(name);
		if (nodeList.size() > 0) {
			Optional<Node> node = nodeList.stream().findFirst();
			return (Element) node.orElse(null);
		} else{
			return doc.getElementById(name);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLCollection]";
	}
}
