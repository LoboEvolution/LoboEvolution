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

import org.loboevolution.w3c.html.DOMSettableTokenList;
import org.loboevolution.w3c.html.HTMLFormElement;
import org.loboevolution.w3c.html.HTMLOutputElement;
import org.loboevolution.w3c.html.ValidityState;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class HTMLOutputElementImpl.
 */
public class HTMLOutputElementImpl extends HTMLElementImpl implements HTMLOutputElement {

	/**
	 * Instantiates a new HTML output element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLOutputElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getHtmlFor()
	 */
	@Override
	public DOMSettableTokenList getHtmlFor() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLOutputElement#setHtmlFor(java.lang.String)
	 */
	@Override
	public void setHtmlFor(String htmlFor) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getForm()
	 */
	@Override
	public HTMLFormElement getForm() {
		Node parent = this.getParentNode();
		while (parent != null && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getType()
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getDefaultValue()
	 */
	@Override
	public String getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLOutputElement#setDefaultValue(java.lang.
	 * String)
	 */
	@Override
	public void setDefaultValue(String defaultValue) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getValue()
	 */
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLOutputElement#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getWillValidate()
	 */
	@Override
	public boolean getWillValidate() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getValidity()
	 */
	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getValidationMessage()
	 */
	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#checkValidity()
	 */
	@Override
	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLOutputElement#setCustomValidity(java.lang.
	 * String )
	 */
	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLOutputElement#getLabels()
	 */
	@Override
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

}
