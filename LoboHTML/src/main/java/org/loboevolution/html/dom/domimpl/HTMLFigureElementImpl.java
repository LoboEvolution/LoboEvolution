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
