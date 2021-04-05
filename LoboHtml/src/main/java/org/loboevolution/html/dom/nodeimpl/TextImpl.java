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
 * Created on Sep 4, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLSlotElement;
import org.loboevolution.html.dom.domimpl.CharacterDataImpl;
import org.loboevolution.html.node.Code;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;
import org.loboevolution.html.node.Text;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventListener;

/**
 * <p>TextImpl class.</p>
 *
 *
 *
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
	public NodeType getNodeType() {
		return NodeType.TEXT_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.domimpl.NodeImpl#getnodeValue()
	 */
	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
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
			throw new DOMException(Code.HIERARCHY_REQUEST_ERR, "Text node has no parent");
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
	public Text replaceWholeText(String content) {
		final NodeImpl parent = (NodeImpl) getParentNode();
		if (parent == null) {
			throw new DOMException(Code.HIERARCHY_REQUEST_ERR, "Text node has no parent");
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
	public void setNodeValue(String nodeValue) {
		this.text = nodeValue;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(String textContent) {
		this.text = textContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dom.Text#splitText(int)
	 */
	/** {@inheritDoc} */
	@Override
	public Text splitText(int offset) {
		final NodeImpl parent = (NodeImpl) getParentNode();
		if (parent == null) {
			throw new DOMException(Code.HIERARCHY_REQUEST_ERR, "Text node has no parent");
		}
		final String t = this.text;
		if (offset < 0 || offset > t.length()) {
			throw new DOMException(Code.INDEX_SIZE_ERR, "Bad offset: " + offset);
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
		return "[object Text]";
	}

	/** {@inheritDoc} */
	@Override
	public HTMLSlotElement getAssignedSlot() {
		// TODO Auto-generated method stub
		return null;
	}
}
