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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.*;

import java.util.Objects;

/**
 * <p>EntityImpl class.</p>
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityImpl extends NodeImpl implements Entity {

    private String publicId;
    private String systemId;
    private String nodeName;
    private String nodeValue;
    private String notationName;
    private String localName;
    private boolean hasPI = false;

    @Override
    public void setNodeValue(final String nodeValue) {
        throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "readonly node");
    }

    @Override
    public void setTextContent(final String nodeValue) {
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
    }

    @Override
    public Node replaceChild(final Node newChild, final Node oldChild) {
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
    }

    @Override
    public Node removeChild(Node oldChild) {
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
    }

    @Override
    public Node appendChild(Node newChild) {
         nodeList.add(newChild);

        if (!this.notificationsSuspended) {
            informStructureInvalid();
        }

        return newChild;
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public short compareDocumentPosition(Node other) {
        if (other == null) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "other is null");
        }

        if (other == this) {
            return 0;
        }

        if (!Objects.equals(getOwnerDocument(), other.getOwnerDocument())) {
            return DOCUMENT_POSITION_DISCONNECTED | DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
        }

        if (other.getNodeType() == Node.NOTATION_NODE) {
            return DOCUMENT_POSITION_FOLLOWING;
        }

        if (other.getNodeType() == Node.ENTITY_NODE) {
            DocumentType docType = getOwnerDocument().getDoctype();
            if (docType != null) {
                NamedNodeMap entities = docType.getEntities();
                int indexThis = -1, indexOther = -1;
                for (int i = 0; i < entities.getLength(); i++) {
                    Node n = entities.item(i);
                    if (n == this) indexThis = i;
                    if (n == other) indexOther = i;
                }
                if (indexThis != -1 && indexOther != -1) {
                    short result = DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
                    if (indexThis < indexOther) {
                        result |= DOCUMENT_POSITION_PRECEDING; // 2
                    } else {
                        result |= DOCUMENT_POSITION_FOLLOWING; // 4
                    }
                    return result;
                }
            }
            return DOCUMENT_POSITION_DISCONNECTED | DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
        }

        return DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
    }

    @Override
    public int getNodeType() {
        return Node.ENTITY_NODE;
    }
}