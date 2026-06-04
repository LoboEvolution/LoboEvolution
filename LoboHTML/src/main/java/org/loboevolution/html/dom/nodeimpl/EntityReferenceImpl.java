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

import org.htmlunit.cssparser.dom.DOMException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.loboevolution.html.dom.nodeimpl.internal.NodeInternal;
import org.loboevolution.html.node.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p> EntityReferenceImpl class.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityReferenceImpl extends NodeInternal implements EntityReference {

    private String publicId;
    private String systemId;
    private String nodeName;
    private String nodeValue;
    private String notationName;
    private boolean expanded = false;

    @Override
    public Node replaceChild(final Node newChild, final Node oldChild) {
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
    }

    @Override
    public Node removeChild(final Node oldChild) {
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
    }

    @Override
    public Node appendChild(final Node newChild) {
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
    }

    @Override
    public Node insertBefore(final Node newChild, final Node refChild) {
        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
    }

    @Override
    public void setNodeValue(final String nodeValue) {
        throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "readonly node");
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public String getLocalName() {
        return this.nodeName;
    }

    @Override
    public Node getFirstChild() {
        ensureExpanded();
        return super.getFirstChild();
    }

    private void ensureExpanded() {
        if (expanded) return;
        expanded = true;

        // Check if we already have children (from createEntityReference or previous expansion)
        if (super.getFirstChild() != null) return;

        DocumentType doctype = getOwnerDocument().getDoctype();
        if (doctype == null) return;

        NamedNodeMap entities = doctype.getEntities();
        if (entities == null) return;


        Node entity = entities.getNamedItem(getNodeName());
        if (entity == null) return;

        Node child = entity.getFirstChild();
        while (child != null) {
            Node clone = child.cloneNode(true);
            super.appendChild(clone);
            child = child.getNextSibling();
        }
    }

    @Override
    public int getNodeType() {
        return Node.ENTITY_REFERENCE_NODE;
    }
}
