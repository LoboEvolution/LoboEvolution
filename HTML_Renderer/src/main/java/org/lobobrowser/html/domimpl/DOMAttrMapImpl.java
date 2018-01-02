/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 3, 2005
 */
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.lobobrowser.js.AbstractScriptableDelegate;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * The Class DOMAttrMapImpl.
 */
public class DOMAttrMapImpl extends AbstractScriptableDelegate implements NamedNodeMap {

	/** The attributes. */
	private final Map<String, Node> attributes = new HashMap<String, Node>();

	/** The attribute list. */
	private final ArrayList attributeList = new ArrayList();

	/**
	 * Instantiates a new DOM attr map impl.
	 *
	 * @param owner
	 *            the owner
	 * @param attribs
	 *            the attribs
	 */
	public DOMAttrMapImpl(Element owner, Map attribs) {
		Iterator i = attribs.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry entry = (Map.Entry) i.next();
			String name = (String) entry.getKey();
			String value = (String) entry.getValue();
			// TODO: "specified" attributes
			Attr attr = new DOMAttrImpl(name, value, true, owner, "ID".equals(name));
			this.attributes.put(name, attr);
			this.attributeList.add(attr);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NamedNodeMap#getLength()
	 */
	@Override
	public int getLength() {
		return this.attributeList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NamedNodeMap#getNamedItem(java.lang.String)
	 */
	@Override
	public Node getNamedItem(String name) {
		return this.attributes.get(name);
	}

	/**
	 * Named item.
	 *
	 * @param name
	 *            the name
	 * @return the node
	 */
	public Node namedItem(String name) {
		// Method needed for Javascript indexing.
		return this.getNamedItem(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NamedNodeMap#getNamedItemNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No namespace support");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NamedNodeMap#item(int)
	 */
	@Override
	public Node item(int index) {
		try {
			return (Node) this.attributeList.get(index);
		} catch (IndexOutOfBoundsException iob) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NamedNodeMap#removeNamedItem(java.lang.String)
	 */
	@Override
	public Node removeNamedItem(String name) throws DOMException {
		return this.attributes.remove(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NamedNodeMap#removeNamedItemNS(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No namespace support");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NamedNodeMap#setNamedItem(org.w3c.dom.Node)
	 */
	@Override
	public Node setNamedItem(Node arg) throws DOMException {
		Object prevValue = this.attributes.put(arg.getNodeName(), arg);
		if (prevValue != null) {
			this.attributeList.remove(prevValue);
		}
		this.attributeList.add(arg);
		return arg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NamedNodeMap#setNamedItemNS(org.w3c.dom.Node)
	 */
	@Override
	public Node setNamedItemNS(Node arg) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No namespace support");
	}
}
