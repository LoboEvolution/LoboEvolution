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
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.common.Objects;
import org.loboevolution.common.Strings;
import org.loboevolution.html.node.*;
import org.loboevolution.js.AbstractScriptableDelegate;

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
