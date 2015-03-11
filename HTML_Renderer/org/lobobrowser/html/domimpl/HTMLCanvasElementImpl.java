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

package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.renderstate.BlockRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.w3c.HTMLCanvasElement;


/**
 * The Class HTMLCanvasElementImpl.
 */
public class HTMLCanvasElementImpl extends HTMLAbstractUIElement implements HTMLCanvasElement {

	/**
	 * Instantiates a new HTML canvas element impl.
	 *
	 * @param name the name
	 */
	public HTMLCanvasElementImpl(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser.html.renderstate.RenderState)
	 */
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new BlockRenderState(prevRenderState, this);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCanvasElement#getWidth()
	 */
	@Override
	public int getWidth() {
		String widthText = this.getAttribute(HtmlAttributeProperties.WIDTH);
		if (widthText == null) {
			return 0;
		}
		try {
			return Integer.parseInt(widthText);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCanvasElement#setWidth(int)
	 */
	@Override
	public void setWidth(int width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, String.valueOf(width));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCanvasElement#getHeight()
	 */
	@Override
	public int getHeight() {
		String heightText = this.getAttribute(HtmlAttributeProperties.HEIGHT);
		if (heightText == null) {
			return 0;
		}
		try {
			return Integer.parseInt(heightText);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCanvasElement#setHeight(int)
	 */
	@Override
	public void setHeight(int height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, String.valueOf(height));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCanvasElement#toDataURL()
	 */
	@Override
	public String toDataURL() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCanvasElement#toDataURL(java.lang.String, java.lang.Object[])
	 */
	@Override
	public String toDataURL(String type, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLCanvasElement#getContext(java.lang.String)
	 */
	@Override
	public Object getContext(String contextId) {	
		return null;
	}
}
