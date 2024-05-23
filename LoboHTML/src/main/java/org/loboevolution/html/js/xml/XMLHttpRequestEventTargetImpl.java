/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.html.js.xml;

import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.js.xml.XMLHttpRequestEventTarget;
import org.mozilla.javascript.Function;

/**
 * <p>XMLHttpRequestEventTargetImpl class.</p>
 */
public class XMLHttpRequestEventTargetImpl extends EventTargetImpl implements XMLHttpRequestEventTarget {

    public XMLHttpRequestEventTargetImpl(NodeImpl target) {
        super(target);
    }

    @Override
    public Function getOnloadstart() {
        return getFunction(this, "loadstart");
    }

    @Override
    public void setOnloadstart(Function onloadstart) {
        addEventListener("loadstart", onloadstart);
    }

    @Override
    public Function getOnprogress() {
        return getFunction(this, "progress");
    }

    @Override
    public void setOnprogress(Function onprogress) {
        addEventListener("progress", onprogress);
    }

    @Override
    public Function getOnabort() {
        return getFunction(this, "abort");
    }

    @Override
    public void setOnabort(Function onabort) {
        addEventListener("abort", onabort);
    }

    @Override
    public Function getOnerror() {
        return getFunction(this, "error");
    }

    @Override
    public void setOnerror(Function onerror) {
        addEventListener("error", onerror);
    }

    @Override
    public Function getOnload() {
        return getFunction(this, "load");
    }

    @Override
    public void setOnload(Function onload) {
        addEventListener("load", onload);
    }

    @Override
    public Function getOntimeout() {
        return getFunction(this, "timeout");
    }

    @Override
    public void setOntimeout(Function ontimeout) {
        addEventListener("timeout", ontimeout);
    }

    @Override
    public Function getOnloadend() {
        return getFunction(this, "loadend");
    }

    @Override
    public void setOnloadend(Function onloadend) {
        addEventListener("loadend", onloadend);
    }
}
