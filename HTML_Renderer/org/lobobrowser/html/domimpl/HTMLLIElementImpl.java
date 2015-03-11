/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Feb 12, 2006
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.renderstate.DisplayRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.w3c.HTMLLIElement;


/**
 * The Class HTMLLIElementImpl.
 */
public class HTMLLIElementImpl extends HTMLAbstractUIElement implements
		HTMLLIElement {
	
	/**
	 * Instantiates a new HTMLLI element impl.
	 *
	 * @param name the name
	 */
	public HTMLLIElementImpl(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLLIElement#getType()
	 */
	public String getType() {
		return this.getAttribute(HtmlAttributeProperties.TYPE);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLLIElement#setType(java.lang.String)
	 */
	public void setType(String type) {
		this.setAttribute(HtmlAttributeProperties.TYPE, type);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLLIElement#getValue()
	 */
	public int getValue() {
		String valueText = this.getAttribute(HtmlAttributeProperties.VALUE);
		if (valueText == null) {
			return 0;
		}
		try {
			return Integer.parseInt(valueText);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLLIElement#setValue(int)
	 */
	public void setValue(int value) {
		this.setAttribute(HtmlAttributeProperties.VALUE, String.valueOf(value));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser.html.renderstate.RenderState)
	 */
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new DisplayRenderState(prevRenderState, this,
				RenderState.DISPLAY_LIST_ITEM);
	}
}
