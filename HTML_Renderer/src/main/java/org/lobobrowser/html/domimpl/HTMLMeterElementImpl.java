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
import org.lobobrowser.w3c.html.HTMLMeterElement;
import org.w3c.dom.NodeList;

/**
 * The Class HTMLMeterElementImpl.
 */
public class HTMLMeterElementImpl extends HTMLElementImpl implements HTMLMeterElement {

	/**
	 * Instantiates a new HTML meter element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLMeterElementImpl(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#getValue()
	 */
	@Override
	public double getValue() {
		String valueText = this.getAttribute(VALUE);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#setValue(double)
	 */
	@Override
	public void setValue(double value) {
		this.setAttribute(VALUE, String.valueOf(value));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#getMin()
	 */
	@Override
	public double getMin() {
		String valueText = this.getAttribute(MIN);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#setMin(double)
	 */
	@Override
	public void setMin(double min) {
		this.setAttribute(MIN, String.valueOf(min));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#getMax()
	 */
	@Override
	public double getMax() {
		String valueText = this.getAttribute(MAX);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#setMax(double)
	 */
	@Override
	public void setMax(double max) {
		this.setAttribute(MAX, String.valueOf(max));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#getLow()
	 */
	@Override
	public double getLow() {
		String valueText = this.getAttribute(LOW);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#setLow(double)
	 */
	@Override
	public void setLow(double low) {
		this.setAttribute(LOW, String.valueOf(low));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#getHigh()
	 */
	@Override
	public double getHigh() {
		String valueText = this.getAttribute(HEIGHT);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#setHigh(double)
	 */
	@Override
	public void setHigh(double high) {
		this.setAttribute(HEIGHT, String.valueOf(high));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#getOptimum()
	 */
	@Override
	public double getOptimum() {
		String valueText = this.getAttribute(OPTINUM);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#setOptimum(double)
	 */
	@Override
	public void setOptimum(double optimum) {
		this.setAttribute(OPTINUM, String.valueOf(optimum));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMeterElement#getLabels()
	 */
	@Override
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

}
