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
package org.lobobrowser.html.domimpl;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

public class AttrImpl extends NodeImpl implements Attr {
	private boolean isId;
	private final String name;
	private final Element ownerElement;
	private final boolean specified;
	private String value;

	/**
	 * @param name
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
	 * @param name
	 * @param value
	 */
	public AttrImpl(String name, String value, boolean specified, Element owner, boolean isId) {
		super();
		this.name = name;
		this.value = value;
		this.specified = specified;
		this.ownerElement = owner;
		this.isId = isId;
	}

	@Override
	protected Node createSimilarNode() {
		return new AttrImpl(this.name, this.value, this.specified, this.ownerElement, this.isId);
	}

	@Override
	public String getLocalName() {
		return this.name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getNodeName() {
		return this.name;
	}

	@Override
	public short getNodeType() {
		return Node.ATTRIBUTE_NODE;
	}

	@Override
	public String getNodeValue() throws DOMException {
		return this.value;
	}

	@Override
	public Element getOwnerElement() {
		return this.ownerElement;
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	@Override
	public boolean getSpecified() {
		return this.specified;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public boolean isId() {
		return this.isId;
	}

	public void setId(boolean value) {
		this.isId = value;
	}

	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		this.value = nodeValue;
	}

	@Override
	public void setValue(String value) throws DOMException {
		this.value = value;
	}
}
