/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import org.loboevolution.html.dom.HTMLOptGroupElement;
import org.loboevolution.html.renderstate.BlockRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLOptGroupElementImpl class.</p>
 */
public class HTMLOptGroupElementImpl extends HTMLElementImpl implements HTMLOptGroupElement {

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLOptGroupElementImpl(final String name) {
        super(name);
    }

    @Override
    protected RenderState createRenderState(final RenderState prevRenderState) {
        return new BlockRenderState(prevRenderState, this);
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public void setDisabled(final boolean disabled) {

    }

    @Override
    public HTMLFormElement getForm() {
        return null;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public void setLabel(final String label) {

    }

    /** {@inheritDoc} */
    @Override
    public int getClientHeight() {
        final int clientHeight = super.getClientHeight();
        return clientHeight == 0 ? 20 : clientHeight;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[object HTMLOptGroupElement]";
    }
}
