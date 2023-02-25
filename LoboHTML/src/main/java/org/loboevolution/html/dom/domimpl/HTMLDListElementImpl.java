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

import org.loboevolution.html.dom.HTMLDListElement;
import org.loboevolution.html.renderstate.DListRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLDListElementImpl class.</p>
 */
public class HTMLDListElementImpl extends HTMLElementImpl implements HTMLDListElement {

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLDListElementImpl(String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new DListRenderState(prevRenderState, this);
    }

    /**
     * <p>isCompact.</p>
     *
     * @return a boolean.
     */
    @Override
    public boolean isCompact() {
        return false;
    }

    /**
     * <p>setCompact.</p>
     *
     * @param compact a boolean.
     */
    @Override
    public void setCompact(boolean compact) {

    }
}
