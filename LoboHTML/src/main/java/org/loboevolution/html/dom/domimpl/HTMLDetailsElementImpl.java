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

import org.loboevolution.html.dom.HTMLDetailsElement;
import org.loboevolution.html.renderstate.BlockRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLDetailsElementImpl class.</p>
 */
public class HTMLDetailsElementImpl extends HTMLElementImpl implements HTMLDetailsElement {

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLDetailsElementImpl(String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new BlockRenderState(prevRenderState, this);
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void setOpen(boolean open) {

    }
}
