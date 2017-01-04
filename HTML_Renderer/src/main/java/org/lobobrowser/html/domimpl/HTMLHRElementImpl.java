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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.w3c.html.HTMLHRElement;

/**
 * The Class HTMLHRElementImpl.
 */
public class HTMLHRElementImpl extends HTMLAbstractUIElement implements HTMLHRElement {

	/**
	 * Instantiates a new HTMLHR element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLHRElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#getNoShade()
	 */
	@Override
	public boolean getNoShade() {
		return HtmlAttributeProperties.NOSHADE.equalsIgnoreCase(this.getAttribute(HtmlAttributeProperties.NOSHADE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#getSize()
	 */
	@Override
	public String getSize() {
		return this.getAttribute(HtmlAttributeProperties.SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#getWidth()
	 */
	@Override
	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#setNoShade(boolean)
	 */
	@Override
	public void setNoShade(boolean noShade) {
		this.setAttribute(HtmlAttributeProperties.NOSHADE, noShade ? HtmlAttributeProperties.NOSHADE : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#setSize(java.lang.String)
	 */
	@Override
	public void setSize(String size) {
		this.setAttribute(HtmlAttributeProperties.SIZE, size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#getColor()
	 */
	@Override
	public String getColor() {
		return this.getAttribute(HtmlAttributeProperties.COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHRElement#setColor(java.lang.String)
	 */
	@Override
	public void setColor(String color) {
		this.setAttribute(HtmlAttributeProperties.COLOR, color);

	}
}
