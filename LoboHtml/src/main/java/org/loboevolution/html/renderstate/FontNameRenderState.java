/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import java.awt.Font;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * <p>FontNameRenderState class.</p>
 *
 *
 *
 */
public class FontNameRenderState extends StyleSheetRenderState {
	
	private final String fontName;
	
	private final RenderState delegate;

	/**
	 * <p>Constructor for FontNameRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param fontName a {@link java.lang.String} object.
	 */
	public FontNameRenderState(RenderState prevRenderState, HTMLElementImpl element, String fontName) {
		super(prevRenderState, element);
		this.fontName = fontName;
		this.delegate = prevRenderState;
	}

	/** {@inheritDoc} */
	@Override
	public Font getFont() {
		final Font parentFont = this.delegate.getFont();
		Font f = new Font(this.fontName, parentFont.getStyle(), parentFont.getSize());
		return f;
	}
}
