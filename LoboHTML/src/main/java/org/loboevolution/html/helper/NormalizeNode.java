package org.loboevolution.html.helper;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.DOMErrorHandler;
import org.loboevolution.html.dom.domimpl.DOMErrorImpl;
import org.loboevolution.html.dom.nodeimpl.internal.NodeInternal;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>NormalizeNode class.</p>
 */
public class NormalizeNode {

    public void splitCDataSections(Node node, DOMErrorHandler handler, boolean splitCDATA) {

        if (node == null) return;

        Node child = node.getFirstChild();
        while (child != null) {
            Node next = child.getNextSibling();
            if (child.getNodeType() == Node.CDATA_SECTION_NODE) {
                CDATASection cdata = (CDATASection) child;
                String value = cdata.getData();
                int idx = value.indexOf("]]>");
                if (idx != -1) {
                    if (splitCDATA) {
                        String left = value.substring(0, idx + 2);
                        String right = value.substring(idx + 2);
                        Document doc = node.getOwnerDocument();
                        CDATASection leftNode = doc.createCDATASection(left);
                        CDATASection rightNode = doc.createCDATASection(right);
                        node.replaceChild(leftNode, cdata);
                        node.insertBefore(rightNode, leftNode);
                        handler.handleError(new DOMErrorImpl(
                                DOMError.SEVERITY_WARNING,
                                "cdata-sections-splitted",
                                "cdata-sections-splitted",
                                leftNode,
                                null
                        ));
                        child = rightNode;
                        continue;
                    } else {
                        handler.handleError(new DOMErrorImpl(
                                DOMError.SEVERITY_ERROR,
                                "wf-invalid-character",
                                "CDATA contains illegal sequence ]]>",
                                cdata,
                                null
                        ));
                    }
                }
            }

            splitCDataSections(child, handler, splitCDATA);
            child = next;
        }
    }

    public void normalizeTree(Node node) {

        if (node == null) return;

        // Merge adjacent text nodes (REAL DOM behavior)
        if (node instanceof NodeInternal) {
            mergeAdjacentTextNodes((NodeInternal) node);
        }

        // Recurse
        NodeList children = node.getChildNodes();
        if (children != null) {
            for (int i = 0; i < children.getLength(); i++) {
                normalizeTree(children.item(i));
            }
        }
    }

    public void mergeAdjacentTextNodes(NodeInternal parent) {
        Node current = parent.getFirstChild();
        while (current != null) {
            Node next = current.getNextSibling();
            if (current.getNodeType() == Node.TEXT_NODE) {
                StringBuilder merged =
                        new StringBuilder(current.getNodeValue());
                while (next != null &&
                        next.getNodeType() == Node.TEXT_NODE) {
                    merged.append(next.getNodeValue());
                    Node toRemove = next;
                    next = next.getNextSibling();
                    parent.removeChildInternal(toRemove);
                }
                current.setNodeValue(merged.toString());
            }

            if (current.hasChildNodes()) {
                mergeAdjacentTextNodes((NodeInternal) current);
            }
            current = next;
        }
    }

    public void validateWellFormed(Node node, DOMErrorHandler handler, boolean isXml) {
        if (node == null) return;

        if (node instanceof Element) {

            if (!Strings.isValidTag(node.getNodeName(), isXml)) {
                handler.handleError(new DOMErrorImpl(
                        DOMError.SEVERITY_WARNING,
                        "wf-invalid-character-in-node-name"
                ));
            }

            NamedNodeMap attrs = node.getAttributes();
            for (int i = 0; i < attrs.getLength(); i++) {
                Attr attr = (Attr) attrs.item(i);
                if (!Strings.isValidTag(attr.getName(), isXml)) {
                    handler.handleError(new DOMErrorImpl(
                            DOMError.SEVERITY_WARNING,
                            "wf-invalid-character-in-node-name",
                                    "Invalid XML character",
                            attr, null)
                    );
                }
            }
        }

        NodeList children = node.getChildNodes();
        if (children != null) {
            for (int i = 0; i < children.getLength(); i++) {
                validateWellFormed(children.item(i), handler, isXml);
            }
        }
    }

    public void validateNamespaces(Node node, DOMErrorHandler handler, boolean isXml) {

        if (node == null) {
            return;
        }

        // validate attributes
        if (node instanceof Element element) {

            NamedNodeMap attrs = element.getAttributes();
            for (final Node nodeAttr : Nodes.iterable(attrs)) {
                final Attr attr = (Attr) nodeAttr;
                String value = attr.getValue();
                String name = attr.getName();
                if (!"xmlns".equals(name) && !Strings.isValidTag(value, isXml)) {
                    handler.handleError(new DOMErrorImpl(
                            DOMError.SEVERITY_ERROR,
                            "wf-invalid-character-in-node-name",
                            "wf-invalid-character-in-node-name",
                            attr, null)
                    );
                }
            }
        }

        NodeList children = node.getChildNodes();
        if (children != null) {
            for (int i = 0; i < children.getLength(); i++) {
                validateNamespaces(children.item(i), handler, isXml);
            }
        }
    }

