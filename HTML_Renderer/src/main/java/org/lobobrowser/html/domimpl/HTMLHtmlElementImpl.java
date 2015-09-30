/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
/*
 * Created on Oct 8, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.w3c.html.HTMLHtmlElement;

/**
 * The Class HTMLHtmlElementImpl.
 */
public class HTMLHtmlElementImpl extends HTMLElementImpl implements HTMLHtmlElement {

	/**
	 * Instantiates a new HTML html element impl.
	 */
	public HTMLHtmlElementImpl() {
		super(HtmlProperties.HTML, true);
	}

	/**
	 * Instantiates a new HTML html element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLHtmlElementImpl(String name) {
		super(name, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHtmlElement#getVersion()
	 */
	@Override
	public String getVersion() {
		return this.getAttribute(HtmlAttributeProperties.VERSION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLHtmlElement#setVersion(java.lang.String)
	 */
	@Override
	public void setVersion(String version) {
		this.setAttribute(HtmlAttributeProperties.VERSION, version);
	}
}
