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
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.node.*;
import org.loboevolution.js.AbstractScriptableDelegate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>NamedNodeMapImpl class.</p>
 */
public class NamedNodeMapImpl extends AbstractScriptableDelegate implements NamedNodeMap {

	private final NodeListImpl attributes = new NodeListImpl();

	private final Node owner;

	/**
	 * <p>Constructor for NamedNodeMapImpl.</p>
	 *
	 * @param owner a {@link org.loboevolution.html.node.Node} object.
	 * @param attribs a {@link org.loboevolution.html.dom.nodeimpl.NodeListImpl} object.
	 */
	public NamedNodeMapImpl(Node owner, NodeListImpl attribs) {
		this.owner = owner;
		 attribs.forEach(attr -> {
			final String name = attr.getLocalName();
			final String value = attr.getNodeValue();
			final String prefix = attr.getPrefix();
			final String namespace = attr.getNamespaceURI();
			final AttrImpl attrImpl = new AttrImpl(name, value, "id".equalsIgnoreCase(name), this.owner,  true);
			attrImpl.setPrefix(prefix);
			attrImpl.setNamespaceURI(namespace);
			this.attributes.add(attrImpl);
		 });
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.attributes.size();
	}

	/** {@inheritDoc} */
	@Override
	public Node getNamedItem(String name) {
		AtomicReference<Node> atomicReference = new AtomicReference<>();
		if (Strings.isNotBlank(name)) {
			attributes.forEach(attr -> {
				String key = attr.getNodeName();
				if (name.equalsIgnoreCase(key)) {
					atomicReference.set(attr);
				}
			});
		}
		return atomicReference.get();
	}

	/** {@inheritDoc} */
	@Override
	public Node getNamedItemNS(String namespaceURI, String localName) {
		AtomicReference<Node> atomicReference = new AtomicReference<>();
		if (Strings.isNotBlank(localName)) {
			attributes.forEach(attr -> {
				String namespace = attr.getNamespaceURI();
				String parentNamespace = owner.getParentNode() != null ? owner.getParentNode().getNamespaceURI() : null;

					if (Strings.isBlank(namespaceURI) || "*".equals(namespaceURI) || namespaceURI.equals(namespace) || namespaceURI.equals(parentNamespace)) {
					if (attr.getLocalName().equalsIgnoreCase(localName)) {
						atomicReference.set(attr);
					}
				}
			});
		}
		return atomicReference.get();
	}

	/** {@inheritDoc} */
	@Override
	public Node item(int index) {
		AtomicReference<Node> atomicReference = new AtomicReference<>();
		AtomicInteger atomIndex = new AtomicInteger(0);
		attributes.forEach(attr -> {
			if (atomIndex.getAndIncrement() == index && index > -1) {
				atomicReference.set(attr);
			}
		});
		return atomicReference.get();
	}

	/** {@inheritDoc} */
	@Override
	public Node removeNamedItem(String name) throws DOMException {
		AtomicReference<Node> atomicReference = new AtomicReference<>();
		attributes.forEach(attr -> {
			String key = attr.getNodeName();
			if (key.equalsIgnoreCase(name)) {
				atomicReference.set(attr);
			}
		});

		boolean removed = this.attributes.remove(atomicReference.get());
		if (!removed) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not found.");
		}
		return atomicReference.get();
	}

	/** {@inheritDoc} */
	@Override
	public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {

		AtomicReference<Node> atomicReference = new AtomicReference<>(null);
		attributes.forEach(attr -> {
			String namespace = attr.getNamespaceURI();
			String parentNamespace = owner.getParentNode() != null ? owner.getParentNode().getNamespaceURI() : null;
			if (namespaceURI != null && ("*".equals(namespaceURI) || namespaceURI.equals(namespace) || namespaceURI.equals(parentNamespace))) {
				if (attr.getLocalName().equalsIgnoreCase(localName)) {
					atomicReference.set(attr);
				}
			}
		});

		Node node = atomicReference.get();
		if (node != null) {
			attributes.remove(node);
			return node;
		} else {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not found.");
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node setNamedItem(Node node) {
		Node element = node instanceof Attr ? ((Attr) node).getOwnerElement() : null;

		if (node == null) {
			throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR, "The Node is null");
		}

		if (element!= null  && !Objects.equals(((Attr) node).getOwnerElement(), owner)) {
			throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR, "Different Element");
		}

		if (node instanceof Element) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "An attempt is made to modify an object where modifications are not allowed.");
		}

		if(owner.getNodeType() == Node.DOCUMENT_NODE &&
				!Objects.equals(node.getOwnerDocument(), owner.getOwnerDocument())) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
		}

		if (node instanceof Attr) {
			Attr attr = (Attr) node;
			final TextImpl t = new TextImpl(attr.getValue());
			t.setOwnerDocument(attr.getOwnerDocument());
			t.setParentImpl(attr.getParentNode());
			attr.appendChild(t);
			Node check = getNamedItem(attr.getNodeName());
			if (check != null) {
				this.attributes.remove(check);
				this.attributes.add(node);
				return node;
			}
		}

		this.attributes.add(node);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public Node setNamedItemNS(Node node) throws DOMException {
		Node element = node instanceof Attr ? ((Attr) node).getOwnerElement() : null;

		if (owner.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
		}

		if (!Objects.equals(node.getOwnerDocument(), owner.getOwnerDocument())) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
		}

		if (element != null && !Objects.equals(element, owner)) {
			throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR, "Different Element");
		}

		if (node instanceof Attr) {
			Attr attr = (Attr) node;
			final TextImpl t = new TextImpl(attr.getValue());
			t.setOwnerDocument(attr.getOwnerDocument());
			t.setParentImpl(attr.getParentNode());
			attr.appendChild(t);
			Node check = getNamedItemNS(attr.getNamespaceURI(), attr.getLocalName());
			if (check != null) {
				this.attributes.remove(check);
				this.attributes.add(node);
				return node;
			}
		}

		this.attributes.add(node);
		return node;
	}

	public NodeList getAttributes() {
		return this.attributes;
	}

	@Override
	public String toString() {
		return "[object NamedNodeMap]";
	}
}