    public void canonicalForm(final Node node,
                              final DOMErrorHandler handler) {

        if (node == null) {
            return;
        }

        switch (node.getNodeType()) {

            case Node.CDATA_SECTION_NODE: {
                final CDATASection cdata = (CDATASection) node;
                String data = normalizeLineEndings(cdata.getData());
                if (!Strings.isXMLIdentifier(data)) {
                    handler.handleError(new DOMErrorImpl(
                            DOMError.SEVERITY_ERROR,
                            "wf-invalid-character",
                            "Invalid XML character inside CDATA section",
                            node, null)
                    );
                }

                final Node parent = node.getParentNode();
                if (parent != null) {
                    final Text text = node.getOwnerDocument().createTextNode(data);
                    parent.replaceChild(text, node);
                }

                break;
            }

            case Node.ENTITY_REFERENCE_NODE: {
                expandEntityReference(node);
                break;
            }

            case Node.TEXT_NODE: {
                final Text textNode = (Text) node;
                textNode.setData(normalizeLineEndings(textNode.getData()));
                break;
            }

            case Node.ELEMENT_NODE: {
                final Element element = (Element) node;
                String namespaceURI = element.getNamespaceURI();

                // Check if this is a namespace-aware document and element lacks namespace
                // According to DOM Level 3 Core spec, when canonical-form is true and the
                // document uses namespaces, all elements must have proper namespace declarations
                if (namespaceURI == null || namespaceURI.isEmpty()) {
                    Document doc = node.getOwnerDocument();
                    Element docElement = doc != null ? doc.getDocumentElement() : null;
                    String docNs = docElement != null ? docElement.getNamespaceURI() : null;

                    if (docNs != null && !docNs.isEmpty()) {
                        // Document is namespace-aware but element has no namespace - this is an error
                        handler.handleError(new DOMErrorImpl(
                                DOMError.SEVERITY_ERROR,
                                "wf-invalid-character-in-node-name",
                                "Element created without namespace in a namespace-aware document",
                                node, null)
                        );
                    }
                }
                expandEntityReferences(element);
                break;
            }
            default:
                break;
        }

        Node child = node.getFirstChild();

        while (child != null) {

            final Node next = child.getNextSibling();
            canonicalForm(child, handler);
            child = next;
        }

        node.normalize();
    }

    private void expandEntityReferences(final Element element) {
        Node child = element.getFirstChild();
        while (child != null) {
            Node next = child.getNextSibling();
            if (child.getNodeType() == Node.ENTITY_REFERENCE_NODE) {

                // Get the entity reference content
                // Use a direct approach without triggering lazy expansion side effects
                if (child instanceof NodeInternal nodeInternal) {
                    // Access children directly from the internal list
                    NodeListImpl childList = (NodeListImpl) nodeInternal.getChildNodes();
                    for (int i = 0; i < childList.getLength(); i++) {
                        element.insertBefore(childList.item(i).cloneNode(true), child);
                    }
                }

                element.removeChild(child);
            }
            child = next;
        }
    }


    private String normalizeLineEndings(final String value) {

        if (value == null) {
            return null;
        }

        return value
                .replace("\r\n", "\n")
                .replace("\r", "\n");
    }

    public void removeNamespaceDeclarations(Node node) {

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            NamedNodeMap attributes = element.getAttributes();
            List<String> toRemove = new ArrayList<>();

            for (int i = 0; i < attributes.getLength(); i++) {
                Attr attr = (Attr) attributes.item(i);
                if (Node.XMLNS_NAMESPACE_URI.equals(attr.getNamespaceURI())) {
                    toRemove.add(attr.getName());
                }
            }

            for (String attrName : toRemove) {
                element.removeAttribute(attrName);
            }
        }

        Node child = node.getFirstChild();
        while (child != null) {
            removeNamespaceDeclarations(child);
            child = child.getNextSibling();
        }
    }

    public void removeComments(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            Node next = child.getNextSibling();
            if (child.getNodeType() == Node.COMMENT_NODE) {
                node.removeChild(child);
            } else {
                removeComments(child);
            }
            child = next;
        }
    }

    public void expandEntityReferences(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            Node next = child.getNextSibling();
            if (child.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
                while (child.hasChildNodes()) {
                    Node moved = child.getFirstChild();
                    ((NodeInternal)child).removeChildInternal(moved);
                    node.insertBefore(moved, child);
                }
                node.removeChild(child);
            } else {
                expandEntityReferences(child);
            }
            child = next;
        }
    }

    public void replaceCDATAWithText(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            Node next = child.getNextSibling();
            if (child.getNodeType() == Node.CDATA_SECTION_NODE) {
                Document doc = (node.getNodeType() == Node.DOCUMENT_NODE)
                        ? (Document) node
                        : node.getOwnerDocument();

                Text text = doc.createTextNode(child.getNodeValue());
                node.replaceChild(text, child);
            } else {
                replaceCDATAWithText(child);
            }
            child = next;
        }
    }

    public void removeElementContentWhitespace(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            Node next = child.getNextSibling();
            if (child.getNodeType() == Node.TEXT_NODE) {
                String value = child.getNodeValue();
                if (value != null) {
                    String collapsed = value.replaceAll("\\s+", " ");
                    child.setNodeValue(collapsed);
                }
            } else {
                removeElementContentWhitespace(child);
            }
            child = next;
        }
    }

    private void expandEntityReference(final Node node) {
        final Node parent = node.getParentNode();
        if (parent == null) {
            return;
        }

        final List<Node> toInsert = new ArrayList<>();
        Node child = node.getFirstChild();
        while (child != null) {
            toInsert.add(child.cloneNode(true));
            child = child.getNextSibling();
        }

        final Node nextSibling = node.getNextSibling();
        for (Node value : toInsert) {
            parent.insertBefore(value, nextSibling);
        }

        parent.removeChild(node);
    }
}
