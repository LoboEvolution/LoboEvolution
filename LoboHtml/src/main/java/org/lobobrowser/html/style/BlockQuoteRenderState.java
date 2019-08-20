/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

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
package org.lobobrowser.html.style;

import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;

public class BlockQuoteRenderState extends AbstractMarginRenderState {
	public BlockQuoteRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	@Override
	protected HtmlInsets getDefaultMarginInsets() {
		final HtmlInsets insets = new HtmlInsets();
		final RenderState prevRS = getPreviousRenderState();
		final FontMetrics fm = prevRS == null ? getFontMetrics() : prevRS.getFontMetrics();
		insets.top = fm.getHeight();
		insets.bottom = fm.getHeight();
		insets.topType = HtmlInsets.TYPE_PIXELS;
		insets.bottomType = HtmlInsets.TYPE_PIXELS;
		final int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
		insets.left = (int) Math.round(dpi * 30.0 / 72.0);
		insets.right = insets.left / 2;
		insets.leftType = HtmlInsets.TYPE_PIXELS;
		insets.rightType = HtmlInsets.TYPE_PIXELS;
		return insets;
	}
}
