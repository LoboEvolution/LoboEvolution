/*
 * HtmlProcessingInstruction.java
 * Selima Prague FBI Project
 * 5th-March-2008
 */
package org.lobobrowser.html.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;


/**
 * HTML DOM object representing processing instruction as per HTML 4.0
 * specification.
 * 
 * @author vitek
 */
public class HTMLProcessingInstruction extends DOMNodeImpl implements
		ProcessingInstruction, Cloneable {
	
	/** The target. */
	String target;
	
	/** The data. */
	String data;

	/**
	 * Instantiates a new HTML processing instruction.
	 *
	 * @param target the target
	 * @param data the data
	 */
	public HTMLProcessingInstruction(String target, String data) {
		this.target = target;
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#createSimilarNode()
	 */
	protected Node createSimilarNode() {
		return (Node) clone();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getLocalName()
	 */
	public String getLocalName() {
		return target;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeName()
	 */
	public String getNodeName() {
		return target;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeType()
	 */
	public short getNodeType() {
		return Node.PROCESSING_INSTRUCTION_NODE;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeValue()
	 */
	public String getNodeValue() throws DOMException {
		return data;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#setNodeValue(java.lang.String)
	 */
	public void setNodeValue(String nodeValue) throws DOMException {
		this.data = nodeValue;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.ProcessingInstruction#getData()
	 */
	public String getData() {
		return data;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.ProcessingInstruction#getTarget()
	 */
	public String getTarget() {
		return target;
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.ProcessingInstruction#setData(java.lang.String)
	 */
	public void setData(String data) throws DOMException {
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		try {
			return (HTMLProcessingInstruction) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#toString()
	 */
	public String toString() {
		return "<?" + target + " " + data + ">";
	}
}
