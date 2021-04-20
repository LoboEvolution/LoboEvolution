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
 * HtmlProcessingInstruction.java
 * Selima Prague FBI Project
 * 5th-March-2008
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;
import org.loboevolution.html.node.ProcessingInstruction;

/**
 * HTML DOM object representing processing instruction as per HTML 4.0
 * specification.
 *
 * Author vitek
 *
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
	public NodeType getNodeType() {
		return NodeType.PROCESSING_INSTRUCTION_NODE;
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

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLProcessingElement]";
	}
}
