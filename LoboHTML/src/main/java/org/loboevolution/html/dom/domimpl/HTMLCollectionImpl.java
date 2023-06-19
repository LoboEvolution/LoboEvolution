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
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.filter.ClassNameFilter;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.traversal.NodeFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>HTMLCollectionImpl class.</p>
 */
public class HTMLCollectionImpl extends AbstractList<Node> implements HTMLCollection {
	
	private final NodeImpl rootNode;

	private final NodeFilter filter;

	 /**
	 * <p>
	 * Constructor for HTMLCollectionImpl.
	 * </p>
	 * @param rootNode a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 * @param filter a {@link org.loboevolution.html.node.traversal.NodeFilter} object.
	 */
	public HTMLCollectionImpl(NodeImpl rootNode, NodeFilter filter) {
		setList((NodeListImpl) rootNode.getNodeList(filter));
		this.rootNode = rootNode;
		this.filter = filter;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		setList(Arrays.asList(rootNode.getNodeList(filter).toArray()));
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

	@Override
	public void setItem(Integer index, Node node) {
		if (index > -1) {
			if (getLength() == 0 || getLength() == index || getLength() < index) {
				add(index, node);
			} else {
				set(index, node);
			}
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
