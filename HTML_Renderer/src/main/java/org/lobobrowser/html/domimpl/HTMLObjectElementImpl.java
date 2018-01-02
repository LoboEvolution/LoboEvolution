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


import org.lobobrowser.html.js.Window;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.w3c.html.HTMLFormElement;
import org.lobobrowser.w3c.html.HTMLObjectElement;
import org.lobobrowser.w3c.html.ValidityState;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class HTMLObjectElementImpl.
 */
public class HTMLObjectElementImpl extends HTMLAbstractUIElement implements HTMLObjectElement {

	/**
	 * Instantiates a new HTML object element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLObjectElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	/**
	 * Gets the alt.
	 *
	 * @return the alt
	 */
	public String getAlt() {
		return this.getAttribute(ALT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getArchive()
	 */
	@Override
	public String getArchive() {
		return this.getAttribute(ARCHIVE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getCode()
	 */
	@Override
	public String getCode() {
		return this.getAttribute(CODE_ATTR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getCodeBase()
	 */
	@Override
	public String getCodeBase() {
		return this.getAttribute(CODEBASE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getHeight()
	 */
	@Override
	public String getHeight() {
		return this.getAttribute(HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getName()
	 */
	@Override
	public String getName() {
		return this.getAttribute(NAME);
	}

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public String getObject() {
		return this.getAttribute(OBJECT_HTML);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getWidth()
	 */
	@Override
	public String getWidth() {
		return this.getAttribute(WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);
	}

	/**
	 * Sets the alt.
	 *
	 * @param alt
	 *            the new alt
	 */
	public void setAlt(String alt) {
		this.setAttribute(ALT, alt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setArchive(java.lang.String)
	 */
	@Override
	public void setArchive(String archive) {
		this.setAttribute(ARCHIVE, archive);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#setCode(java.lang.String)
	 */
	@Override
	public void setCode(String code) {
		this.setAttribute(CODE_ATTR, code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setCodeBase(java.lang.String)
	 */
	@Override
	public void setCodeBase(String codeBase) {
		this.setAttribute(CODEBASE, codeBase);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) {
		this.setAttribute(HEIGHT, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.setAttribute(NAME, name);
	}

	/**
	 * Sets the object.
	 *
	 * @param object
	 *            the new object
	 */
	public void setObject(String object) {
		this.setAttribute(OBJECT_HTML, object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		this.setAttribute(WIDTH, width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getBorder()
	 */
	@Override
	public String getBorder() {
		return this.getAttribute(BORDER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getCodeType()
	 */
	@Override
	public String getCodeType() {
		return this.getAttribute(CODETYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getContentDocument()
	 */
	@Override
	public Document getContentDocument() {
		return this.getOwnerDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getData()
	 */
	@Override
	public String getData() {
		return this.getAttribute(DATA);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getDeclare()
	 */
	@Override
	public boolean getDeclare() {
		return "declare".equalsIgnoreCase(this.getAttribute(DECLARE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getForm()
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
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getHspace()
	 */
	@Override
	public int getHspace() {
		String valueText = this.getAttribute(HSPACE);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getStandby()
	 */
	@Override
	public String getStandby() {
		return this.getAttribute(STANDBY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMElementImpl#getTabIndex()
	 */
	@Override
	public int getTabIndex() {
		String valueText = this.getAttribute(TABINDEX);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getType()
	 */
	@Override
	public String getType() {
		return this.getAttribute(TYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getUseMap()
	 */
	@Override
	public String getUseMap() {
		return this.getAttribute(USEMAP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getVspace()
	 */
	@Override
	public int getVspace() {
		String valueText = this.getAttribute(VSPACE);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setBorder(java.lang.String)
	 */
	@Override
	public void setBorder(String border) {
		this.setAttribute(BORDER, border);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setCodeType(java.lang.String)
	 */
	@Override
	public void setCodeType(String codeType) {
		this.setAttribute(CODETYPE, codeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#setData(java.lang.String)
	 */
	@Override
	public void setData(String data) {
		this.setAttribute(DATA, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#setDeclare(boolean)
	 */
	@Override
	public void setDeclare(boolean declare) {
		this.setAttribute(DECLARE, declare ? "declare" : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#setHspace(int)
	 */
	@Override
	public void setHspace(int hspace) {
		this.setAttribute(HSPACE, String.valueOf(hspace));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setStandby(java.lang.String)
	 */
	@Override
	public void setStandby(String standby) {
		this.setAttribute(STANDBY, standby);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMElementImpl#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tabIndex) {
		this.setAttribute(TABINDEX, String.valueOf(tabIndex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.setAttribute(TYPE, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setUseMap(java.lang.String)
	 */
	@Override
	public void setUseMap(String useMap) {
		this.setAttribute(USEMAP, useMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#setVspace(int)
	 */
	@Override
	public void setVspace(int vspace) {
		this.setAttribute(VSPACE, String.valueOf(vspace));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getWillValidate()
	 */
	@Override
	public boolean getWillValidate() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getValidity()
	 */
	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#getValidationMessage()
	 */
	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLObjectElement#checkValidity()
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
	 * org.lobobrowser.w3c.html.HTMLObjectElement#setCustomValidity(java.lang.
	 * String )
	 */
	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getTypeMustMatch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTypeMustMatch(boolean typeMustMatch) {
		// TODO Auto-generated method stub

	}

	@Override
	public Window getContentWindow() {
		// TODO Auto-generated method stub
		return null;
	}
}
