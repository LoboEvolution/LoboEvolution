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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.renderstate.HeadingRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.util.gui.LAFSettings;
import org.lobobrowser.w3c.html.HTMLHeadingElement;

/**
 * The Class HTMLHeadingElementImpl.
 */
public class HTMLHeadingElementImpl extends HTMLAbstractUIElement implements HTMLHeadingElement {

	/**
	 * Instantiates a new HTML heading element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLHeadingElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHeadingElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLHeadingElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * lobobrowser .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new HeadingRenderState(prevRenderState, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.DOMNodeImpl#appendInnerTextImpl(java.lang.
	 * StringBuffer)
	 */
	@Override
	protected void appendInnerTextImpl(StringBuffer buffer) {
		int length = buffer.length();
		int lineBreaks;
		if (length == 0) {
			lineBreaks = 2;
		} else {
			int start = length - 4;
			if (start < 0) {
				start = 0;
			}
			lineBreaks = 0;
			for (int i = start; i < length; i++) {
				char ch = buffer.charAt(i);
				if (ch == '\n') {
					lineBreaks++;
				}
			}
		}
		for (int i = 0; i < (2 - lineBreaks); i++) {
			buffer.append("\r\n");
		}
		super.appendInnerTextImpl(buffer);
		buffer.append("\r\n\r\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.HTMLElementImpl#createDefaultStyleSheet()
	 */
	@Override
	protected AbstractCSS2Properties createDefaultStyleSheet() {
		ComputedCSS2Properties css = new ComputedCSS2Properties(this);
		css.internalSetLC("font-size", String.valueOf((int)LAFSettings.getInstance().getFontSize())+"px");
		css.internalSetLC("font-weight", "bolder");
		return css;
	}
}
