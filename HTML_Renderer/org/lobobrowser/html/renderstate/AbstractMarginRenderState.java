/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.renderstate;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.style.HtmlInsets;

public abstract class AbstractMarginRenderState extends BlockRenderState {
	public AbstractMarginRenderState(RenderState prevRenderState,
			HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	protected abstract HtmlInsets getDefaultMarginInsets();

	public HtmlInsets getMarginInsets() {
		HtmlInsets insets = this.marginInsets;
		if (insets != INVALID_INSETS) {
			return insets;
		}
		insets = super.getMarginInsets();
		if (insets == null) {
			insets = this.getDefaultMarginInsets();
		}
		this.marginInsets = insets;
		return insets;
	}

}
