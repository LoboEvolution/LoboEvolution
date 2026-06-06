/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import lombok.Data;
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.DOMLocator;
import org.loboevolution.html.node.Node;

/**
 * This is a utility implementation of EventListener
 * that captures all events and provides access
 * to lists of all events by mode
 */
@Data
public class DOMErrorImpl implements DOMError {

    private short severity;
    private String message;
    private String type;
    private Object relatedData;
    private Object relatedException;
    private DOMLocator location;

    public DOMErrorImpl(short severity, String message) {
        this(severity, message, null, null, null);
    }

    public DOMErrorImpl(short severity,
                        String message,
                        String type,
                        Object relatedData,
                        Object relatedException) {
        this.severity = severity;
        this.message = message;
        this.type = type;
        this.relatedData = relatedData;
        this.relatedException = relatedException;
        this.location = new DOMLocatorImpl((Node) relatedData);
    }
}
