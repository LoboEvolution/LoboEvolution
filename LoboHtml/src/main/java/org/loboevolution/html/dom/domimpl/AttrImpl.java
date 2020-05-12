/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Sep 10, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

/**
 * <p>AttrImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class AttrImpl extends NodeImpl implements Attr {
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
	public AttrImpl(String name) {
		super();
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
		super();
		this.name = name;
		this.value = value;
		this.specified = specified;
		this.ownerElement = owner;
		this.isId = isId;
	}

	/** {@inheritDoc} */
	@Override
	protected Node createSimilarNode() {
		return new AttrImpl(this.name, this.value, this.specified, this.ownerElement, this.isId);
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
	public short getNodeType() {
		return Node.ATTRIBUTE_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() throws DOMException {
		return this.value;
	}

	/** {@inheritDoc} */
	@Override
	public Element getOwnerElement() {
		return this.ownerElement;
	}

	/** {@inheritDoc} */
	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/** {@inheritDoc} */
	@Override
	public boolean getSpecified() {
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
	public void setNodeValue(String nodeValue) throws DOMException {
		this.value = nodeValue;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) throws DOMException {
		this.value = value;
	}
}
