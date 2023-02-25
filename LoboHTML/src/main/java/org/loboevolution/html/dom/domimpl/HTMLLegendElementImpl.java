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

import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLLegendElement;
import org.loboevolution.html.renderstate.LegendRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLFigureElementImpl class.</p>
 */
public class HTMLLegendElementImpl extends HTMLElementImpl implements HTMLLegendElement {

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLLegendElementImpl(String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new LegendRenderState(prevRenderState, this);
    }

    /**
     * <p>getAlign.</p>
     *
     * @return a {@link String} object.
     */
    @Override
    public String getAlign() {
        return null;
    }

    /**
     * <p>setAlign.</p>
     *
     * @param align a {@link String} object.
     */
    @Override
    public void setAlign(String align) {

    }

    /**
     * Retrieves a reference to the form that the object is embedded in.
     *
     * @return a {@link HTMLFormElement} object.
     */
    @Override
    public HTMLFormElement getForm() {
        return null;
    }
}
