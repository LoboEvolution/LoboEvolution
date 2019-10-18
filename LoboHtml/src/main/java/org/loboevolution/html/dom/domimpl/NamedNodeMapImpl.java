/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NamedNodeMapImpl extends AbstractScriptableDelegate implements NamedNodeMap {
	
	private final ArrayList<Node> attributeList = new ArrayList<Node>();
	private final Map<String, Node> attributes = new HashMap<String, Node>();

	public NamedNodeMapImpl(Element owner, Map<?, ?> attribs) {
		final Iterator<?> i = attribs.entrySet().iterator();
		while (i.hasNext()) {
			final Map.Entry entry = (Map.Entry) i.next();
			final String name = (String) entry.getKey();
			final String value = (String) entry.getValue();
			// TODO: "specified" attributes
			final Attr attr = new AttrImpl(name, value, true, owner, "ID".equals(name));
			this.attributes.put(name, attr);
			this.attributeList.add(attr);
		}
	}

	@Override
	public int getLength() {
		return this.attributeList.size();
	}

	@Override
	public Node getNamedItem(String name) {
		return (Node) this.attributes.get(name);
	}

	@Override
	public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No namespace support");
	}

	@Override
	public Node item(int index) {
		int size = this.attributeList.size();
        if (size > index && index > -1) {
            return (Node) this.attributeList.get(index);
        } else {
            return null;
        }
	}

	/**
	 * @param name
	 */
	public Node namedItem(String name) {
		// Method needed for Javascript indexing.
		return getNamedItem(name);
	}

	@Override
	public Node removeNamedItem(String name) throws DOMException {
		return (Node) this.attributes.remove(name);
	}

	@Override
	public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No namespace support");
	}

	@Override
	public Node setNamedItem(Node arg) throws DOMException {
		final Object prevValue = this.attributes.put(arg.getNodeName(), arg);
		if (prevValue != null) {
			this.attributeList.remove(prevValue);
		}
		this.attributeList.add(arg);
		return arg;
	}

	@Override
	public Node setNamedItemNS(Node arg) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No namespace support");
	}
}
