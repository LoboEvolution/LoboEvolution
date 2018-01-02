/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 4, 2005
 */
package org.loboevolution.html.domimpl;

import org.loboevolution.util.Strings;
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
	 * @param text
	 *            the text
	 */
	public DOMTextImpl(String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.Text#isElementContentWhitespace()
	 */
	@Override
	public boolean isElementContentWhitespace() {
		String t = this.text;
		return t == null || t.trim().equals("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.Text#replaceWholeText(String)
	 */
	@Override
	public Text replaceWholeText(String content) throws DOMException {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Text node has no parent");
		}
		return parent.replaceAdjacentTextNodes(this, content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.Text#splitText(int)
	 */
	@Override
	public Text splitText(int offset) throws DOMException {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Text node has no parent");
		}
		String t = this.text;
		if (offset < 0 || offset > t.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Bad offset: " + offset);
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
	 * @see org.loboevolution.w3c.html.Text#getwholeText()
	 */
	@Override
	public String getWholeText() {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Text node has no parent");
		}
		return parent.getTextContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DOMNodeImpl#getlocalName()
	 */
	@Override
	public String getLocalName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DOMNodeImpl#getnodeName()
	 */
	@Override
	public String getNodeName() {
		return "#text";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DOMNodeImpl#getnodeType()
	 */
	@Override
	public short getNodeType() {
		return Node.TEXT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DOMNodeImpl#getnodeValue()
	 */
	@Override
	public String getNodeValue() throws DOMException {
		return this.text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DOMNodeImpl#setnodeValue(String)
	 */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		this.text = nodeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.domimpl.DOMCharacterDataImpl#setTextContent(java.
	 * lang .String)
	 */
	@Override
	public void setTextContent(String textContent) throws DOMException {
		this.text = textContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#createSimilarNode()
	 */
	@Override
	protected Node createSimilarNode() {
		return new DOMTextImpl(this.text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMCharacterDataImpl#toString()
	 */
	@Override
	public String toString() {
		String text = this.text;
		int textLength = text == null ? 0 : text.length();
		return "#text[length=" + textLength + ",value=\"" + Strings.truncate(text, 64) + "\",renderState="
				+ this.getRenderState() + "]";
	}
}
