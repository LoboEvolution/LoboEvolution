/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.BaseFontRenderState;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.w3c.HTMLBaseFontElement;

public class HTMLBaseFontElementImpl extends HTMLAbstractUIElement implements
		HTMLBaseFontElement {
	public HTMLBaseFontElementImpl(String name) {
		super(name);
	}

	public String getColor() {
		return this.getAttribute(HtmlAttributeProperties.COLOR);
	}

	public String getFace() {
		return this.getAttribute(HtmlAttributeProperties.FACE);
	}

	public void setColor(String color) {
		this.setAttribute(HtmlAttributeProperties.COLOR, color);
	}

	public void setFace(String face) {
		this.setAttribute(HtmlAttributeProperties.FACE, face);
	}

	public int getSize() {
		try {
			return Integer.parseInt(this.getAttribute(HtmlAttributeProperties.SIZE));
		} catch (Exception thrown) {
			this.warn("getSize(): Unable to parse size attribute in " + this
					+ ".", thrown);
			return 0;
		}
	}

	public void setSize(int size) {
		this.setAttribute(HtmlAttributeProperties.SIZE, String.valueOf(size));
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		String size = this.getAttribute(HtmlAttributeProperties.SIZE);
		if (size != null) {
			int fontNumber = HtmlValues.getFontNumberOldStyle(size,
					prevRenderState);
			prevRenderState = new BaseFontRenderState(prevRenderState,
					fontNumber);
		}
		return super.createRenderState(prevRenderState);
	}

	protected AbstractCSS2Properties createDefaultStyleSheet() {
		String fontFamily = this.getAttribute(HtmlAttributeProperties.FACE);
		String color = this.getAttribute(HtmlAttributeProperties.COLOR);
		String size = this.getAttribute(HtmlAttributeProperties.SIZE);
		ModelNode parentModelNode = this.getParentModelNode();
		RenderState parentRS = parentModelNode == null ? null : parentModelNode
				.getRenderState();
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
