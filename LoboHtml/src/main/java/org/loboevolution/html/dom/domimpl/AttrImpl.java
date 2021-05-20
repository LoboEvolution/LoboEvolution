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
 * Created on Sep 10, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;

/**
 * <p>AttrImpl class.</p>
 *
 *
 *
 */
public class AttrImpl extends AttrAbstract {
	
	private boolean isId;
	private final String name;
	private final Element ownerElement;
	private final boolean specified;
	private String value;

	/**
	 * <p>Constructor for AttrImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public AttrImpl(final String name) {
		this.name = name;
		this.value = "";
		this.specified = false;
		this.ownerElement = null;
		this.isId = false;
	}

	/**
	 * <p>Constructor for AttrImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @param specified a boolean.
	 * @param owner a {@link org.w3c.dom.Element} object.
	 * @param isId a boolean.
	 */
	public AttrImpl(String name, String value, boolean specified, Element owner, boolean isId) {
		this.name = name;
		this.value = value;
		this.specified = specified;
		this.ownerElement = owner;
		this.isId = isId;
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public NodeType getNodeType() {
		return NodeType.ATTRIBUTE_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
		return this.value;
	}

	/** {@inheritDoc} */
	@Override
	public Element getOwnerElement() {
		return this.ownerElement;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSpecified() {
		return this.specified;
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		return this.value;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isId() {
		return this.isId;
	}

	/**
	 * <p>setId.</p>
	 *
	 * @param value a boolean.
	 */
	public void setId(boolean value) {
		this.isId = value;
	}

	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) {
		this.value = nodeValue;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
