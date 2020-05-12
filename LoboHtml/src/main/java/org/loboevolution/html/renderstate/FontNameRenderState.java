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

package org.loboevolution.html.renderstate;

import java.awt.Font;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * <p>FontNameRenderState class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class FontNameRenderState extends StyleSheetRenderState {
	
	private final String fontName;
	
	private RenderState delegate;

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
