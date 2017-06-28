/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.dombl.InputContext;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.w3c.html.HTMLTextAreaElement;

/**
 * The Class HTMLTextAreaElementImpl.
 */
public class HTMLTextAreaElementImpl extends HTMLBaseInputElement implements HTMLTextAreaElement {

	/**
	 * Instantiates a new HTML text area element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLTextAreaElementImpl(String name) {
		super(name);
	}

	/**
	 * Instantiates a new HTML text area element impl.
	 */
	public HTMLTextAreaElementImpl() {
		super(HtmlProperties.TEXTAREA);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#getFormInputs()
	 */
	@Override
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
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#getCols()
	 */
	@Override
	public int getCols() {
		InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getCols();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#getRows()
	 */
	@Override
	public int getRows() {
		InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getRows();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#getType()
	 */
	@Override
	public String getType() {
		return "textarea";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#setCols(int)
	 */
	@Override
	public void setCols(int cols) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setCols(cols);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#setRows(int)
	 */
	@Override
	public void setRows(int rows) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setRows(rows);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#getAutofocus()
	 */
	@Override
	public boolean getAutofocus() {
		String autofocus = this.getAttribute(HtmlAttributeProperties.AUTOFOCUS);
		return HtmlAttributeProperties.AUTOFOCUS.equalsIgnoreCase(autofocus);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#setAutofocus(boolean)
	 */
	@Override
	public void setAutofocus(boolean autofocus) {
		this.setAttribute(HtmlAttributeProperties.AUTOFOCUS, autofocus ? HtmlAttributeProperties.AUTOFOCUS : null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#getMaxLength()
	 */
	@Override
	public int getMaxLength() {
		String maxLength = this.getAttribute(HtmlAttributeProperties.MAXLENGTH);
		return HtmlValues.getPixelSize(maxLength, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#setMaxLength(int)
	 */
	@Override
	public void setMaxLength(int maxLength) {
		this.setAttribute(HtmlAttributeProperties.MAXLENGTH, String.valueOf(maxLength));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#getRequired()
	 */
	@Override
	public boolean getRequired() {
		String required = this.getAttribute(HtmlAttributeProperties.REQUIRED);
		return HtmlAttributeProperties.REQUIRED.equalsIgnoreCase(required);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#setRequired(boolean)
	 */
	@Override
	public void setRequired(boolean required) {
		this.setAttribute(HtmlAttributeProperties.REQUIRED, required ? HtmlAttributeProperties.REQUIRED : null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#getWrap()
	 */
	@Override
	public String getWrap() {
		return this.getAttribute(HtmlAttributeProperties.WRAP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTextAreaElement#setWrap(java.lang.String)
	 */
	@Override
	public void setWrap(String wrap) {
		this.setAttribute(HtmlAttributeProperties.WRAP, wrap);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#getTextLength()
	 */
	@Override
	public int getTextLength() {
		return this.getValue().length();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTextAreaElement#getWillValidate()
	 */
	@Override
	public boolean getWillValidate() {
		return true;
	}
}
