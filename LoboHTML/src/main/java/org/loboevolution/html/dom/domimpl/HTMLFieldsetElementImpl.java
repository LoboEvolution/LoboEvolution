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

    /**
     * <p>isDisabled.</p>
     *
     * @return a boolean.
     */
    @Override
    public boolean isDisabled() {
        return false;
    }

    /**
     * <p>setDisabled.</p>
     *
     * @param disabled a boolean.
     */
    @Override
    public void setDisabled(boolean disabled) {

    }

    /**
     * <p>getElements.</p>
     *
     * @return a {@link HTMLCollection} object.
     */
    @Override
    public HTMLCollection getElements() {
        return null;
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

    /**
     * <p>getName.</p>
     *
     * @return a {@link String} object.
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * <p>setName.</p>
     *
     * @param name a {@link String} object.
     */
    @Override
    public void setName(String name) {

    }

    /**
     * <p>getType.</p>
     *
     * @return a {@link String} object.
     */
    @Override
    public String getType() {
        return null;
    }

    /**
     * Returns the error message that would be displayed if the user submits the form, or an empty string if no error message. It also triggers the standard error message, such as "this is a required field". The result is that the user sees validation messages without actually submitting.
     *
     * @return a {@link String} object.
     */
    @Override
    public String getValidationMessage() {
        return null;
    }

    /**
     * Returns a  ValidityState object that represents the validity states of an element.
     *
     * @return a {@link ValidityState} object.
     */
    @Override
    public ValidityState getValidity() {
        return null;
    }

    /**
     * Returns whether an element will successfully validate based on forms validation rules and constraints.
     *
     * @return a boolean.
     */
    @Override
    public boolean isWillValidate() {
        return false;
    }

    /**
     * Returns whether a form will validate when it is submitted, without having to submit it.
     *
     * @return a boolean.
     */
    @Override
    public boolean checkValidity() {
        return false;
    }

    /**
     * <p>reportValidity.</p>
     *
     * @return a boolean.
     */
    @Override
    public boolean reportValidity() {
        return false;
    }

    /**
     * Sets a custom error message that is displayed when a form is submitted.
     *
     * @param error Sets a custom error message that is displayed when a form is submitted.
     */
    @Override
    public void setCustomValidity(String error) {

    }
}
