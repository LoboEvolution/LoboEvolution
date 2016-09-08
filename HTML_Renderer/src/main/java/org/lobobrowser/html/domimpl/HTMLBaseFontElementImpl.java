/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.renderstate.BaseFontRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.w3c.html.HTMLBaseFontElement;

/**
 * The Class HTMLBaseFontElementImpl.
 */
public class HTMLBaseFontElementImpl extends HTMLAbstractUIElement implements HTMLBaseFontElement {

	/**
	 * Instantiates a new HTML base font element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLBaseFontElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLBaseFontElement#getColor()
	 */
	@Override
	public String getColor() {
		return this.getAttribute(HtmlAttributeProperties.COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLBaseFontElement#getFace()
	 */
	@Override
	public String getFace() {
		return this.getAttribute(HtmlAttributeProperties.FACE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLBaseFontElement#setColor(java.lang.String)
	 */
	@Override
	public void setColor(String color) {
		this.setAttribute(HtmlAttributeProperties.COLOR, color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLBaseFontElement#setFace(java.lang.String)
	 */
	@Override
	public void setFace(String face) {
		this.setAttribute(HtmlAttributeProperties.FACE, face);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLBaseFontElement#getSize()
	 */
	@Override
	public int getSize() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.SIZE));
		} catch (Exception thrown) {
			this.warn("getSize(): Unable to parse size attribute in " + this + ".", thrown);
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLBaseFontElement#setSize(int)
	 */
	@Override
	public void setSize(int size) {
		this.setAttribute(HtmlAttributeProperties.SIZE, String.valueOf(size));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * lobobrowser .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		String size = this.getAttribute(HtmlAttributeProperties.SIZE);
		if (size != null) {
			int fontNumber = HtmlValues.getFontNumberOldStyle(size, prevRenderState);
			prevRenderState = new BaseFontRenderState(prevRenderState, fontNumber);
		}
		return super.createRenderState(prevRenderState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.HTMLElementImpl#createDefaultStyleSheet()
	 */
	@Override
	protected AbstractCSS2Properties createDefaultStyleSheet() {
		String fontFamily = this.getAttribute(HtmlAttributeProperties.FACE);
		String color = this.getAttribute(HtmlAttributeProperties.COLOR);
		String size = this.getAttribute(HtmlAttributeProperties.SIZE);
		ModelNode parentModelNode = this.getParentModelNode();
		RenderState parentRS = parentModelNode == null ? null : parentModelNode.getRenderState();
		String fontSize = null;
		if (parentRS != null) {
			int fontNumber = HtmlValues.getFontNumberOldStyle(size, parentRS);
			fontSize = HtmlValues.getFontSizeSpec(fontNumber);
		}
		ComputedCSS2Properties css = new ComputedCSS2Properties(this);
		if (fontSize != null) {
			css.internalSetLC("font-size", fontSize);
		}
		if (fontFamily != null) {
			css.internalSetLC("font-family", fontFamily);
		}
		if (color != null) {
			css.internalSetLC(HtmlAttributeProperties.COLOR, color);
		}
		return css;
	}

}
