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
import org.loboevolution.html.dom.DOMLocator;
import org.loboevolution.html.node.Node;

/**
 * Implementation of DOMLocator
 */
@Data
public class DOMLocatorImpl implements DOMLocator {

    private int lineNumber = -1;
    private int columnNumber = -1;
    private int byteOffset = -1;
    private int utf16Offset = -1;
    private Node relatedNode;
    private String uri;

    public DOMLocatorImpl() {
    }

    public DOMLocatorImpl(Node relatedNode) {
        this.relatedNode = relatedNode;
    }

    public DOMLocatorImpl(Node relatedNode, String uri) {
        this.relatedNode = relatedNode;
        this.uri = uri;
    }

    public DOMLocatorImpl(int lineNumber,
                          int columnNumber,
                          int byteOffset,
                          int utf16Offset,
                          Node relatedNode,
                          String uri) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.byteOffset = byteOffset;
        this.utf16Offset = utf16Offset;
        this.relatedNode = relatedNode;
        this.uri = uri;
    }
}
