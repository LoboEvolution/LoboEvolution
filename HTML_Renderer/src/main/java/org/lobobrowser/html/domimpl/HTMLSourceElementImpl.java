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


import org.lobobrowser.w3c.html.HTMLSourceElement;

/**
 * The Class HTMLSourceElementImpl.
 */
public class HTMLSourceElementImpl extends HTMLElementImpl implements HTMLSourceElement {

	/**
	 * Instantiates a new HTML source element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLSourceElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSourceElement#getSrc()
	 */
	@Override
	public String getSrc() {
		return this.getAttribute(SRC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSourceElement#setSrc(java.lang.String)
	 */
	@Override
	public void setSrc(String src) {
		this.setAttribute(SRC, src);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSourceElement#getType()
	 */
	@Override
	public String getType() {
		return this.getAttribute(TYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSourceElement#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.setAttribute(TYPE, type);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSourceElement#getMedia()
	 */
	@Override
	public String getMedia() {
		return this.getAttribute(MEDIA);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLSourceElement#setMedia(java.lang.String)
	 */
	@Override
	public void setMedia(String media) {
		this.setAttribute(MEDIA, media);

	}

}
