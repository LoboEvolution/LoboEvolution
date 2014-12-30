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
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.dombl.InputContext;
import org.lobobrowser.html.w3c.HTMLTextAreaElement;
import org.lobobrowser.html.w3c.ValidityState;
import org.w3c.dom.NodeList;

public class HTMLTextAreaElementImpl extends HTMLBaseInputElement implements
		HTMLTextAreaElement {
	public HTMLTextAreaElementImpl(String name) {
		super(name);
	}

	public HTMLTextAreaElementImpl() {
		super(HtmlProperties.TEXTAREA);
	}

	protected FormInput[] getFormInputs() {
		String name = this.getName();
		if (name == null) {
			return null;
		}
		return new FormInput[] { new FormInput(name, this.getValue()) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.w3c.HTMLTextAreaElement#getCols()
	 */
	public int getCols() {
		InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getCols();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.w3c.HTMLTextAreaElement#getRows()
	 */
	public int getRows() {
		InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getRows();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.w3c.HTMLTextAreaElement#getType()
	 */
	public String getType() {
		return "textarea";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.w3c.HTMLTextAreaElement#setCols(int)
	 */
	public void setCols(int cols) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setCols(cols);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.w3c.HTMLTextAreaElement#setRows(int)
	 */
	public void setRows(int rows) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setRows(rows);
		}
	}

	@Override
	public boolean getAutofocus() {
		String autofocus = this.getAttribute(HtmlAttributeProperties.AUTOFOCUS);
		return HtmlAttributeProperties.AUTOFOCUS.equalsIgnoreCase(autofocus);
	}

	@Override
	public void setAutofocus(boolean autofocus) {
		this.setAttribute(HtmlAttributeProperties.AUTOFOCUS, autofocus ? HtmlAttributeProperties.AUTOFOCUS : null);
		
	}

	@Override
	public int getMaxLength() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.MAXLENGTH));
		} catch (Exception thrown) {
			this.warn("getMaxLength(): Unable to parse size attribute in " + this + ".", thrown);
			return 0;
		}
	}

	@Override
	public void setMaxLength(int maxLength) {
		this.setAttribute(HtmlAttributeProperties.MAXLENGTH, String.valueOf(maxLength));
		
	}

	@Override
	public boolean getRequired() {
		String required = this.getAttribute(HtmlAttributeProperties.REQUIRED);
		return HtmlAttributeProperties.REQUIRED.equalsIgnoreCase(required);
	}

	@Override
	public void setRequired(boolean required) {
		this.setAttribute(HtmlAttributeProperties.REQUIRED, required ? HtmlAttributeProperties.REQUIRED : null);
		
	}

	@Override
	public String getWrap() {
		return this.getAttribute(HtmlAttributeProperties.WRAP);
	}

	@Override
	public void setWrap(String wrap) {
		this.setAttribute(HtmlAttributeProperties.WRAP,wrap);
		
	}

	@Override
	public int getTextLength() {
		return this.getValue().length();
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

	@Override
	public int getSelectionStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSelectionStart(int selectionStart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSelectionEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSelectionEnd(int selectionEnd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectionRange(int start, int end) {
		// TODO Auto-generated method stub
		
	}
}
