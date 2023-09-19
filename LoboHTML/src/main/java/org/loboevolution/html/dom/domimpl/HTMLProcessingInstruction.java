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
 * HtmlProcessingInstruction.java
 * Selima Prague FBI Project
 * 5th-March-2008
 */
package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.ProcessingInstruction;

/**
 * HTML DOM object representing processing instruction as per HTML 4.0
 * specification.
 */
public class HTMLProcessingInstruction extends HTMLElementImpl implements ProcessingInstruction {

	private String data;
	private String target;

	/**
	 * <p>Constructor for HTMLButtonElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLProcessingInstruction(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getData() {
		return this.data;
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return this.target;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return this.target;
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.PROCESSING_INSTRUCTION_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
		return this.data;
	}

	/** {@inheritDoc} */
	@Override
	public String getTarget() {
		return this.target;
	}

	/** {@inheritDoc} */
	@Override
	public void setData(String data) {
		this.data = data;
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) {
		this.data = nodeValue;
	}

	/**
	 * <p>Setter for the field <code>target</code>.</p>
	 *
	 * @param target a {@link java.lang.String} object.
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void appendData(String data) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void deleteData(int offset, int count) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void insertData(int offset, String data) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void replaceData(int offset, int count, String data) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public String substringData(int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getClientHeight() {
		int clientHeight = super.getClientHeight();
		return clientHeight == 0 ? 16 : clientHeight;
	}

	@Override
	public Node appendChild(Node newChild) {
		throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append node.");
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLProcessingElement]";
	}
}
