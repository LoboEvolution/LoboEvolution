/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.w3c.dom.html.HTMLAppletElement;

/**
 * The Class HTMLAppletElementImpl.
 */
public class HTMLAppletElementImpl extends HTMLAbstractUIElement implements HTMLAppletElement {

	/**
	 * Instantiates a new HTML applet element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLAppletElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getAlt()
	 */
	@Override
	public String getAlt() {
		return this.getAttribute(HtmlAttributeProperties.ALT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getArchive()
	 */
	@Override
	public String getArchive() {
		return this.getAttribute(HtmlAttributeProperties.ARCHIVE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getCode()
	 */
	@Override
	public String getCode() {
		return this.getAttribute(HtmlAttributeProperties.CODE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getCodeBase()
	 */
	@Override
	public String getCodeBase() {
		return this.getAttribute(HtmlAttributeProperties.CODEBASE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getHeight()
	 */
	@Override
	public String getHeight() {
		return this.getAttribute(HtmlAttributeProperties.HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getHspace()
	 */
	@Override
	public String getHspace() {
		return this.getAttribute(HtmlAttributeProperties.HSPACE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getName()
	 */
	@Override
	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getObject()
	 */
	@Override
	public String getObject() {
		return this.getAttribute(HtmlAttributeProperties.OBJECT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getVspace()
	 */
	@Override
	public String getVspace() {
		return this.getAttribute(HtmlAttributeProperties.VSPACE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#getWidth()
	 */
	@Override
	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setAlt(java.lang.String)
	 */
	@Override
	public void setAlt(String alt) {
		this.setAttribute(HtmlAttributeProperties.ALT, alt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setArchive(java.lang.String)
	 */
	@Override
	public void setArchive(String archive) {
		this.setAttribute(HtmlAttributeProperties.ARCHIVE, archive);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setCode(java.lang.String)
	 */
	@Override
	public void setCode(String code) {
		this.setAttribute(HtmlAttributeProperties.CODE, code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setCodeBase(java.lang.String)
	 */
	@Override
	public void setCodeBase(String codeBase) {
		this.setAttribute(HtmlAttributeProperties.CODEBASE, codeBase);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setHspace(java.lang.String)
	 */
	@Override
	public void setHspace(String hspace) {
		this.setAttribute(HtmlAttributeProperties.HSPACE, hspace);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setObject(java.lang.String)
	 */
	@Override
	public void setObject(String object) {
		this.setAttribute(HtmlAttributeProperties.OBJECT, object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setVspace(java.lang.String)
	 */
	@Override
	public void setVspace(String vspace) {
		this.setAttribute(HtmlAttributeProperties.VSPACE, vspace);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.html.HTMLAppletElement#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}
}
