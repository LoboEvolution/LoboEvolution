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

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * <p>AlignXRenderState class.</p>
 *
 *
 *
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
