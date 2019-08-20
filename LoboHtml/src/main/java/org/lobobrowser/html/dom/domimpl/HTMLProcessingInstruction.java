/*
 * HtmlProcessingInstruction.java
 * Selima Prague FBI Project
 * 5th-March-2008
 */
package org.lobobrowser.html.dom.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;

/**
 * HTML DOM object representing processing instruction as per HTML 4.0
 * specification.
 * 
 * @author vitek
 */
public class HTMLProcessingInstruction extends NodeImpl implements ProcessingInstruction, Cloneable {
	String data;
	String target;

	public HTMLProcessingInstruction(String target, String data) {
		this.target = target;
		this.data = data;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (final CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	protected Node createSimilarNode() {
		return (Node) clone();
	}

	@Override
	public String getData() {
		return this.data;
	}

	@Override
	public String getLocalName() {
		return this.target;
	}

	@Override
	public String getNodeName() {
		return this.target;
	}

	@Override
	public short getNodeType() {
		return Node.PROCESSING_INSTRUCTION_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return this.data;
	}

	@Override
	public String getTarget() {
		return this.target;
	}

	@Override
	public void setData(String data) throws DOMException {
		this.data = data;
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		this.data = nodeValue;
	}

	@Override
	public String toString() {
		return "<?" + this.target + " " + this.data + ">";
	}
}
