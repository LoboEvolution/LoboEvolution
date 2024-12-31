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

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.html.dom.HTMLDialogElement;
import org.loboevolution.html.js.events.EventImpl;
import org.loboevolution.html.renderstate.DialogRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLDialogElementImpl class.</p>
 */
@Getter
@Setter
public class HTMLDialogElementImpl extends HTMLElementImpl implements HTMLDialogElement {

    private boolean modal;
    private String returnValue;

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLDialogElementImpl(final String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(final RenderState prevRenderState) {
        return new DialogRenderState(prevRenderState, this);
    }

    @Override
    public boolean getOpen() {
        return hasAttribute("open");
    }

    @Override
    public void setOpen(final boolean newValue) {
        if (newValue) {
            setAttribute("open", "");
        }
        else {
            removeAttribute("open");
            modal = false;
        }
    }

    @Override
    public void close(final String returnValue) {
        if (getOpen()) {
            setReturnValue(returnValue);
            modal = false;
            close();
        }
    }

    @Override
    public void close() {
        if (getOpen()) {
            removeAttribute("open");
            modal = false;
            informNodeLoaded();
            final EventImpl evt = new EventImpl();
            evt.initEvent("close");
            evt.setTarget(this);
            dispatchEvent(evt);
        }
    }

    @Override
    public void show() {
        if (!getOpen()) {
            setOpen(true);
            informNodeLoaded();
        }
    }

    @Override
    public void showModal() {
        if (!getOpen()) {
            setOpen(true);
            informNodeLoaded();
            modal = true;
        }
    }

    @Override
    public String toString() {
        return "[object HTMLDialogElement]";
    }
}