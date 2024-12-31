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

package org.loboevolution.html.js.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.http.HttpRequest;
import org.loboevolution.js.Window;
import org.loboevolution.js.xml.XMLHttpRequest;
import org.loboevolution.js.xml.XMLHttpRequestUpload;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.net.Proxy;

/**
 * <p>XMLHttpRequestImpl class.</p>
 */
@Setter
@Getter
@Slf4j
public class XMLHttpRequestImpl extends HttpRequest implements XMLHttpRequest {

    private int timeout = 0;
    private boolean listenerAdded = false;
    private final Scriptable scriptable;
    private final WindowImpl window;

    /**
     * <p>Constructor for XMLHttpRequestImpl.</p>
     */
    public XMLHttpRequestImpl(HTMLDocumentImpl document, Scriptable scriptable, Window window) {
        super(Proxy.NO_PROXY, document.getDocumentURI());
        this.scriptable = scriptable;
        this.window = (WindowImpl) window;
    }

    @Override
    public Function getOnreadystatechange() {
        return getFunction(this, "readystatechange");
    }

    @Override
    public void setOnreadystatechange(final Function readystatechange) {
        synchronized (this) {
            if (readystatechange != null && !this.listenerAdded) {
                addEventListener("readystatechange", readystatechange);
                addReadyStateChangeListener(this::executeReadyStateChange);
                this.listenerAdded = true;
            }
        }
    }

    private void executeReadyStateChange() {
        final Function f = getOnreadystatechange();
        if (f != null) {
            try (Context ctx = Executor.createContext(window.getContextFactory())) {
                f.call(ctx, scriptable, scriptable, new Object[0]);
            } catch (final Exception err) {
                log.error("Error processing ready state change.", err);
            }
        }
    }

    @Override
    public void send() throws Exception {
        this.send(null, timeout);
    }

    @Override
    public void send(Object data) throws Exception {
        this.send(data, timeout);
    }

    @Override
    public void setRequestHeader(String header, String value) {

    }

    @Override
    public boolean getWithCredentials() {
        return false;
    }

    @Override
    public void setWithCredentials(boolean withCredentials) {

    }

    @Override
    public XMLHttpRequestUpload getUpload() {
        return null;
    }

    @Override
    public void overrideMimeType(String mime) {

    }

    @Override
    public String getResponseType() {
        return "";
    }

    @Override
    public void setResponseType(String responseType) {

    }

    @Override
    public Object getResponse() {
        return null;
    }
}
