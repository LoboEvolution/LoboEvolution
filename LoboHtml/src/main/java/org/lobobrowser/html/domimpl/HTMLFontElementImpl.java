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

import org.lobobrowser.html.dom.HTMLFontElement;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.html.style.FontValues;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.style.RenderState;

public class HTMLFontElementImpl extends HTMLAbstractUIElement implements HTMLFontElement {
	public HTMLFontElementImpl(String name) {
		super(name);
	}

	@Override
	protected AbstractCSS2Properties createDefaultStyleSheet() {
		final String fontFamily = getAttribute("face");
		final String color = getAttribute("color");
		final String size = getAttribute("size");
		String fontSize = null;
		if (size != null) {
			final ModelNode parentModelNode = getParentModelNode();
			final RenderState parentRS = parentModelNode == null ? null : parentModelNode.getRenderState();
			if (parentRS != null) {
				final int fontNumber = FontValues.getFontNumberOldStyle(size, parentRS);
				fontSize = FontValues.getFontSizeSpec(fontNumber);
			}
		}
		final ComputedCSS2Properties css = new ComputedCSS2Properties(this);
		if (fontSize != null) {
			css.internalSetLC("font-size", fontSize);
		}
		if (fontFamily != null) {
			css.internalSetLC("font-family", fontFamily);
		}
		if (color != null) {
			css.internalSetLC("color", color);
		}
		return css;
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return super.createRenderState(prevRenderState);
	}

	@Override
	public String getColor() {
		return getAttribute("color");
	}

	@Override
	public String getFace() {
		return getAttribute("face");
	}

	@Override
	public String getSize() {
		return getAttribute("size");
	}

	@Override
	public void setColor(String color) {
		setAttribute("color", color);
	}

	@Override
	public void setFace(String face) {
		setAttribute("face", face);
	}

	@Override
	public void setSize(String size) {
		setAttribute("size", size);
	}

}
