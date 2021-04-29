/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.html.dom.domimpl.AttrImpl;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Code;

import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

/**
 * <p>NamedNodeMapImpl class.</p>
 *
 *
 *
 */
public class NamedNodeMapImpl extends AbstractScriptableDelegate implements NamedNodeMap {
	
	private final List<Attr> attributeList = new ArrayList<>();
	private final Map<String, Attr> attributes = new HashMap<>();

	/**
	 * <p>Constructor for NamedNodeMapImpl.</p>
	 *
	 * @param owner a {@link org.w3c.dom.Element} object.
	 * @param attribs a {@link java.util.Map} object.
	 */
	public NamedNodeMapImpl(Element owner, Map<?, ?> attribs) {
		for (Map.Entry entry : attribs.entrySet()) {
			final String name = (String) entry.getKey();
			final String value = (String) entry.getValue();
			final Attr attr = new AttrImpl(name, value, true, owner, "ID".equals(name));
			this.attributes.put(name, attr);
			this.attributeList.add(attr);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return this.attributeList.size();
	}

	/** {@inheritDoc} */
	@Override
	public Attr getNamedItem(String name) {
		return this.attributes.get(name);
	}

	/** {@inheritDoc} */
	@Override
	public Attr getNamedItemNS(String namespaceURI, String localName) {
		throw new DOMException(Code.NOT_SUPPORTED_ERR, "No namespace support");
	}

	/** {@inheritDoc} */
	@Override
	public Attr item(int index) {
		int size = this.attributeList.size();
        if (size > index && index > -1) {
            return this.attributeList.get(index);
        } else {
            return null;
        }
	}

	/**
	 * <p>namedItem.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	public Node namedItem(String name) {
		// Method needed for Javascript indexing.
		return getNamedItem(name);
	}

	/** {@inheritDoc} */
	@Override
	public Attr removeNamedItem(String name) {
		return this.attributes.remove(name);
	}

	/** {@inheritDoc} */
	@Override
	public Attr removeNamedItemNS(String namespaceURI, String localName) {
		throw new DOMException(Code.NOT_SUPPORTED_ERR, "No namespace support");
	}

	/** {@inheritDoc} */
	@Override
	public Attr setNamedItem(Attr arg) {
		final Object prevValue = this.attributes.put(arg.getNodeName(), arg);
		if (prevValue != null) {
			this.attributeList.remove(prevValue);
		}
		this.attributeList.add(arg);
		return arg;
	}

	/** {@inheritDoc} */
	@Override
	public Attr setNamedItemNS(Attr arg) {
		throw new DOMException(Code.NOT_SUPPORTED_ERR, "No namespace support");
	}
}
