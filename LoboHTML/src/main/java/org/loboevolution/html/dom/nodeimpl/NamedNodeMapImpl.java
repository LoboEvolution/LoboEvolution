/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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
import org.loboevolution.common.Nodes;
import org.loboevolution.common.Objects;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.node.*;
import org.loboevolution.js.AbstractScriptableDelegate;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>NamedNodeMapImpl class.</p>
 */
public class NamedNodeMapImpl extends AbstractScriptableDelegate implements NamedNodeMap {

	private final Map<String, Node> attributes = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	private Node owner;

	/**
	 * <p>Constructor for NamedNodeMapImpl.</p>
	 *
	 * @param owner a {@link org.loboevolution.html.node.Node} object.
	 * @param attribs a {@link java.util.Map} object.
	 */
	public NamedNodeMapImpl(Node owner, Map<?, ?> attribs) {
		this.owner = owner;
		for (Map.Entry entry : attribs.entrySet()) {
			final String name = (String) entry.getKey();
			final String value = (String) entry.getValue();
			final Node attr = new AttrImpl(name, value, "id".equalsIgnoreCase(name), this.owner,  true);
			this.attributes.put(name, attr);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.attributes.size();
	}

	/** {@inheritDoc} */
	@Override
	public Node getNamedItem(String name) {
		if (Strings.isNotBlank(name)) {
			for (Map.Entry<String, Node> entry : attributes.entrySet()) {
				String key = entry.getKey();

				if (key.contains(":")) {
					if (key.split(":")[1].equalsIgnoreCase(name)) {
						return entry.getValue();
					}
				} else {
					if (key.equalsIgnoreCase(name)){
						return entry.getValue();
					}
				}
			}
			return null;
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node getNamedItemNS(String namespaceURI, String localName) {
		if (Strings.isNotBlank(localName)) {
			for (Map.Entry entry : this.attributes.entrySet()) {
				Node attr = (Node) entry.getValue();
				String namespace = attr.getNamespaceURI();
				String parentNamespace = owner.getParentNode() != null ? owner.getParentNode().getNamespaceURI() : null;

				if (Strings.isBlank(namespaceURI) || namespaceURI.equals(namespace) ||  namespaceURI.equals(parentNamespace)) {
					if (attr.getNodeName().equalsIgnoreCase(localName)) {
						return attr;
					}

					if (attr.getNodeName().contains(":")) {
						String attribute = attr.getNodeName().split(":")[1];
						if (attribute.equalsIgnoreCase(localName)) {
							return attr;
						}
					}
				}
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node item(int index) {
		int size = this.attributes.size();
		AtomicInteger atomIndex = new AtomicInteger(0);
		for (Map.Entry<String, Node> entry : attributes.entrySet()) {
			if (size > atomIndex.getAndIncrement() && index > -1) {
				return entry.getValue();
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node removeNamedItem(String name) throws DOMException {

		for (Map.Entry<String, Node> entry : attributes.entrySet()) {
			String key = entry.getKey();
			if (key.contains(":")) {
				if (key.split(":")[1].equalsIgnoreCase(name)) {
					name = key;
				}
			}
		}

		Node node = this.attributes.remove(name);
		if (node == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not found.");
		}
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
		for (Map.Entry entry : this.attributes.entrySet()) {
			Node attr = (Node) entry.getValue();
			String namespace = attr.getNamespaceURI();
			String parentNamespace = owner.getParentNode() != null ? owner.getParentNode().getNamespaceURI() : null;

			if (Strings.isBlank(namespaceURI) || namespaceURI.equals(namespace) ||  namespaceURI.equals(parentNamespace)) {
				if (attr.getNodeName().equalsIgnoreCase(localName)) {
					return this.attributes.remove(attr.getNodeName());
				}

				if (attr.getNodeName().contains(":")) {
					return this.attributes.remove(attr.getNodeName());
				}
			}
		}
		throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not found.");
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

		this.attributes.put(node.getNodeName(), node);
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public Node setNamedItemNS(Node node) throws DOMException {
		Node element = node instanceof Attr ? ((Attr) node).getOwnerElement() : null;

		if(owner.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
		}

		if(!Objects.equals(node.getOwnerDocument(), owner.getOwnerDocument())) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
		}

		if (element != null && !Objects.equals(element, owner)) {
			throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR, "Different Element");
		}

		this.attributes.put(node.getNodeName(), node);
		return node;
	}

	public Map<String, Node> getAttributes() {
		return this.attributes;
	}

	@Override
	public String toString() {
		return "[object NamedNodeMap]";
	}
}
