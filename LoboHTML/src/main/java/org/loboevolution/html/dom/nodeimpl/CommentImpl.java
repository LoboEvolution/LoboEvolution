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
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.Node;

/**
 * <p>CommentImpl class.</p>
 */
public class CommentImpl extends CharacterDataImpl implements Comment {
	/**
	 * <p>Constructor for CommentImpl.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	public CommentImpl(String text) {
		super(text);
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#comment";
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.COMMENT_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
		return getTextContent();
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) {
		setTextContent(nodeValue);
	}

	@Override
	public Node appendChild(Node newChild) {
		throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "This node type does not support this method.");
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object Comment]";
	}
}
