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

	private final Map<String, Attr> attributes = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	private Element owner;

	/**
	 * <p>Constructor for NamedNodeMapImpl.</p>
	 *
	 * @param owner a {@link org.loboevolution.html.node.Element} object.
	 * @param attribs a {@link java.util.Map} object.
	 */
	public NamedNodeMapImpl(Element owner, Map<?, ?> attribs) {
		this.owner = owner;
		for (Map.Entry entry : attribs.entrySet()) {
			final String name = (String) entry.getKey();
			final String value = (String) entry.getValue();
			final Attr attr = new AttrImpl(name, value, "id".equalsIgnoreCase(name), null, true);
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
	public Attr getNamedItem(String name) {
		if (Strings.isNotBlank(name)) {
			for (Map.Entry<String, Attr> entry : attributes.entrySet()) {
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
	public Attr getNamedItemNS(String namespaceURI, String localName) {
		if (Strings.isNotBlank(localName)) {
			for (Map.Entry entry : this.attributes.entrySet()) {
				Attr attr = (Attr) entry.getValue();
				if (Strings.isBlank(namespaceURI) || namespaceURI.equals(attr.getNamespaceURI())) {
					if (attr.getName().equalsIgnoreCase(localName)) {
						return attr;
					}

					if (attr.getName().contains(":")) {
						String attribute = attr.getName().split(":")[1];
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
	public Attr item(int index) {
		int size = this.attributes.size();
		AtomicInteger atomIndex = new AtomicInteger(0);
		for (Map.Entry<String, Attr> entry : attributes.entrySet()) {
			if (size > atomIndex.getAndIncrement() && index > -1) {
				return entry.getValue();
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Attr removeNamedItem(String name) {
		return this.attributes.remove(name);
	}

	/** {@inheritDoc} */
	@Override
	public Attr removeNamedItemNS(String namespaceURI, String localName) {
		for (Map.Entry entry : this.attributes.entrySet()) {
			Attr attr = (Attr) entry.getValue();
			if (namespaceURI == null || namespaceURI.equals(attr.getNamespaceURI())) {
				if (attr.getName().equalsIgnoreCase(localName)) {
					return this.attributes.remove(attr.getName());
				}

				if (attr.getName().contains(":")) {
					return this.attributes.remove(attr.getName());
				}
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Attr setNamedItem(Node attr) {

		if(attr == null) {
			throw new DOMException(DOMException.INUSE_ATTRIBUTE_ERR, "The Attr is null");
		}
		if(!(attr instanceof Attr)) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot set node.");
		}
		Attr attribute = (Attr) attr;
		this.attributes.put(attr.getNodeName(), attribute);
		return attribute;
	}

	/** {@inheritDoc} */
	@Override
	public Attr setNamedItemNS(Attr attr) {
		return setNamedItem(attr);
	}

	public Map<String, Attr> getAttributes() {
		return this.attributes;
	}

	@Override
	public String toString() {
		return "[object NamedNodeMap]";
	}
}
