/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.HtmlInsets;

import java.awt.*;

/**
 * <p>ListRenderState class.</p>s
 */
public class ListRenderState extends AbstractMarginRenderState {
	/**
	 * <p>Constructor for ListRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public ListRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/** {@inheritDoc} */
	@Override
	protected HtmlInsets getDefaultMarginInsets() {
		final HtmlInsets insets = new HtmlInsets();
		final RenderState prevRS = getPreviousRenderState();
		final FontMetrics fm = prevRS == null ? getFontMetrics() : prevRS.getFontMetrics();
		insets.setTop(fm.getHeight());
		insets.setBottom(fm.getHeight());
		insets.setTopType(HtmlInsets.TYPE_PIXELS);
		insets.setBottomType(HtmlInsets.TYPE_PIXELS);
		final int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
		insets.setLeft((int) Math.round(dpi * 30.0 / 72.0));
		insets.setLeftType(HtmlInsets.TYPE_PIXELS);
		return insets;
	}
}
