/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLFormElement;
import org.lobobrowser.html.w3c.HTMLMeterElement;
import org.w3c.dom.NodeList;


/**
 * The Class HTMLMeterElementImpl.
 */
public class HTMLMeterElementImpl extends HTMLElementImpl implements
		HTMLMeterElement {

	/**
	 * Instantiates a new HTML meter element impl.
	 *
	 * @param name the name
	 */
	public HTMLMeterElementImpl(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#getValue()
	 */
	@Override
	public float getValue() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.VALUE));
		} catch (Exception thrown) {
			this.warn("getSize(): Unable to parse size attribute in " + this
					+ ".", thrown);
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#setValue(float)
	 */
	@Override
	public void setValue(float value) {
		this.setAttribute(HtmlAttributeProperties.VALUE, String.valueOf(value));
		
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#getMin()
	 */
	@Override
	public float getMin() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.MIN));
		} catch (Exception thrown) {
			this.warn("getSize(): Unable to parse size attribute in " + this
					+ ".", thrown);
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#setMin(float)
	 */
	@Override
	public void setMin(float min) {
		this.setAttribute(HtmlAttributeProperties.MIN, String.valueOf(min));
		
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#getMax()
	 */
	@Override
	public float getMax() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.MAX));
		} catch (Exception thrown) {
			this.warn("getSize(): Unable to parse size attribute in " + this
					+ ".", thrown);
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#setMax(float)
	 */
	@Override
	public void setMax(float max) {
		this.setAttribute(HtmlAttributeProperties.MAX, String.valueOf(max));
		
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#getLow()
	 */
	@Override
	public float getLow() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.LOW));
		} catch (Exception thrown) {
			this.warn("getSize(): Unable to parse size attribute in " + this
					+ ".", thrown);
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#setLow(float)
	 */
	@Override
	public void setLow(float low) {
		this.setAttribute(HtmlAttributeProperties.LOW, String.valueOf(low));
		
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#getHigh()
	 */
	@Override
	public float getHigh() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.HEIGHT));
		} catch (Exception thrown) {
			this.warn("getSize(): Unable to parse size attribute in " + this
					+ ".", thrown);
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#setHigh(float)
	 */
	@Override
	public void setHigh(float high) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, String.valueOf(high));
		
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#getOptimum()
	 */
	@Override
	public float getOptimum() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.OPTINUM));
		} catch (Exception thrown) {
			this.warn("getSize(): Unable to parse size attribute in " + this
					+ ".", thrown);
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#setOptimum(float)
	 */
	@Override
	public void setOptimum(float optimum) {
		this.setAttribute(HtmlAttributeProperties.OPTINUM, String.valueOf(optimum));
		
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#getForm()
	 */
	@Override
	public HTMLFormElement getForm() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLMeterElement#getLabels()
	 */
	@Override
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

}
