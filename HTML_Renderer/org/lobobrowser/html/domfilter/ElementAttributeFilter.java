/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

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

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.domfilter;

import org.lobobrowser.html.dombl.AttrImpl;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.w3c.HTMLElement;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElementAttributeFilter {
	private final String attribute;
	private final NodeList nodeList;

	public ElementAttributeFilter(NodeList nodeList, String attribute) {
		this.attribute = attribute;
		this.nodeList = nodeList;
	}
	
	public ElementAttributeFilter(String attribute) {
		this.attribute = attribute;
		nodeList = null;
	}

	public String getAttribute() {

		String result = "";

		Node node = nodeList.item(0);
		NamedNodeMap attribs = node.getAttributes();

		for (int s = 0; s < attribs.getLength(); s++) {
			Attr attr = (Attr) attribs.item(s);

			if (attribute.equalsIgnoreCase(attr.getNodeName()))
				result = attr.getNodeValue();
		}

		return result;

	}

	public void setAttribute(HTMLDocumentImpl doc,String val) {
		boolean result = false;
		HTMLElement el = doc.getBody();
		NamedNodeMap attributes = el.getAttributes();
		for (int s = 0; s < attributes.getLength(); s++) {
			Attr attr = (Attr) attributes.item(s);
			
			if(attribute.equals(attr.getNodeName())){
				attr.setNodeValue(val);
				el.setAttributeNode(attr);
				result = true;
			}
		}
		
		if(!result){			
			Attr attr = new AttrImpl(attribute, val, true, el,true);
			el.setAttributeNode(attr);
		}
		doc.setBody(el);
	}
}