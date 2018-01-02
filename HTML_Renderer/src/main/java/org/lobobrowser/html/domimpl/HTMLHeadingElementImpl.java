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




import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.renderstate.HeadingRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.w3c.html.HTMLHeadingElement;

/**
 * The Class HTMLHeadingElementImpl.
 */
public class HTMLHeadingElementImpl extends HTMLAbstractUIElement implements HTMLHeadingElement, HtmlProperties {

	private String name;

	/**
	 * Instantiates a new HTML heading element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLHeadingElementImpl(String name) {
		super(name);
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLHeadingElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLHeadingElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);
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
		for (int i = 0; i < 2 - lineBreaks; i++) {
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

		switch (name) {
		case H1:
			css.internalSetLC("font-size", "2em");
			css.internalSetLC("font-weight", "bolder");
			css.internalSetLC("margin-top", "0.67em");
			css.internalSetLC("margin-bottom", "0.67em");
			css.internalSetLC("margin-left", "0em");
			css.internalSetLC("margin-right", "0em");
			break;
		case H2:
			css.internalSetLC("font-size", "1.5em");
			css.internalSetLC("font-weight", "bolder");
			css.internalSetLC("margin-top", "0.83em");
			css.internalSetLC("margin-bottom", "0.83em");
			css.internalSetLC("margin-left", "0em");
			css.internalSetLC("margin-right", "0em");
			break;
		case H3:
			css.internalSetLC("font-size", "1.17em");
			css.internalSetLC("font-weight", "bolder");
			css.internalSetLC("margin-top", "1em");
			css.internalSetLC("margin-bottom", "1em");
			css.internalSetLC("margin-left", "0em");
			css.internalSetLC("margin-right", "0em");
			break;
		case H4:
			css.internalSetLC("font-weight", "bolder");
			css.internalSetLC("margin-top", "1.33em");
			css.internalSetLC("margin-bottom", "1.33em");
			css.internalSetLC("margin-left", "0em");
			css.internalSetLC("margin-right", "0em");
			break;
		case H5:
			css.internalSetLC("font-size", "0.83em");
			css.internalSetLC("font-weight", "bolder");
			css.internalSetLC("margin-top", "1.67em");
			css.internalSetLC("margin-bottom", "1.67em");
			css.internalSetLC("margin-left", "0em");
			css.internalSetLC("margin-right", "0em");
			break;
		case H6:
			css.internalSetLC("font-size", "0.67em");
			css.internalSetLC("font-weight", "bolder");
			css.internalSetLC("margin-top", "2.33em");
			css.internalSetLC("margin-bottom", "2.33em");
			css.internalSetLC("margin-left", "0em");
			css.internalSetLC("margin-right", "0em");
			break;
		default:
			break;
		}
		return css;
	}
}
