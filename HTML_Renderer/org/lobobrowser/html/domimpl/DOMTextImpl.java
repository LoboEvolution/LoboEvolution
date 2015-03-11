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
 * Created on Sep 4, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.util.Strings;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;


/**
 * The Class DOMTextImpl.
 */
public class DOMTextImpl extends DOMCharacterDataImpl implements Text {
	
	/**
	 * Instantiates a new DOM text impl.
	 */
	public DOMTextImpl() {
		this("");
	}

	/**
	 * Instantiates a new DOM text impl.
	 *
	 * @param text the text
	 */
	public DOMTextImpl(String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.w3c.Text#isElementContentWhitespace()
	 */
	public boolean isElementContentWhitespace() {
		String t = this.text;
		return t == null || t.trim().equals("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.w3c.Text#replaceWholeText(String)
	 */
	public Text replaceWholeText(String content) throws DOMException {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
					"Text node has no parent");
		}
		return parent.replaceAdjacentTextNodes(this, content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.w3c.Text#splitText(int)
	 */
	public Text splitText(int offset) throws DOMException {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
					"Text node has no parent");
		}
		String t = this.text;
		if (offset < 0 || offset > t.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Bad offset: "
					+ offset);
		}
		String content1 = t.substring(0, offset);
		String content2 = t.substring(offset);
		this.text = content1;
		DOMTextImpl newNode = new DOMTextImpl(content2);
		newNode.setOwnerDocument(this.document);
		return (Text) parent.insertAfter(newNode, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.w3c.Text#getwholeText()
	 */
	public String getWholeText() {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
					"Text node has no parent");
		}
		return parent.getTextContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getlocalName()
	 */
	public String getLocalName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getnodeName()
	 */
	public String getNodeName() {
		return "#text";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getnodeType()
	 */
	public short getNodeType() {
		return Node.TEXT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#getnodeValue()
	 */
	public String getNodeValue() throws DOMException {
		return this.text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.DOMNodeImpl#setnodeValue(String)
	 */
	public void setNodeValue(String nodeValue) throws DOMException {
		this.text = nodeValue;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMCharacterDataImpl#setTextContent(java.lang.String)
	 */
	public void setTextContent(String textContent) throws DOMException {
		this.text = textContent;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#createSimilarNode()
	 */
	protected Node createSimilarNode() {
		return new DOMTextImpl(this.text);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMCharacterDataImpl#toString()
	 */
	public String toString() {
		String text = this.text;
		int textLength = text == null ? 0 : text.length();
		return "#text[length=" + textLength + ",value=\""
				+ Strings.truncate(text, 64) + "\",renderState="
				+ this.getRenderState() + "]";
	}
}
