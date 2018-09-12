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
package org.loboevolution.html.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;

/**
 * HTML DOM object representing processing instruction as per HTML 4.0
 * specification.
 *
 * @author vitek
 */
public class HTMLProcessingInstruction extends DOMNodeImpl implements ProcessingInstruction, Cloneable {

	/** The target. */
	private String target;

	/** The data. */
	private String data;

	/**
	 * Instantiates a new HTML processing instruction.
	 *
	 * @param target
	 *            the target
	 * @param data
	 *            the data
	 */
	public HTMLProcessingInstruction(String target, String data) {
		this.target = target;
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#createSimilarNode()
	 */
	@Override
	protected Node createSimilarNode() {
		return new HTMLProcessingInstruction(target, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getLocalName()
	 */
	@Override
	public String getLocalName() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getNodeType()
	 */
	@Override
	public short getNodeType() {
		return Node.PROCESSING_INSTRUCTION_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getNodeValue()
	 */
	@Override
	public String getNodeValue() throws DOMException {
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.domimpl.DOMNodeImpl#setNodeValue(java.lang.String)
	 */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		this.data = nodeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.ProcessingInstruction#getData()
	 */
	@Override
	public String getData() {
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.ProcessingInstruction#getTarget()
	 */
	@Override
	public String getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.ProcessingInstruction#setData(java.lang.String)
	 */
	@Override
	public void setData(String data) throws DOMException {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#toString()
	 */
	@Override
	public String toString() {
		return "<?" + target + " " + data + ">";
	}
}
