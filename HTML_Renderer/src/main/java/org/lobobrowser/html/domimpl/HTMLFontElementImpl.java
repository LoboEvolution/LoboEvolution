/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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


import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.html.style.FontValues;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.w3c.html.HTMLFontElement;

/**
 * The Class HTMLFontElementImpl.
 */
public class HTMLFontElementImpl extends HTMLAbstractUIElement implements HTMLFontElement {

	/**
	 * Instantiates a new HTML font element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLFontElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFontElement#getColor()
	 */
	@Override
	public String getColor() {
		return this.getAttribute(COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFontElement#getFace()
	 */
	@Override
	public String getFace() {
		return this.getAttribute(FACE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFontElement#getSize()
	 */
	@Override
	public String getSize() {
		return this.getAttribute(SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFontElement#setColor(java.lang.String)
	 */
	@Override
	public void setColor(String color) {
		this.setAttribute(COLOR, color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFontElement#setFace(java.lang.String)
	 */
	@Override
	public void setFace(String face) {
		this.setAttribute(FACE, face);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLFontElement#setSize(java.lang.String)
	 */
	@Override
	public void setSize(String size) {
		this.setAttribute(SIZE, size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * lobobrowser .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
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
		String fontFamily = this.getAttribute(FACE);
		String color = this.getAttribute(COLOR);
		String size = this.getAttribute(SIZE);
		String fontSize = null;
		if (size != null) {
			ModelNode parentModelNode = this.getParentModelNode();
			RenderState parentRS = parentModelNode == null ? null : parentModelNode.getRenderState();
			if (parentRS != null) {
				int fontNumber = FontValues.getFontNumberOldStyle(size, parentRS);
				fontSize = FontValues.getFontSizeSpec(fontNumber);
			}
		}
		ComputedCSS2Properties css = new ComputedCSS2Properties(this);
		if (fontSize != null) {
			css.internalSetLC("font-size", fontSize);
		}
		if (fontFamily != null) {
			css.internalSetLC("font-family", fontFamily);
		}
		if (color != null) {
			css.internalSetLC(COLOR, color);
		}
		return css;
	}

}
