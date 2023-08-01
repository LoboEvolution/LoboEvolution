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

import org.loboevolution.html.renderstate.DDRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLDDElementImpl class.</p>
 */
public class HTMLDDElementImpl extends HTMLElementImpl {

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLDDElementImpl(String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new DDRenderState(prevRenderState, this);
    }

    @Override
    public int getClientHeight() {
        int clientHeight = super.getClientHeight();
        return clientHeight == 0 ? 17 : clientHeight;
    }

    /** {@inheritDoc} */
    @Override
    public Integer getClientWidth() {
        int clientWidth = super.getClientWidth();
        if (clientWidth == 0) {
            int clientWidthDoc = getParentElement().getClientWidth();

            if (getRenderState() != null && getRenderState().getPaddingInsets() != null) {
                clientWidthDoc -= getRenderState().getPaddingInsets().getRight();
                clientWidthDoc -= getRenderState().getPaddingInsets().getLeft();
                clientWidth = clientWidthDoc;
            }
        }

        return clientWidth;
    }

    /** {@inheritDoc} */
    @Override
    public Integer getOffsetWidth() {
        return getClientWidth();
    }
}
