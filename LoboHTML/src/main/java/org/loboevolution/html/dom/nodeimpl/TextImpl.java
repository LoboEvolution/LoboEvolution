/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.nodeimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.HTMLSlotElement;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;

/**
 * <p>TextImpl class.</p>
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

	@Override
	public Node appendChild(Node newChild) {
		throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "This node type does not support this method.");
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#text";
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.TEXT_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
		return this.text;
	}

	/** {@inheritDoc} */
	@Override
	public String getWholeText() {
		final NodeImpl parent = (NodeImpl) getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Text node has no parent");
		}
		return parent.getTextContent();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isElementContentWhitespace() {
		final String t = this.text;
		return t == null || t.trim().equals("");
	}

	/** {@inheritDoc} */
	@Override
	public Text replaceWholeText(String content) {
		if (content == null) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "null content (use empty string instead)");
		}

		if ("".equals(content)) {
			return null;
		}

		final NodeImpl parent = (NodeImpl) getParentNode();
		if (parent == null) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Text node has no parent");
		}
		return parent.replaceAdjacentTextNodes(this, content);
	}

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

	/** {@inheritDoc} */
	@Override
	public Text splitText(int offset) {
		final NodeImpl parent = (NodeImpl) getParentNode();
		final String t = this.text;
		if (offset < 0 || offset > t.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Bad offset: " + offset);
		}
		final String content1 = t.substring(0, offset);
		final String content2 = t.substring(offset);
		this.text = content1;
		final TextImpl newNode = new TextImpl(content2);
		newNode.setOwnerDocument(this.document);

		if (parent != null) {
			return (Text) parent.insertAfter(newNode, this);
		}

		return newNode;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLSlotElement getAssignedSlot() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object Text]";
	}
}
