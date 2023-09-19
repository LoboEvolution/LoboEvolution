/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
