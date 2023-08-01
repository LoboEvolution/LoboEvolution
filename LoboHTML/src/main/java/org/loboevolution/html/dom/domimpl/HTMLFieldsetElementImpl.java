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