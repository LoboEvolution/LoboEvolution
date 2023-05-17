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

import org.loboevolution.html.dom.HTMLDialogElement;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLDialogElementImpl class.</p>
 */
public class HTMLDialogElementImpl extends  HTMLElementImpl implements HTMLDialogElement {

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLDialogElementImpl(String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_NONE);
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void setOpen(boolean open) {

    }

    @Override
    public String getReturnValue() {
        return null;
    }

    @Override
    public void setReturnValue(String returnValue) {

    }

    @Override
    public void close(String returnValue) {

    }

    @Override
    public void close() {

    }

    @Override
    public void show() {

    }

    @Override
    public void showModal() {

    }
}
