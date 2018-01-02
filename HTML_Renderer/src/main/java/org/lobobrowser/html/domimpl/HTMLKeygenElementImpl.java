/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.domimpl;


import org.lobobrowser.w3c.html.HTMLFormElement;
import org.lobobrowser.w3c.html.HTMLKeygenElement;
import org.lobobrowser.w3c.html.ValidityState;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class HTMLKeygenElementImpl.
 */
public class HTMLKeygenElementImpl extends HTMLElementImpl implements HTMLKeygenElement {

	/**
	 * Instantiates a new HTML keygen element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLKeygenElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getAutofocus()
	 */
	@Override
	public boolean getAutofocus() {
		String auto = this.getAttribute(AUTOFOCUS);
		return AUTOFOCUS.equalsIgnoreCase(auto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#setAutofocus(boolean)
	 */
	@Override
	public void setAutofocus(boolean autofocus) {
		this.setAttribute(AUTOFOCUS, autofocus ? AUTOFOCUS : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getChallenge()
	 */
	@Override
	public String getChallenge() {
		return this.getAttribute(CHALLENGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLKeygenElement#setChallenge(java.lang.String)
	 */
	@Override
	public void setChallenge(String challenge) {
		this.setAttribute(CHALLENGE, challenge);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled) {
		this.setAttribute(DISABLE, disabled ? DISABLE : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getForm()
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
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getKeytype()
	 */
	@Override
	public String getKeytype() {
		return this.getAttribute(KEYTYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLKeygenElement#setKeytype(java.lang.String)
	 */
	@Override
	public void setKeytype(String keytype) {
		this.setAttribute(KEYTYPE, keytype);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getName()
	 */
	@Override
	public String getName() {
		return this.getAttribute(NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.setAttribute(NAME, name);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getType()
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getWillValidate()
	 */
	@Override
	public boolean getWillValidate() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getValidity()
	 */
	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getValidationMessage()
	 */
	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#checkValidity()
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
	 * org.lobobrowser.w3c.html.HTMLKeygenElement#setCustomValidity(java.lang.
	 * String )
	 */
	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLKeygenElement#getLabels()
	 */
	@Override
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

}
