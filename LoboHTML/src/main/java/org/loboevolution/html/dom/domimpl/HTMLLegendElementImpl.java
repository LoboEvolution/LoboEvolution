/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
