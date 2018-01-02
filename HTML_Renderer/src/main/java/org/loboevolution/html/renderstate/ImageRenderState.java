/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderstate;

import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.AbstractCSS2Properties;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;

/**
 * The Class ImageRenderState.
 */
public class ImageRenderState extends StyleSheetRenderState {

	/**
	 * Instantiates a new image render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @param element
	 *            the element
	 */
	public ImageRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderstate.StyleSheetRenderState#getMarginInsets()
	 */
	@Override
	public HtmlInsets getMarginInsets() {
		HtmlInsets mi = this.marginInsets;
		if (!MarginRenderState.INVALID_INSETS.equals(mi)) {
			return mi;
		}
		AbstractCSS2Properties props = this.getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = MarginRenderState.getMarginInsets(props, this);
		}
		if (mi == null) {
			boolean createNew = false;
			String hspaceText = this.element.getAttribute(HSPACE);
			int hspace = HtmlValues.getPixelSize(hspaceText, this, 0);
			String vspaceText = this.element.getAttribute(VSPACE);
			int vspace = HtmlValues.getPixelSize(vspaceText, this, 0);

			if (createNew) {
				mi = new HtmlInsets();
				mi.top = vspace;
				mi.topType = HtmlInsets.TYPE_PIXELS;
				mi.bottom = vspace;
				mi.bottomType = HtmlInsets.TYPE_PIXELS;
				mi.left = hspace;
				mi.leftType = HtmlInsets.TYPE_PIXELS;
				mi.right = hspace;
				mi.rightType = HtmlInsets.TYPE_PIXELS;
			}
		}
		this.marginInsets = mi;
		return mi;
	}
}
