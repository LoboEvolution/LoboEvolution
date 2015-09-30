/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 10, 2005
 */
package org.lobobrowser.html.domimpl;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

/**
 * The Class DOMAttrImpl.
 */
public class DOMAttrImpl extends DOMNodeImpl implements Attr {

	/** The name. */
	private String name;

	/** The value. */
	private String value;

	/** The specified. */
	private boolean specified;

	/** The owner element. */
	private Element ownerElement;

	/** The is id. */
	private boolean isId;

	/**
	 * Instantiates a new DOM attr impl.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param specified
	 *            the specified
	 * @param owner
	 *            the owner
	 * @param isId
	 *            the is id
	 */
	public DOMAttrImpl(String name, String value, boolean specified, Element owner, boolean isId) {
		super();
		this.name = name;
		this.value = value;
		this.specified = specified;
		this.ownerElement = owner;
		this.isId = isId;
	}

	/**
	 * Instantiates a new DOM attr impl.
	 *
	 * @param name
	 *            the name
	 */
	public DOMAttrImpl(String name) {
		super();
		this.name = name;
		this.value = "";
		this.specified = false;
		this.ownerElement = null;
		this.isId = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getLocalName()
	 */
	@Override
	public String getLocalName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeName()
	 */
	@Override
	public String getNodeName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeValue()
	 */
	@Override
	public String getNodeValue() throws DOMException {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.DOMNodeImpl#setNodeValue(java.lang.String)
	 */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		this.value = nodeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#getNodeType()
	 */
	@Override
	public short getNodeType() {
		return Node.ATTRIBUTE_NODE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Attr#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Attr#getSpecified()
	 */
	@Override
	public boolean getSpecified() {
		return this.specified;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Attr#getValue()
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Attr#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) throws DOMException {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Attr#getOwnerElement()
	 */
	@Override
	public Element getOwnerElement() {
		return this.ownerElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Attr#getSchemaTypeInfo()
	 */
	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Attr#isId()
	 */
	@Override
	public boolean isId() {
		return this.isId;
	}

	/**
	 * Sets the id.
	 *
	 * @param value
	 *            the new id
	 */
	public void setId(boolean value) {
		this.isId = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#createSimilarNode()
	 */
	@Override
	protected Node createSimilarNode() {
		return new DOMAttrImpl(this.name, this.value, this.specified, this.ownerElement, this.isId);
	}
}
