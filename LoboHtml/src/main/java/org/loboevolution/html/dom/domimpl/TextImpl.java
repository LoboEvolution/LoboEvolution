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
 * Created on Sep 4, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * <p>TextImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TextImpl extends CharacterDataImpl implements Text {
	/**
	 * <p>Constructor for TextImpl.</p>
	 */
	public TextImpl() {
		this("");
	}

	/**
	 * <p>Constructor for TextImpl.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	public TextImpl(String text) {
		this.text = text;
	}

	/** {@inheritDoc} */
	@Override
	protected Node createSimilarNode() {
		return new TextImpl(this.text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getlocalName()
	 */
	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getnodeName()
	 */
	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#text";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getnodeType()
	 */
	/** {@inheritDoc} */
	@Override
	public short getNodeType() {
		return Node.TEXT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getnodeValue()
	 */
	/** {@inheritDoc} */
	@Override
	public String getNodeValue() throws DOMException {
		return this.text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.Text#getwholeText()
	 */
	/** {@inheritDoc} */
	@Override
	public String getWholeText() {
		final NodeImpl parent = (NodeImpl) getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Text node has no parent");
		}
		return parent.getTextContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.Text#isElementContentWhitespace()
	 */
	/** {@inheritDoc} */
	@Override
	public boolean isElementContentWhitespace() {
		final String t = this.text;
		return t == null || t.trim().equals("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.Text#replaceWholeText(java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public Text replaceWholeText(String content) throws DOMException {
		final NodeImpl parent = (NodeImpl) getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Text node has no parent");
		}
		return parent.replaceAdjacentTextNodes(this, content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#setnodeValue(java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		this.text = nodeValue;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(String textContent) throws DOMException {
		this.text = textContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.Text#splitText(int)
	 */
	/** {@inheritDoc} */
	@Override
	public Text splitText(int offset) throws DOMException {
		final NodeImpl parent = (NodeImpl) getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Text node has no parent");
		}
		final String t = this.text;
		if (offset < 0 || offset > t.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Bad offset: " + offset);
		}
		final String content1 = t.substring(0, offset);
		final String content2 = t.substring(offset);
		this.text = content1;
		final TextImpl newNode = new TextImpl(content2);
		newNode.setOwnerDocument(this.document);
		return (Text) parent.insertAfter(newNode, this);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final String text = this.text;
		final int textLength = text == null ? 0 : text.length();
		return "#text[length=" + textLength + ",value=\"" + Strings.truncate(text, 64) + "\",renderState="
				+ getRenderState() + "]";
	}
}
