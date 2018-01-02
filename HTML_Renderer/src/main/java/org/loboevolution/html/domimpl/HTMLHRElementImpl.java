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
package org.loboevolution.html.domimpl;


import org.loboevolution.w3c.html.HTMLHRElement;

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
	 * @see org.loboevolution.w3c.html.HTMLHRElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHRElement#getNoShade()
	 */
	@Override
	public boolean getNoShade() {
		return NOSHADE.equalsIgnoreCase(this.getAttribute(NOSHADE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHRElement#getSize()
	 */
	@Override
	public String getSize() {
		return this.getAttribute(SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHRElement#getWidth()
	 */
	@Override
	public String getWidth() {
		return this.getAttribute(WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHRElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHRElement#setNoShade(boolean)
	 */
	@Override
	public void setNoShade(boolean noShade) {
		this.setAttribute(NOSHADE, noShade ? NOSHADE : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHRElement#setSize(java.lang.String)
	 */
	@Override
	public void setSize(String size) {
		this.setAttribute(SIZE, size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHRElement#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		this.setAttribute(WIDTH, width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHRElement#getColor()
	 */
	@Override
	public String getColor() {
		return this.getAttribute(COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLHRElement#setColor(java.lang.String)
	 */
	@Override
	public void setColor(String color) {
		this.setAttribute(COLOR, color);

	}
}
