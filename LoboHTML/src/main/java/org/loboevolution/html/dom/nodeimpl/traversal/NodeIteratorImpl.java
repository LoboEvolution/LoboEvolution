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

package org.loboevolution.html.dom.nodeimpl.traversal;

import org.htmlunit.cssparser.dom.DOMException;
import lombok.Getter;
import lombok.Setter;
import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.traversal.NodeFilter;
import org.loboevolution.traversal.NodeIterator;

/**
 * <p>TreeWalkerImpl class.</p>
 */
@Getter
@Setter
public class NodeIteratorImpl extends AbstractList<Node> implements NodeIterator {

    private final Node root;

    private Node currentNode;

    private Document document;

    private final NodeFilter filter;

    private boolean fForward = true;

    private boolean fDetach = false;

    private final int whatToShow;

    public NodeIteratorImpl(final Node root, final int whatToShow, final NodeFilter filter) {
        this.root = root;
        this.whatToShow = whatToShow;
        this.filter = filter;
    }

    @Override
    public void detach() {
        fDetach = true;
    }

    @Override
    public Node nextNode() {

        if (fDetach) {
            throw new DOMException(
                    DOMException.INVALID_STATE_ERR, "");
        }

        if (root == null) return null;
        Node nextNode = currentNode;
        boolean accepted = false;

        while (!accepted) {
            if (!fForward && nextNode != null) {
                nextNode = currentNode;
            } else {
                if (nextNode != null
                        && nextNode.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
                    nextNode = nextNode(nextNode, false);
                } else {
                    nextNode = nextNode(nextNode, true);
                }
            }

            fForward = true;
            if (nextNode == null) return null;
            accepted = acceptNode(nextNode);
            if (accepted) {
                currentNode = nextNode;
                return currentNode;
            }

        }
        return null;

    }

    @Override
    public Node previousNode() {
        if(fDetach) {
            throw new DOMException(
                    DOMException.INVALID_STATE_ERR, "");
        }

        if (root == null || currentNode == null) return null;

        Node previousNode = currentNode;
        boolean accepted = false;

        while (!accepted) {

            if (fForward && previousNode != null) {
                //repeat last node.
                previousNode = currentNode;
            } else {
                previousNode = previousNode(previousNode);
            }

            fForward = false;
            if (previousNode == null) return null;
            accepted = acceptNode(previousNode);
            if (accepted) {
                currentNode = previousNode;
                return currentNode;
            }
        }
        return null;
    }

    private Node nextNode(final Node node, final boolean visitChildren) {

        if (node == null) return root;

        Node result;
        if (visitChildren) {
            if (node.hasChildNodes()) {
                result = node.getFirstChild();
                return result;
            }
        }

        if (node == root) {
            return null;
        }

        result = node.getNextSibling();
        if (result != null) return result;


        Node parent = node.getParentNode();
        while (parent != null && parent != root) {
            result = parent.getNextSibling();
            if (result != null) {
                return result;
            } else {
                parent = parent.getParentNode();
            }

        }
        return null;
    }

    private Node previousNode(final Node node) {
        Node result;
        if (node == root) return null;

        result = node.getPreviousSibling();
        if (result == null) {
            result = node.getParentNode();
            return result;
        }

        if (result.hasChildNodes() &&
                !(result.getNodeType() == Node.ENTITY_REFERENCE_NODE)) {
            while (result.hasChildNodes()) {
                result = result.getLastChild();
            }
        }

        return result;
    }

    boolean acceptNode(final Node node) {

        if (filter == null) {
            return ( whatToShow & (1 << node.getNodeType()-1)) != 0 ;
        } else {
            return ((whatToShow & (1 << node.getNodeType()-1)) != 0 )
                    && filter.acceptNode(node) == NodeFilter.FILTER_ACCEPT;
        }
    }
}
