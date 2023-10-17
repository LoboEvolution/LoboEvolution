/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import lombok.*;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.TypeInfo;

import java.util.Objects;

/**
 * <p>AttrImpl class.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class AttrImpl extends NodeImpl implements Attr {

    private String name;

    private String value;

    private boolean nameId;

    private Node ownerElement;

    private boolean specified;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return Strings.isNotBlank(getPrefix()) ? getPrefix() + ":" + this.name : this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNodeName() {
        return Strings.isNotBlank(getPrefix()) ? getPrefix() + ":" + this.name : this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNodeType() {
        return Node.ATTRIBUTE_NODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNodeValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextContent() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeInfo getSchemaTypeInfo() {
        return new AttributeTypeInfo(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isId() {
        return ownerElement != null && (this.nameId || name.toLowerCase().contains("id"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNodeValue(final String nodeValue) {
        this.value = nodeValue;
    }

    @Override
    public Node insertBefore(final Node newChild, final Node refChild) {
        if (newChild instanceof Attr) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknwon node implementation");
        } else {
            return super.insertBefore(newChild, refChild);
        }
    }

    @Override
    public Node removeChild(final Node oldChild) {
        if (oldChild instanceof Attr) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknwon node implementation");
        } else {
            return super.removeChild(oldChild);
        }
    }

    @Override
    public Node replaceChild(final Node newChild, final Node oldChild) {
        if (newChild instanceof Attr) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknwon node implementation");
        } else {
            return super.replaceChild(newChild, oldChild);
        }
    }

    @Override
    public Node getNextSibling() {
        if (ownerElement != null) {
            final NamedNodeMap attributes = ownerElement.getAttributes();
            boolean next = false;
            for (final Node nodeAttr : Nodes.iterable(attributes)) {
                final Attr attr = (Attr) nodeAttr;
                if (next) {
                    return attr;
                }

                if (Objects.equals(attr, this)) {
                    next = true;
                }
            }
        }
        return null;
    }

    @Override
    public Node getPreviousSibling() {
        if (ownerElement != null) {
            final NamedNodeMap attributes = ownerElement.getAttributes();
            Attr previus = null;
            for (final Node nodeAttr : Nodes.iterable(attributes)) {
                final Attr attr = (Attr) nodeAttr;

                if (Objects.equals(attr, this) && !Objects.equals(previus, this)) {
                    return previus;
                }

                previus = attr;
            }
        }
        return null;
    }

    @Override
    public short compareDocumentPosition(final Node other) {
        short comparison = super.compareDocumentPosition(other);
        if (other instanceof Attr) {
            final AttrImpl otherImpl = (AttrImpl) other;
            if (otherImpl.getOwnerElement().isSameNode(this.ownerElement)) {
                comparison += Node.DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
            }
        }
        return comparison;
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    public void setOwnerElement(final Node ownerElement) {
        this.ownerElement = ownerElement;
        setParentImpl(ownerElement);
    }

    @Override
    public String toString() {
        return "[object Attr]";
    }
}
