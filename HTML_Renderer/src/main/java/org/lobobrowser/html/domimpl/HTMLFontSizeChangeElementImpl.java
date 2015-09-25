/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.html.style.HtmlValues;

/**
 * This element is used for SMALL and BIG.
 */
public class HTMLFontSizeChangeElementImpl extends HTMLAbstractUIElement {

	/** The font change. */
	private final int fontChange;

	/**
	 * Instantiates a new HTML font size change element impl.
	 *
	 * @param name
	 *            the name
	 * @param fontChange
	 *            the font change
	 */
	public HTMLFontSizeChangeElementImpl(String name, int fontChange) {
		super(name);
		this.fontChange = fontChange;
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
		ModelNode parentModelNode = this.getParentModelNode();
		RenderState parentRS = parentModelNode == null ? null : parentModelNode.getRenderState();
		String fontSize = null;
		int prevFontSize = parentRS != null ? parentRS.getFont().getSize() : HtmlValues.DEFAULT_FONT_SIZE_INT;
		int newFontSize = prevFontSize + (this.fontChange * 2);
		fontSize = newFontSize + "px";
		ComputedCSS2Properties css = new ComputedCSS2Properties(this);
		css.internalSetLC("font-size", fontSize);
		return css;
	}

}
