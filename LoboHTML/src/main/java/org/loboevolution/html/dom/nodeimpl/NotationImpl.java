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

package org.loboevolution.html.dom.nodeimpl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.Node;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotationImpl extends NodeImpl implements Notation {

    private String publicId;
    private String systemId;
    private String nodeName;

    @Override
    public String getLocalName() {
        return this.nodeName;
    }

    @Override
    public int getNodeType() {
        return NOTATION_NODE;
    }

    @Override
    public String getNodeValue() throws DOMException {
        return null;
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
        throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "readonly node");
    }

    @Override
    public short compareDocumentPosition(Node other) {
        if (other == null) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "other is null");
        }

        if (other == this) {
            return 0;
        }

        if (other.getNodeType() == Node.ENTITY_NODE) {
            return DOCUMENT_POSITION_PRECEDING;
        }

        return this.compareDocumentPosition(other);
    }
    @Override
    public String getTextContent() {
        return null;
    }

    @Override
    public void setTextContent(String textContent) {}

    @Override
    public boolean hasAttributes() {
        return false;
    }
}
