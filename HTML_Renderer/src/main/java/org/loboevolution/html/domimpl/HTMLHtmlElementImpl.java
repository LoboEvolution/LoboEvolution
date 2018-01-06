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
/*
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.domimpl;




import org.loboevolution.w3c.html.HTMLHtmlElement;

/**
 * The Class HTMLHtmlElementImpl.
 */
public class HTMLHtmlElementImpl extends HTMLElementImpl implements HTMLHtmlElement {

	/**
	 * Instantiates a new HTML html element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLHtmlElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHtmlElement#getVersion()
	 */
	@Override
	public String getVersion() {
		return this.getAttribute(VERSION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLHtmlElement#setVersion(java.lang.String)
	 */
	@Override
	public void setVersion(String version) {
		this.setAttribute(VERSION, version);
	}
}
