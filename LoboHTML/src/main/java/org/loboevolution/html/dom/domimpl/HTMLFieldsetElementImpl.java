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

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLFieldSetElement;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.html.renderstate.FieldsetRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLFieldsetElementImpl class.</p>
 */
public class HTMLFieldsetElementImpl extends HTMLElementImpl implements HTMLFieldSetElement {

    /**
     * <p>Constructor for HTMLFieldsetElementImpl.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public HTMLFieldsetElementImpl(final String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new FieldsetRenderState(prevRenderState, this);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDisabled() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void setDisabled(boolean disabled) {

    }

    /** {@inheritDoc} */
    @Override
    public HTMLCollection getElements() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public HTMLFormElement getForm() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void setName(String name) {

    }

    /** {@inheritDoc} */
    @Override
    public String getType() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getValidationMessage() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ValidityState getValidity() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isWillValidate() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean checkValidity() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean reportValidity() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void setCustomValidity(String error) {

    }

    /** {@inheritDoc} */
    @Override
    public Integer getClientWidth() {
        int clientWidth = super.getClientWidth();
        clientWidth = clientWidth == 0 ? getDocumentNode().getDocumentElement().getClientWidth() : clientWidth;

        if (getRenderState() != null && getRenderState().getPaddingInsets() != null) {
            clientWidth -= getRenderState().getPaddingInsets().getRight();
            clientWidth -= getRenderState().getPaddingInsets().getLeft();
        }

        return clientWidth;
    }

    /** {@inheritDoc} */
    @Override
    public Integer getOffsetWidth() {
        return getClientWidth();
    }
}