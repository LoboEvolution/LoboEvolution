/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
 * Created on Oct 9, 2005
 */
package org.lobobrowser.html.dombl;

import org.lobobrowser.html.domfilter.ElementIdFilter;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.w3c.HTMLDocumentFragment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentFragmentImpl extends NodeImpl implements DocumentFragment,HTMLDocumentFragment {
	public DocumentFragmentImpl() {
		super();
	}

	public String getLocalName() {
		return null;
	}

	public String getNodeName() {
		return "#document-fragment";
	}

	public String getNodeValue() throws DOMException {
		return null;
	}

	public void setNodeValue(String nodeValue) throws DOMException {
	}

	public short getNodeType() {
		return org.w3c.dom.Node.DOCUMENT_FRAGMENT_NODE;
	}

	protected Node createSimilarNode() {
		return new DocumentFragmentImpl();
	}

	@Override
	public Element querySelector(String selectors) {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		Element element = null;
		if (selectors.startsWith("#"))
			element = doc.getElementById(selectors.replace("#", ""));
		if (selectors.startsWith("."))
			element = (Element) doc.getElementsByClassName(selectors.replace(".", "")).item(0);
		return element;
	}

	@Override
	public NodeList querySelectorAll(String selectors) {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		NodeList list = null;

		if (selectors.contains(",")) {
			String[] strList = selectors.split(",");

			for (int i = 0; i < strList.length; i++) {

				String node = strList[i];

				if (list == null) {
					list = getList(doc, node);
				} else {// TODO}
				}
			}
		} else {
			list = getList(doc, selectors);
		}
		return list;
	}
	
	private NodeList getList(HTMLDocumentImpl doc,String selectors){
		NodeList list = null;
		
		if (selectors.startsWith("#")) {
			list = getNodeList(new ElementIdFilter(selectors.replace("#", "")));
		}
		if (selectors.startsWith(".")) {
			list = doc.getElementsByClassName(selectors.replace(".", ""));
		}
		
		return list;
	}
}
