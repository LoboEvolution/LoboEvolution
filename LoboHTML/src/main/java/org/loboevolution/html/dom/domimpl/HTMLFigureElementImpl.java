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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.renderstate.QuoteRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLFigureElementImpl class.</p>
 */
public class HTMLFigureElementImpl extends HTMLElementImpl /*implements HTMLFigureElement*/ {

    /**
     * <p>Constructor for HTMLFigureElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLFigureElementImpl(String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new QuoteRenderState(prevRenderState, this);
    }

    /** {@inheritDoc} */
    @Override
    public Integer getClientWidth() {
        int clientWidth = super.getClientWidth();
        clientWidth = clientWidth == 0 ? getParentElement().getClientWidth() : clientWidth;
        if (getRenderState() != null && getRenderState().getPaddingInsets() != null) {
            clientWidth -= getRenderState().getPaddingInsets().getRight();
            clientWidth -= getRenderState().getPaddingInsets().getLeft();
        }

        if (getRenderState() != null && getRenderState().getMarginInsets() != null) {
            clientWidth -= getRenderState().getMarginInsets().getRight();
            clientWidth -= getRenderState().getMarginInsets().getLeft();
        }
        return clientWidth;
    }

    /** {@inheritDoc} */
    @Override
    public Integer getOffsetWidth() {
        return getClientWidth();
    }
}
