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
package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * <p>AlignXRenderState class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class AlignXRenderState extends BlockRenderState {
	private final int alignXPercent;

    /**
     * <p>Constructor for AlignXRenderState.</p>
     *
     * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     * @param alignXPercent a int.
     */
    public AlignXRenderState(final RenderState prevRenderState, HTMLElementImpl element, int alignXPercent) {
        super(prevRenderState, element);
		this.alignXPercent = alignXPercent;
	}

	/** {@inheritDoc} */
	@Override
	public int getAlignXPercent() {
		return this.alignXPercent;
	}
}
