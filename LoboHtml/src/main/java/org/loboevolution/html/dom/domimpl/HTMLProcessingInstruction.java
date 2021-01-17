/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

/*
 * HtmlProcessingInstruction.java
 * Selima Prague FBI Project
 * 5th-March-2008
 */
package org.loboevolution.html.dom.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;

/**
 * HTML DOM object representing processing instruction as per HTML 4.0
 * specification.
 *
 * @author vitek
 * @version $Id: $Id
 */
public class HTMLProcessingInstruction extends NodeImpl implements ProcessingInstruction, Cloneable {
	String data;
	final String target;

	/**
	 * <p>Constructor for HTMLProcessingInstruction.</p>
	 *
	 * @param target a {@link java.lang.String} object.
	 * @param data a {@link java.lang.String} object.
	 */
	public HTMLProcessingInstruction(String target, String data) {
		this.target = target;
		this.data = data;
	}

	/** {@inheritDoc} */
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (final CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected Node createSimilarNode() {
		return (Node) clone();
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
	public short getNodeType() {
		return Node.PROCESSING_INSTRUCTION_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() throws DOMException {
		return this.data;
	}

	/** {@inheritDoc} */
	@Override
	public String getTarget() {
		return this.target;
	}

	/** {@inheritDoc} */
	@Override
	public void setData(String data) throws DOMException {
		this.data = data;
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		this.data = nodeValue;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLProcessingElement]";
	}
}
