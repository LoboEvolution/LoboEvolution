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
package org.loboevolution.html.dom.nodeimpl;

import lombok.*;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.*;
import org.loboevolution.html.dom.nodeimpl.internal.*;
import java.util.Objects;

import static org.htmlunit.cssparser.dom.DOMException.NAMESPACE_ERR;

/**
 * <p>AttrImpl class.</p>
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
public class AttrImpl extends NodeInternal implements Attr {

    private String name;

    private String value;

    private boolean nameId;

    private Node ownerElement;

    private boolean specified;

    /**
     * Constructor that initializes child nodes with the value.
     *
     * @param name the attribute name
     * @param value the attribute value
     * @param nameId whether this is a name id attribute
     * @param ownerElement the owner element
     * @param specified whether this attribute was specified
     */
    public AttrImpl(String name, String value, boolean nameId, Node ownerElement, boolean specified) {
        this.name = name;
        this.value = value;
        this.nameId = nameId;
        this.ownerElement = ownerElement;
        this.specified = specified;
        if (this.value != null && !this.value.isEmpty()) {
            final TextImpl textNode = new TextImpl(this.value);
            textNode.setOwnerDocument(getOwnerDocument());
            textNode.setParentImpl(this);
            this.nodeList.add(textNode);
        }
    }

    @Override
    public Document getOwnerDocument() {
        if (ownerElement != null) {
            return ownerElement.getOwnerDocument();
        }
        return super.getOwnerDocument();
    }

    @Override
    public Node appendChild(Node newChild) {

        if (newChild.getOwnerDocument() != null &&
                newChild.getOwnerDocument() != this.getOwnerDocument()) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
                    "Cannot append node from a different document");
        }

        int type = newChild.getNodeType();
        if (type == Node.ENTITY_REFERENCE_NODE || type == Node.CDATA_SECTION_NODE) {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                    "Invalid child type for Attr");
        }

        if (ownerElement == null && type == Node.TEXT_NODE) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                    "Invalid child type for Attr");
        }

        if (type == Node.TEXT_NODE) {
            nodeList.add(newChild);
            newChild.setParentImpl(this);
            this.setValue(newChild.getNodeValue());
            return newChild;
        }

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Invalid child type for Attr");
    }

    @Override
    public Node insertBefore(final Node newChild, final Node refChild) {
        if (newChild.getOwnerDocument() != this.getOwnerDocument()) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
                    "Cannot append node from a different document");
        }

        int type = newChild.getNodeType();

         if (type == Node.TEXT_NODE) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                    "Invalid child type for Attr");
        }

        if (refChild == null) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                    "Invalid child type for Attr");
        }


        if (type == Node.ENTITY_REFERENCE_NODE || type == Node.CDATA_SECTION_NODE) {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                    "Invalid child type for Attr");
        }

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Applications cannot add children to an Attr node");
    }

    @Override
    public Node removeChild(final Node oldChild) {

        if (oldChild == null) {
            throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild is null");
        }

        if (oldChild.getOwnerDocument() != this.getOwnerDocument()) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
                    "Cannot append node from a different document");
        }

        int type = oldChild.getNodeType();
        if (type == Node.ENTITY_REFERENCE_NODE || type == Node.CDATA_SECTION_NODE || type == Node.DOCUMENT_FRAGMENT_NODE) {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                    "Invalid child type for Attr");
        }

        if (type == Node.TEXT_NODE) {
            return removeChildInternal(oldChild);
        }

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Invalid child type for Attr");
    }

    @Override
    public Node replaceChild(final Node newChild, final Node oldChild) {

        final int idx = this.nodeList.indexOf(oldChild);
        if (idx == -1) {
            throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild not found");
        }

        if (newChild.getOwnerDocument() != this.getOwnerDocument()) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
                    "Cannot append node from a different document");
        }

        int type = newChild.getNodeType();
        if (type != Node.ENTITY_REFERENCE_NODE && type != Node.CDATA_SECTION_NODE) {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                    "Invalid child type for Attr");
        }

        return super.replaceChild(newChild, oldChild);
    }

    @Override
    public void setTextContent(String textContent) {
        this.setValue(textContent == null ? "" : textContent);
    }

    @Override
    public Node getNextSibling() {
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
        if (other == null) {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "other is null");
        }

        if (other.getNodeType() == Node.DOCUMENT_NODE) {
            return DOCUMENT_POSITION_CONTAINS | DOCUMENT_POSITION_PRECEDING;
        }

        if (other.getNodeType() == Node.ELEMENT_NODE) {
            return DOCUMENT_POSITION_CONTAINS | DOCUMENT_POSITION_PRECEDING;
        }

        if (other.getNodeType() == Node.TEXT_NODE) {
            String thisValue = this.value;
            String otherValue = other.getNodeValue();
            if (thisValue != null && thisValue.equals(otherValue)) {
                return DOCUMENT_POSITION_CONTAINED_BY | DOCUMENT_POSITION_FOLLOWING;
            } else {
                return DOCUMENT_POSITION_FOLLOWING;
            }
        }

        short comparison = 0;
        if (other instanceof Attr) {
            final AttrImpl otherImpl = (AttrImpl) other;
            if (otherImpl.getOwnerElement().isSameNode(this.ownerElement)) {
                comparison = DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
            }
            return comparison;
        }
        return super.compareDocumentPosition(other);
    }

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
    public String getNodeValue() {
        return this.value;
    }

    @Override
    public void setPrefix(String prefix) throws DOMException {
        String ns = getNamespaceURI();

        if (prefix != null && ns == null) {
            throw new DOMException(NAMESPACE_ERR, "Prefix with null namespace");
        }

        if ("xmlns".equals(prefix)) {
            if (!XMLNS_NAMESPACE_URI.equals(ns)) {
                throw new DOMException(DOMException.NAMESPACE_ERR, "xmlns prefix mismatch");
            }
        }

        if (XMLNS_NAMESPACE_URI.equals(ns)) {
            if (prefix != null && !"xmlns".equals(prefix)) {
                throw new DOMException(DOMException.NAMESPACE_ERR, "xmlns namespace requires xmlns prefix");
            }
        }

        super.setPrefix(prefix);
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
        return ownerElement != null && (this.nameId || name.equalsIgnoreCase("id"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNodeValue(final String nodeValue) {
        setValue(nodeValue);
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
    public String getBaseURI() {
        return null;
    }

    @Override
    public Node getOwnerElement() {
        return this.ownerElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNodeType() {
        return Node.ATTRIBUTE_NODE;
    }

    @Override
    public String toString() {
        return "[object Attr]";
    }
}
