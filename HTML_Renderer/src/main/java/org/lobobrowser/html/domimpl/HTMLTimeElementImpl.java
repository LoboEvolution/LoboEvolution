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


import org.lobobrowser.w3c.html.HTMLTimeElement;

/**
 * The Class HTMLTimeElementImpl.
 */
public class HTMLTimeElementImpl extends HTMLElementImpl implements HTMLTimeElement {

	/**
	 * Instantiates a new HTML time element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLTimeElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTimeElement#getDateTime()
	 */
	@Override
	public String getDateTime() {
		return this.getAttribute(DATETIME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTimeElement#setDateTime(java.lang.String)
	 */
	@Override
	public void setDateTime(String dateTime) {
		this.setAttribute(DATETIME, dateTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTimeElement#getPubDate()
	 */
	@Override
	public boolean getPubDate() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTimeElement#setPubDate(boolean)
	 */
	@Override
	public void setPubDate(boolean pubDate) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTimeElement#getValueAsDate()
	 */
	@Override
	public long getValueAsDate() {
		// TODO Auto-generated method stub
		return 0;
	}
}
