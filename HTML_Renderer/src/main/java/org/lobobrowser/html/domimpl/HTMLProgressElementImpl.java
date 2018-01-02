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


import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.w3c.html.HTMLProgressElement;
import org.w3c.dom.NodeList;

/**
 * The Class HTMLProgressElementImpl.
 */
public class HTMLProgressElementImpl extends HTMLElementImpl implements HTMLProgressElement {

	/**
	 * Instantiates a new HTML progress element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLProgressElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLProgressElement#getValue()
	 */
	@Override
	public double getValue() {
		String valueText = this.getAttribute(VALUE);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLProgressElement#setValue(double)
	 */
	@Override
	public void setValue(double value) {
		this.setAttribute(VALUE, String.valueOf(value));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLProgressElement#getMax()
	 */
	@Override
	public double getMax() {
		String valueText = this.getAttribute(MAX);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLProgressElement#setMax(double)
	 */
	@Override
	public void setMax(double max) {
		this.setAttribute(VALUE, String.valueOf(max));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLProgressElement#getPosition()
	 */
	@Override
	public double getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLProgressElement#getLabels()
	 */
	@Override
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

}
