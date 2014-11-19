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

import org.lobobrowser.html.w3c.HTMLFormElement;
import org.lobobrowser.html.w3c.HTMLKeygenElement;
import org.lobobrowser.html.w3c.ValidityState;
import org.w3c.dom.NodeList;

public class HTMLKeygenElementImpl extends HTMLElementImpl implements
		HTMLKeygenElement {

	public HTMLKeygenElementImpl(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean getAutofocus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutofocus(boolean autofocus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getChallenge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setChallenge(String challenge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDisabled(boolean disabled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HTMLFormElement getForm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKeytype() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKeytype(String keytype) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
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
