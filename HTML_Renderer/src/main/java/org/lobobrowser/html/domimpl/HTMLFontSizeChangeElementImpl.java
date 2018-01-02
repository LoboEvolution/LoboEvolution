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

import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.util.gui.LAFSettings;

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
		int prevFontSize = parentRS != null ? parentRS.getFont().getSize()
				: (int) LAFSettings.getInstance().getFontSize();
		int newFontSize = prevFontSize + this.fontChange * 2;
		fontSize = newFontSize + "px";
		ComputedCSS2Properties css = new ComputedCSS2Properties(this);
		css.internalSetLC("font-size", fontSize);
		return css;
	}

}
