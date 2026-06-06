/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import java.util.stream.Stream;

/**
 * <p>DocumentFragmentImpl class. </p>
 */
public class DocumentFragmentImpl extends NodeImpl implements DocumentFragment {

	/** Constructor for DocumentFragmentImpl.*/
	public DocumentFragmentImpl() {}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "[object DocumentFragment]";
	}

	@Override
	public Node appendChild(final Node newChild) {
		if (newChild.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append a fragment.");
		}
		return super.appendChild(newChild);
	}

	@Override
	public Node prependChild(final Node newChild) {
		if (newChild.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append a fragment.");
		}
		return super.prependChild(newChild);
	}

	@Override
	public Node replaceChild(Node newChild, Node oldChild) {

		if (newChild.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
					"DocumentType not allowed in DocumentFragment");
		}

		if (newChild.getNodeType() == Node.ATTRIBUTE_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
					"Attr not allowed in DocumentFragment");
		}

		return super.replaceChild(newChild, oldChild);
	}

	@Override
	public short compareDocumentPosition(Node other) {

		if (other == null) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "other is null");
		}

		if (other == this) {
			return 0;
		}

		// Handle DocumentFragment or other disconnected nodes
		if (getDocumentNode() != other.getDocumentNode() ||
				(getNodeType() == Node.DOCUMENT_FRAGMENT_NODE && other.getNodeType() == Node.ATTRIBUTE_NODE)) {
			return DOCUMENT_POSITION_DISCONNECTED | DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
		}

		return super.compareDocumentPosition(other);
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public String getLocalName() {
		return "";
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.DOCUMENT_FRAGMENT_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return null;
	}

	@Override
	public void setNodeValue(final String nodeValue) throws DOMException {
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "readonly node");
	}

	/** {@inheritDoc} */
	@Override
	public int getChildElementCount() {
		return (int) nodeList.stream().
				filter(n -> n instanceof Element &&
						n.getNodeType() != PROCESSING_INSTRUCTION_NODE &&
						n.getNodeType() != DOCUMENT_TYPE_NODE &&
						!"xml".equals(n.getNodeName())).count();
	}

	/** {@inheritDoc} */
	@Override
	public Element getFirstElementChild() {
		return (Element) nodeList.stream().filter(n -> n instanceof Element).findFirst().orElse(null);
	}

	/** {@inheritDoc} */
	@Override
	public Element getLastElementChild() {
		final long count = nodeList.stream().filter(n -> n instanceof Element).count();
		final Stream<Node> stream = nodeList.stream();
		return (Element) stream.filter(n -> n instanceof Element).skip(count > 0 ? count - 1 : 0).findFirst().orElse(null);
	}

	@Override
	public Element querySelector(String selectors) {
		return null;
	}

	@Override
	public NodeList querySelectorAll(String selectors) {
		return null;
	}
}
