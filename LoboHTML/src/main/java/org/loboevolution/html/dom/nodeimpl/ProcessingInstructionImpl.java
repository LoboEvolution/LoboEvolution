/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.html.dom.nodeimpl;

import lombok.*;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.ProcessingInstruction;

/**
 * HTML DOM object representing processing instruction as per HTML 4.0
 * specification.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingInstructionImpl extends NodeImpl implements ProcessingInstruction {

	private String data;
	private String target;
	private String publicId;
	private String systemId;
	private String nodeName;
	private String nodeValue;
	private String notationName;
	private String localName;

	@Override
	public boolean hasAttributes() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return this.target;
	}

	/** {@inheritDoc} */
	@Override
	public void setData(String data) {
		if (data.contains("?>")) {
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Invalid PI data");
		}
		this.data = data;
	}

	/** {@inheritDoc} */
	@Override
	public String getData() {
		if (this.nodeValue == null) {
			return this.data;
		} else {
			return this.nodeValue;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
		return getData();
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void appendData(String data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteData(int offset, int count) {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertData(int offset, String data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void replaceData(int offset, int count, String data) {
		// TODO Auto-generated method stub
	}

	@Override
	public String substringData(int offset, int count) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Node appendChild(Node newChild) {

		int type = newChild.getNodeType();
		if (type == Node.DOCUMENT_TYPE_NODE || type == Node.DOCUMENT_FRAGMENT_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
					"Invalid child type for Attr");
		}

		return super.appendChild(newChild);
	}

	@Override
	public String getBaseURI() {
		final Node parent = getParentNode();
		if (parent != null) {
			return parent.getBaseURI();
		}
		return document.getBaseURI();
	}

	@Override
	public int getNodeType() {
		return Node.PROCESSING_INSTRUCTION_NODE;
	}

	@Override
	public String toString() {
		return "[object HTMLProcessingElement]";
	}
}
