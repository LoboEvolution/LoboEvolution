/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

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

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLFormElement;
import org.lobobrowser.html.w3c.HTMLKeygenElement;
import org.lobobrowser.html.w3c.ValidityState;
import org.w3c.dom.NodeList;

public class HTMLKeygenElementImpl extends HTMLElementImpl implements
		HTMLKeygenElement {

	public HTMLKeygenElementImpl(String name) {
		super(name);
	}

	@Override
	public boolean getAutofocus() {
		String auto = this.getAttribute(HtmlAttributeProperties.AUTOFOCUS);
		return HtmlAttributeProperties.AUTOFOCUS.equalsIgnoreCase(auto);
	}

	@Override
	public void setAutofocus(boolean autofocus) {
		this.setAttribute(HtmlAttributeProperties.AUTOFOCUS, autofocus ? HtmlAttributeProperties.AUTOFOCUS : null);		
	}

	@Override
	public String getChallenge() {
		return this.getAttribute(HtmlAttributeProperties.CHALLENGE);
	}

	@Override
	public void setChallenge(String challenge) {
		this.setAttribute(HtmlAttributeProperties.CHALLENGE, challenge);
		
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.setAttribute(HtmlAttributeProperties.DISABLE, disabled ? HtmlAttributeProperties.DISABLE : null);		
	}

	@Override
	public HTMLFormElement getForm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKeytype() {
		return this.getAttribute(HtmlAttributeProperties.KEYTYPE);
	}

	@Override
	public void setKeytype(String keytype) {
		this.setAttribute(HtmlAttributeProperties.KEYTYPE,keytype);
		
	}

	@Override
	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	@Override
	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME,name);
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getWillValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

}
